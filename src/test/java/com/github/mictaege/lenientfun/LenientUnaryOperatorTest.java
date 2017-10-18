package com.github.mictaege.lenientfun;

import org.junit.Before;
import org.junit.Test;

import java.util.function.UnaryOperator;

import static com.github.mictaege.lenientfun.LenientAdapter.unaryOp;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

public class LenientUnaryOperatorTest {

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void shouldAcceptUnaryOperator() {
        final int result = feedLenientUnaryOperator(1, v -> v * 2);

        assertThat(result, is(2));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleRaisedException() {
        feedLenientUnaryOperator(1, v -> {
            throw new Exception();
        });
    }

    @Test
    public void shouldIdentify() throws Exception {
        final LenientUnaryOperator operator = LenientUnaryOperator.identity();

        assertThat(operator.apply(5), is(5));
    }

    @Test
    public void shouldAdaptLenientOperator() {
        final int result = feedJavaUnaryOperator(1, unaryOp(v -> v * 2));

        assertThat(result, is(2));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldAdaptThrowingLenientOperator() {
        feedJavaUnaryOperator(1, unaryOp(v -> {
            throw new Exception();
        }));
    }

    private int feedLenientUnaryOperator(final int value, final LenientUnaryOperator<Integer> operator) {
        try {
            return operator.apply(value);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    private int feedJavaUnaryOperator(final int value, final UnaryOperator<Integer> operator) {
        return operator.apply(value);
    }

}
