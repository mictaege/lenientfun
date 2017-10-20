package com.github.mictaege.lenientfun;

import java.util.function.ToDoubleFunction;

/** @see java.util.function.ToDoubleFunction */
@FunctionalInterface
public interface LenientToDoubleFunction<T> extends ToDoubleFunction<T> {

    @SuppressWarnings("squid:S00112")
    double applyAsDoubleLenient(T value) throws Exception;

    @Override
    default double applyAsDouble(final T value) {
        try {
            return applyAsDoubleLenient(value);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

}
