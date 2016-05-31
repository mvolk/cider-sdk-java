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

import com.ciderref.sdk.property.Mass;
import com.ciderref.sdk.property.MassConcentration;
import com.ciderref.sdk.property.PercentAlcoholByVolume;
import com.ciderref.sdk.property.Volume;
import com.ciderref.sdk.property.units.UnitsOfMass;

/**
 * Calculator for potential alcohol by volume. Calculations use the formula given by Warcollier in La Cidrerie (1928).
 */
public class PotentialAlcoholCalculator {

    /**
     * Obtain the potential percent alcohol by volume if all sugar is fermented out.
     *
     * @param sugarConcentration (not null) the mass concentration of sugar in the aqueous solution
     * @return (not null) potential alcohol by volume at 20 degrees C assuming all sugar is fermented
     * @throws NullPointerException if {@code sugarConcentration} is null.
     */
    public PercentAlcoholByVolume getPotentialAlcohol(MassConcentration sugarConcentration) {
        return new PercentAlcoholByVolume(0.06 * sugarConcentration.getValueInGramsPerLiter());
    }

    /**
     * Obtain the sugar concentration required to attain a given potential alcohol concentration.
     *
     * @param targetAbv (not null) the percent alcohol by volume desired at the end of fermentation
     * @return (not null) the fermentable sugar concentration required to attain the {@code targetAbv}
     * @throws NullPointerException if {@code targetAbv} is null.
     */
    public MassConcentration getSugarConcentration(PercentAlcoholByVolume targetAbv) {
        return new MassConcentration(new Mass(targetAbv.getValue() / 0.06, UnitsOfMass.Grams), Volume.ONE_LITER);
    }

}
