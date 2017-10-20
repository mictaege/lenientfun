package com.github.mictaege.lenientfun;

import java.util.function.ToIntBiFunction;

/** @see java.util.function.ToIntBiFunction */
@FunctionalInterface
public interface LenientToIntBiFunction<T, U> extends ToIntBiFunction<T, U> {

    @SuppressWarnings("squid:S00112")
    int applyAsIntLenient(T t, U u) throws Exception;

    @Override
    default int applyAsInt(final T t, final U u) {
        try {
            return applyAsIntLenient(t, u);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

}
