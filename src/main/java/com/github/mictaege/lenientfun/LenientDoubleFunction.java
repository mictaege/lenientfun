package com.github.mictaege.lenientfun;

import java.util.function.DoubleFunction;

/** @see java.util.function.DoubleFunction */
@FunctionalInterface
public interface LenientDoubleFunction<R> extends DoubleFunction<R> {

    @SuppressWarnings("squid:S00112")
    R applyLenient(double value) throws Exception;

    @Override
    default R apply(final double value) {
        try {
            return applyLenient(value);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

}
