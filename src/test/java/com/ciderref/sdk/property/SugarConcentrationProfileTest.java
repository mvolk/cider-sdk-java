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
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * Unit tests for {@link SugarConcentrationProfile}.
 */
@SuppressWarnings("PMD.TooManyMethods")
public class SugarConcentrationProfileTest {
    private static final double LOW_COEFFICIENT = 2050.0;
    private static final double HIGH_COEFFICIENT = 2150.0;

    private static final double LOW_STD_DEVIATION = 50.0;
    private static final double HIGH_STD_DEVIATION = 100.0;

    /** Constructor rejects NaN coefficient. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructWithCoefficientNaN() {
        new SugarConcentrationProfile(Double.NaN, LOW_STD_DEVIATION);
    }

    /** Constructor rejects negative infinity coefficient. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructWithCoefficientNegativeInfinity() {
        new SugarConcentrationProfile(Double.NEGATIVE_INFINITY, LOW_STD_DEVIATION);
    }

    /** Constructor rejects positive infinity coefficient. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructWithCoefficientInfinity() {
        new SugarConcentrationProfile(Double.POSITIVE_INFINITY, LOW_STD_DEVIATION);
    }

    /** Constructor rejects negative coefficient. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructWithCoefficientNegative() {
        new SugarConcentrationProfile(-0 - Math.ulp(-0), LOW_STD_DEVIATION);
    }

    /** Constructor accepts zero coefficient. */
    @Test
    public void testConstructWithCoefficientZero() {
        new SugarConcentrationProfile(-0, LOW_STD_DEVIATION);
    }

    /** Constructor rejects NaN std deviation. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructWithStdDeviationNaN() {
        new SugarConcentrationProfile(HIGH_COEFFICIENT, Double.NaN);
    }

    /** Constructor rejects negative infinity std deviation. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructWithStdDeviationNegativeInfinity() {
        new SugarConcentrationProfile(HIGH_COEFFICIENT, Double.NEGATIVE_INFINITY);
    }

    /** Constructor rejects positive infinity std deviation. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructWithStdDeviationInfinity() {
        new SugarConcentrationProfile(HIGH_COEFFICIENT, Double.POSITIVE_INFINITY);
    }

    /** Constructor rejects negative std deviation. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructWithStdDeviationNegative() {
        new SugarConcentrationProfile(HIGH_COEFFICIENT, -0 - Math.ulp(-0));
    }

    /** Constructor accepts zero std deviation. */
    @Test
    public void testConstructWithStdDeviationZero() {
        new SugarConcentrationProfile(HIGH_COEFFICIENT, -0);
    }

    /** Specific gravity cannot be null. */
    @Test(expected = IllegalArgumentException.class)
    public void testGetAverageSugarConcentrationThrowsWithNull() {
        new SugarConcentrationProfile(LOW_COEFFICIENT, LOW_STD_DEVIATION).getAverageSugarConcentration(null);
    }

    /** Confirm that average sugar concentration at 1.040 SG is the expected concentration. */
    @Test
    public void testGetAverageSugarConcentration40degSg() {
        SugarConcentrationProfile profile = new SugarConcentrationProfile(LOW_COEFFICIENT, LOW_STD_DEVIATION);
        SpecificGravity specificGravity = new SpecificGravity(1.040);
        assertEquals(getExpectedConcentration(1.040, LOW_COEFFICIENT),
                inGramsPerLiter(profile.getAverageSugarConcentration(specificGravity)), 0);
    }

    /** Confirm that average sugar concentration at 1.050 SG is the expected concentration. */
    @Test
    public void testGetAverageSugarConcentration50degSg() {
        SugarConcentrationProfile profile = new SugarConcentrationProfile(HIGH_COEFFICIENT, LOW_STD_DEVIATION);
        SpecificGravity specificGravity = new SpecificGravity(1.050);
        assertEquals(getExpectedConcentration(1.050, HIGH_COEFFICIENT),
                inGramsPerLiter(profile.getAverageSugarConcentration(specificGravity)), 0);
    }

    /** Confirm that average sugar concentration at 1.060 SG is the expected concentration. */
    @Test
    public void testGetAverageSugarConcentration60degSg() {
        SugarConcentrationProfile profile = new SugarConcentrationProfile(LOW_COEFFICIENT, HIGH_STD_DEVIATION);
        SpecificGravity specificGravity = new SpecificGravity(1.060);
        assertEquals(getExpectedConcentration(1.060, LOW_COEFFICIENT),
                inGramsPerLiter(profile.getAverageSugarConcentration(specificGravity)), 0);
    }

    /** Specific gravity cannot be null. */
    @Test(expected = IllegalArgumentException.class)
    public void testGetMinimumSugarConcentrationThrowsWithNull() {
        new SugarConcentrationProfile(LOW_COEFFICIENT, HIGH_STD_DEVIATION).getMinimumSugarConcentration(null);
    }

