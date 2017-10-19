package com.github.mictaege.lenientfun;

import org.junit.Before;
import org.junit.Test;

import java.util.function.DoubleBinaryOperator;

import static com.github.mictaege.lenientfun.LenientAdapter.doubleBinOp;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

public class LenientDoubleBinaryOperatorTest {

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void shouldAcceptBinaryOperator() {
        final double result = feedLenientDoubleBinaryOperator(1.0, 2.0, (l, r) -> l + r);

        assertThat(result, is(3.0));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleRaisedException() {
        feedLenientDoubleBinaryOperator(1.0, 2.0, (l, r) -> {
            throw new Exception();
        });
    }

    @Test
    public void shouldAdaptLenientOperator() {
        final double result = feedJavaDoubleBinaryOperator(1.0, 2.0, doubleBinOp((l, r) -> l + r));

        assertThat(result, is(3.0));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldAdaptThrowingLenientOperator() {
        feedJavaDoubleBinaryOperator(1.0, 2.0, doubleBinOp((l, r) -> {
            throw new Exception();
        }));
    }

    private double feedLenientDoubleBinaryOperator(final double left, final double right, final LenientDoubleBinaryOperator operator) {
        try {
            return operator.applyAsDouble(left, right);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    private double feedJavaDoubleBinaryOperator(final double left, final double right, final DoubleBinaryOperator operator) {
        return operator.applyAsDouble(left, right);
    }

}
