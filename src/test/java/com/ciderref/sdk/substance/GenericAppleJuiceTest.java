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

import com.ciderref.sdk.property.MassConcentration;
import com.ciderref.sdk.property.SpecificGravity;
import com.ciderref.sdk.property.units.UnitsOfMass;
import com.ciderref.sdk.property.units.UnitsOfVolume;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link GenericAppleJuice}.
 */
@SuppressWarnings("PMD.TooManyMethods")
public class GenericAppleJuiceTest {
    
    private static final double AVERAGE_SUGAR_COEFFICIENT = 2130;
    private static final double SUGAR_COEFFICIENT_STANDARD_DEVIATION = 120;
    
    private GenericAppleJuice juice;

    /** Sets up preconditions for each test. */
    @Before
    public void setUp() {
        juice = getInstanceOfClassUnderTest();
    }

    /** Specific gravity cannot be null. */
    @Test(expected = IllegalArgumentException.class)
    public void testGetAverageSugarConcentrationThrowsWithNull() {
        juice.getAverageSugarConcentration(null);
    }

    /** Confirm that average sugar concentration at 1.040 SG is the expected concentration. */
    @Test
    public void testGetAverageSugarConcentration40degSg() {
        double coefficient = getAverageSugarCoefficient();
        SpecificGravity specificGravity = new SpecificGravity(1.040);
        assertEquals(getExpectedConcentration(1.040, coefficient),
                inGramsPerLiter(juice.getAverageSugarConcentration(specificGravity)), 0);
    }

    /** Confirm that average sugar concentration at 1.050 SG is the expected concentration. */
    @Test
    public void testGetAverageSugarConcentration50degSg() {
        double coefficient = getAverageSugarCoefficient();
        SpecificGravity specificGravity = new SpecificGravity(1.050);
        assertEquals(getExpectedConcentration(1.050, coefficient),
                inGramsPerLiter(juice.getAverageSugarConcentration(specificGravity)), 0);
    }

    /** Confirm that average sugar concentration at 1.060 SG is the expected concentration. */
    @Test
    public void testGetAverageSugarConcentration60degSg() {
        double coefficient = getAverageSugarCoefficient();
        SpecificGravity specificGravity = new SpecificGravity(1.060);
        assertEquals(getExpectedConcentration(1.060, coefficient),
                inGramsPerLiter(juice.getAverageSugarConcentration(specificGravity)), 0);
    }

    /** Specific gravity cannot be null. */
    @Test(expected = IllegalArgumentException.class)
    public void testGetMinimumSugarConcentrationThrowsWithNull() {
        juice.getMinimumSugarConcentration(null);
    }

    /** Confirm that minimum sugar concentration at 1.040 SG is the expected concentration. */
    @Test
    public void testGetMinimumSugarConcentration40degSg() {
        double coefficient = getAverageSugarCoefficient() - (2 * getStandardDeviation());
        SpecificGravity specificGravity = new SpecificGravity(1.040);
        assertEquals(getExpectedConcentration(1.040, coefficient),
                inGramsPerLiter(juice.getMinimumSugarConcentration(specificGravity)), 0);
    }

    /** Confirm that minimum sugar concentration at 1.050 SG is the expected concentration. */
    @Test
    public void testGetMinimumSugarConcentration50degSg() {
        double coefficient = getAverageSugarCoefficient() - (2 * getStandardDeviation());
        SpecificGravity specificGravity = new SpecificGravity(1.050);
        assertEquals(getExpectedConcentration(1.050, coefficient),
                inGramsPerLiter(juice.getMinimumSugarConcentration(specificGravity)), 0);
    }

    /** Confirm that minimum sugar concentration at 1.060 SG is the expected concentration. */
    @Test
    public void testGetMinimumSugarConcentration60degSg() {
        double coefficient = getAverageSugarCoefficient() - (2 * getStandardDeviation());
        SpecificGravity specificGravity = new SpecificGravity(1.060);
        assertEquals(getExpectedConcentration(1.060, coefficient),
                inGramsPerLiter(juice.getMinimumSugarConcentration(specificGravity)), 0);
    }

    /** Specific gravity cannot be null. */
    @Test(expected = IllegalArgumentException.class)
    public void testGetMaximumSugarConcentrationThrowsWithNull() {
        juice.getMaximumSugarConcentration(null);
    }

