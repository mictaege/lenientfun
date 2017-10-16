package com.github.mictaege.lenientfun;

import java.util.Objects;

/** @see java.util.function.DoubleConsumer */
@FunctionalInterface
public interface LenientDoubleConsumer {

    @SuppressWarnings("squid:S00112")
    void accept(double value) throws Exception;

    default LenientDoubleConsumer andThen(final LenientDoubleConsumer after) {
        Objects.requireNonNull(after);
        return (double t) -> { accept(t); after.accept(t); };
    }

}
