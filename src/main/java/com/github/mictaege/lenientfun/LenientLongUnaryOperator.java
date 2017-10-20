package com.github.mictaege.lenientfun;

import java.util.Objects;
import java.util.function.LongUnaryOperator;

/** @see java.util.function.LongUnaryOperator */
@FunctionalInterface
public interface LenientLongUnaryOperator extends LongUnaryOperator {

    @SuppressWarnings("squid:S00112")
    long applyAsLongLenient(long operand) throws Exception;

    @Override
    default long applyAsLong(final long operand) {
        try {
            return applyAsLongLenient(operand);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    default LenientLongUnaryOperator compose(final LenientLongUnaryOperator before) {
        Objects.requireNonNull(before);
        return (long v) -> applyAsLongLenient(before.applyAsLongLenient(v));
    }

    default LenientLongUnaryOperator andThen(final LenientLongUnaryOperator after) {
        Objects.requireNonNull(after);
        return (long t) -> after.applyAsLongLenient(applyAsLongLenient(t));
    }

    static LenientLongUnaryOperator identity() {
        return t -> t;
    }

}
