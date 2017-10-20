package com.github.mictaege.lenientfun;

import java.util.function.DoubleToIntFunction;

/** @see java.util.function.DoubleToIntFunction */
@FunctionalInterface
public interface LenientDoubleToIntFunction extends DoubleToIntFunction {

    @SuppressWarnings("squid:S00112")
    int applyAsIntLenient(double value) throws Exception;

    @Override
    default int applyAsInt(final double value) {
        try {
            return applyAsIntLenient(value);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

}
