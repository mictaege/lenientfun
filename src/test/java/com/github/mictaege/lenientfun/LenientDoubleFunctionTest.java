package com.github.mictaege.lenientfun;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;

import static com.github.mictaege.lenientfun.LenientAdapter.doubleConsumer;
import static com.github.mictaege.lenientfun.LenientAdapter.doubleFunction;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class LenientDoubleFunctionTest {

    @Mock
    private List<Double> value0;
    @Mock
    private List<Double> value1;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void shouldAcceptFunction() {
        feedLenientDoubleFunction(5.0, d -> value0.add(d));

        verify(value0).add(5.0);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleRaisedException() {
        feedLenientDoubleFunction(5.0, d -> {
            throw new Exception();
        });
    }

    @Test
    public void shouldAdaptLenientFunction() {
        feedJavaDoubleFunction(5.0, doubleFunction(d -> value0.add(d)));

        verify(value0).add(5.0);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldAdaptThrowingLenientConsumer() {
        feedJavaDoubleFunction(5.0, doubleFunction(d -> {
            throw new Exception();
        }));
    }

    private <R> R feedLenientDoubleFunction(final double value, final LenientDoubleFunction<R> function) {
        try {
            return function.apply(value);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    private <R> R feedJavaDoubleFunction(final double value, final DoubleFunction<R> function) {
        return function.apply(value);
    }

}
