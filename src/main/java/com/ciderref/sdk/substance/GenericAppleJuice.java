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

import com.ciderref.sdk.calculate.BrixCalculator;
import com.ciderref.sdk.property.Mass;
import com.ciderref.sdk.property.MassConcentration;
import com.ciderref.sdk.property.SpecificGravity;
import com.ciderref.sdk.property.SugarConcentrationProfile;
import com.ciderref.sdk.property.Volume;
import com.ciderref.sdk.property.units.UnitsOfMass;

/**
 * Properties of an average apple juice. If you were to blend juices from America, the United Kingdom, France and
 * Germany, you would get something like this. Supporting analysis and test case data drawn from the New Cider Maker's
 * Handbook by Claude Jolicoeur, chapter 8.3.
 */
public class GenericAppleJuice implements AppleJuice {

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public SugarConcentrationProfile getSugarConcentrationProfile() {
        return new SugarConcentrationProfile(2130, 120);
    }

    /**
     * {@inheritDoc}
     *
     * @param specificGravity (not null) actual specific gravity.
     * @return {@inheritDoc}
     */
    @Override
    public final MassConcentration getTotalSolidsConcentration(SpecificGravity specificGravity) {
        if (specificGravity == null) {
            throw new IllegalArgumentException("Specific gravity is required.");
        }
        double sg = specificGravity.getValue();
        double brix = new BrixCalculator().getDegreesBrix(specificGravity).getValue();
        double pw = Water.DENSITY_AT_20_DEGREES_CELSIUS.getValueInGramsPerLiter();
        double density = sg * pw;
        double totalSolids = density * brix / 100;
        return new MassConcentration(new Mass(totalSolids, UnitsOfMass.Grams), Volume.ONE_LITER);
    }

}
