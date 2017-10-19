package com.github.mictaege.lenientfun;

import org.junit.Test;

import java.util.function.DoubleToLongFunction;

import static com.github.mictaege.lenientfun.LenientAdapter.doubleToLongFunc;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LenientDoubleToLongFunctionTest {

    @Test
    public void shouldAcceptFunction() {
        final long result = feedLenientDoubleToLongFunction(5.0, d -> ((Double)d).longValue());

        assertThat(result, is(5L));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleRaisedException() {
        feedLenientDoubleToLongFunction(5.0, d -> {
            throw new Exception();
        });
    }

    @Test
    public void shouldAdaptLenientFunction() {
        final long result = feedJavaDoubleToLongFunction(5.0, doubleToLongFunc(d -> ((Double)d).longValue()));

        assertThat(result, is(5L));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldAdaptThrowingLenientFunction() {
        feedJavaDoubleToLongFunction(5.0, doubleToLongFunc(d -> {
                throw new Exception();
        }));
    }

    private long feedLenientDoubleToLongFunction(final double value, final LenientDoubleToLongFunction function) {
        try {
            return function.applyAsLong(value);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    private long feedJavaDoubleToLongFunction(final double value, final DoubleToLongFunction function) {
        return function.applyAsLong(value);
    }

}
