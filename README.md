# Config
Project configuration mechanism.

## Status
[![Build Status](https://travis-ci.com/BorderTech/java-config.svg?branch=master)](https://travis-ci.com/BorderTech/java-config)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=bordertech-java-config&metric=alert_status)](https://sonarcloud.io/dashboard?id=bordertech-java-config)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=bordertech-java-config&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=bordertech-java-config)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=bordertech-java-config&metric=coverage)](https://sonarcloud.io/dashboard?id=bordertech-java-config)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/738a3851c483470da86ffe1d047f344c)](https://www.codacy.com/app/BorderTech/java-config?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=BorderTech/java-config&amp;utm_campaign=Badge_Grade)
[![Javadocs](https://www.javadoc.io/badge/com.github.bordertech.config/config.svg)](https://www.javadoc.io/doc/com.github.bordertech.config/config)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.bordertech.config/config.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.bordertech.config%22%20AND%20a:%22config%22)

# Why Use Config?
The `Config` class provides a standard mechanism java applications can use to access configuration data via the [Apache Commons Configuration](https://commons.apache.org/proper/commons-configuration/index.html) interface.

The [features](#config-features) of the default `Configuration` (i.e. `com.github.bordertech.config.DefaultConfiguration`) combine and enhance the functionality of the `PropertiesConfiguration` and `SystemConfiguration` with predefined property file resources.

Projects can easily override this default implementation via the [Config Initialization](#config-initialization) settings.

# Getting started

Add `Config` dependency:

``` xml
<project>
  ....
  <dependency>
    <groupId>com.github.bordertech.config</groupId>
    <artifactId>config</artifactId>
    <version>1.0.4</version>
  </dependency>
  ....
</project>
```

`Config.getInstance()` is the central access point to the configuration mechanism. Configuration properties can be read from the current configuration as follows:

``` java
  ....
  // Retrieve a property `my.example` with a default value:
  String value = Config.getInstance().getString("my.example", "a-default-value");
  ....
```

To override `my.example`, create a `bordertech-app.properties` file in the resources directory:

``` java
my.example=a-override-value
```

# Config Features

## Predefined property resources

The default implementation looks for the following resources:-
 * `bordertech-defaults.properties` - framework defaults
 * `bordertech-app.properties` - application properties
 * `bordertech-local.properties` - local developer properties

`local` overrides `app` which overrides `defaults`. Projects will usually use `bordertech-app.properties`.

The resources loaded into the Configuration can be overridden via [Config Initialization](#config-initialization) settings.

## Environment Suffix

When the environment property is set, it is used as the suffix for each property lookup:

``` java
## MOCK Environment
bordertech.config.environment=MOCK
```

This allows properties to have values only activated for a specific environment:

``` java
my.example.property.MOCK=mocking
my.example.property.PROD=proding
my.example.property=defaulting
```

If no property exists with the current environment suffix then the default property (ie no suffix) value is used.

## Logging

The default implementation uses [SimpleLog](https://commons.apache.org/proper/commons-logging/apidocs/org/apache/commons/logging/impl/SimpleLog.html). This Simple implementation of Log sends all enabled log messages, for all defined loggers, to System.err.

Other logging options:

|Property key|Description|Default value|
|-------------|-----------|-------------|
|bordertech.config.parameters.dump.console|This flag allows properties to be dumped to the console after being loaded.|false|
|bordertech.config.parameters.dump.file|The file name to dump the properties to after being loaded.|null|

## System Properties into Configuration

Control merging System Properties into Configuration.

|Property key|Description|Default value|
|-------------|-----------|-------------|
|bordertech.config.parameters.useSystemProperties|This flag allows system properties to be merged into the Configuration at the end of the loading process.|false|
|bordertech.config.parameters.useSystemOverWriteOnly|This flag controls if a system property will only overwrite an existing property|true|
|bordertech.config.parameters.useSystemPrefixes|Define a list of system attribute prefixes that are allowed to be merged. Default is allow all.|null|

## Configuration into System Properties

Control merging Configuration properties into System Properties.

|Property key|Description|Default value|
|-------------|-----------|-------------|
|bordertech.config.parameters.system.\*|Parameters with this prefix will be dumped into the System parameters. Not for general use|n/a|

## Include resources

Other property files can be included from the predefined (eg `bordertech-app.properties`) property files:

``` java
include=another.properties
```

## Touchfile

The reload of the configuration can be triggered via a `touchfile`. The `touchfile` can be set via the property:

``` java
bordertech.config.touchfile=my-touchfile.properties
```

The `touchfile` is checked when `getInstance()` is called. To avoid excessive IO an interval (in milli seconds) between checks can be set and defaults to 10000.

``` xml
bordertech.config.touchfile.interval=3000
```

## Property listeners

Property listeners can be set on the `Config` to be notified whenever the `Config` is set or reloaded.

``` java
  Config.addPropertyChangeListener(new MyListener());
```

# Config Initialization

The initial configuration of `Config` can be overridden by setting properties in a file `bordertech-config.properties`.

The following properties can be set:-

|Parameter key|Description|Default value|
|-------------|-----------|-------------|
|bordertech.config.default.impl|Default implementation class name|com.github.bordertech.config.DefaultConfiguration|
|bordertech.config.spi.enabled|The flag to enable SPI lookup|true|
|bordertech.config.spi.append.default|The flag to append the default configuration|true|
|bordertech.config.resource.order|The list of property resources to load into the configuration|bordertech-defaults.properties, bordertech-app.properties, bordertech-local.properties|

## Default Implementation

Example of overriding the default implementation:

``` java
bordertech.config.default.impl=my.example.SpecialConfiguration
```

## Custom Resources to Load

Example of loading the default resources and a project specific resource:

``` java
bordertech.config.resource.order+=my-project.properties
```

## SPI

`ConfigurationLoader` is the SPI interface for classes that can load a custom configuration.

By default, the SPI lookup is enabled and if found, will be appended to the default implementation.

# Testing

The following methods in the `Config` class are useful for unit testing:

- `reset()` - will restore the configuration.

- `copyConfiguration(Configuration)` - will perform a deep-copy of the given configuration. This is useful when you need to create a backup copy of the current configuration before modifying it for a particular test.

# Contributing

Refer to these guidelines for [Workflow](https://github.com/BorderTech/java-common/wiki/Workflow) and [Releasing](https://github.com/BorderTech/java-common/wiki/Releasing).
