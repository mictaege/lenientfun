package com.github.mictaege.lenientfun;

import java.util.Objects;
import java.util.function.Function;

/** @see java.util.function.Function */
@FunctionalInterface
public interface LenientFunction<T, R> extends Function<T, R> {

    @SuppressWarnings("squid:S00112")
    R applyLenient(T t) throws Exception;

    @Override
    default R apply(final T t) {
        try {
            return applyLenient(t);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    default <V> LenientFunction<V, R> compose(final LenientFunction<? super V, ? extends T> before) {
        Objects.requireNonNull(before);
        return (V v) -> applyLenient(before.applyLenient(v));
    }

    default <V> LenientFunction<T, V> andThen(final LenientFunction<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (T t) -> after.applyLenient(applyLenient(t));
    }

    static <T> LenientFunction<T, T> identity() {
        return t -> t;
    }

}
