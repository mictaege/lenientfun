package com.github.mictaege.lenientfun;

import org.junit.Before;
import org.junit.Test;

import java.util.function.DoubleUnaryOperator;

import static com.github.mictaege.lenientfun.LenientAdapter.doubleUnaryOp;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

public class LenientDoubleUnaryOperatorTest {

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void shouldAcceptUnaryOperator() {
        final double result = feedLenientDoubleUnaryOperator(1.0, v -> v * 2);

        assertThat(result, is(2.0));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleRaisedException() {
        feedLenientDoubleUnaryOperator(1.0, v -> {
            throw new Exception();
        });
    }

    @Test
    public void shouldComposeOperators() {
        final LenientDoubleUnaryOperator functionOne = v -> v * 2;
        final LenientDoubleUnaryOperator functionTwo = v -> v * 3;

        final double result = feedLenientDoubleUnaryOperator(1, functionOne.compose(functionTwo));

        assertThat(result, is(6.0));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInFirstComposedOperators() {
        final LenientDoubleUnaryOperator functionOne = v -> { throw new Exception(); };
        final LenientDoubleUnaryOperator functionTwo = v -> v * 3;

        feedLenientDoubleUnaryOperator(1, functionOne.compose(functionTwo));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInSecondComposedOperators() {
        final LenientDoubleUnaryOperator functionOne = v -> v * 3;
        final LenientDoubleUnaryOperator functionTwo = v -> { throw new Exception(); };

        feedLenientDoubleUnaryOperator(1, functionOne.compose(functionTwo));
    }

    @Test
    public void shouldChainOperators() {
        final LenientDoubleUnaryOperator functionOne = v -> v * 2;
        final LenientDoubleUnaryOperator functionTwo = v -> v * 3;

        final double result = feedLenientDoubleUnaryOperator(1, functionOne.andThen(functionTwo));

        assertThat(result, is(6.0));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInFirstChainedOperators() {
        final LenientDoubleUnaryOperator functionOne = v -> { throw new Exception(); };
        final LenientDoubleUnaryOperator functionTwo = v -> v * 3;

        feedLenientDoubleUnaryOperator(1, functionOne.andThen(functionTwo));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInSecondChainedOperators() {
        final LenientDoubleUnaryOperator functionOne = v -> v * 3;
        final LenientDoubleUnaryOperator functionTwo = v -> { throw new Exception(); };

        feedLenientDoubleUnaryOperator(1, functionOne.andThen(functionTwo));
    }

    @Test
    public void shouldIdentify() throws Exception {
        final LenientDoubleUnaryOperator operator = LenientDoubleUnaryOperator.identity();

        assertThat(operator.applyAsDouble(5.0), is(5.0));
    }

    @Test
    public void shouldAdaptLenientOperator() {
        final double result = feedJavaDoubleUnaryOperator(1.0, doubleUnaryOp(v -> v * 2));

        assertThat(result, is(2.0));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldAdaptThrowingLenientOperator() {
        feedJavaDoubleUnaryOperator(1.0, doubleUnaryOp(v -> {
            throw new Exception();
        }));
    }

    private double feedLenientDoubleUnaryOperator(final double value, final LenientDoubleUnaryOperator operator) {
        try {
            return operator.applyAsDouble(value);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    private double feedJavaDoubleUnaryOperator(final double value, final DoubleUnaryOperator operator) {
        return operator.applyAsDouble(value);
    }

}
