package com.github.mictaege.lenientfun;

import java.util.function.IntBinaryOperator;

/** @see java.util.function.IntBinaryOperator */
@FunctionalInterface
public interface LenientIntBinaryOperator extends IntBinaryOperator {

    @SuppressWarnings("squid:S00112")
    int applyAsIntLenient(int left, int right) throws Exception;

    @Override
    default int applyAsInt(final int left, final int right) {
        try {
            return applyAsIntLenient(left, right);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

}