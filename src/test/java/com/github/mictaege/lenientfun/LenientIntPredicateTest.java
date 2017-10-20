package com.github.mictaege.lenientfun;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.function.IntPredicate;

import static com.github.mictaege.lenientfun.LenientAdapter.intPredicate;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class LenientIntPredicateTest {

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
    public void shouldAcceptIntPredicate() {
        feedLenientIntPredicate(5, i -> value0.contains(i));

        verify(value0).contains(5);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleRaisedException() {
        feedLenientIntPredicate(5, i -> {
            throw new Exception();
        });
    }

    @Test
    public void shouldCombineIntPredicates() {
        final LenientIntPredicate predicateOne = i -> !value0.contains(i);
        final LenientIntPredicate predicateTwo = i -> value1.contains(i);

        feedLenientIntPredicate(5, predicateOne.and(predicateTwo));

        verify(value0).contains(5);
        verify(value1).contains(5);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInFirstCombinedIntPredicate() {
        final LenientIntPredicate predicateOne = i -> { throw new Exception(); };
        final LenientIntPredicate predicateTwo = i -> value1.contains(i);

        feedLenientIntPredicate(5, predicateOne.and(predicateTwo));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInSecondCombinedBiPredicate() {
        final LenientIntPredicate predicateOne = i -> !value0.contains(i);
        final LenientIntPredicate predicateTwo = i -> { throw new Exception(); };

        feedLenientIntPredicate(5, predicateOne.and(predicateTwo));
    }

    @Test
    public void shouldSeparateIntPredicates() {
        final LenientIntPredicate predicateOne = i -> value0.contains(i);
        final LenientIntPredicate predicateTwo = i -> value1.contains(i);

        feedLenientIntPredicate(5, predicateOne.or(predicateTwo));

        verify(value0).contains(5);
        verify(value1).contains(5);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInFirstSeparatedIntPredicate() {
        final LenientIntPredicate predicateOne = i -> { throw new Exception(); };
        final LenientIntPredicate predicateTwo = i -> value1.contains(i);

        feedLenientIntPredicate(5, predicateOne.or(predicateTwo));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInSecondSeparatedIntPredicate() {
        final LenientIntPredicate predicateOne = i -> value0.contains(i);
        final LenientIntPredicate predicateTwo = i -> { throw new Exception(); };

        feedLenientIntPredicate(5, predicateOne.or(predicateTwo));
    }

    @Test
    public void shouldNegateIntPredicate() {
        final LenientIntPredicate predicateOne = i -> value0.contains(i);

        feedLenientIntPredicate(5, predicateOne.negate());

        verify(value0).contains(5);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInNegatedIntPredicate() {
        final LenientIntPredicate predicateOne = i -> { throw new Exception();};

        feedLenientIntPredicate(5, predicateOne.negate());
    }

    @Test
    public void shouldUseLenientIntPredicateInJaca() {
        final LenientIntPredicate lenient = i -> value0.contains(i);
        feedJavaIntPredicate(5, lenient);

        verify(value0).contains(5);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldUseThrowingLenientBiPredicateInJava() {
        final LenientIntPredicate lenient = i -> {
            throw new Exception();
        };
        feedJavaIntPredicate(5, lenient);
    }

    @Test
    public void shouldAdaptLenientIntPredicate() {
        feedJavaIntPredicate(5, intPredicate(i -> value0.contains(i)));

        verify(value0).contains(5);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldAdaptThrowingLenientBiPredicate() {
        feedJavaIntPredicate(5, intPredicate(i -> {
            throw new Exception();
        }));
    }

    private void feedLenientIntPredicate(final int value, final LenientIntPredicate predicate) {
        try {
            predicate.test(value);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    private void feedJavaIntPredicate(final int value, final IntPredicate predicate) {
        predicate.test(value);
    }

}
