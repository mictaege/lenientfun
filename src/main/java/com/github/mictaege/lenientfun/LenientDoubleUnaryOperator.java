package com.github.mictaege.lenientfun;

import java.util.Objects;
import java.util.function.DoubleUnaryOperator;

/** @see java.util.function.DoubleUnaryOperator */
@FunctionalInterface
public interface LenientDoubleUnaryOperator extends DoubleUnaryOperator {

    @SuppressWarnings("squid:S00112")
    double applyAsDoubleLenient(double operand) throws Exception;

    @Override
    default double applyAsDouble(final double operand) {
        try {
            return applyAsDoubleLenient(operand);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    default LenientDoubleUnaryOperator compose(final LenientDoubleUnaryOperator before) {
        Objects.requireNonNull(before);
        return (double v) -> applyAsDoubleLenient(before.applyAsDoubleLenient(v));
    }

    default LenientDoubleUnaryOperator andThen(final LenientDoubleUnaryOperator after) {
        Objects.requireNonNull(after);
        return (double t) -> after.applyAsDoubleLenient(applyAsDoubleLenient(t));
    }

    static LenientDoubleUnaryOperator identity() {
        return t -> t;
    }

}
