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
