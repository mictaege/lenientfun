package com.github.mictaege.lenientfun;

import org.junit.Test;

import java.util.function.LongToDoubleFunction;

import static com.github.mictaege.lenientfun.LenientAdapter.longToDoubleFunc;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LenientLongToDoubleFunctionTest {

    @Test
    public void shouldAcceptFunction() {
        final double result = feedLenientLongToDoubleFunction(5L, i -> ((Long)i).doubleValue());

        assertThat(result, is(5.0));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleRaisedException() {
        feedLenientLongToDoubleFunction(5L, i -> {
            throw new Exception();
        });
    }

    @Test
    public void shouldAdaptLenientFunction() {
        final double result = feedJavaLongToDoubleFunction(5L, longToDoubleFunc(i -> ((Long)i).doubleValue()));

        assertThat(result, is(5.0));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldAdaptThrowingLenientFunction() {
        feedJavaLongToDoubleFunction(5L, longToDoubleFunc(i -> {
                throw new Exception();
        }));
    }

    private double feedLenientLongToDoubleFunction(final long value, final LenientLongToDoubleFunction function) {
        try {
            return function.applyAsDouble(value);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    private double feedJavaLongToDoubleFunction(final long value, final LongToDoubleFunction function) {
        return function.applyAsDouble(value);
    }

}