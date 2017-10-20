package com.github.mictaege.lenientfun;

import java.util.function.LongBinaryOperator;

/** @see java.util.function.LongBinaryOperator */
@FunctionalInterface
public interface LenientLongBinaryOperator extends LongBinaryOperator {

    @SuppressWarnings("squid:S00112")
    long applyAsLongLenient(long left, long right) throws Exception;

    @Override
    default long applyAsLong(final long left, final long right) {
        try {
            return applyAsLongLenient(left, right);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

}