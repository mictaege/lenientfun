package com.github.mictaege.lenientfun;

import java.util.function.LongToDoubleFunction;

/** @see java.util.function.LongToDoubleFunction */
@FunctionalInterface
public interface LenientLongToDoubleFunction extends LongToDoubleFunction {

    @SuppressWarnings("squid:S00112")
    double applyAsDoubleLenient(long value) throws Exception;

    @Override
    default double applyAsDouble(final long value) {
        try {
            return applyAsDoubleLenient(value);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

}
