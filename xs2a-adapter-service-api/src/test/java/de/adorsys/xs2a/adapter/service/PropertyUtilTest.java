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

package de.adorsys.xs2a.adapter.service;


import de.adorsys.xs2a.adapter.api.PropertyUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PropertyUtilTest {

    private static final String PROPERTY_NAME = "p1";
    private static final String DEFAULT_VALUE = "42";
    private static final String PROPERTY_VALUE = "value";
    private static final String NOT_EXISTING_PROPERTY = "not-existing-property";

    @BeforeEach
    public void setUp() {
        System.setProperty(PROPERTY_NAME, PROPERTY_VALUE);
    }

    @Test
    void readProperty() {
        Assertions.assertEquals(PROPERTY_VALUE, PropertyUtil.readProperty(PROPERTY_NAME));
        Assertions.assertEquals("", PropertyUtil.readProperty(NOT_EXISTING_PROPERTY));
    }

    @Test
    void readPropertyWithDefaultValue() {
        Assertions.assertEquals("42", PropertyUtil.readProperty(NOT_EXISTING_PROPERTY, DEFAULT_VALUE));
        Assertions.assertEquals(PROPERTY_VALUE, PropertyUtil.readProperty(PROPERTY_NAME, "42"));
    }
}
