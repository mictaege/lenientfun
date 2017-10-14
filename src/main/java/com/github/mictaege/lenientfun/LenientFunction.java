package com.github.mictaege.lenientfun;

import java.util.Objects;

/** @see java.util.function.Function */
@FunctionalInterface
public interface LenientFunction<T, R> {

    @SuppressWarnings("squid:S00112")
    R apply(T t) throws Exception;

    default <V> LenientFunction<V, R> compose(final LenientFunction<? super V, ? extends T> before) {
        Objects.requireNonNull(before);
        return (V v) -> apply(before.apply(v));
    }

    default <V> LenientFunction<T, V> andThen(final LenientFunction<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (T t) -> after.apply(apply(t));
    }

    static <T> LenientFunction<T, T> identity() {
        return t -> t;
    }

}
