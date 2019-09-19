package de.adorsys.xs2a.adapter.service;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PropertyUtilTest {

    private static final String PROPERTY_NAME = "p1";
    private static final String DEFAULT_VALUE = "42";
    private static final String PROPERTY_VALUE = "value";
    private static final String NOT_EXISTING_PROPERTY = "not-existing-property";

    @Before
    public void setUp() throws Exception {
        System.setProperty(PROPERTY_NAME, PROPERTY_VALUE);
    }

    @Test
    public void readProperty() {
        assertThat(PropertyUtil.readProperty(PROPERTY_NAME), is(PROPERTY_VALUE));
        assertThat(PropertyUtil.readProperty(NOT_EXISTING_PROPERTY), is(""));
    }

    @Test
    public void readPropertyWithDefaultValue() {
        assertThat(PropertyUtil.readProperty(NOT_EXISTING_PROPERTY, DEFAULT_VALUE), is("42"));
        assertThat(PropertyUtil.readProperty(PROPERTY_NAME, "42"), is(PROPERTY_VALUE));
    }
}
