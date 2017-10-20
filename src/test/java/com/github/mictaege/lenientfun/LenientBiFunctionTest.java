package com.github.mictaege.lenientfun;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.function.BiFunction;

import static com.github.mictaege.lenientfun.LenientAdapter.biFunc;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class LenientBiFunctionTest {

    @Mock
    private List value0;
    @Mock
    private List value1;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void shouldAcceptBiFunction() {
        feedLenientBiFunction(value0, value1, (v0, v1) -> v0.size() + v1.size());

        verify(value0).size();
        verify(value1).size();
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleRaisedException() {
        feedLenientBiFunction(value0, value1, (v0, v1) -> {
           throw new Exception();
        });
    }

    @Test
    public void shouldChainBiFunction() {
        final LenientBiFunction<List, List, Integer> functionOne = (v0, v1) -> v0.size() + v1.size();
        final LenientFunction<Integer, Integer> functionTwo = LenientFunction.identity();

        feedLenientBiFunction(value0, value1, functionOne.andThen(functionTwo));

        verify(value0).size();
        verify(value1).size();
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInFirstChainedBiFunction() {
        final LenientBiFunction<List, List, Integer> functionOne = (v0, v1) -> { throw new Exception(); };
        final LenientFunction<Integer, Integer> functionTwo = LenientFunction.identity();

        feedLenientBiFunction(value0, value1, functionOne.andThen(functionTwo));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInSecondChainedBiFunction() {
        final LenientBiFunction<List, List, Integer> functionOne = (v0, v1) -> v0.size() + v1.size();
        final LenientFunction<Integer, Integer> functionTwo = v -> { throw new Exception(); };

        feedLenientBiFunction(value0, value1, functionOne.andThen(functionTwo));
    }

    @Test
    public void shouldUseLenientBiFunctionInJava() {
        final LenientBiFunction<List, List, Integer> lenient = (v0, v1) -> v0.size() + v1.size();
        feedJavaBiFunction(value0, value1, lenient);

        verify(value0).size();
        verify(value1).size();
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldUseThrowingLenientBiFunctionInJava() {
        final LenientBiFunction<List, List, Object> lenient = (v0, v1) -> {
            throw new Exception();
        };
        feedJavaBiFunction(value0, value1, biFunc(lenient));
    }

    @Test
    public void shouldAdaptLenientBiFunction() {
        feedJavaBiFunction(value0, value1, biFunc((v0, v1) -> v0.size() + v1.size()));

        verify(value0).size();
        verify(value1).size();
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldAdaptThrowingLenientBiFunction() {
        feedJavaBiFunction(value0, value1, biFunc((v0, v1) -> {
                throw new Exception();
        }));
    }

    private <T, U, R> R feedLenientBiFunction(final T value0, final U value1, final LenientBiFunction<T, U, R> function) {
        try {
            return function.apply(value0, value1);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    private <T, U, R> R feedJavaBiFunction(final T value0, final U value1, final BiFunction<T, U, R> function) {
        return function.apply(value0, value1);
    }

}
