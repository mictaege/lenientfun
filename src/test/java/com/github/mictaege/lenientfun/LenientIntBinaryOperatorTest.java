package com.github.mictaege.lenientfun;

import org.junit.Before;
import org.junit.Test;

import java.util.function.IntBinaryOperator;

import static com.github.mictaege.lenientfun.LenientAdapter.intBinOp;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

public class LenientIntBinaryOperatorTest {

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void shouldAcceptBinaryOperator() {
        final int result = feedLenientIntBinaryOperator(1, 2, (l, r) -> l + r);

        assertThat(result, is(3));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleRaisedException() {
        feedLenientIntBinaryOperator(1, 2, (l, r) -> {
            throw new Exception();
        });
    }

    @Test
    public void shouldAdaptLenientOperator() {
        final int result = feedJavaIntBinaryOperator(1, 2, intBinOp((l, r) -> l + r));

        assertThat(result, is(3));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldAdaptThrowingLenientOperator() {
        feedJavaIntBinaryOperator(1, 2, intBinOp((l, r) -> {
            throw new Exception();
        }));
    }

    private int feedLenientIntBinaryOperator(final int left, final int right, final LenientIntBinaryOperator operator) {
        try {
            return operator.applyAsInt(left, right);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    private int feedJavaIntBinaryOperator(final int left, final int right, final IntBinaryOperator operator) {
        return operator.applyAsInt(left, right);
    }

}
