package com.github.mictaege.lenientfun;

import java.util.function.LongFunction;

/** @see java.util.function.LongFunction */
@FunctionalInterface
public interface LenientLongFunction<R> extends LongFunction<R> {

    @SuppressWarnings("squid:S00112")
    R applyLenient(long value) throws Exception;

    @Override
    default R apply(final long value) {
        try {
            return applyLenient(value);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

}
