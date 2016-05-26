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

import com.ciderref.sdk.property.Mass;
import com.ciderref.sdk.property.MassConcentration;
import com.ciderref.sdk.property.SpecificGravity;
import com.ciderref.sdk.property.Temperature;
import com.ciderref.sdk.property.Volume;
import com.ciderref.sdk.property.units.UnitsOfMass;
import com.ciderref.sdk.property.units.UnitsOfTemperature;
import com.ciderref.sdk.property.units.UnitsOfVolume;

/**
 * Properties of an average apple juice. If you were to blend juices from America, the United Kingdom, France and
 * Germany, you would get something like this. Supporting analysis and test case data drawn from the New Cider Maker's
 * Handbook by Claude Jolicoeur, chapter 8.3.
 */
public class GenericAppleJuice implements AppleJuice {

    private static final Volume ONE_LITER = new Volume(1, UnitsOfVolume.Liters);

    /**
     * {@inheritDoc}
     *
     * @param specificGravity (not null) actual specific gravity.
     * @return {@inheritDoc}
     */
    @Override
    public MassConcentration getAverageSugarConcentration(SpecificGravity specificGravity) {
        requireSpecificGravity(specificGravity);
        double sg = specificGravity.getValue();
        double sc = getAverageSugarCoefficient();
        return new MassConcentration(new Mass(sc * (sg - 1), UnitsOfMass.Grams), ONE_LITER);
    }

    /**
     * {#inheritDoc}
     *
     * @param specificGravity (not null) actual specific gravity.
     * @return {@inheritDoc}
     */
    @Override
    public MassConcentration getMinimumSugarConcentration(SpecificGravity specificGravity) {
        requireSpecificGravity(specificGravity);
        double sg = specificGravity.getValue();
        double sc = getAverageSugarCoefficient() - 2 * getSugarCoefficientStandardDeviation();
        return new MassConcentration(new Mass(sc * (sg - 1), UnitsOfMass.Grams), ONE_LITER);
    }

    /**
     * {@inheritDoc}
     *
     * @param specificGravity (not null) actual specific gravity.
     * @return {@inheritDoc}
     */
    @Override
    public MassConcentration getMaximumSugarConcentration(SpecificGravity specificGravity) {
        requireSpecificGravity(specificGravity);
        double sg = specificGravity.getValue();
        double sc = getAverageSugarCoefficient() + 2 * getSugarCoefficientStandardDeviation();
        return new MassConcentration(new Mass(sc * (sg - 1), UnitsOfMass.Grams), ONE_LITER);
    }

    /**
     * {@inheritDoc}
     *
     * @param specificGravity (not null) actual specific gravity.
     * @return {@inheritDoc}
     */
    @Override
    public MassConcentration getTotalSolidsConcentration(SpecificGravity specificGravity) {
        requireSpecificGravity(specificGravity);
        double sg = specificGravity.getValue();
        double brix = new SugarWater().getBrix(specificGravity).getValue();
        double pw = new Water().getDensity(new Temperature(20, UnitsOfTemperature.Celsius))
                .getValue(UnitsOfMass.Grams, UnitsOfVolume.Liters);
        double density = sg * pw;
        double totalSolids = density * brix / 100;
        return new MassConcentration(new Mass(totalSolids, UnitsOfMass.Grams), new Volume(1, UnitsOfVolume.Liters));
    }

    private void requireSpecificGravity(SpecificGravity specificGravity) {
        if (specificGravity == null) {
            throw new IllegalArgumentException("Specific gravity is required.");
        }
    }

    protected double getAverageSugarCoefficient() {
        return 2130;
    }

    protected double getSugarCoefficientStandardDeviation() {
        return 120;
    }

}
