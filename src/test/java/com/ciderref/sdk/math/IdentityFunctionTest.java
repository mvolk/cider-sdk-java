package com.ciderref.sdk.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;

/**
 * Unit tests for {@link IdentityFunction}.
 */
public class IdentityFunctionTest {

    /** Confirm that when passed a value the applyTo(double) function returns that argument. */
    @Test
    public void testApplyToWithZero() {
        assertEquals(0, new IdentityFunction().applyTo(0), 0);
    }

    /** Confirm that when passed a value the applyTo(double) function returns that argument. */
    @Test
    public void testApplyToWithNegative() {
        assertEquals(-5.2129, new IdentityFunction().applyTo(-5.2129), 0);
    }

    /** Confirm that when passed a value the applyTo(double) function returns that argument. */
    @Test
    public void testApplyToWithPositive() {
        assertEquals(912348.492, new IdentityFunction().applyTo(912348.492), 0);
    }

    /** Confirm that the inverse of the identity function is the identity function. */
    @Test
    public void testInverseIsSelf() {
        Function identityFunction = new IdentityFunction();
        assertSame(identityFunction.getInverse(), identityFunction);
    }

}
