package com.github.mictaege.lenientfun;

import org.junit.Test;

import java.util.function.IntToLongFunction;

import static com.github.mictaege.lenientfun.LenientAdapter.intToLongFunc;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LenientIntToLongFunctionTest {

    @Test
    public void shouldAcceptFunction() {
        final long result = feedLenientIntToLongFunction(5, i -> ((Integer)i).longValue());

        assertThat(result, is(5L));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleRaisedException() {
        feedLenientIntToLongFunction(5, i -> {
            throw new Exception();
        });
    }

    @Test
    public void shouldUseLenientFunctionInJava() {
        final LenientIntToLongFunction lenient = i -> ((Integer) i).longValue();
        final long result = feedJavaIntToLongFunction(5, lenient);

        assertThat(result, is(5L));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldUseThrowingLenientFunctionInJava() {
        final LenientIntToLongFunction lenient = i -> {
            throw new Exception();
        };
        feedJavaIntToLongFunction(5, lenient);
    }

    @Test
    public void shouldAdaptLenientFunction() {
        final long result = feedJavaIntToLongFunction(5, intToLongFunc(i -> ((Integer)i).longValue()));

        assertThat(result, is(5L));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldAdaptThrowingLenientFunction() {
        feedJavaIntToLongFunction(5, intToLongFunc(i -> {
                throw new Exception();
        }));
    }

    private long feedLenientIntToLongFunction(final int value, final LenientIntToLongFunction function) {
        try {
            return function.applyAsLong(value);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    private long feedJavaIntToLongFunction(final int value, final IntToLongFunction function) {
        return function.applyAsLong(value);
    }

}