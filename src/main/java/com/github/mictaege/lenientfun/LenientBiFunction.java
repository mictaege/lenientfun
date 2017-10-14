package com.github.mictaege.lenientfun;

import java.util.Objects;

/** @see java.util.function.BiFunction */
@FunctionalInterface
public interface LenientBiFunction<T, U, R> {

    @SuppressWarnings("squid:S00112")
    R apply(T t, U u) throws Exception;

    default <V> LenientBiFunction<T, U, V> andThen(final LenientFunction<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (T t, U u) -> after.apply(apply(t, u));
    }

}
