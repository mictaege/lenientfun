package com.github.mictaege.lenientfun;

import org.junit.Before;
import org.junit.Test;

import java.util.function.LongBinaryOperator;

import static com.github.mictaege.lenientfun.LenientAdapter.longBinOp;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

public class LenientLongBinaryOperatorTest {

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void shouldAcceptBinaryOperator() {
        final long result = feedLenientLongBinaryOperator(1L, 2L, (l, r) -> l + r);

        assertThat(result, is(3L));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleRaisedException() {
        feedLenientLongBinaryOperator(1L, 2L, (l, r) -> {
            throw new Exception();
        });
    }

    @Test
    public void shouldAdaptLenientOperator() {
        final long result = feedJavaLongBinaryOperator(1L, 2L, longBinOp((l, r) -> l + r));

        assertThat(result, is(3L));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldAdaptThrowingLenientOperator() {
        feedJavaLongBinaryOperator(1L, 2L, longBinOp((l, r) -> {
            throw new Exception();
        }));
    }

    private long feedLenientLongBinaryOperator(final long left, final long right, final LenientLongBinaryOperator operator) {
        try {
            return operator.applyAsLong(left, right);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    private long feedJavaLongBinaryOperator(final long left, final long right, final LongBinaryOperator operator) {
        return operator.applyAsLong(left, right);
    }

}
