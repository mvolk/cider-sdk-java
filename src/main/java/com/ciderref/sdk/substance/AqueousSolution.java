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
import com.ciderref.sdk.property.Volume;
import com.ciderref.sdk.property.units.UnitsOfMass;
import com.ciderref.sdk.property.units.UnitsOfVolume;

/**
 * Properties of a aqueous solution.
 */
public class AqueousSolution {
    private final SpecificGravity specificGravity;
    private final MassConcentration solids;
    private final MassConcentration sugar;
    private final Volume volume;

    /**
     *
     * @param specificGravity (not null) the specific gravity of the solution
     * @param solids (not null) the concentration of all solids in the solution
     * @param sugar (not null) the concentration of sugar in the solution
     * @param volume (not null) the volume occupied by the solution
     * @throws IllegalArgumentException if any of the parameters are null.
     */
    public AqueousSolution(SpecificGravity specificGravity,
                           MassConcentration solids,
                           MassConcentration sugar,
                           Volume volume) {
        if (specificGravity == null || solids == null || sugar == null || volume == null) {
            throw new IllegalArgumentException("None of the arguments may be null");
        }
        this.specificGravity = specificGravity;
        this.solids = solids;
        this.sugar = sugar;
        this.volume = volume;
    }

    /**
     * The solution's specific gravity.
     *
     * @return (not null) the specific gravity of the solution
     */
    public SpecificGravity getSpecificGravity() {
        return specificGravity;
    }

    /**
     * The concentration of solids in the solution.
     *
     * @return (not null) the mass of all solids in the solution divided by the volume of the solution
     */
    public MassConcentration getSolids() {
        return solids;
    }

    /**
     * The concentration of solids in the solution excluding sugar.
     *
     * @return (not null) the mass of all non-sugar solids in the solution divided by the volume of the solution
     */
    public MassConcentration getSugarFreeDryExtract() {
        return new MassConcentration(new Mass(solids.getValue(UnitsOfMass.Grams, UnitsOfVolume.Liters)
                - sugar.getValue(UnitsOfMass.Grams, UnitsOfVolume.Liters), UnitsOfMass.Grams), Volume.ONE_LITER);
    }

    /**
     * The concentration of sugar in the solution.
     *
     * @return (not null) the mass of all sugar in the solution divided by the volume of the solution
     */
    public MassConcentration getSugar() {
        return sugar;
    }

    /**
     * The volume of the solution.
     *
     * @return (not null) the volume of the solution
     */
    public Volume getVolume() {
        return volume;
    }

}