    /** Confirm that maximum sugar concentration at 1.040 SG is the expected concentration. */
    @Test
    public void testGetMaximumSugarConcentration40degSg() {
        double coefficient = getAverageSugarCoefficient() + (2 * getStandardDeviation());
        SpecificGravity specificGravity = new SpecificGravity(1.040);
        assertEquals(getExpectedConcentration(1.040, coefficient),
                inGramsPerLiter(juice.getMaximumSugarConcentration(specificGravity)), 0);
    }

    /** Confirm that maximum sugar concentration at 1.050 SG is the expected concentration. */
    @Test
    public void testGetMaximumSugarConcentration50degSg() {
        double coefficient = getAverageSugarCoefficient() + (2 * getStandardDeviation());
        SpecificGravity specificGravity = new SpecificGravity(1.050);
        assertEquals(getExpectedConcentration(1.050, coefficient),
                inGramsPerLiter(juice.getMaximumSugarConcentration(specificGravity)), 0);
    }

    /** Confirm that maximum sugar concentration at 1.060 SG is the expected concentration. */
    @Test
    public void testGetMaximumSugarConcentration60degSg() {
        double coefficient = getAverageSugarCoefficient() + (2 * getStandardDeviation());
        SpecificGravity specificGravity = new SpecificGravity(1.060);
        assertEquals(getExpectedConcentration(1.060, coefficient),
                inGramsPerLiter(juice.getMaximumSugarConcentration(specificGravity)), 0);
    }

    /** Specific gravity cannot be null. */
    @Test(expected = IllegalArgumentException.class)
    public void testGetTotalSolidsConcentrationThrowsWithNull() {
        juice.getTotalSolidsConcentration(null);
    }

    /**
     * Total solids at 1.040 SG are within 1 g/L of approximation formula TS = 2608(SG - 1), given by
     * The New Cider Maker's Handbook by Claude Jolicoeur.
     */
    @Test
    public void testGetTotalSolidsConcentration40degSg() {
        double coefficient = 2608;
        SpecificGravity specificGravity = new SpecificGravity(1.040);
        assertEquals(getExpectedConcentration(1.040, coefficient),
                inGramsPerLiter(juice.getTotalSolidsConcentration(specificGravity)), 1.0);
    }

    /**
     * Total solids at 1.050 SG are within 1 g/L of approximation formula TS = 2608(SG - 1), given by
     * The New Cider Maker's Handbook by Claude Jolicoeur.
     */
    @Test
    public void testGetTotalSolidsConcentration50degSg() {
        double coefficient = 2608;
        SpecificGravity specificGravity = new SpecificGravity(1.050);
        assertEquals(getExpectedConcentration(1.050, coefficient),
                inGramsPerLiter(juice.getTotalSolidsConcentration(specificGravity)), 1.0);
    }

    /**
     * Total solids at 1.060 SG are within 1 g/L of approximation formula TS = 2608(SG - 1), given by
     * The New Cider Maker's Handbook by Claude Jolicoeur.
     */
    @Test
    public void testGetTotalSolidsConcentration60degSg() {
        double coefficient = 2608;
        SpecificGravity specificGravity = new SpecificGravity(1.060);
        assertEquals(getExpectedConcentration(1.060, coefficient),
                inGramsPerLiter(juice.getTotalSolidsConcentration(specificGravity)), 1.0);
    }

    /** Confirm that average sugar coefficient is expected. */
    @Test
    public void testGetAverageSugarCoefficient() {
        assertEquals(getAverageSugarCoefficient(), juice.getAverageSugarCoefficient(), 0);
    }

    /** Confirm that sugar coefficient standard deviation is expected. */
    @Test
    public void testGetSugarCoefficientStandardDeviation() {
        assertEquals(getStandardDeviation(), juice.getSugarCoefficientStandardDeviation(), 0);
    }
    
    private double getExpectedConcentration(double specificGravity, double coefficient) {
        return coefficient * (specificGravity - 1);
    }

    private double inGramsPerLiter(MassConcentration massConcentration) {
        return massConcentration.getValue(UnitsOfMass.Grams, UnitsOfVolume.Liters);
    }
    
    protected double getAverageSugarCoefficient() {
        return AVERAGE_SUGAR_COEFFICIENT;
    }
    
    protected double getStandardDeviation() {
        return SUGAR_COEFFICIENT_STANDARD_DEVIATION;
    }

    protected GenericAppleJuice getInstanceOfClassUnderTest() {
        return new GenericAppleJuice();
    }

}
