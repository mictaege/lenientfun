package com.github.mictaege.lenientfun;

import java.util.function.IntToDoubleFunction;

/** @see java.util.function.IntToDoubleFunction */
@FunctionalInterface
public interface LenientIntToDoubleFunction extends IntToDoubleFunction {

    @SuppressWarnings("squid:S00112")
    double applyAsDoubleLenient(int value) throws Exception;

    @Override
    default double applyAsDouble(final int value) {
        try {
            return applyAsDoubleLenient(value);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

}
