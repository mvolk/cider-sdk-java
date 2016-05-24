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

import java.util.ArrayList;
import java.util.List;

/**
 * Describes the sweetness of a cider using commonly used (but imprecisely and inconsistently defined) classifications.
 */
public enum Sweetness {
    Dry("Dry", new SpecificGravity(0.990), new SpecificGravity(1.004)),
    OffDry("Off Dry", new SpecificGravity(1.004), new SpecificGravity(1.009)),
    MediumDry("Medium Dry", new SpecificGravity(1.009), new SpecificGravity(1.015)),
    MediumSweet("Medium Sweet", new SpecificGravity(1.015), new SpecificGravity(1.020)),
    Sweet("Sweet", new SpecificGravity(1.020), new SpecificGravity(1.100));

    private final String displayName;
    private final SpecificGravity minimumSg;
    private final SpecificGravity maximumSg;

    Sweetness(String displayName, SpecificGravity minimumSg, SpecificGravity maximumSg) {
        this.displayName = displayName;
        this.minimumSg = minimumSg;
        this.maximumSg = maximumSg;
    }

    /**
     * The name of this sweetness classification as typically used by people.
     *
     * @return (not null) the human-friendly name of this sweetness classification
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * The minimum specific gravity of ciders that fall into this sweetness classification.
     *
     * @return (not null) the lowest SG that a cider in this sweetness category can have.
     */
    public SpecificGravity getMinimumSpecificGravity() {
        return minimumSg;
    }

    /**
     * The maximum specific gravity of ciders that fall into this sweetness classification.
     *
     * @return (not null) the highest SG that a cider in this sweetness category can have.
     */
    public SpecificGravity getMaximumSpecificGravity() {
        return maximumSg;
    }

    /**
     * All sweetness categories that may be used to describe a cider with the given specific gravity (SG).
     *
     * @param specificGravity (not null) a specific gravity
     * @return (not null) a list, possibly empty, of all sweetness classifications that may be used to describe a
     *         cider with the given {@code specificGravity}.
     * @throws IllegalArgumentException if {@code specificGravity} is null.
     */
    public static List<Sweetness> allValuesOf(SpecificGravity specificGravity) {
        if (specificGravity == null) {
            throw new IllegalArgumentException("Sweetness cannot be determined without knowing the specific gravity.");
        }
        List<Sweetness> sweetnesses = new ArrayList<>();
        for (Sweetness sweetness : Sweetness.values()) {
            if (specificGravity.compareTo(sweetness.minimumSg) >= 0
                    && specificGravity.compareTo(sweetness.maximumSg) <= 0) {
                sweetnesses.add(sweetness);
            }
        }
        return sweetnesses;
    }

}
