package com.github.mictaege.lenientfun;

import java.util.function.BooleanSupplier;

/** @see java.util.function.BooleanSupplier */
@FunctionalInterface
public interface LenientBooleanSupplier extends BooleanSupplier {

    @SuppressWarnings("squid:S00112")
    boolean getAsBooleanLenient() throws Exception;

    @Override
    default boolean getAsBoolean() {
        try {
            return getAsBooleanLenient();
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

}