package com.github.mictaege.lenientfun;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.function.ToIntBiFunction;

import static com.github.mictaege.lenientfun.LenientAdapter.toIntBiFunc;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class LenientToIntBiFunctionTest {

    @Mock
    private List value0;
    @Mock
    private List value1;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void shouldAcceptToIntBiFunction() {
        feedLenientToIntBiFunction(value0, value1, (v0, v1) -> v0.size() + v1.size());

        verify(value0).size();
        verify(value1).size();
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleRaisedException() {
        feedLenientToIntBiFunction(value0, value1, (v0, v1) -> {
           throw new Exception();
        });
    }

    @Test
    public void shouldAdaptLenientToIntBiFunction() {
        feedJavaToIntBiFunction(value0, value1, toIntBiFunc((v0, v1) -> v0.size() + v1.size()));

        verify(value0).size();
        verify(value1).size();
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldAdaptThrowingLenientToIntBiFunction() {
        feedJavaToIntBiFunction(value0, value1, toIntBiFunc((v0, v1) -> {
                throw new Exception();
        }));
    }

    private <T, U> int feedLenientToIntBiFunction(final T value0, final U value1, final LenientToIntBiFunction<T, U> function) {
        try {
            return function.applyAsInt(value0, value1);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    private <T, U> int feedJavaToIntBiFunction(final T value0, final U value1, final ToIntBiFunction<T, U> function) {
        return function.applyAsInt(value0, value1);
    }

}
