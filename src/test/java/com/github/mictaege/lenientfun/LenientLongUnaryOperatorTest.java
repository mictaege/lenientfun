package com.github.mictaege.lenientfun;

import org.junit.Before;
import org.junit.Test;

import java.util.function.LongUnaryOperator;

import static com.github.mictaege.lenientfun.LenientAdapter.longUnaryOp;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

public class LenientLongUnaryOperatorTest {

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void shouldAcceptUnaryOperator() {
        final long result = feedLenientLongUnaryOperator(1L, v -> v * 2);

        assertThat(result, is(2L));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleRaisedException() {
        feedLenientLongUnaryOperator(1L, v -> {
            throw new Exception();
        });
    }

    @Test
    public void shouldComposeOperators() {
        final LenientLongUnaryOperator functionOne = v -> v * 2;
        final LenientLongUnaryOperator functionTwo = v -> v * 3;

        final long result = feedLenientLongUnaryOperator(1, functionOne.compose(functionTwo));

        assertThat(result, is(6L));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInFirstComposedOperators() {
        final LenientLongUnaryOperator functionOne = v -> { throw new Exception(); };
        final LenientLongUnaryOperator functionTwo = v -> v * 3;

        feedLenientLongUnaryOperator(1L, functionOne.compose(functionTwo));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInSecondComposedOperators() {
        final LenientLongUnaryOperator functionOne = v -> v * 3;
        final LenientLongUnaryOperator functionTwo = v -> { throw new Exception(); };

        feedLenientLongUnaryOperator(1L, functionOne.compose(functionTwo));
    }

    @Test
    public void shouldChainOperators() {
        final LenientLongUnaryOperator functionOne = v -> v * 2;
        final LenientLongUnaryOperator functionTwo = v -> v * 3;

        final long result = feedLenientLongUnaryOperator(1L, functionOne.andThen(functionTwo));

        assertThat(result, is(6L));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInFirstChainedOperators() {
        final LenientLongUnaryOperator functionOne = v -> { throw new Exception(); };
        final LenientLongUnaryOperator functionTwo = v -> v * 3;

        feedLenientLongUnaryOperator(1L, functionOne.andThen(functionTwo));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInSecondChainedOperators() {
        final LenientLongUnaryOperator functionOne = v -> v * 3;
        final LenientLongUnaryOperator functionTwo = v -> { throw new Exception(); };

        feedLenientLongUnaryOperator(1L, functionOne.andThen(functionTwo));
    }

    @Test
    public void shouldIdentify() throws Exception {
        final LenientLongUnaryOperator operator = LenientLongUnaryOperator.identity();

        assertThat(operator.applyAsLong(5L), is(5L));
    }

    @Test
    public void shouldAdaptLenientOperator() {
        final long result = feedJavaLongUnaryOperator(1L, longUnaryOp(v -> v * 2));

        assertThat(result, is(2L));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldAdaptThrowingLenientOperator() {
        feedJavaLongUnaryOperator(1L, longUnaryOp(v -> {
            throw new Exception();
        }));
    }

    private long feedLenientLongUnaryOperator(final long value, final LenientLongUnaryOperator operator) {
        try {
            return operator.applyAsLong(value);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    private long feedJavaLongUnaryOperator(final long value, final LongUnaryOperator operator) {
        return operator.applyAsLong(value);
    }

}