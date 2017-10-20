package com.github.mictaege.lenientfun;


import java.util.function.IntSupplier;

/** @see java.util.function.IntSupplier */
@FunctionalInterface
public interface LenientIntSupplier extends IntSupplier {

    @SuppressWarnings("squid:S00112")
    int getAsIntLenient() throws Exception;

    @Override
    default int getAsInt() {
        try {
            return getAsIntLenient();
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

}