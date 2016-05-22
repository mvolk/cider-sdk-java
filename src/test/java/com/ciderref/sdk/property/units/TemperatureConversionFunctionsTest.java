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

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * Unit tests for {@link TemperatureConversionFunctions}.
 */
public class TemperatureConversionFunctionsTest {

    /** Conversion from null units produces exception. */
    @Test(expected = IllegalArgumentException.class)
    public void testGetFunctionWithNullFromUnitsThrows() {
        new TemperatureConversionFunctions().getFunction(null, UnitsOfTemperature.Celsius);
    }

    /** Conversion to null units produces exception. */
    @Test(expected = IllegalArgumentException.class)
    public void testGetFunctionWithNullToUnitsThrows() {
        new TemperatureConversionFunctions().getFunction(UnitsOfTemperature.Celsius, null);
    }

    /** Functions are provided for all possible permutations of from and to units. */
    @Test
    public void testGetFunctionAllPermutations() {
        TemperatureConversionFunctions library = new TemperatureConversionFunctions();
        for (UnitsOfTemperature fromUnits : UnitsOfTemperature.values()) {
            for (UnitsOfTemperature toUnits : UnitsOfTemperature.values()) {
                assertNotNull(library.getFunction(fromUnits, toUnits));
            }
        }
    }

}
