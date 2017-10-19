package com.github.mictaege.lenientfun;

import java.util.Objects;

/** @see java.util.function.LongUnaryOperator */
@FunctionalInterface
public interface LenientLongUnaryOperator {

    @SuppressWarnings("squid:S00112")
    long applyAsLong(long operand) throws Exception;

    default LenientLongUnaryOperator compose(final LenientLongUnaryOperator before) {
        Objects.requireNonNull(before);
        return (long v) -> applyAsLong(before.applyAsLong(v));
    }

    default LenientLongUnaryOperator andThen(final LenientLongUnaryOperator after) {
        Objects.requireNonNull(after);
        return (long t) -> after.applyAsLong(applyAsLong(t));
    }

    static LenientLongUnaryOperator identity() {
        return t -> t;
    }

}
