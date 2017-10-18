package com.github.mictaege.lenientfun;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.function.ToDoubleFunction;

import static com.github.mictaege.lenientfun.LenientAdapter.toDoubleFunc;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class LenientToDoubleFunctionTest {

    @Mock
    private List value;
    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void shouldAcceptToDoubleFunction() {
        feedLenientToDoubleFunction(value, v -> v.size() * 2.0);

        verify(value).size();
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleRaisedException() {
        feedLenientToDoubleFunction(value, v -> {
           throw new Exception();
        });
    }

    @Test
    public void shouldAdaptLenientToDoubleFunction() {
        feedJavaToDoubleFunction(value, toDoubleFunc(v -> v.size() * 2.0));

        verify(value).size();
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldAdaptThrowingLenientToDoubleFunction() {
        feedJavaToDoubleFunction(value, toDoubleFunc(v -> {
                throw new Exception();
        }));
    }

    private <T> double feedLenientToDoubleFunction(final T value, final LenientToDoubleFunction<T> function) {
        try {
            return function.applyAsDouble(value);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    private <T> double feedJavaToDoubleFunction(final T value, final ToDoubleFunction<T> function) {
        return function.applyAsDouble(value);
    }

}
