package com.github.mictaege.lenientfun;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.function.ToIntFunction;

import static com.github.mictaege.lenientfun.LenientAdapter.toIntFunc;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class LenientToIntFunctionTest {

    @Mock
    private List value;
    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void shouldAcceptToIntFunction() {
        feedLenientToIntFunction(value, List::size);

        verify(value).size();
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleRaisedException() {
        feedLenientToIntFunction(value, v -> {
           throw new Exception();
        });
    }

    @Test
    public void shouldAdaptLenientToIntFunction() {
        feedJavaToIntFunction(value, toIntFunc(List::size));

        verify(value).size();
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldAdaptThrowingLenientToIntFunction() {
        feedJavaToIntFunction(value, toIntFunc(v -> {
                throw new Exception();
        }));
    }

    private <T> int feedLenientToIntFunction(final T value, final LenientToIntFunction<T> function) {
        try {
            return function.applyAsInt(value);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    private <T> int feedJavaToIntFunction(final T value, final ToIntFunction<T> function) {
        return function.applyAsInt(value);
    }

}
