package com.github.mictaege.lenientfun;

import java.util.function.LongToIntFunction;

/** @see java.util.function.LongToIntFunction */
@FunctionalInterface
public interface LenientLongToIntFunction extends LongToIntFunction {

    @SuppressWarnings("squid:S00112")
    int applyAsIntLenient(long value) throws Exception;

    @Override
    default int applyAsInt(final long value) {
        try {
            return applyAsIntLenient(value);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

}
