package com.github.mictaege.lenientfun;

import java.util.Objects;
import java.util.function.IntConsumer;

/** @see java.util.function.IntConsumer */
@FunctionalInterface
public interface LenientIntConsumer extends IntConsumer {

    @SuppressWarnings("squid:S00112")
    void acceptLenient(int value) throws Exception;

    @Override
    default void accept(final int value) {
        try {
            acceptLenient(value);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    default LenientIntConsumer andThen(final LenientIntConsumer after) {
        Objects.requireNonNull(after);
        return (int t) -> { acceptLenient(t); after.acceptLenient(t); };
    }

}
