package com.github.mictaege.lenientfun;


import java.util.function.LongSupplier;

/** @see java.util.function.LongSupplier */
@FunctionalInterface
public interface LenientLongSupplier extends LongSupplier {

    @SuppressWarnings("squid:S00112")
    long getAsLongLenient() throws Exception;

    @Override
    default long getAsLong() {
        try {
            return getAsLongLenient();
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

}