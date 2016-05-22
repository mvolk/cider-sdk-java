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

package com.ciderref.sdk.property.units;

import com.ciderref.sdk.math.ConversionFactorFunction;
import com.ciderref.sdk.math.Function;
import com.ciderref.sdk.math.IdentityFunction;

/**
 * Converts measurements in one unit of volume to another unit of volume. Conversion factors are drawn from the
 * 2012  NIST Handbook 44, Appendix C: General Tables of Units and Measurement, available online at
 * http://www.nist.gov/pml/wmd/pubs/upload/AppC-12-hb44-final.pdf.
 */
public class VolumeConversionFunctions extends TableOfConversionFunctions<UnitsOfVolume> {

    // The following conversion factors are exact
    protected static final double LITERS_PER_US_GALLON = 3.785411784;
    protected static final double MILLILITERS_PER_LITER = 1000.0;
    protected static final double MILLILITERS_PER_US_GALLON = 3785.411784;

    protected static final Function IDENTITY_FUNCTION = new IdentityFunction();

    protected static final Function LITERS_TO_MILLILITERS = new ConversionFactorFunction(MILLILITERS_PER_LITER);
    protected static final Function US_GALLONS_TO_LITERS = new ConversionFactorFunction(LITERS_PER_US_GALLON);
    protected static final Function US_GALLONS_TO_MILLILITERS = new ConversionFactorFunction(MILLILITERS_PER_US_GALLON);

    /** Constructor. */
    public VolumeConversionFunctions() {
        super(UnitsOfVolume.values());
        
        put(UnitsOfVolume.Liters, UnitsOfVolume.Liters, IDENTITY_FUNCTION);
        put(UnitsOfVolume.Liters, UnitsOfVolume.Milliliters, LITERS_TO_MILLILITERS);
        put(UnitsOfVolume.Liters, UnitsOfVolume.USGallons, US_GALLONS_TO_LITERS.getInverse());

        put(UnitsOfVolume.Milliliters, UnitsOfVolume.Liters, LITERS_TO_MILLILITERS.getInverse());
        put(UnitsOfVolume.Milliliters, UnitsOfVolume.Milliliters, IDENTITY_FUNCTION);
        put(UnitsOfVolume.Milliliters, UnitsOfVolume.USGallons, US_GALLONS_TO_MILLILITERS.getInverse());

        put(UnitsOfVolume.USGallons, UnitsOfVolume.Liters, US_GALLONS_TO_LITERS);
        put(UnitsOfVolume.USGallons, UnitsOfVolume.Milliliters, US_GALLONS_TO_MILLILITERS);
        put(UnitsOfVolume.USGallons, UnitsOfVolume.USGallons, IDENTITY_FUNCTION);
    }

}
