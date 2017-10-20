package com.github.mictaege.lenientfun;

import java.util.Objects;
import java.util.function.LongConsumer;

/** @see java.util.function.LongConsumer */
@FunctionalInterface
public interface LenientLongConsumer extends LongConsumer {

    @SuppressWarnings("squid:S00112")
    void acceptLenient(long value) throws Exception;

    @Override
    default void accept(final long value) {
        try {
            acceptLenient(value);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    default LenientLongConsumer andThen(final LenientLongConsumer after) {
        Objects.requireNonNull(after);
        return (long t) -> { acceptLenient(t); after.acceptLenient(t); };
    }

}
