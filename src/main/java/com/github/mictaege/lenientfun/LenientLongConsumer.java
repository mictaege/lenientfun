package com.github.mictaege.lenientfun;

import java.util.Objects;

/** @see java.util.function.LongConsumer */
@FunctionalInterface
public interface LenientLongConsumer {

    @SuppressWarnings("squid:S00112")
    void accept(long value) throws Exception;

    default LenientLongConsumer andThen(final LenientLongConsumer after) {
        Objects.requireNonNull(after);
        return (long t) -> { accept(t); after.accept(t); };
    }

}
