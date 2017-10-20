package com.github.mictaege.lenientfun;

import java.util.Objects;
import java.util.function.BiFunction;

/** @see java.util.function.BiFunction */
@FunctionalInterface
public interface LenientBiFunction<T, U, R> extends BiFunction<T, U, R> {

    @SuppressWarnings("squid:S00112")
    R applyLenient(T t, U u) throws Exception;

    @Override
    default R apply(final T t, final U u) {
        try {
            return applyLenient(t, u);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    default <V> LenientBiFunction<T, U, V> andThen(final LenientFunction<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (T t, U u) -> after.applyLenient(applyLenient(t, u));
    }

}
