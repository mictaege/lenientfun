package com.github.mictaege.lenientfun;

import java.util.function.DoubleBinaryOperator;

/** @see java.util.function.DoubleBinaryOperator */
@FunctionalInterface
public interface LenientDoubleBinaryOperator extends DoubleBinaryOperator {

    @SuppressWarnings("squid:S00112")
    double applyAsDoubleLenient(double left, double right) throws Exception;

    @Override
    default double applyAsDouble(final double left, final double right) {
        try {
            return applyAsDoubleLenient(left, right);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

}