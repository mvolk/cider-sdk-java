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

import com.ciderref.sdk.property.MassConcentration;
import com.ciderref.sdk.property.PercentAlcoholByVolume;
import com.ciderref.sdk.property.SpecificGravity;
import com.ciderref.sdk.property.units.UnitsOfMass;
import com.ciderref.sdk.property.units.UnitsOfVolume;
import com.ciderref.sdk.substance.AppleJuice;

/**
 * Calculator for potential alcohol by volume.
 */
public class PotentialAlcoholByVolume {
    private final AppleJuice appleJuice;

    /**
     * Constructor.
     *
     * @param appleJuice (not null) the type of apple juice being evaluated
     * @throws IllegalArgumentException if {@code appleJuice} is {@code null}.
     */
    public PotentialAlcoholByVolume(AppleJuice appleJuice) {
        if (appleJuice == null) {
            throw new IllegalArgumentException("Properties of the apple juice are required by this calculator");
        }
        this.appleJuice = appleJuice;
    }

    /**
     * The minimum ABV that will be produced if the juice is fermented to dryness.
     *
     * @param specificGravity (not null) unfermented apple juice's specific gravity
     * @return (not null) minimum %ABV that will result assuming fermentation to dryness
     * @throws IllegalArgumentException if {@code specificGravity} is {@code null}.
     */
    public PercentAlcoholByVolume getMinimum(SpecificGravity specificGravity) {
        requireSpecificGravity(specificGravity);
        return new PercentAlcoholByVolume(calculateAbv(
                appleJuice.getSugarConcentrationProfile().getMinimumSugarConcentration(specificGravity)));
    }

    /**
     * The average ABV that will be produced if the juice is fermented to dryness.
     *
     * @param specificGravity (not null) unfermented apple juice's specific gravity
     * @return (not null) average %ABV that will result assuming fermentation to dryness
     * @throws IllegalArgumentException if {@code specificGravity} is {@code null}.
     */
    public PercentAlcoholByVolume getAverage(SpecificGravity specificGravity) {
        requireSpecificGravity(specificGravity);
        return new PercentAlcoholByVolume(calculateAbv(
                appleJuice.getSugarConcentrationProfile().getAverageSugarConcentration(specificGravity)));
    }

    /**
     * The maximum ABV that will be produced if the juice is fermented to dryness.
     *
     * @param specificGravity (not null) unfermented apple juice's specific gravity
     * @return (not null) maximum %ABV that will result assuming fermentation to dryness
     * @throws IllegalArgumentException if {@code specificGravity} is {@code null}.
     */
    public PercentAlcoholByVolume getMaximum(SpecificGravity specificGravity) {
        requireSpecificGravity(specificGravity);
        return new PercentAlcoholByVolume(calculateAbv(
                appleJuice.getSugarConcentrationProfile().getMaximumSugarConcentration(specificGravity)));
    }

    private void requireSpecificGravity(SpecificGravity specificGravity) {
        if (specificGravity == null) {
            throw new IllegalArgumentException("Specific gravity required to determine sugar content for potential "
                    + "ABV calculation");
        }
    }

    private double calculateAbv(MassConcentration sugarContent) {
        return 0.06 * sugarContent.getValue(UnitsOfMass.Grams, UnitsOfVolume.Liters);
    }
}