    /** Confirm that minimum sugar concentration at 1.040 SG is the expected concentration. */
    @Test
    public void testGetMinimumSugarConcentration40degSg() {
        SugarConcentrationProfile profile = new SugarConcentrationProfile(LOW_COEFFICIENT, LOW_STD_DEVIATION);
        SpecificGravity specificGravity = new SpecificGravity(1.040);
        assertEquals(getExpectedConcentration(1.040, LOW_COEFFICIENT - 2 * LOW_STD_DEVIATION),
                inGramsPerLiter(profile.getMinimumSugarConcentration(specificGravity)), 0);
    }

    /** Confirm that minimum sugar concentration at 1.050 SG is the expected concentration. */
    @Test
    public void testGetMinimumSugarConcentration50degSg() {
        SugarConcentrationProfile profile = new SugarConcentrationProfile(HIGH_COEFFICIENT, LOW_STD_DEVIATION);
        SpecificGravity specificGravity = new SpecificGravity(1.050);
        assertEquals(getExpectedConcentration(1.050, HIGH_COEFFICIENT - 2 * LOW_STD_DEVIATION),
                inGramsPerLiter(profile.getMinimumSugarConcentration(specificGravity)), 0);
    }

    /** Confirm that minimum sugar concentration at 1.060 SG is the expected concentration. */
    @Test
    public void testGetMinimumSugarConcentration60degSg() {
        SugarConcentrationProfile profile = new SugarConcentrationProfile(HIGH_COEFFICIENT, HIGH_STD_DEVIATION);
        SpecificGravity specificGravity = new SpecificGravity(1.060);
        assertEquals(getExpectedConcentration(1.060, HIGH_COEFFICIENT - 2 * HIGH_STD_DEVIATION),
                inGramsPerLiter(profile.getMinimumSugarConcentration(specificGravity)), 0);
    }

    /** Specific gravity cannot be null. */
    @Test(expected = IllegalArgumentException.class)
    public void testGetMaximumSugarConcentrationThrowsWithNull() {
        new SugarConcentrationProfile(HIGH_COEFFICIENT, HIGH_STD_DEVIATION).getMaximumSugarConcentration(null);
    }

    /** Confirm that maximum sugar concentration at 1.040 SG is the expected concentration. */
    @Test
    public void testGetMaximumSugarConcentration40degSg() {
        SugarConcentrationProfile profile = new SugarConcentrationProfile(HIGH_COEFFICIENT, HIGH_STD_DEVIATION);
        SpecificGravity specificGravity = new SpecificGravity(1.040);
        assertEquals(getExpectedConcentration(1.040, HIGH_COEFFICIENT + 2 * HIGH_STD_DEVIATION),
                inGramsPerLiter(profile.getMaximumSugarConcentration(specificGravity)), 0);
    }

    /** Confirm that maximum sugar concentration at 1.050 SG is the expected concentration. */
    @Test
    public void testGetMaximumSugarConcentration50degSg() {
        SugarConcentrationProfile profile = new SugarConcentrationProfile(LOW_COEFFICIENT, HIGH_STD_DEVIATION);
        SpecificGravity specificGravity = new SpecificGravity(1.050);
        assertEquals(getExpectedConcentration(1.050, LOW_COEFFICIENT + 2 * HIGH_STD_DEVIATION),
                inGramsPerLiter(profile.getMaximumSugarConcentration(specificGravity)), 0);
    }

    /** Confirm that maximum sugar concentration at 1.060 SG is the expected concentration. */
    @Test
    public void testGetMaximumSugarConcentration60degSg() {
        SugarConcentrationProfile profile = new SugarConcentrationProfile(LOW_COEFFICIENT, LOW_STD_DEVIATION);
        SpecificGravity specificGravity = new SpecificGravity(1.060);
        assertEquals(getExpectedConcentration(1.060, LOW_COEFFICIENT + 2 * LOW_STD_DEVIATION),
                inGramsPerLiter(profile.getMaximumSugarConcentration(specificGravity)), 0);
    }

    private double getExpectedConcentration(double specificGravity, double coefficient) {
        return coefficient * (specificGravity - 1);
    }

    private double inGramsPerLiter(MassConcentration massConcentration) {
        return massConcentration.getValue(UnitsOfMass.Grams, UnitsOfVolume.Liters);
    }

    /** Java .equals(Object) contract is met. */
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    public void equalsContract() {
        EqualsVerifier
                .forClass(SugarConcentrationProfile.class)
                .verify();
    }

    /** Hash code value is as expected. */
    @Test
    public void testHashCode() {
        assertEquals(-33554432, new SugarConcentrationProfile(1, 1).hashCode());
    }

}
