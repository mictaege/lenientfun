package com.github.mictaege.lenientfun;

import java.util.function.ToLongBiFunction;

/** @see java.util.function.ToLongBiFunction */
@FunctionalInterface
public interface LenientToLongBiFunction<T, U> extends ToLongBiFunction<T, U> {

    @SuppressWarnings("squid:S00112")
    long applyAsLongLenient(T t, U u) throws Exception;

    @Override
    default long applyAsLong(final T t, final U u) {
        try {
            return applyAsLongLenient(t, u);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

}
