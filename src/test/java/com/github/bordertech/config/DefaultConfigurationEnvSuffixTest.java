package com.github.bordertech.config;

import org.junit.Assert;
import org.junit.Test;

import static com.github.bordertech.config.DefaultConfiguration.ENVIRONMENT_PROPERTY;

/**
 * DefaultConfiguration_EnvironmentSuffix_Test - JUnit tests for {@link DefaultConfiguration}.
 */
public class DefaultConfigurationEnvSuffixTest {

	/**
	 * A value for this property should not exist.
	 */
	private static final String TEST_PROPERTY_KEY = "env.suffix.testPropertyKey";
	private static final String DEFAULT_PROPERTY_VALUE = "defaultValue";
	private static final String SUFFIX1_PROPERTY_VALUE = "suffix1Value";
	private static final String SUFFIX2_PROPERTY_VALUE = "suffix2Value";


	@Test
	public void testEnvSuffixProperties() {
		DefaultConfiguration config = new DefaultConfiguration(
			"com/github/bordertech/config/DefaultConfigurationEnvSuffixTest.properties");

		//Test the default value
		assertPropertyEquals(config, TEST_PROPERTY_KEY, DEFAULT_PROPERTY_VALUE);

		//Set the environment suffix 1 and reload
		System.setProperty(ENVIRONMENT_PROPERTY, "suffix1");
		config.refresh();

		//Test for the suffix 1 is set
		assertPropertyEquals(config, TEST_PROPERTY_KEY, SUFFIX1_PROPERTY_VALUE);

		//Set the environment suffix 2 and reload
		System.setProperty(ENVIRONMENT_PROPERTY, "suffix2");
		config.refresh();

		//Test for the suffix 2 is set
		assertPropertyEquals(config, TEST_PROPERTY_KEY, SUFFIX2_PROPERTY_VALUE);

	}

	/**
	 * Asserts that the configuration contains the given key/value.
	 *
	 * @param key the property key
	 * @param expected the expected property value.
	 */
	private void assertPropertyEquals(final DefaultConfiguration config, final String key, final Object expected) {
		Assert.assertEquals("Incorrect value for " + key, expected, config.get(key));
	}
}
