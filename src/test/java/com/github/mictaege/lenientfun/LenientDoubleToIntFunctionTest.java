package com.github.mictaege.lenientfun;

import org.junit.Test;

import java.util.function.DoubleToIntFunction;

import static com.github.mictaege.lenientfun.LenientAdapter.doubleToIntFunc;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LenientDoubleToIntFunctionTest {

    @Test
    public void shouldAcceptFunction() {
        final int result = feedLenientDoubleToIntFunction(5.0, d -> ((Double)d).intValue());

        assertThat(result, is(5));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleRaisedException() {
        feedLenientDoubleToIntFunction(5.0, d -> {
            throw new Exception();
        });
    }

    @Test
    public void shouldAdaptLenientFunction() {
        final int result = feedJavaDoubleToIntFunction(5.0, doubleToIntFunc(d -> ((Double)d).intValue()));

        assertThat(result, is(5));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldAdaptThrowingLenientFunction() {
        feedJavaDoubleToIntFunction(5.0, doubleToIntFunc(d -> {
                throw new Exception();
        }));
    }

    private int feedLenientDoubleToIntFunction(final double value, final LenientDoubleToIntFunction function) {
        try {
            return function.applyAsInt(value);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    private int feedJavaDoubleToIntFunction(final double value, DoubleToIntFunction function) {
        return function.applyAsInt(value);
    }

}
