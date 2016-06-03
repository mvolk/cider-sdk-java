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

import com.ciderref.sdk.property.DegreesBrix;
import com.ciderref.sdk.property.IllegalPropertyValueException;
import com.ciderref.sdk.property.SpecificGravity;
import org.junit.Test;

/**
 * Unit tests for {@link BrixCalculator}.
 */
@SuppressWarnings("PMD.TooManyMethods")
public class BrixCalculatorTest {

    /** Throws if getSpecificGravity is passed null. */
    @Test(expected = IllegalArgumentException.class)
    public void testGetSpecificGravityNull() {
        new BrixCalculator().getSpecificGravity(null);
    }

    /** Throws if getSpecificGravity is passed brix > 44 degrees. */
    @Test(expected = IllegalPropertyValueException.class)
    public void testGetSpecificGravityAboveMax() {
        new BrixCalculator().getSpecificGravity(new DegreesBrix(44 + Math.ulp(44)));
    }

    /** getSpecificGravity accepts brix <= 44 degrees. */
    @Test
    public void testGetSpecificGravityMax() {
        new BrixCalculator().getSpecificGravity(new DegreesBrix(44));
    }

    /**
     * Confirm getDegreesBrix against NIST table, available online at:
     * http://www.boulder.nist.gov/div838/SelectedPubs/Circular%20440%20Table%20114.pdf.
     * Regression fit within ±0.0015 degrees Brix considered passing.
     */
    @Test
    public void testGetSpecificGravity() {
        testGetSg(8.78, 1.035);
        testGetSg(9.99, 1.040);
        testGetSg(10.48, 1.042);
        testGetSg(10.96, 1.044);
        testGetSg(11.19, 1.045);
        testGetSg(11.43, 1.046);
        testGetSg(11.67, 1.047);
        testGetSg(11.91, 1.048);
        testGetSg(12.15, 1.049);
        testGetSg(12.39, 1.050);
        testGetSg(12.62, 1.051);
        testGetSg(12.86, 1.052);
        testGetSg(13.10, 1.053);
        testGetSg(13.33, 1.054);
        testGetSg(13.57, 1.055);
        testGetSg(13.80, 1.056);
        testGetSg(14.04, 1.057);
        testGetSg(14.27, 1.058);
        testGetSg(14.51, 1.059);
        testGetSg(14.74, 1.060);
        testGetSg(14.97, 1.061);
        testGetSg(15.21, 1.062);
        testGetSg(15.44, 1.063);
        testGetSg(15.67, 1.064);
        testGetSg(15.90, 1.065);
        testGetSg(16.13, 1.066);
        testGetSg(16.59, 1.068);
        testGetSg(17.05, 1.070);
        testGetSg(17.51, 1.072);
        testGetSg(18.20, 1.075);
        testGetSg(19.33, 1.080);
        testGetSg(20.46, 1.085);
    }

    private void testGetSg(double brixValue, double expectedSgValue) {
        DegreesBrix degreesBrix = new DegreesBrix(brixValue);
        SpecificGravity actualSpecificGravity = new BrixCalculator().getSpecificGravity(degreesBrix);
        assertEquals(expectedSgValue, actualSpecificGravity.getValue(), 0.0000349);
    }

    /** Throws if getDegreesBrix is passed null. */
    @Test(expected = IllegalArgumentException.class)
    public void testGetDegreesBrixNull() {
        new BrixCalculator().getDegreesBrix(null);
    }

    /** getDegreesBrix accepts a specific gravity of 1.17875. */
    @Test
    public void testGetDegreesBrixMax() {
        assertEquals(40, new BrixCalculator().getDegreesBrix(new SpecificGravity(1.17875)).getValue(), 0.003);
    }

    /** Throws if getDegreesBrix is passed a specific gravity above 1.17875. */
    @Test(expected = IllegalArgumentException.class)
    public void testGetDegreesBrixAboveMax() {
        new BrixCalculator().getDegreesBrix(new SpecificGravity(1.17875 + Math.ulp(1.17875)));
    }

    /** Ensure that near-zero negative value correction survives mutation testing. */
    @Test
    public void testZeroCorrectionBoundary() {
        double boundary = 1.0000185477666312;
        testGetBrix(boundary - Math.ulp(boundary), 0.0, 0.0);
        testGetBrix(boundary, 0.0, 0.0);
        testGetBrix(boundary + Math.ulp(boundary), 1.1368683772161603E-13, 0.0);
    }

    /**
     * Confirm getDegreesBrix against NIST table, available online at:
     * http://www.boulder.nist.gov/div838/SelectedPubs/Circular%20440%20Table%20114.pdf.
     * Regression fit within ±0.0015 degrees Brix considered passing.
     */
    @Test
    public void testGetBrix() {
        testGetBrix(1.00000,  0.0);
        testGetBrix(1.00390,  1.0);
        testGetBrix(1.00780,  2.0);
        testGetBrix(1.01173,  3.0);
        testGetBrix(1.01569,  4.0);
        testGetBrix(1.01968,  5.0);
        testGetBrix(1.02369,  6.0);
        testGetBrix(1.02773,  7.0);
        testGetBrix(1.03180,  8.0);
        testGetBrix(1.03590,  9.0);
        testGetBrix(1.04003, 10.0);
        testGetBrix(1.04418, 11.0);
        testGetBrix(1.04837, 12.0);
        testGetBrix(1.05259, 13.0);
        testGetBrix(1.05683, 14.0);
        testGetBrix(1.06111, 15.0);
        testGetBrix(1.06542, 16.0);
        testGetBrix(1.06976, 17.0);
        testGetBrix(1.07413, 18.0);
        testGetBrix(1.07853, 19.0);
        testGetBrix(1.08297, 20.0);
        testGetBrix(1.08744, 21.0);
        testGetBrix(1.09194, 22.0);
        testGetBrix(1.09647, 23.0);
    }

    private void testGetBrix(double sgValue, double expectedBrixValue) {
        testGetBrix(sgValue, expectedBrixValue, 0.0015);
    }

    private void testGetBrix(double sgValue, double expectedBrixValue, double tolerance) {
        SpecificGravity specificGravity = new SpecificGravity(sgValue);
        DegreesBrix actualDegreesBrix = new BrixCalculator().getDegreesBrix(specificGravity);
        assertEquals(expectedBrixValue, actualDegreesBrix.getValue(), tolerance);
    }

}
