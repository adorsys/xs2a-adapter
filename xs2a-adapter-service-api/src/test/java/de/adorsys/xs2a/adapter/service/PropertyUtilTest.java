package de.adorsys.xs2a.adapter.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PropertyUtilTest {

    private static final String PROPERTY_NAME = "p1";
    private static final String DEFAULT_VALUE = "42";
    private static final String PROPERTY_VALUE = "value";
    private static final String NOT_EXISTING_PROPERTY = "not-existing-property";

    @BeforeEach
    public void setUp() {
        System.setProperty(PROPERTY_NAME, PROPERTY_VALUE);
    }

    @Test
    public void readProperty() {
        Assertions.assertEquals(PropertyUtil.readProperty(PROPERTY_NAME), PROPERTY_VALUE);
        Assertions.assertEquals(PropertyUtil.readProperty(NOT_EXISTING_PROPERTY), "");
    }

    @Test
    public void readPropertyWithDefaultValue() {
        Assertions.assertEquals(PropertyUtil.readProperty(NOT_EXISTING_PROPERTY, DEFAULT_VALUE), "42");
        Assertions.assertEquals(PropertyUtil.readProperty(PROPERTY_NAME, "42"), PROPERTY_VALUE);
    }
}
