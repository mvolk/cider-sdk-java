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

package com.ciderref.sdk.substance;

import static org.junit.Assert.assertEquals;

import com.ciderref.sdk.property.Mass;
import com.ciderref.sdk.property.MassConcentration;
import com.ciderref.sdk.property.SpecificGravity;
import com.ciderref.sdk.property.Volume;
import com.ciderref.sdk.property.units.UnitsOfMass;
import com.ciderref.sdk.property.units.UnitsOfVolume;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link AqueousSolution}.
 */
@SuppressWarnings("PMD.TooManyMethods")
public class AqueousSolutionTest {

    private static final SpecificGravity SPECIFIC_GRAVITY = new SpecificGravity(1.048);
    private static final MassConcentration SOLIDS =
            new MassConcentration(new Mass(67.8, UnitsOfMass.Grams), Volume.ONE_LITER);
    private static final MassConcentration SUGAR =
            new MassConcentration(new Mass(34.5, UnitsOfMass.Grams), Volume.ONE_LITER);
    private static final Volume VOLUME = new Volume(31, UnitsOfVolume.Liters);

    private AqueousSolution aqueousSolution;

    /** Construct a fresh instance of the class under test before each test. */
    @Before
    public void setUp() {
        aqueousSolution = new AqueousSolution(SPECIFIC_GRAVITY, SOLIDS, SUGAR, VOLUME);
    }

    /** Confirm constructor throws if passed a null specific gravity. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullSpecificGravity() {
        new AqueousSolution(null, SOLIDS, SUGAR, VOLUME);
    }

    /** Confirm constructor throws if passed a null solids concentration. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullSolids() {
        new AqueousSolution(SPECIFIC_GRAVITY, null, SUGAR, VOLUME);
    }

    /** Confirm constructor throws if passed a null sugar concentration. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullSugar() {
        new AqueousSolution(SPECIFIC_GRAVITY, SOLIDS, null, VOLUME);
    }

    /** Confirm constructor throws if passed a null volume. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullVolume() {
        new AqueousSolution(SPECIFIC_GRAVITY, SOLIDS, SUGAR, null);
    }

    /** Confirm getSpecificGravity() returns the expected value. */
    @Test
    public void testGetSpecificGravity() {
        assertEquals(SPECIFIC_GRAVITY, aqueousSolution.getSpecificGravity());
    }

    /** Confirm getSolids() returns the expected value. */
    @Test
    public void testGetSolids() {
        assertEquals(SOLIDS, aqueousSolution.getSolids());
    }

    /** Confirm getSugar() returns the expected value. */
    @Test
    public void testGetSugar() {
        assertEquals(SUGAR, aqueousSolution.getSugar());
    }

    /** Confirm getVolume() returns the expected value. */
    @Test
    public void testGetVolume() {
        assertEquals(VOLUME, aqueousSolution.getVolume());
    }

    /** Confirm getSugarFreeDryExtract() returns the expected value. */
    @Test
    public void testGetSugarFreeDryExtract() {
        MassConcentration sdfe = new MassConcentration(new Mass(33.3, UnitsOfMass.Grams), Volume.ONE_LITER);
        assertEquals(sdfe, aqueousSolution.getSugarFreeDryExtract());
    }

}
