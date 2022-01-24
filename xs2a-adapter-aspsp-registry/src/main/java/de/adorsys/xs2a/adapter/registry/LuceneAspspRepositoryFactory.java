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

package de.adorsys.xs2a.adapter.registry;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import de.adorsys.xs2a.adapter.api.PropertyUtil;
import de.adorsys.xs2a.adapter.api.exception.Xs2aAdapterException;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.registry.exception.RegistryIOException;
import de.adorsys.xs2a.adapter.registry.mapper.AspspMapper;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.mapstruct.factory.Mappers;

import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class LuceneAspspRepositoryFactory {
    private static final String LUCENE_DIR_PATH_PROPERTY = "csv.aspsp.adapter.lucene.dir.path";
    private static final String CSV_ASPSP_ADAPTER_CONFIG_FILE_PATH_PROPERTY = "csv.aspsp.adapter.config.file.path";
    private static final String DEFAULT_LUCENE_DIR_PATH = "lucene";
    private static final String DEFAULT_CSV_ASPSP_ADAPTER_CONFIG_FILE = "aspsp-adapter-config.csv";

    private final AspspMapper aspspMapper = Mappers.getMapper(AspspMapper.class);

    public LuceneAspspRepository newLuceneAspspRepository() {
        try {
            return newLuceneAspspRepositoryInternal();
        } catch (IOException e) {
            throw new RegistryIOException(e);
        }
    }

    private LuceneAspspRepository newLuceneAspspRepositoryInternal() throws IOException {
        String luceneDirPath = PropertyUtil.readProperty(LUCENE_DIR_PATH_PROPERTY, DEFAULT_LUCENE_DIR_PATH);
        Directory directory = FSDirectory.open(Paths.get(luceneDirPath, "index"));
        LuceneAspspRepository luceneAspspRepository = new LuceneAspspRepository(directory);
        byte[] csv = getCsvFileAsByteArray();

        MessageDigest messageDigest = getMessageDigest();
        String computedDigest = new BigInteger(1, messageDigest.digest(csv)).toString(16);

        Path digestPath = Paths.get(luceneDirPath, "digest.sha256");
        boolean changed;
        if (Files.exists(digestPath)) {
            String storedDigest = new String(Files.readAllBytes(digestPath));
            changed = !computedDigest.equals(storedDigest);
        } else {
            changed = true;
        }

        if (changed) {
            for (String f : directory.listAll()) {
                directory.deleteFile(f);
            }
            List<Aspsp> aspsps = readAllRecords(csv);
            luceneAspspRepository.saveAll(aspsps);
            Files.write(digestPath, computedDigest.getBytes());
        }
        return luceneAspspRepository;
    }

    @SuppressWarnings("java:S4790") // hashing is not used in security context
    private MessageDigest getMessageDigest() {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new Xs2aAdapterException(e);
        }
        return messageDigest;
    }

    private byte[] getCsvFileAsByteArray() {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[8192];
        try (InputStream is = getCsvFileAsStream()) {
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            return buffer.toByteArray();
        } catch (IOException e) {
            throw new RegistryIOException(e);
        }
    }

    private InputStream getCsvFileAsStream() {
        String csvConfigFileProperty = PropertyUtil.readProperty(CSV_ASPSP_ADAPTER_CONFIG_FILE_PATH_PROPERTY);

        InputStream inputStream;

        if (csvConfigFileProperty.isEmpty()) {
            inputStream = getResourceAsStream(DEFAULT_CSV_ASPSP_ADAPTER_CONFIG_FILE);
        } else {
            inputStream = getFileAsStream(csvConfigFileProperty);
        }
        return inputStream;
    }

    private InputStream getResourceAsStream(String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
    }

    private InputStream getFileAsStream(String filePath) {
        try {
            return new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            throw new RegistryIOException(e);
        }
    }

    private List<Aspsp> readAllRecords(byte[] csv) {
        ObjectReader objectReader = new CsvMapper()
            .readerWithTypedSchemaFor(AspspCsvRecord.class)
            .withHandler(new DeserializationProblemHandler() {
                @Override
                public Object handleWeirdStringValue(DeserializationContext ctxt, Class<?> targetType, String valueToConvert, String failureMsg) {
                    if (targetType.isEnum()) {
                        return Enum.valueOf((Class<Enum>) targetType, valueToConvert.trim().toUpperCase());
                    }

                    return DeserializationProblemHandler.NOT_HANDLED;
                }
            });

        List<AspspCsvRecord> aspsps;
        try {
            aspsps = objectReader
                .<AspspCsvRecord>readValues(csv)
                .readAll();
        } catch (IOException e) {
            throw new RegistryIOException(e);
        }

        return aspspMapper.toAspsps(aspsps);
    }
}
