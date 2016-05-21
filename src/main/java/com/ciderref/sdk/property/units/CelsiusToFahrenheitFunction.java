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

package com.ciderref.sdk.property.units;

import com.ciderref.sdk.math.Function;

/**
 * Converts temperature measured in Celsius to temperature measured in Fahrenheit. Immutable and thread-safe.
 */
public class CelsiusToFahrenheitFunction implements Function {

    /**
     * Converts temperature measured in Celsius to temperature measured in Fahrenheit.
     *
     * @param degreesC temperature in Celsius
     * @return temperature in Fahrenheit
     */
    @Override
    public double applyTo(double degreesC) {
        return degreesC * 9.0 / 5.0 + 32.0;
    }

    /**
     * Function for converting temperature measured in Fahrenheit to temperature measured in Celsius.
     *
     * @return Fahrenheit to Celsius units of measurement conversion function
     */
    @Override
    public Function getInverse() {
        return new Function() {
            /**
             * Converts temperature measured in Fahrenheit to temperature measured in Celsius.
             *
             * @param degreesF temperature in Fahrenheit
             * @return temperature in Celsius
             */
            @Override
            public double applyTo(double degreesF) {
                return (degreesF - 32) * 5.0 / 9.0;
            }

            /**
             * Function for converting temperature measured in Celsius to temperature measured in Fahrenheit.
             *
             * @return Celsius to Fahrenheit units of measurement conversion function
             */
            @Override
            public Function getInverse() {
                return CelsiusToFahrenheitFunction.this;
            }
        };
    }

}
