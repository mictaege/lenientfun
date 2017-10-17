package com.github.mictaege.lenientfun;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.function.BiPredicate;

import static com.github.mictaege.lenientfun.LenientAdapter.biPredicate;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class LenientBiPredicateTest {

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
    public void shouldAcceptBiPredicate() {
        feedLenientBiPredicate(value0, value1, (v0, v1) -> v0.size() < v1.size());

        verify(value0).size();
        verify(value1).size();
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleRaisedException() {
        feedLenientBiPredicate(value0, value1, (v0, v1) -> {
            throw new Exception();
        });
    }

    @Test
    public void shouldCombineBiPredicates() {
        final LenientBiPredicate<List, List> predicateOne = (v0, v1) -> v0.size() == v1.size();
        final LenientBiPredicate<List, List> predicateTwo = (v0, v1) -> v0.isEmpty() && v1.isEmpty();

        feedLenientBiPredicate(value0, value1, predicateOne.and(predicateTwo));

        verify(value0).size();
        verify(value1).size();
        verify(value0).isEmpty();
        verify(value1).isEmpty();
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInFirstCombinedBiPredicate() {
        final LenientBiPredicate<List, List> predicateOne = (v0, v1) -> { throw new Exception();};
        final LenientBiPredicate<List, List> predicateTwo = (v0, v1) -> v0.isEmpty() && v1.isEmpty();

        feedLenientBiPredicate(value0, value1, predicateOne.and(predicateTwo));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInSecondCombinedBiPredicate() {
        final LenientBiPredicate<List, List> predicateOne = (v0, v1) -> v0.size() == v1.size();
        final LenientBiPredicate<List, List> predicateTwo = (v0, v1) -> { throw new Exception(); };

        feedLenientBiPredicate(value0, value1, predicateOne.and(predicateTwo));
    }

    @Test
    public void shouldSeparateBiPredicates() {
        final LenientBiPredicate<List, List> predicateOne = (v0, v1) -> v0.size() == v1.size();
        final LenientBiPredicate<List, List> predicateTwo = (v0, v1) -> v0.isEmpty() && v1.isEmpty();

        feedLenientBiPredicate(value0, value1, predicateOne.or(predicateTwo));

        verify(value0).size();
        verify(value1).size();
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInFirstSeparatedBiPredicate() {
        final LenientBiPredicate<List, List> predicateOne = (v0, v1) -> { throw new Exception();};
        final LenientBiPredicate<List, List> predicateTwo = (v0, v1) -> v0.isEmpty() && v1.isEmpty();

        feedLenientBiPredicate(value0, value1, predicateOne.or(predicateTwo));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInSecondSeparatedBiPredicate() {
        final LenientBiPredicate<List, List> predicateOne = (v0, v1) -> v0.size() != v1.size();
        final LenientBiPredicate<List, List> predicateTwo = (v0, v1) -> { throw new Exception(); };

        feedLenientBiPredicate(value0, value1, predicateOne.or(predicateTwo));
    }

    @Test
    public void shouldNegateBiPredicate() {
        final LenientBiPredicate<List, List> predicateOne = (v0, v1) -> v0.size() == v1.size();

        feedLenientBiPredicate(value0, value1, predicateOne.negate());

        verify(value0).size();
        verify(value1).size();
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInNegatedBiPredicate() {
        final LenientBiPredicate<List, List> predicateOne = (v0, v1) -> { throw new Exception();};

        feedLenientBiPredicate(value0, value1, predicateOne.negate());
    }

    @Test
    public void shouldAdaptLenientBiPredicate() {
        feedJavaBiPredicate(value0, value1, biPredicate((v0, v1) -> v0.size() < v1.size()));

        verify(value0).size();
        verify(value1).size();
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldAdaptThrowingLenientBiPredicate() {
        feedJavaBiPredicate(value0, value1, biPredicate((v0, v1) -> {
            throw new Exception();
        }));
    }

    private <T, U> void feedLenientBiPredicate(final T value0, final U value1, final LenientBiPredicate<T, U> predicate) {
        try {
            predicate.test(value0, value1);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    private <T, U> void feedJavaBiPredicate(final T value0, final U value1, final BiPredicate<T, U> predicate) {
        predicate.test(value0, value1);
    }

}
