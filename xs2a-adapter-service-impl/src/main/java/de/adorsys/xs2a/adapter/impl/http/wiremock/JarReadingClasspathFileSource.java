/*
 * Copyright 2018-2022 adorsys GmbH & Co KG
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version. This program is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see https://www.gnu.org/licenses/.
 *
 * This project is also available under a separate commercial license. You can
 * contact us at psd2@adorsys.com.
 */

package de.adorsys.xs2a.adapter.impl.http.wiremock;

import com.github.tomakehurst.wiremock.common.BinaryFile;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.common.TextFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static com.github.tomakehurst.wiremock.common.Exceptions.throwUnchecked;
import static com.google.common.collect.Lists.newArrayList;

/**
 * @deprecated Will be deleted with https://jira.adorsys.de/browse/XS2AAD-624.
 */
@Deprecated
class JarReadingClasspathFileSource implements FileSource {

    private final String path;
    private URI pathUri;
    private ZipFile warFile;
    private String jarFileName;
    private File rootDirectory;

    JarReadingClasspathFileSource(String path) {
        this.path = path;
        try {
            pathUri = new JarClassPathResource(path).getURI();
            if (Arrays.asList("jar", "war", "ear", "zip").contains(pathUri.getScheme())) {
                String[] pathElements = pathUri.getSchemeSpecificPart().split("!", 3);
                String warFileUri = pathElements[0];
                String warFilePath = warFileUri.replace("file:", "");
                warFile = new ZipFile(new File(warFilePath));
                jarFileName = pathElements[1].substring(1);
            } else if (pathUri.getScheme().equals("file")) {
                rootDirectory = new File(pathUri);
            } else {
                throw new RuntimeException("ClasspathFileSource can't handle paths of type " + pathUri.getScheme());
            }
        } catch (Exception e) {
            throwUnchecked(e);
        }
    }

    @Override
    public BinaryFile getBinaryFileNamed(final String name) {
        if (isFileSystem()) {
            return new BinaryFile(new File(rootDirectory, name).toURI());
        }
        return new BinaryFile(getUri(name));
    }

    private boolean isFileSystem() {
        return rootDirectory != null;
    }


    @Override
    public TextFile getTextFileNamed(String name) {
        if (isFileSystem()) {
            return new TextFile(new File(rootDirectory, name).toURI());
        }
        return new TextFile(getUri(name));
    }

    private URI getUri(final String name) {
        try {
            return new JarClassPathResource(path + "/" + name).getURI();
        } catch (IOException e) {
            return throwUnchecked(e, URI.class);
        }
    }

    @Override
    public void createIfNecessary() {
    }

    @Override
    public FileSource child(String subDirectoryName) {
        return new JarReadingClasspathFileSource(path + "/" + subDirectoryName);
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public URI getUri() {
        return pathUri;
    }

    @Override
    public List<TextFile> listFilesRecursively() {
        if (isFileSystem()) {
            assertExistsAndIsDirectory();
            List<File> fileList = newArrayList();
            recursivelyAddFilesToList(rootDirectory, fileList);
            return toTextFileList(fileList);
        }
        List<TextFile> files = new ArrayList<>();
        ZipEntry zipEntry = warFile.getEntry(jarFileName);
        try (InputStream zipFileStream = warFile.getInputStream(zipEntry)) {
            final ZipInputStream zipInputStream = new ZipInputStream(zipFileStream);
            ZipEntry nestedZipEntry;
            while ((nestedZipEntry = zipInputStream.getNextEntry()) != null) {
                if (!nestedZipEntry.isDirectory() && nestedZipEntry.getName().startsWith(path)) {
                    files.add(new TextFile(new JarClassPathResource(nestedZipEntry.getName()).getURI()));
                }
            }
        } catch (IOException e) {
//            log.error("Unable to read war file", e);
        }

        return files;
    }

    @Override
    public void writeTextFile(String name, String contents) {
    }

    @Override
    public void writeBinaryFile(String name, byte[] contents) {
    }

    @Override
    public boolean exists() {
        return !isFileSystem() || rootDirectory.exists();
    }

    @Override
    public void deleteFile(String name) {
    }

    private List<TextFile> toTextFileList(List<File> fileList) {
        return fileList.stream()
                   .map(input -> new TextFile(input.toURI()))
                   .collect(Collectors.toList());
    }


    private void recursivelyAddFilesToList(File root, List<File> fileList) {
        File[] files = root.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                recursivelyAddFilesToList(file, fileList);
            } else {
                fileList.add(file);
            }
        }
    }


    private void assertExistsAndIsDirectory() {
        if (rootDirectory.exists() && !rootDirectory.isDirectory()) {
            throw new RuntimeException(rootDirectory + " is not a directory");
        } else if (!rootDirectory.exists()) {
            throw new RuntimeException(rootDirectory + " does not exist");
        }
    }
}
