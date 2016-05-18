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
import static org.junit.Assert.assertTrue;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

/**
 * Unit tests for {@link Density}.
 */
@SuppressWarnings({"PMD.TooManyMethods", "PMD.ExcessivePublicCount", "PMD.TooManyStaticImports"})
public class DensityTest {

    /** Constructing with null mass produces an exception. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullMassThrows() {
        new Density(null, new Volume(1, Volume.Units.USGallons));
    }

    /** Constructing with null volume produces an exception. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullVolumeUnitsThrows() {
        new Density(new Mass(4, Mass.Units.Grams), null);
    }

    /** Constructing with null mass and volume produces an exception. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorBothArgsNullThrows() {
        new Density(null, null);
    }

    /** Constructing with zero volume produces an exception. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorZeroVolumeThrows() {
        new Density(new Mass(4, Mass.Units.Grams), new Volume(0, Volume.Units.USGallons));
    }

    /** Constructor handles args correctly. */
    @Test
    public void testConstructor() {
        assertEquals(2, new Density(new Mass(4, Mass.Units.Grams),
                new Volume(2, Volume.Units.Liters)).getValue(Mass.Units.Grams, Volume.Units.Liters), 0);
    }

    /** getValue with null mass units throws. */
    @Test(expected = IllegalArgumentException.class)
    public void testGetValueNullMassUnitsThrows() {
        new Density(new Mass(4, Mass.Units.Grams),
                new Volume(2, Volume.Units.Liters)).getValue(null, Volume.Units.Liters);
    }

    /** getValue with null volume units throws. */
    @Test(expected = IllegalArgumentException.class)
    public void testGetValueNullVolumeUnitsThrows() {
        new Density(new Mass(4, Mass.Units.Grams),
                new Volume(2, Volume.Units.Liters)).getValue(Mass.Units.Grams, null);
    }

    /** getValue with both args null throws. */
    @Test(expected = IllegalArgumentException.class)
    public void testGetValueBothArgsNullThrows() {
        new Density(new Mass(4, Mass.Units.Grams),
                new Volume(2, Volume.Units.Liters)).getValue(null, null);
    }

    /** Comparison to null yields a NPE. */
    @Test(expected = NullPointerException.class)
    public void testCompareToNullThrows() {
        //noinspection ConstantConditions
        new Density(new Mass(4, Mass.Units.Grams), new Volume(100, Volume.Units.Milliliters)).compareTo(null);
    }

    /** 5g/L is less than 1 oz/us gallon. */
    @Test
    public void testCompare5GramsPerLiterTo1OuncePerUsGallon() {
        assertEquals(-1, new Density(new Mass(5, Mass.Units.Grams), new Volume(1, Volume.Units.Liters)).compareTo(
                new Density(new Mass(1, Mass.Units.Ounces), new Volume(1, Volume.Units.USGallons))));
    }

    /** 1 oz/us gallon is more than 5g/L. */
    @Test
    public void testCompare1OuncePerUsGallonTo5GramsPerLiter() {
        assertEquals(1, new Density(new Mass(1, Mass.Units.Ounces), new Volume(1, Volume.Units.USGallons)).compareTo(
                new Density(new Mass(5, Mass.Units.Grams), new Volume(1, Volume.Units.Liters))));
    }

    /** 5g/L is equal to 5g/L. */
    @Test
    public void testCompare1EquivalentDensities() {
        assertEquals(0, new Density(new Mass(5, Mass.Units.Grams), new Volume(1, Volume.Units.Liters)).compareTo(
                new Density(new Mass(5, Mass.Units.Grams), new Volume(1, Volume.Units.Liters))));
    }

    /** Density equals itself. */
    @Test
    public void testDensityEqualsItself() {
        Density density = new Density(new Mass(5, Mass.Units.Grams), new Volume(100, Volume.Units.Milliliters));
        @SuppressWarnings("UnnecessaryLocalVariable")
        Density itself = density;
        assertTrue(density.equals(itself)); // NOPMD - exercising .equals(Object) is the point of this test
    }

    /** Java .equals(Object) contract is met. */
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    public void equalsContract() {
        // Null fields warning is suppressed because our constructor prevents construction of Density with null
        //  mass or volume.
        EqualsVerifier.forClass(Density.class).suppress(Warning.NULL_FIELDS).verify();
    }

}
