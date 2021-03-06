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

/**
 * Provides units conversion functions and a point of injections for mocks to assist in unit testing.
 */
public final class ConversionFunctions {

    private ConversionFunctions() { }

    /**
     * Provides a collection of units of mass conversion functions.
     *
     * @return (not null) a collection of units of mass conversion functions
     */
    public static MassConversionFunctions getForUnitsOfMass() {
        return new MassConversionFunctions();
    }

    /**
     * Provides a collection of functions for converting temperature expressed in one unit of measurement to another.
     *
     * @return (not null) a collection of units of temperature conversion functions
     */
    public static TemperatureConversionFunctions getForUnitsOfTemperature() {
        return new TemperatureConversionFunctions();
    }

    /**
     * Provides a collection of functions for converting volume expressed in one unit of measurement to another.
     *
     * @return (not null) a collection of units of volume conversion functions
     */
    public static VolumeConversionFunctions getForUnitsOfVolume() {
        return new VolumeConversionFunctions();
    }

}
