package com.github.mictaege.lenientfun;

import org.junit.Before;
import org.junit.Test;

import java.util.function.IntUnaryOperator;

import static com.github.mictaege.lenientfun.LenientAdapter.intUnaryOp;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

public class LenientIntUnaryOperatorTest {

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void shouldAcceptUnaryOperator() {
        final int result = feedLenientIntUnaryOperator(1, v -> v * 2);

        assertThat(result, is(2));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleRaisedException() {
        feedLenientIntUnaryOperator(1, v -> {
            throw new Exception();
        });
    }

    @Test
    public void shouldComposeOperators() {
        final LenientIntUnaryOperator functionOne = v -> v * 2;
        final LenientIntUnaryOperator functionTwo = v -> v * 3;

        final int result = feedLenientIntUnaryOperator(1, functionOne.compose(functionTwo));

        assertThat(result, is(6));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInFirstComposedOperators() {
        final LenientIntUnaryOperator functionOne = v -> { throw new Exception(); };
        final LenientIntUnaryOperator functionTwo = v -> v * 3;

        feedLenientIntUnaryOperator(1, functionOne.compose(functionTwo));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInSecondComposedOperators() {
        final LenientIntUnaryOperator functionOne = v -> v * 3;
        final LenientIntUnaryOperator functionTwo = v -> { throw new Exception(); };

        feedLenientIntUnaryOperator(1, functionOne.compose(functionTwo));
    }

    @Test
    public void shouldChainOperators() {
        final LenientIntUnaryOperator functionOne = v -> v * 2;
        final LenientIntUnaryOperator functionTwo = v -> v * 3;

        final int result = feedLenientIntUnaryOperator(1, functionOne.andThen(functionTwo));

        assertThat(result, is(6));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInFirstChainedOperators() {
        final LenientIntUnaryOperator functionOne = v -> { throw new Exception(); };
        final LenientIntUnaryOperator functionTwo = v -> v * 3;

        feedLenientIntUnaryOperator(1, functionOne.andThen(functionTwo));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInSecondChainedOperators() {
        final LenientIntUnaryOperator functionOne = v -> v * 3;
        final LenientIntUnaryOperator functionTwo = v -> { throw new Exception(); };

        feedLenientIntUnaryOperator(1, functionOne.andThen(functionTwo));
    }

    @Test
    public void shouldIdentify() throws Exception {
        final LenientIntUnaryOperator operator = LenientIntUnaryOperator.identity();

        assertThat(operator.applyAsInt(5), is(5));
    }

    @Test
    public void shouldAdaptLenientOperator() {
        final int result = feedJavaIntUnaryOperator(1, intUnaryOp(v -> v * 2));

        assertThat(result, is(2));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldAdaptThrowingLenientOperator() {
        feedJavaIntUnaryOperator(1, intUnaryOp(v -> {
            throw new Exception();
        }));
    }

    private int feedLenientIntUnaryOperator(final int value, final LenientIntUnaryOperator operator) {
        try {
            return operator.applyAsInt(value);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    private int feedJavaIntUnaryOperator(final int value, final IntUnaryOperator operator) {
        return operator.applyAsInt(value);
    }

}
