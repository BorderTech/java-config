package com.github.bordertech.config;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;

public class TestSpiConfiguration implements ConfigurationLoader {

	@Override
	public Configuration getConfiguration() {
		return new PropertiesConfiguration();
	}
}
