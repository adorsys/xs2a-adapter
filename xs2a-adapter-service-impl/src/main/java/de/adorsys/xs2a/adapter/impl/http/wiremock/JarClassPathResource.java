/*
 * Copyright 2018-2018 adorsys GmbH & Co KG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.adorsys.xs2a.adapter.impl.http.wiremock;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @deprecated Will be deleted with https://jira.adorsys.de/browse/XS2AAD-624.
 */
@Deprecated
class JarClassPathResource {

    private final String path;
    private ClassLoader classLoader;
    private Class<?> clazz;

    JarClassPathResource(String path) {
        this(path, (ClassLoader) null);
    }

    JarClassPathResource(String path, ClassLoader classLoader) {
        String pathToUse = StringUtils.cleanPath(path);
        if (pathToUse.startsWith("/")) {
            pathToUse = pathToUse.substring(1);
        }

        this.path = pathToUse;
        this.classLoader = classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader();
    }

    JarClassPathResource(String path, Class<?> clazz) {
        this.path = StringUtils.cleanPath(path);
        this.clazz = clazz;
    }

    public String getDescription() {
        StringBuilder builder = new StringBuilder("class path resource [");
        String pathToUse = this.path;
        if (this.clazz != null && !pathToUse.startsWith("/")) {
            builder.append(ClassUtils.classPackageAsResourcePath(this.clazz));
            builder.append('/');
        }

        if (pathToUse.startsWith("/")) {
            pathToUse = pathToUse.substring(1);
        }

        builder.append(pathToUse);
        builder.append(']');
        return builder.toString();
    }

    protected URL resolveURL() {
        if (this.clazz != null) {
            return this.clazz.getResource(this.path);
        } else {
            return this.classLoader != null ? this.classLoader.getResource(this.path) : ClassLoader.getSystemResource(this.path);
        }
    }


    public URL getURL() throws IOException {
        URL url = this.resolveURL();
        if (url == null) {
            throw new FileNotFoundException(this.getDescription() + " cannot be resolved to URL because it does not exist");
        } else {
            return url;
        }
    }

    public URI getURI() throws IOException {
        URL url = this.getURL();

        try {
            return ResourceUtils.toURI(url);
        } catch (URISyntaxException var3) {
            throw new IOException("Invalid URI [" + url + "]", var3);
        }
    }
}
