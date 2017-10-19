package com.github.mictaege.lenientfun;

import java.util.Objects;

/** @see java.util.function.DoubleUnaryOperator */
@FunctionalInterface
public interface LenientDoubleUnaryOperator {

    @SuppressWarnings("squid:S00112")
    double applyAsDouble(double operand) throws Exception;

    default LenientDoubleUnaryOperator compose(final LenientDoubleUnaryOperator before) {
        Objects.requireNonNull(before);
        return (double v) -> applyAsDouble(before.applyAsDouble(v));
    }

    default LenientDoubleUnaryOperator andThen(final LenientDoubleUnaryOperator after) {
        Objects.requireNonNull(after);
        return (double t) -> after.applyAsDouble(applyAsDouble(t));
    }

    static LenientDoubleUnaryOperator identity() {
        return t -> t;
    }

}
