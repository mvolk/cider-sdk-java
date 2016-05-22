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

import static org.junit.Assert.assertEquals;

import com.ciderref.sdk.property.units.UnitsOfMass;
import com.ciderref.sdk.property.units.UnitsOfVolume;
import org.junit.Test;

/**
 * Unit tests for {@link Density}.
 */
@SuppressWarnings({"PMD.TooManyMethods", "PMD.ExcessivePublicCount", "PMD.TooManyStaticImports"})
public class DensityTest {

    /** Constructing with null mass produces an exception. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullMassThrows() {
        new Density(null, new Volume(1, UnitsOfVolume.USGallons));
    }

    /** Constructing with null volume produces an exception. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullVolumeUnitsThrows() {
        new Density(new Mass(4, UnitsOfMass.Grams), null);
    }

    /** Constructing with null mass and volume produces an exception. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorBothArgsNullThrows() {
        new Density(null, null);
    }

    /** Constructing with zero volume produces an exception. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorZeroVolumeThrows() {
        new Density(new Mass(4, UnitsOfMass.Grams), new Volume(0, UnitsOfVolume.USGallons));
    }

    /** Constructor handles args correctly. */
    @Test
    public void testConstructor() {
        assertEquals(2, new Density(new Mass(4, UnitsOfMass.Grams),
                new Volume(2, UnitsOfVolume.Liters)).getValue(UnitsOfMass.Grams, UnitsOfVolume.Liters), 0);
    }

}
