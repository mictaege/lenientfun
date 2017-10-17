package com.github.mictaege.lenientfun;

import org.junit.Test;

import java.util.function.LongToIntFunction;

import static com.github.mictaege.lenientfun.LenientAdapter.longToIntFunc;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LenientLongToIntFunctionTest {

    @Test
    public void shouldAcceptFunction() {
        final int result = feedLenientLongToIntFunction(5L, l -> ((Long)l).intValue());

        assertThat(result, is(5));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleRaisedException() {
        feedLenientLongToIntFunction(5L, l -> {
            throw new Exception();
        });
    }

    @Test
    public void shouldAdaptLenientFunction() {
        final int result = feedJavaLongToIntFunction(5L, longToIntFunc(l -> ((Long)l).intValue()));

        assertThat(result, is(5));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldAdaptThrowingLenientFunction() {
        feedJavaLongToIntFunction(5L, longToIntFunc(l -> {
                throw new Exception();
        }));
    }

    private int feedLenientLongToIntFunction(final long value, final LenientLongToIntFunction function) {
        try {
            return function.applyAsInt(value);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    private int feedJavaLongToIntFunction(final long value, final LongToIntFunction function) {
        return function.applyAsInt(value);
    }

}
