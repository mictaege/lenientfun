package com.github.mictaege.lenientfun;

import java.util.Objects;

/** @see java.util.function.IntUnaryOperator */
@FunctionalInterface
public interface LenientIntUnaryOperator {

    @SuppressWarnings("squid:S00112")
    int applyAsInt(int operand) throws Exception;

    default LenientIntUnaryOperator compose(final LenientIntUnaryOperator before) {
        Objects.requireNonNull(before);
        return (int v) -> applyAsInt(before.applyAsInt(v));
    }

    default LenientIntUnaryOperator andThen(final LenientIntUnaryOperator after) {
        Objects.requireNonNull(after);
        return (int t) -> after.applyAsInt(applyAsInt(t));
    }

    static LenientIntUnaryOperator identity() {
        return t -> t;
    }

}
