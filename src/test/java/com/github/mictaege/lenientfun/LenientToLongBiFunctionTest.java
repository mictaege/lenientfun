package com.github.mictaege.lenientfun;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.function.ToLongBiFunction;

import static com.github.mictaege.lenientfun.LenientAdapter.toLongBiFunc;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class LenientToLongBiFunctionTest {

    @Mock
    private List value0;
    @Mock
    private List value1;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void shouldAcceptToLongBiFunction() {
        feedLenientToLongBiFunction(value0, value1, (v0, v1) -> v0.size() + v1.size());

        verify(value0).size();
        verify(value1).size();
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleRaisedException() {
        feedLenientToLongBiFunction(value0, value1, (v0, v1) -> {
           throw new Exception();
        });
    }

    @Test
    public void shouldAdaptLenientToLongBiFunction() {
        feedJavaToLongBiFunction(value0, value1, toLongBiFunc((v0, v1) -> v0.size() + v1.size()));

        verify(value0).size();
        verify(value1).size();
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldAdaptThrowingLenientToLongBiFunction() {
        feedJavaToLongBiFunction(value0, value1, toLongBiFunc((v0, v1) -> {
                throw new Exception();
        }));
    }

    private <T, U> long feedLenientToLongBiFunction(final T value0, final U value1, final LenientToLongBiFunction<T, U> function) {
        try {
            return function.applyAsLong(value0, value1);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    private <T, U> long feedJavaToLongBiFunction(final T value0, final U value1, final ToLongBiFunction<T, U> function) {
        return function.applyAsLong(value0, value1);
    }

}
