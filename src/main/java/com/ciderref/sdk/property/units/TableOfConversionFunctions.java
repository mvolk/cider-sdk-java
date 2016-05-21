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

import java.util.HashMap;
import java.util.Map;

/**
 * Base implementation of a library of unit of measurement conversion functions.
 */
public class TableOfConversionFunctions<T extends Enum<T>> {

    private final Map<T, Map<T, Function>> functionMap;

    @SuppressWarnings("PMD.UseVarargs")
    protected TableOfConversionFunctions(T[] values) {
        functionMap = new HashMap<>();
        for (T units : values) {
            functionMap.put(units, new HashMap<T, Function>()); // NOPMD
        }
    }

    /**
     * Hook for injecting functions into the lookup table.
     *
     * @param fromUnits (not null) units of measurement that {@code function} converts from
     * @param toUnits (not null) units of measurement that {@code function} converts to
     * @param function (nullable) the conversion function to transform {@code fromUnits} to {@code toUnits}.
     * @throws IllegalArgumentException if {@code fromUnits} or {@code toUnits} is null
     */
    protected void put(T fromUnits, T toUnits, Function function) {
        if (fromUnits == null || toUnits == null) {
            throw new IllegalArgumentException("Null units are not acceptable in this context");
        }
        functionMap.get(fromUnits).put(toUnits, function);
    }

    /**
     * Obtain a function for converting a measurement expressed in terms of {@code fromUnits} to a measurement
     * expressed in terms of {@code toUnits}.
     *
     * @param fromUnits (not null) the units that the function needs to convert from
     * @param toUnits (not null) the units that the function needs to convert to
     * @return (not null) the conversion function
     * @throws IllegalArgumentException if {@code fromUnits} or {@code toUnits} is {@code null}.
     */
    public Function getFunction(T fromUnits, T toUnits) {
        if (fromUnits == null || toUnits == null) {
            throw new IllegalArgumentException("Conversion to or from null units is not possible");
        }
        return functionMap.get(fromUnits).get(toUnits);
    }

}
