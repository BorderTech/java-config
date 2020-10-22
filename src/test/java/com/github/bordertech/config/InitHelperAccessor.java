package com.github.bordertech.config;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

/**
 * Class to access the static default configuration of {@link InitHelper} to allow
 * for more complete testing of the classes using {@link InitHelper}.
 */
public class InitHelperAccessor {

	private static final List<String> DEFAULT_BORDERTECH_LOAD_ORDER = Arrays.asList(
		// The name of the first resource we look for is for internal default properties
		"bordertech-defaults.properties",
		// The name of the next resource we look at is for application properties
		"bordertech-app.properties",
		// The last properties which are loaded are local/developer properties
		"bordertech-local.properties");

	private static Field getField(String name) throws Exception {
		Field field = InitHelper.class.getField(name);

		field.setAccessible(true);

		Field modifiersFiled = Field.class.getDeclaredField("modifiers");
		modifiersFiled.setAccessible(true);
		modifiersFiled.setInt(field, field.getModifiers() & ~Modifier.FINAL);

		return field;
	}

	public static void overrideSpiEnabled(boolean value, boolean resetConfig) throws Exception {
		getField("SPI_ENABLED").set(null, value);

		if (resetConfig) {
			Config.reset();
		}
	}

	public static void overrideSpiAppend(boolean value, boolean resetConfig) throws Exception {
		getField("SPI_APPEND_DEFAULT_CONFIG").set(null, value);

		if (resetConfig) {
			Config.reset();
		}
	}

	public static void overrideDefaultConfig(String defaultConfig, boolean resetConfig) throws Exception {
		getField("DEFAULT_CONFIG_IMPL").set(null, defaultConfig);

		if (resetConfig) {
			Config.reset();
		}
	}

//	public static void overrideDefaultResourceOrder(List<String> resourceOrder, boolean resetConfig) throws Exception {
//		getField("DEFAULT_RESOURCE_LOAD_ORDER").set(null, resourceOrder);
//
//		if (resetConfig) {
//			Config.reset();
//		}
//	}

	public static void reset() throws Exception {
		overrideSpiEnabled(true, false);
		overrideSpiAppend(true, false);
		overrideDefaultConfig(DefaultConfiguration.class.getName(), false);
//		overrideDefaultResourceOrder(DEFAULT_BORDERTECH_LOAD_ORDER, false);

		Config.reset();
	}
}
