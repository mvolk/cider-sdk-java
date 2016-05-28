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

package com.ciderref.sdk.calculate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.ciderref.sdk.property.Mass;
import com.ciderref.sdk.property.MassConcentration;
import com.ciderref.sdk.property.SpecificGravity;
import com.ciderref.sdk.property.Volume;
import com.ciderref.sdk.property.units.UnitsOfMass;
import com.ciderref.sdk.property.units.UnitsOfVolume;
import com.ciderref.sdk.substance.AppleJuice;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link PotentialAlcoholByVolume}.
 */
public class PotentialAlcoholByVolumeTest {
    private AppleJuice mockAppleJuice;
    private PotentialAlcoholByVolume potentialAbv;

    /** Set up test w/mock apple juice. */
    @Before
    public void setUp() {
        mockAppleJuice = mock(AppleJuice.class);
        potentialAbv = new PotentialAlcoholByVolume(mockAppleJuice);
    }

    /** Constructor throws if passed null. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullThrows() {
        new PotentialAlcoholByVolume(null);
    }

    /** getMinimum throws if passed null. */
    @Test(expected = IllegalArgumentException.class)
    public void testGetMinimumNull() {
        potentialAbv.getMinimum(null);
    }

    /** Confirm minimum ABV is as expected. */
    @Test
    public void testGetMinimumAbv() {
        SpecificGravity sg = new SpecificGravity(1.020);
        MassConcentration sugarContent =
                new MassConcentration(new Mass(1, UnitsOfMass.Grams), new Volume(1, UnitsOfVolume.Liters));
        when(mockAppleJuice.getMinimumSugarConcentration(sg)).thenReturn(sugarContent);
        assertEquals(0.06, potentialAbv.getMinimum(sg).getValue(), 0);
    }

    /** getAverage throws if passed null. */
    @Test(expected = IllegalArgumentException.class)
    public void testGetAverageNull() {
        potentialAbv.getAverage(null);
    }

    /** Confirm average ABV is as expected. */
    @Test
    public void testGetAverageAbv() {
        SpecificGravity sg = new SpecificGravity(1.030);
        MassConcentration sugarContent =
                new MassConcentration(new Mass(2, UnitsOfMass.Grams), new Volume(1, UnitsOfVolume.Liters));
        when(mockAppleJuice.getAverageSugarConcentration(sg)).thenReturn(sugarContent);
        assertEquals(2 * 0.06, potentialAbv.getAverage(sg).getValue(), 0);
    }

    /** getMaximum throws if passed null. */
    @Test(expected = IllegalArgumentException.class)
    public void testGetMaximumNull() {
        potentialAbv.getMaximum(null);
    }

    /** Confirm maximum ABV is as expected. */
    @Test
    public void testGetMaximumAbv() {
        SpecificGravity sg = new SpecificGravity(1.040);
        MassConcentration sugarContent =
                new MassConcentration(new Mass(4, UnitsOfMass.Grams), new Volume(1, UnitsOfVolume.Liters));
        when(mockAppleJuice.getMaximumSugarConcentration(sg)).thenReturn(sugarContent);
        assertEquals(4 * 0.06, potentialAbv.getMaximum(sg).getValue(), 0);
    }

}
