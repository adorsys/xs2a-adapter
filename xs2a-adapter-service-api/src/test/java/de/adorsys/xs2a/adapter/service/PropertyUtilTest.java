package de.adorsys.xs2a.adapter.service;


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
