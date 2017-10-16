package com.github.mictaege.lenientfun;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.function.DoubleToIntFunction;

import static com.github.mictaege.lenientfun.LenientAdapter.doubleToIntFunction;
import static com.github.mictaege.lenientfun.LenientAdapter.function;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class LenientDoubleToIntFunctionTest {

    @Mock
    private List value;

    @Before
    public void before() {
        initMocks(this);
    }

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
        final int result = feedJavaDoubleToIntFunction(5.0, doubleToIntFunction(d -> ((Double)d).intValue()));

        assertThat(result, is(5));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldAdaptThrowingLenientFunction() {
        feedJavaDoubleToIntFunction(5.0, doubleToIntFunction(d -> {
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
