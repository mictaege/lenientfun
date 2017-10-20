package com.github.mictaege.lenientfun;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.function.LongPredicate;

import static com.github.mictaege.lenientfun.LenientAdapter.longPredicate;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class LenientLongPredicateTest {

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
    public void shouldAcceptLongPredicate() {
        feedLenientLongPredicate(5L, l -> value0.contains(l));

        verify(value0).contains(5L);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleRaisedException() {
        feedLenientLongPredicate(5L, l -> {
            throw new Exception();
        });
    }

    @Test
    public void shouldCombineLongPredicates() {
        final LenientLongPredicate predicateOne = l -> !value0.contains(l);
        final LenientLongPredicate predicateTwo = l -> value1.contains(l);

        feedLenientLongPredicate(5L, predicateOne.and(predicateTwo));

        verify(value0).contains(5L);
        verify(value1).contains(5L);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInFirstCombinedLongPredicate() {
        final LenientLongPredicate predicateOne = l -> { throw new Exception(); };
        final LenientLongPredicate predicateTwo = l -> value1.contains(l);

        feedLenientLongPredicate(5L, predicateOne.and(predicateTwo));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInSecondCombinedBiPredicate() {
        final LenientLongPredicate predicateOne = l -> !value0.contains(l);
        final LenientLongPredicate predicateTwo = l -> { throw new Exception(); };

        feedLenientLongPredicate(5L, predicateOne.and(predicateTwo));
    }

    @Test
    public void shouldSeparateLongPredicates() {
        final LenientLongPredicate predicateOne = l -> value0.contains(l);
        final LenientLongPredicate predicateTwo = l -> value1.contains(l);

        feedLenientLongPredicate(5L, predicateOne.or(predicateTwo));

        verify(value0).contains(5L);
        verify(value1).contains(5L);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInFirstSeparatedLongPredicate() {
        final LenientLongPredicate predicateOne = l -> { throw new Exception(); };
        final LenientLongPredicate predicateTwo = l -> value1.contains(l);

        feedLenientLongPredicate(5L, predicateOne.or(predicateTwo));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInSecondSeparatedLongPredicate() {
        final LenientLongPredicate predicateOne = l -> value0.contains(l);
        final LenientLongPredicate predicateTwo = l -> { throw new Exception(); };

        feedLenientLongPredicate(5L, predicateOne.or(predicateTwo));
    }

    @Test
    public void shouldNegateLongPredicate() {
        final LenientLongPredicate predicateOne = l -> value0.contains(l);

        feedLenientLongPredicate(5L, predicateOne.negate());

        verify(value0).contains(5L);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInNegatedLongPredicate() {
        final LenientLongPredicate predicateOne = l -> { throw new Exception();};

        feedLenientLongPredicate(5L, predicateOne.negate());
    }

    @Test
    public void shouldUseLenientLongPredicateInJava() {
        final LenientLongPredicate lenient = l -> value0.contains(l);
        feedJavaLongPredicate(5L, lenient);

        verify(value0).contains(5L);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldUseThrowingLenientBiPredicateInJava() {
        final LenientLongPredicate lenient = l -> {
            throw new Exception();
        };
        feedJavaLongPredicate(5L, lenient);
    }

    @Test
    public void shouldAdaptLenientLongPredicate() {
        feedJavaLongPredicate(5L, longPredicate(l -> value0.contains(l)));

        verify(value0).contains(5L);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldAdaptThrowingLenientBiPredicate() {
        feedJavaLongPredicate(5L, longPredicate(l -> {
            throw new Exception();
        }));
    }

    private void feedLenientLongPredicate(final long value, final LenientLongPredicate predicate) {
        try {
            predicate.test(value);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    private void feedJavaLongPredicate(final long value, final LongPredicate predicate) {
        predicate.test(value);
    }

}
