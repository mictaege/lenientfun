package com.github.mictaege.lenientfun;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.function.IntFunction;

import static com.github.mictaege.lenientfun.LenientAdapter.intFunc;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class LenientIntFunctionTest {

    @Mock
    private List<Integer> value0;
    @Mock
    private List<Integer> value1;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void shouldAcceptFunction() {
        feedLenientIntFunction(5, i -> value0.add(i));

        verify(value0).add(5);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleRaisedException() {
        feedLenientIntFunction(5, i -> {
            throw new Exception();
        });
    }

    @Test
    public void shouldAdaptLenientFunction() {
        feedJavaIntFunction(5, intFunc(i -> value0.add(i)));

        verify(value0).add(5);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldAdaptThrowingLenientFunction() {
        feedJavaIntFunction(5, intFunc(i -> {
            throw new Exception();
        }));
    }

    private <R> R feedLenientIntFunction(final int value, final LenientIntFunction<R> function) {
        try {
            return function.apply(value);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    private <R> R feedJavaIntFunction(final int value, final IntFunction<R> function) {
        return function.apply(value);
    }

}
