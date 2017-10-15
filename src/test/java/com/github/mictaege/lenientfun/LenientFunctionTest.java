package com.github.mictaege.lenientfun;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import static com.github.mictaege.lenientfun.LenientAdapter.function;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class LenientFunctionTest {

    @Spy
    private ArrayList value;
    @Mock
    private Iterator iterator;

    @Before
    public void before() {
        initMocks(this);
        when(value.iterator()).thenReturn(iterator);
        when(iterator.hasNext()).thenReturn(true);
    }

    @Test
    public void shouldAcceptFunction() {
        feedLenientFunction(value, List::iterator);

        verify(value).iterator();
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleRaisedException() {
        feedLenientFunction(value, v -> {
            throw new Exception();
        });
    }

    @Test
    public void shouldComposeFunctions() {
        final LenientFunction<List, List> functionOne = ArrayList::new;
        final LenientFunction<List, List> functionTwo = ArrayList::new;

        final List result = feedLenientFunction(value, functionOne.compose(functionTwo));

        assertThat(result, is(notNullValue()));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInFirstComposedFunction() {
        final LenientFunction<List, List> functionOne = v -> { throw new Exception(); };
        final LenientFunction<List, List> functionTwo = ArrayList::new;

        feedLenientFunction(value, functionOne.compose(functionTwo));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInSecondComposedFunction() {
        final LenientFunction<List, List> functionOne = ArrayList::new;
        final LenientFunction<List, List> functionTwo = v -> { throw new Exception(); };

        feedLenientFunction(value, functionOne.compose(functionTwo));
    }

    @Test
    public void shouldChainFunction() {
        final LenientFunction<List, Iterator> functionOne = List::iterator;
        final LenientFunction<Iterator, Boolean> functionTwo = Iterator::hasNext;

        feedLenientFunction(value, functionOne.andThen(functionTwo));

        verify(value).iterator();
        verify(iterator).hasNext();
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInFirstChainedFunction() {
        final LenientFunction<List, Iterator> functionOne = v -> { throw new Exception(); };
        final LenientFunction<Iterator, Boolean> functionTwo = Iterator::hasNext;

        feedLenientFunction(value, functionOne.andThen(functionTwo));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInSecondChainedFunction() {
        final LenientFunction<List, Iterator> functionOne = List::iterator;
        final LenientFunction<Iterator, Boolean> functionTwo = v -> { throw new Exception(); };

        feedLenientFunction(value, functionOne.andThen(functionTwo));
    }

    @Test
    public void shouldIdentify() throws Exception {
        final LenientFunction<List, List> function = LenientFunction.identity();

        assertThat(function.apply(value), is(value));
    }

    @Test
    public void shouldAdaptLenientFunction() {
        feedJavaFunction(value, function(List::iterator));

        verify(value).iterator();
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldAdaptThrowingLenientFunction() {
        feedJavaFunction(value, function(v -> {
                throw new Exception();
        }));
    }

    private <T, R> R feedLenientFunction(final T value, final LenientFunction<T, R> function) {
        try {
            return function.apply(value);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    private <T, R> R feedJavaFunction(final T value, final Function<T, R> function) {
        return function.apply(value);
    }

}
