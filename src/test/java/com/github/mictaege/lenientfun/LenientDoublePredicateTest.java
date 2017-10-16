package com.github.mictaege.lenientfun;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.DoublePredicate;

import static com.github.mictaege.lenientfun.LenientAdapter.biPredicate;
import static com.github.mictaege.lenientfun.LenientAdapter.doublePredicate;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class LenientDoublePredicateTest {

    @Mock
    private List value0;
    @Mock
    private List value1;

    @Before
    public void before() {
        initMocks(this);
        when(value0.size()).thenReturn(1);
        when(value1.size()).thenReturn(1);
        when(value0.isEmpty()).thenReturn(true);
        when(value1.isEmpty()).thenReturn(true);
    }

    @Test
    public void shouldAcceptDoublePredicate() {
        feedLenientDoublePredicate(5.0, d -> value0.contains(d));

        verify(value0).contains(5.0);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleRaisedException() {
        feedLenientDoublePredicate(5.0, d -> {
            throw new Exception();
        });
    }

    @Test
    public void shouldCombineDoublePredicates() {
        final LenientDoublePredicate predicateOne = d -> !value0.contains(d);
        final LenientDoublePredicate predicateTwo = d -> value1.contains(d);

        feedLenientDoublePredicate(5.0, predicateOne.and(predicateTwo));

        verify(value0).contains(5.0);
        verify(value1).contains(5.0);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInFirstCombinedDoublePredicate() {
        final LenientDoublePredicate predicateOne = d -> { throw new Exception(); };
        final LenientDoublePredicate predicateTwo = d -> value1.contains(d);

        feedLenientDoublePredicate(5.0, predicateOne.and(predicateTwo));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInSecondCombinedBiPredicate() {
        final LenientDoublePredicate predicateOne = d -> !value0.contains(d);
        final LenientDoublePredicate predicateTwo = d -> { throw new Exception(); };

        feedLenientDoublePredicate(5.0, predicateOne.and(predicateTwo));
    }

    @Test
    public void shouldSeparateDoublePredicates() {
        final LenientDoublePredicate predicateOne = d -> value0.contains(d);
        final LenientDoublePredicate predicateTwo = d -> value1.contains(d);

        feedLenientDoublePredicate(5.0, predicateOne.or(predicateTwo));

        verify(value0).contains(5.0);
        verify(value1).contains(5.0);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInFirstSeparatedDoublePredicate() {
        final LenientDoublePredicate predicateOne = d -> { throw new Exception(); };
        final LenientDoublePredicate predicateTwo = d -> value1.contains(d);

        feedLenientDoublePredicate(5.0, predicateOne.or(predicateTwo));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInSecondSeparatedDoublePredicate() {
        final LenientDoublePredicate predicateOne = d -> value0.contains(d);
        final LenientDoublePredicate predicateTwo = d -> { throw new Exception(); };

        feedLenientDoublePredicate(5.0, predicateOne.or(predicateTwo));
    }

    @Test
    public void shouldNegateDoublePredicate() {
        final LenientDoublePredicate predicateOne = d -> value0.contains(d);

        feedLenientDoublePredicate(5.0, predicateOne.negate());

        verify(value0).contains(5.0);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInNegatedDoublePredicate() {
        final LenientDoublePredicate predicateOne = d -> { throw new Exception();};

        feedLenientDoublePredicate(5.0, predicateOne.negate());
    }

    @Test
    public void shouldAdaptLenientDoublePredicate() {
        feedJavaDoublePredicate(5.0, doublePredicate(d -> value0.contains(d)));

        verify(value0).contains(5.0);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldAdaptThrowingLenientBiPredicate() {
        feedJavaDoublePredicate(5.0, doublePredicate(d -> {
            throw new Exception();
        }));
    }

    private void feedLenientDoublePredicate(final double value, final LenientDoublePredicate predicate) {
        try {
            predicate.test(value);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    private void feedJavaDoublePredicate(final double value, final DoublePredicate predicate) {
        predicate.test(value);
    }

}
