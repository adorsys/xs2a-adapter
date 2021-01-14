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

package de.adorsys.xs2a.adapter.api.config;

import de.adorsys.xs2a.adapter.api.PropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class AdapterConfig {
    private static final Logger logger = LoggerFactory.getLogger(AdapterConfig.class);
    private static final String ADAPTER_CONFIG_FILE_PATH_PROPERTY = "adapter.config.file.path";
    private static final String DEFAULT_ADAPTER_CONFIG_FILE = "adapter.config.properties";
    private static final Properties properties = new Properties();

    static {
        reload();
    }

    public static void reload() {
        try {
            properties.clear();
            InputStream configFileAsStream = getConfigFileAsStream();
            properties.load(configFileAsStream);
            logger.debug("Adapter configuration file is loaded");
        } catch (IOException e) {
            throw new IllegalStateException("Can't load adapter config file");
        }
    }

    private AdapterConfig() {
    }

    public static void setConfigFile(String file) {
        System.setProperty(ADAPTER_CONFIG_FILE_PATH_PROPERTY, file);
        reload();
    }

    public static String readProperty(String property) {
        return properties.getProperty(property);
    }

    public static String readProperty(String property, String defaultValue) {
        return properties.getProperty(property, defaultValue);
    }

    private static InputStream getConfigFileAsStream() throws FileNotFoundException {
        String filePath = PropertyUtil.readProperty(ADAPTER_CONFIG_FILE_PATH_PROPERTY);

        InputStream inputStream;

        if (filePath == null || filePath.isEmpty()) {
            inputStream = getResourceAsStream(DEFAULT_ADAPTER_CONFIG_FILE);
            logger.debug("Default configuration file [{}] will be used", DEFAULT_ADAPTER_CONFIG_FILE);
        } else {
            inputStream = getFileAsStream(filePath);
            logger.debug("[{}] configuration file will be used", filePath);
        }
        return inputStream;
    }

    private static InputStream getResourceAsStream(String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
    }

    private static InputStream getFileAsStream(String filePath) throws FileNotFoundException {
        return new FileInputStream(filePath);
    }
}
