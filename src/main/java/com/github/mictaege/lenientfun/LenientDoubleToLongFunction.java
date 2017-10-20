package com.github.mictaege.lenientfun;

import java.util.function.DoubleToLongFunction;

/** @see java.util.function.DoubleToLongFunction */
@FunctionalInterface
public interface LenientDoubleToLongFunction extends DoubleToLongFunction {

    @SuppressWarnings("squid:S00112")
    long applyAsLongLenient(double value) throws Exception;

    @Override
    default long applyAsLong(final double value) {
        try {
            return applyAsLongLenient(value);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

}
