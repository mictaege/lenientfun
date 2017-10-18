package com.github.mictaege.lenientfun;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.function.Predicate;

import static com.github.mictaege.lenientfun.LenientAdapter.predicate;
import static com.github.mictaege.lenientfun.LenientPredicate.isEqual;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class LenientPredicateTest {

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
    public void shouldAcceptPredicate() {
        feedLenientPredicate(5, i -> value0.contains(i));

        verify(value0).contains(5);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleRaisedException() {
        feedLenientPredicate(5, i -> {
            throw new Exception();
        });
    }

    @Test
    public void shouldCombinePredicates() {
        final LenientPredicate<Integer> predicateOne = i -> !value0.contains(i);
        final LenientPredicate<Integer> predicateTwo = i -> value1.contains(i);

        feedLenientPredicate(5, predicateOne.and(predicateTwo));

        verify(value0).contains(5);
        verify(value1).contains(5);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInFirstCombinedPredicate() {
        final LenientPredicate<Integer> predicateOne = i -> { throw new Exception(); };
        final LenientPredicate<Integer> predicateTwo = i -> value1.contains(i);

        feedLenientPredicate(5, predicateOne.and(predicateTwo));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInSecondCombinedBiPredicate() {
        final LenientPredicate<Integer> predicateOne = i -> !value0.contains(i);
        final LenientPredicate<Integer> predicateTwo = i -> { throw new Exception(); };

        feedLenientPredicate(5, predicateOne.and(predicateTwo));
    }

    @Test
    public void shouldSeparatePredicates() {
        final LenientPredicate<Integer> predicateOne = i -> value0.contains(i);
        final LenientPredicate<Integer> predicateTwo = i -> value1.contains(i);

        feedLenientPredicate(5, predicateOne.or(predicateTwo));

        verify(value0).contains(5);
        verify(value1).contains(5);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInFirstSeparatedPredicate() {
        final LenientPredicate<Integer> predicateOne = i -> { throw new Exception(); };
        final LenientPredicate<Integer> predicateTwo = i -> value1.contains(i);

        feedLenientPredicate(5, predicateOne.or(predicateTwo));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInSecondSeparatedPredicate() {
        final LenientPredicate<Integer> predicateOne = i -> value0.contains(i);
        final LenientPredicate<Integer> predicateTwo = i -> { throw new Exception(); };

        feedLenientPredicate(5, predicateOne.or(predicateTwo));
    }

    @Test
    public void shouldNegatePredicate() {
        final LenientPredicate<Integer> predicateOne = i -> value0.contains(i);

        feedLenientPredicate(5, predicateOne.negate());

        verify(value0).contains(5);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInNegatedPredicate() {
        final LenientPredicate<Integer> predicateOne = i -> { throw new Exception();};

        feedLenientPredicate(5, predicateOne.negate());
    }

    @Test
    public void shouldProvideIsEqualPredicate() throws Exception {
        assertThat(isEqual(5).test(5), is(true));
        assertThat(isEqual(5).test(4), is(false));
        assertThat(isEqual(5).test(null), is(false));
        assertThat(isEqual(null).test(5), is(false));
        assertThat(isEqual(null).test(null), is(true));
    }

    @Test
    public void shouldAdaptLenientPredicate() {
        feedJavaPredicate(5, predicate(i -> value0.contains(i)));

        verify(value0).contains(5);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldAdaptThrowingLenientBiPredicate() {
        feedJavaPredicate(5, predicate(i -> {
            throw new Exception();
        }));
    }

    private void feedLenientPredicate(final Integer value, final LenientPredicate<Integer> predicate) {
        try {
            predicate.test(value);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    private void feedJavaPredicate(final Integer value, final Predicate predicate) {
        predicate.test(value);
    }

}
