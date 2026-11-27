package com.github.bordertech.config;

import java.lang.reflect.Field;

/**
 * Class to access the static default configuration of {@link InitHelper} to allow
 * for more complete testing of the classes using {@link InitHelper}.
 */
public class AccessInitHelper {

	private AccessInitHelper(){}

	private static Field getField(String name) throws Exception {
		Field field = InitHelper.class.getDeclaredField(name);
		field.setAccessible(true);
		return field;
	}

	public static void overrideSpiEnabled(boolean value, boolean resetConfig) throws Exception {
		getField("spiEnabled").set(null, value);

		if (resetConfig) {
			Config.reset();
		}
	}

	public static void overrideSpiAppend(boolean value, boolean resetConfig) throws Exception {
		getField("spiAppendDefaultConfig").set(null, value);

		if (resetConfig) {
			Config.reset();
		}
	}

	public static void overrideDefaultConfig(String defaultConfig, boolean resetConfig) throws Exception {
		getField("defaultConfigImplClassName").set(null, defaultConfig);

		if (resetConfig) {
			Config.reset();
		}
	}

	public static void reset() throws Exception {
		overrideSpiEnabled(true, false);
		overrideSpiAppend(true, false);
		overrideDefaultConfig(DefaultConfiguration.class.getName(), false);

		Config.reset();
	}
}
