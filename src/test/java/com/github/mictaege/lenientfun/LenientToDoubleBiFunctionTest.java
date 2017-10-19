package com.github.mictaege.lenientfun;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.function.ToDoubleBiFunction;

import static com.github.mictaege.lenientfun.LenientAdapter.toDoubleBiFunc;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class LenientToDoubleBiFunctionTest {

    @Mock
    private List value0;
    @Mock
    private List value1;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void shouldAcceptToDoubleBiFunction() {
        feedLenientToDoubleBiFunction(value0, value1, (v0, v1) -> (v0.size() + v1.size()) * 2.0);

        verify(value0).size();
        verify(value1).size();
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleRaisedException() {
        feedLenientToDoubleBiFunction(value0, value1, (v0, v1) -> {
           throw new Exception();
        });
    }

    @Test
    public void shouldAdaptLenientToDoubleBiFunction() {
        feedJavaToDoubleBiFunction(value0, value1, toDoubleBiFunc((v0, v1) -> (v0.size() + v1.size()) * 2.0));

        verify(value0).size();
        verify(value1).size();
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldAdaptThrowingLenientToDoubleBiFunction() {
        feedJavaToDoubleBiFunction(value0, value1, toDoubleBiFunc((v0, v1) -> {
                throw new Exception();
        }));
    }

    private <T, U> double feedLenientToDoubleBiFunction(final T value0, final U value1, final LenientToDoubleBiFunction<T, U> function) {
        try {
            return function.applyAsDouble(value0, value1);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    private <T, U> double feedJavaToDoubleBiFunction(final T value0, final U value1, final ToDoubleBiFunction<T, U> function) {
        return function.applyAsDouble(value0, value1);
    }

}
