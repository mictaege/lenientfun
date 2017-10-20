package com.github.mictaege.lenientfun;

import java.util.Objects;
import java.util.function.BiConsumer;

/** @see java.util.function.BiConsumer */
@FunctionalInterface
public interface LenientBiConsumer<T, U> extends BiConsumer<T, U> {

    @SuppressWarnings("squid:S00112")
    void acceptLenient(T t, U u) throws Exception;

    @Override
    default void accept(T t, U u) {
        try {
            acceptLenient(t, u);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    default LenientBiConsumer<T, U> andThen(final LenientBiConsumer<? super T, ? super U> after) {
        Objects.requireNonNull(after);
        return (l, r) -> {
            acceptLenient(l, r);
            after.acceptLenient(l, r);
        };
    }

}
