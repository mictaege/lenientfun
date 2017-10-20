package com.github.mictaege.lenientfun;

import java.util.function.IntFunction;

/** @see java.util.function.IntFunction */
@FunctionalInterface
public interface LenientIntFunction<R> extends IntFunction<R> {

    @SuppressWarnings("squid:S00112")
    R applyLenient(int value) throws Exception;

    @Override
    default R apply(final int value) {
        try {
            return applyLenient(value);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

}
