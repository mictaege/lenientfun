package com.github.mictaege.lenientfun;

import java.util.Objects;

/** @see java.util.function.Consumer */
@FunctionalInterface
public interface LenientConsumer<T> {

    @SuppressWarnings("squid:S00112")
    void accept(T t) throws Exception;

    default LenientConsumer<T> andThen(final LenientConsumer<? super T> after) {
        Objects.requireNonNull(after);
        return (T t) -> { accept(t); after.accept(t); };
    }

}
