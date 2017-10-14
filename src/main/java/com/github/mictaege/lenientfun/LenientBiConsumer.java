package com.github.mictaege.lenientfun;

import java.util.Objects;

/** @see java.util.function.BiConsumer */
@FunctionalInterface
public interface LenientBiConsumer<T, U> {

    @SuppressWarnings("squid:S00112")
    void accept(T t, U u) throws Exception;

    default LenientBiConsumer<T, U> andThen(final LenientBiConsumer<? super T, ? super U> after) {
        Objects.requireNonNull(after);
        return (l, r) -> {
            accept(l, r);
            after.accept(l, r);
        };
    }

}
