package com.github.mictaege.lenientfun;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleBinaryOperator;

import static com.github.mictaege.lenientfun.LenientAdapter.booleanSupplier;
import static com.github.mictaege.lenientfun.LenientAdapter.doubleBinOperator;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
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
    public void shouldAdaptLenientConsumer() {
        final double result = feedJavaDoubleBinaryOperator(1.0, 2.0, doubleBinOperator((l, r) -> l + r));

        assertThat(result, is(3.0));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldAdaptThrowingLenientConsumer() {
        feedJavaDoubleBinaryOperator(1.0, 2.0, doubleBinOperator((l, r) -> {
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
