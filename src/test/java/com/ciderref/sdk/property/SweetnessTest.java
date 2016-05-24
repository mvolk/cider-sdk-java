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

import org.junit.Test;

/**
 * Unit tests for {@link Sweetness}.
 */
@SuppressWarnings("PMD.TooManyMethods")
public class SweetnessTest {

    /** Ensure that valueOf 'Dry' is correct. */
    @Test
    public void testValueOfDry() {
        assertEquals(Sweetness.Dry, Sweetness.valueOf("Dry"));
    }

    /** Ensure that the display name of 'Dry' is correct. */
    @Test
    public void testDryDisplayName() {
        assertEquals("Dry", Sweetness.Dry.getDisplayName());
    }

    /** Ensure that the minimum SG of 'Dry' is correct. */
    @Test
    public void testDryMinimumSg() {
        assertEquals(0.990, Sweetness.Dry.getMinimumSpecificGravity().getValue(), 0);
    }

    /** Ensure that the maximum SG of 'Dry' is correct. */
    @Test
    public void testDryMaximumSg() {
        assertEquals(1.004, Sweetness.Dry.getMaximumSpecificGravity().getValue(), 0);
    }

    /** Ensure that valueOf 'OffDry' is correct. */
    @Test
    public void testValueOfOffDry() {
        assertEquals(Sweetness.OffDry, Sweetness.valueOf("OffDry"));
    }

    /** Ensure that the display name of 'OffDry' is correct. */
    @Test
    public void testOffDryDisplayName() {
        assertEquals("Off Dry", Sweetness.OffDry.getDisplayName());
    }

    /** Ensure that the minimum SG of 'OffDry' is correct. */
    @Test
    public void testOffDryMinimumSg() {
        assertEquals(1.004, Sweetness.OffDry.getMinimumSpecificGravity().getValue(), 0);
    }

    /** Ensure that the maximum SG of 'OffDry' is correct. */
    @Test
    public void testOffDryMaximumSg() {
        assertEquals(1.009, Sweetness.OffDry.getMaximumSpecificGravity().getValue(), 0);
    }

    /** Ensure that valueOf 'MediumDry' is correct. */
    @Test
    public void testValueOfMediumDry() {
        assertEquals(Sweetness.MediumDry, Sweetness.valueOf("MediumDry"));
    }

    /** Ensure that the display name of 'MediumDry' is correct. */
    @Test
    public void testMediumDryDisplayName() {
        assertEquals("Medium Dry", Sweetness.MediumDry.getDisplayName());
    }

    /** Ensure that the minimum SG of 'MediumDry' is correct. */
    @Test
    public void testMediumDryMinimumSg() {
        assertEquals(1.009, Sweetness.MediumDry.getMinimumSpecificGravity().getValue(), 0);
    }

    /** Ensure that the maximum SG of 'MediumDry' is correct. */
    @Test
    public void testMediumDryMaximumSg() {
        assertEquals(1.015, Sweetness.MediumDry.getMaximumSpecificGravity().getValue(), 0);
    }

    /** Ensure that valueOf 'MediumSweet' is correct. */
    @Test
    public void testValueOfMediumSweet() {
        assertEquals(Sweetness.MediumSweet, Sweetness.valueOf("MediumSweet"));
    }

    /** Ensure that the display name of 'MediumSweet' is correct. */
    @Test
    public void testMediumSweetDisplayName() {
        assertEquals("Medium Sweet", Sweetness.MediumSweet.getDisplayName());
    }

    /** Ensure that the minimum SG of 'MediumSweet' is correct. */
    @Test
    public void testMediumSweetMinimumSg() {
        assertEquals(1.015, Sweetness.MediumSweet.getMinimumSpecificGravity().getValue(), 0);
    }

    /** Ensure that the maximum SG of 'MediumSweet' is correct. */
    @Test
    public void testMediumSweetMaximumSg() {
        assertEquals(1.020, Sweetness.MediumSweet.getMaximumSpecificGravity().getValue(), 0);
    }

    /** Ensure that valueOf 'Sweet' is correct. */
    @Test
    public void testValueOfSweet() {
        assertEquals(Sweetness.Sweet, Sweetness.valueOf("Sweet"));
    }

    /** Ensure that the display name of 'Sweet' is correct. */
    @Test
    public void testSweetDisplayName() {
        assertEquals("Sweet", Sweetness.Sweet.getDisplayName());
    }

    /** Ensure that the minimum SG of 'Sweet' is correct. */
    @Test
    public void testSweetMinimumSg() {
        assertEquals(1.020, Sweetness.Sweet.getMinimumSpecificGravity().getValue(), 0);
    }

    /** Ensure that the maximum SG of 'Sweet' is correct. */
    @Test
    public void testSweetMaximumSg() {
        assertEquals(1.100, Sweetness.Sweet.getMaximumSpecificGravity().getValue(), 0);
    }

    /** Ensure that allValuesOf is correct when there is only one matching sweetness. */
    @Test
    public void testAllValuesOfWithOneMatch() {
        assertEquals(1, Sweetness.allValuesOf(new SpecificGravity(1.003)).size());
    }

    /** Ensure that allValuesOf correctly selects a sweetness for a given SG. */
    @Test
    public void testAllValuesCorrectMatch() {
        assertEquals(Sweetness.MediumSweet, Sweetness.allValuesOf(new SpecificGravity(1.018)).get(0));
    }

    /** Ensure that allValuesOf is correct when there are multiple matching sweetnesses. */
    @Test
    public void testAllValuesOfWithMultipleMatchs() {
        assertEquals(2, Sweetness.allValuesOf(new SpecificGravity(1.009)).size());
    }

}
