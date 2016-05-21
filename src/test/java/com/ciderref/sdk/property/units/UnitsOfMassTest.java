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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit tests for {@link UnitsOfMass}.
 */
public class UnitsOfMassTest {

    /** UnitsOfMass.values() should return 4 values. */
    @Test
    public void testCountOfUnitValues() {
        assertEquals(4, UnitsOfMass.values().length);
    }

    /** UnitsOfMass.valueOf("Grams") should return Grams. */
    @Test
    public void testUnitsIncludeMilligrams() {
        assertEquals(UnitsOfMass.Grams, UnitsOfMass.valueOf("Grams"));
    }

    /** UnitsOfMass.valueOf("Kilograms") should return Kilograms. */
    @Test
    public void testUnitsIncludeGrams() {
        assertEquals(UnitsOfMass.Kilograms, UnitsOfMass.valueOf("Kilograms"));
    }

    /** UnitsOfMass.valueOf("Ounces") should return Ounces. */
    @Test
    public void testUnitsIncludeOunces() {
        assertEquals(UnitsOfMass.Ounces, UnitsOfMass.valueOf("Ounces"));
    }

    /** UnitsOfMass.valueOf("Pounds") should return Pounds. */
    @Test
    public void testUnitsIncludePounds() {
        assertEquals(UnitsOfMass.Pounds, UnitsOfMass.valueOf("Pounds"));
    }

}
