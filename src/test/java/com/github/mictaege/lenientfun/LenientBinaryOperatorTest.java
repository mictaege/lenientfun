package com.github.mictaege.lenientfun;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BinaryOperator;

import static com.github.mictaege.lenientfun.LenientAdapter.binOperator;
import static com.github.mictaege.lenientfun.LenientBinaryOperator.maxBy;
import static com.github.mictaege.lenientfun.LenientBinaryOperator.minBy;
import static java.util.Comparator.comparingInt;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class LenientBinaryOperatorTest {

    @Mock
    private List value0;
    @Mock
    private List value1;
    @Mock
    private Object element;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void shouldAcceptBiFunction() {
        feedLenientBinaryOperator(value0, value1, (v0, v1) -> {
            final List sum = new ArrayList();
            sum.add(v0.size());
            sum.add(v1.size());
            return sum;
        });

        verify(value0).size();
        verify(value1).size();
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleRaisedException() {
        feedLenientBinaryOperator(value0, value1, (v0, v1) -> {
           throw new Exception();
        });
    }

    @Test
    public void shouldChainBinaryOperator() {
        final LenientBinaryOperator<List> operatorOne = (v0, v1) -> {
            final List sum = new ArrayList();
            sum.add(v0.size());
            sum.add(v1.size());
            return sum;
        };
        final LenientFunction<List, List> operatorTwo = v -> {
            final List sum = new ArrayList();
            sum.add(v.size());
            return sum;
        };

        feedLenientBiFunction(value0, value1, operatorOne.andThen(operatorTwo));

        verify(value0).size();
        verify(value1).size();
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInFirstChainedBinaryOperator() {
        final LenientBinaryOperator<List> operatorOne = (v0, v1) -> { throw new Exception(); };
        final LenientFunction<List, List> operatorTwo = v -> {
            final List sum = new ArrayList();
            sum.add(v.size());
            return sum;
        };

        feedLenientBiFunction(value0, value1, operatorOne.andThen(operatorTwo));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInSecondChainedBinaryOperator() {
        final LenientBinaryOperator<List> operatorOne = (v0, v1) -> {
            final List sum = new ArrayList();
            sum.add(v0.size());
            sum.add(v1.size());
            return sum;
        };
        final LenientFunction<List, List> operatorTwo = v -> { throw new Exception(); };

        feedLenientBiFunction(value0, value1, operatorOne.andThen(operatorTwo));
    }

    @Test
    public void shouldCompareByMin() {
        feedLenientBiFunction(value0, value1, minBy(comparingInt(List::size)));

        verify(value0).size();
        verify(value1).size();
    }

    @Test
    public void shouldCompareByMax() {
        feedLenientBiFunction(value0, value1, maxBy(comparingInt(List::size)));

        verify(value0).size();
        verify(value1).size();
    }

    @Test
    public void shouldAdaptLenientBinaryOperator() {
        feedJavaBinaryOperator(value0, value1, binOperator((v0, v1) -> {
            final List sum = new ArrayList();
            sum.add(v0.size());
            sum.add(v1.size());
            return sum;
        }));

        verify(value0).size();
        verify(value1).size();
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldAdaptThrowingLenientBinaryOperator() {
        feedJavaBinaryOperator(value0, value1, binOperator((v0, v1) -> {
                throw new Exception();
        }));
    }

    private <T> T feedLenientBinaryOperator(final T value0, final T value1, final LenientBinaryOperator<T> function) {
        try {
            return function.apply(value0, value1);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    private <T> T feedJavaBinaryOperator(final T value0, final T value1, final BinaryOperator<T> function) {
        return function.apply(value0, value1);
    }

    private <T, U, R> R feedLenientBiFunction(final T value0, final U value1, final LenientBiFunction<T, U, R> function) {
        try {
            return function.apply(value0, value1);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

}
