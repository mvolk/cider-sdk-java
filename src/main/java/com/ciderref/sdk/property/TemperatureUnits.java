/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Michael Volk (michael@volksys.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.ciderref.sdk.property;

/**
 * Units of measurement for temperature with methods for conversion between units of measurement.
 */
public enum TemperatureUnits {

    Celsius("Celsius", "℃", new ConversionLogic() {
        /**
         * Returns the argument. Celsius is the SDK's reference unit of measurement for temperature.
         *
         * @param value a temperature measured in degrees Celsius
         * @return {@code value}
         */
        @Override
        public double fromReferenceUnits(double value) {
            return value;
        }

        /**
         * Returns the argument. Celsius is the SDK's reference unit of measurement for temperature.
         *
         * @param value a temperature measured in degrees Celsius
         * @return {@code value}
         */
        @Override
        public double toReferenceUnits(double value) {
            return value;
        }
    }),

    Fahrenheit("Fahrenheit", "℉", new ConversionLogic() {
        /**
         * Converts a temperature expressed in degrees Fahrenheit to a temperature expressed in degrees Celsius.
         *
         * @param value a temperature expressed in degrees Fahrenheit
         * @return a temperature expressed in degrees Celsius and semantically equivalent to {@code value}.
         */
        @Override
        public double fromReferenceUnits(double value) {
            return value * 9.0 / 5.0 + 32.0;
        }

        /**
         * Converts a temperature expressed in degrees Celsius to a temperature expressed in degrees Fahrenheit.
         *
         * @param value a temperature expressed in degrees Celsius
         * @return a temperature expressed in degrees Fahrenheit and semantically equivalent to {@code value}.
         */
        @Override
        public double toReferenceUnits(double value) {
            return (value - 32.0) * 5.0 / 9.0;
        }
    });

    private final String name;
    private final String symbol;
    private final ConversionLogic conversionLogic;

    TemperatureUnits(String name, String symbol, ConversionLogic conversionLogic) {
        this.name = name;
        this.symbol = symbol;
        this.conversionLogic = conversionLogic;
    }

    /**
     * The spelled-out name of this unit of measure.
     *
     * @return the full name of this unit of measure
     */
    public String getName() {
        return name;
    }

    /**
     * The abbreviated label for this unit of measure.
     *
     * @return shorthand notation for this unit of measure.
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Converts a temperature value expressed in this unit of measurement to another unit of measurement.
     *
     * @param value a temperature expressed in this unit of measurement
     * @param newUnits a unit of measurement in which the returned value will be expressed (may be the same as this
     *                 unit of measurement)
     * @return the temperature expressed in {code newUnits}
     */
    public double convert(double value, TemperatureUnits newUnits) {
        return this == newUnits ? value :
                newUnits.conversionLogic.fromReferenceUnits(conversionLogic.toReferenceUnits(value));
    }

}