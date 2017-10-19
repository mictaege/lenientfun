package com.github.mictaege.lenientfun;

import java.util.Objects;
import java.util.function.DoubleConsumer;

/** @see java.util.function.DoubleConsumer */
@FunctionalInterface
public interface LenientDoubleConsumer extends DoubleConsumer {

    @SuppressWarnings("squid:S00112")
    void acceptLenient(double value) throws Exception;

    @Override
    default void accept(final double value) {
        try {
            acceptLenient(value);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    default LenientDoubleConsumer andThen(final LenientDoubleConsumer after) {
        Objects.requireNonNull(after);
        return (double t) -> { acceptLenient(t); after.acceptLenient(t); };
    }

}
