package com.github.mictaege.lenientfun;

import java.util.Objects;
import java.util.function.IntUnaryOperator;

/** @see java.util.function.IntUnaryOperator */
@FunctionalInterface
public interface LenientIntUnaryOperator extends IntUnaryOperator {

    @SuppressWarnings("squid:S00112")
    int applyAsIntLenient(int operand) throws Exception;

    @Override
    default int applyAsInt(final int operand) {
        try {
            return applyAsIntLenient(operand);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    default LenientIntUnaryOperator compose(final LenientIntUnaryOperator before) {
        Objects.requireNonNull(before);
        return (int v) -> applyAsIntLenient(before.applyAsIntLenient(v));
    }

    default LenientIntUnaryOperator andThen(final LenientIntUnaryOperator after) {
        Objects.requireNonNull(after);
        return (int t) -> after.applyAsIntLenient(applyAsIntLenient(t));
    }

    static LenientIntUnaryOperator identity() {
        return t -> t;
    }

}
