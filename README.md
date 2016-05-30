# CiderRef Java SDK

Hard cider models and calculators to support cidermaking tools.

## Features

### Type-safe Physical Properties Library

The `com.ciderref.sdk.property` package contains classes for representing temperature, mass, volume, mass concentration,
density, Brix, specific gravity, cider sweetness categories and percent alcohol by volume. Units of measurement
conversions are supported, and the library provides considerable protection against accidental calculation in the
wrong units of measurement.

### Apple Juice Properties Library

The `com.ciderref.sdk.substance` package provides access to properties of apple juices from various countries as well
as blended juice, pure water, and sugar water.

### Calculator Library

The `com.ciderref.sdk.calculate` package provides calculators for common cider-related calculations.

## Build

1. Clone this repository to your Java 7+ development workstation.

2. In the root of the cloned repository, execute:

    `./gradlew clean build`

## IntelliJ IDEA Support

Before opening this project with IntelliJ IDEA, consider executing
`./gradlew idea` to generate the IDE configuration files.

## Dependencies

This SDK has no run-time dependencies.

