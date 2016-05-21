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

import com.ciderref.sdk.property.IllegalPropertyValueException;
import com.ciderref.sdk.property.Temperature;
import com.ciderref.sdk.property.units.UnitsOfTemperature;

import java.text.DecimalFormat;

/**
 * Describes pure water.
 */
public class Water {

    /** The freezing point of pure water under standard pressure. */
    public static final Temperature STANDARD_FREEZING_POINT = new Temperature(0, UnitsOfTemperature.Celsius);

    /** The boiling point of pure water under standard pressure. */
    public static final Temperature STANDARD_BOILING_POINT = new Temperature(100, UnitsOfTemperature.Celsius);

    /**
     * The density of pure water at a given temperature. Accurate to within 0.1 g/L between 0℃ and 100℃. Accurate
     * within 0.05 g/L between 10℃ and 20℃ inclusive.
     *
     * @param temperature the temperature of the water. Valid values are between {@link #STANDARD_FREEZING_POINT} and
     *                    {@link #STANDARD_BOILING_POINT} inclusive.
     * @return the density of the water in g/L at the given temperature.
     *
     * @throws IllegalArgumentException if {@code temperature} is {@code null}
     * @throws IllegalPropertyValueException if {@code temperature} is below {@link #STANDARD_FREEZING_POINT} or
     *         exceeds {@link #STANDARD_BOILING_POINT}
     */
    public double getDensity(Temperature temperature) {
        if (temperature == null) {
            throw new IllegalArgumentException("The temperature argument may not be null.");
        }
        if (temperature.isCoolerThan(STANDARD_FREEZING_POINT) || temperature.isWarmerThan(STANDARD_BOILING_POINT)) {
            throw new IllegalPropertyValueException("The CiderRef SDK can only calculate the density of pure water "
                    + "at standard pressure in its liquid state. Pure water at standard pressure is not a liquid at "
                    + new DecimalFormat("#0.0## ℃").format(temperature.getValue(UnitsOfTemperature.Celsius)) + ".");
        }
        double degreesC = temperature.getValue(UnitsOfTemperature.Celsius);
        // The following regression was developed using MS Excel and datapoints cross-verified in multiple sources.
        // Solid from 0℃ to 50℃, but accuracy degrades a little above 50℃.
        // Higher-order polynomials performed only marginally better.
        double sg = 999.85
                + (0.0531 * degreesC)
                - (0.0075 * Math.pow(degreesC, 2.0))
                + (0.00004 * Math.pow(degreesC, 3.0))
                - (0.0000001 * Math.pow(degreesC, 4.0));
        // This regression provides correction above 50℃ that degrades above 70℃
        if (degreesC > 50) {
            double degreesCOver50 = degreesC - 50;
            double correction = 0.0299
                    + (0.0267 * degreesCOver50)
                    - (0.0019 * Math.pow(degreesCOver50, 2.0))
                    + (0.00009 * Math.pow(degreesCOver50, 3.0))
                    - (0.0000009 * Math.pow(degreesCOver50, 4.0));
            sg = sg - correction;
        }
        // This regression provides further correction above 70℃
        if (degreesC > 70) {
            double degreesCOver70 = degreesC - 70;
            double correction = 0.0463
                    + (0.0024 * degreesCOver70)
                    + (0.0006 * Math.pow(degreesCOver70, 2.0));
            sg = sg + correction;
        }
        return sg;
    }

}
