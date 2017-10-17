package com.github.mictaege.lenientfun;

import java.util.Objects;

/** @see java.util.function.IntConsumer */
@FunctionalInterface
public interface LenientIntConsumer {

    @SuppressWarnings("squid:S00112")
    void accept(int value) throws Exception;

    default LenientIntConsumer andThen(final LenientIntConsumer after) {
        Objects.requireNonNull(after);
        return (int t) -> { accept(t); after.accept(t); };
    }

}
