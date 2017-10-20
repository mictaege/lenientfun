package com.github.mictaege.lenientfun;

import java.util.Objects;
import java.util.function.Consumer;

/** @see java.util.function.Consumer */
@FunctionalInterface
public interface LenientConsumer<T> extends Consumer<T> {

    @SuppressWarnings("squid:S00112")
    void acceptLenient(T t) throws Exception;

    @Override
    default void accept(final T t) {
        try {
            acceptLenient(t);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    default LenientConsumer<T> andThen(final LenientConsumer<? super T> after) {
        Objects.requireNonNull(after);
        return (T t) -> { acceptLenient(t); after.acceptLenient(t); };
    }

}
