package com.github.mictaege.lenientfun;

import java.util.function.IntToLongFunction;

/** @see java.util.function.IntToLongFunction */
@FunctionalInterface
public interface LenientIntToLongFunction extends IntToLongFunction {

    @SuppressWarnings("squid:S00112")
    long applyAsLongLenient(int value) throws Exception;

    @Override
    default long applyAsLong(final int value) {
        try {
            return applyAsLongLenient(value);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

}
