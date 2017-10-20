package com.github.mictaege.lenientfun;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.function.LongFunction;

import static com.github.mictaege.lenientfun.LenientAdapter.longFunc;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class LenientLongFunctionTest {

    @Mock
    private List<Long> value0;
    @Mock
    private List<Long> value1;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void shouldAcceptFunction() {
        feedLenientLongFunction(5L, i -> value0.add(i));

        verify(value0).add(5L);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleRaisedException() {
        feedLenientLongFunction(5L, i -> {
            throw new Exception();
        });
    }

    @Test
    public void shouldUseLenientFunctionInJava() {
        final LenientLongFunction<Boolean> lenient = i -> value0.add(i);
        feedJavaLongFunction(5L, lenient);

        verify(value0).add(5L);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldUseThrowingLenientFunctionInJava() {
        final LenientLongFunction<Object> lenient = i -> {
            throw new Exception();
        };
        feedJavaLongFunction(5L, lenient);
    }

    @Test
    public void shouldAdaptLenientFunction() {
        feedJavaLongFunction(5L, longFunc(i -> value0.add(i)));

        verify(value0).add(5L);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldAdaptThrowingLenientFunction() {
        feedJavaLongFunction(5L, longFunc(i -> {
            throw new Exception();
        }));
    }

    private <R> R feedLenientLongFunction(final long value, final LenientLongFunction<R> function) {
        try {
            return function.apply(value);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    private <R> R feedJavaLongFunction(final long value, final LongFunction<R> function) {
        return function.apply(value);
    }

}
