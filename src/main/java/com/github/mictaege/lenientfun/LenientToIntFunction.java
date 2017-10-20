package com.github.mictaege.lenientfun;

import java.util.function.ToIntFunction;

/** @see java.util.function.ToIntFunction */
@FunctionalInterface
public interface LenientToIntFunction<T> extends ToIntFunction<T> {

    @SuppressWarnings("squid:S00112")
    int applyAsIntLenient(T value) throws Exception;

    @Override
    default int applyAsInt(final T value) {
        try {
            return applyAsIntLenient(value);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

}
