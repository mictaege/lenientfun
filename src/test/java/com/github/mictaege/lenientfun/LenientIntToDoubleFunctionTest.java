package com.github.mictaege.lenientfun;

import org.junit.Test;

import java.util.function.IntToDoubleFunction;

import static com.github.mictaege.lenientfun.LenientAdapter.intToDoubleFunc;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LenientIntToDoubleFunctionTest {

    @Test
    public void shouldAcceptFunction() {
        final double result = feedLenientIntToDoubleFunction(5, i -> ((Integer)i).doubleValue());

        assertThat(result, is(5.0));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleRaisedException() {
        feedLenientIntToDoubleFunction(5, i -> {
            throw new Exception();
        });
    }

    @Test
    public void shouldUseLenientFunctionInJava() {
        final LenientIntToDoubleFunction lenient = i -> ((Integer) i).doubleValue();
        final double result = feedJavaIntToDoubleFunction(5, lenient);

        assertThat(result, is(5.0));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldUseThrowingLenientFunctionInJava() {
        final LenientIntToDoubleFunction lenient = i -> {
            throw new Exception();
        };
        feedJavaIntToDoubleFunction(5, lenient);
    }

    @Test
    public void shouldAdaptLenientFunction() {
        final double result = feedJavaIntToDoubleFunction(5, intToDoubleFunc(i -> ((Integer)i).doubleValue()));

        assertThat(result, is(5.0));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldAdaptThrowingLenientFunction() {
        feedJavaIntToDoubleFunction(5, intToDoubleFunc(i -> {
                throw new Exception();
        }));
    }

    private double feedLenientIntToDoubleFunction(final int value, final LenientIntToDoubleFunction function) {
        try {
            return function.applyAsDouble(value);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    private double feedJavaIntToDoubleFunction(final int value, final IntToDoubleFunction function) {
        return function.applyAsDouble(value);
    }

}
