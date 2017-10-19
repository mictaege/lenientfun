package com.github.mictaege.lenientfun;


import java.util.function.DoubleSupplier;

/** @see java.util.function.DoubleSupplier */
@FunctionalInterface
public interface LenientDoubleSupplier extends DoubleSupplier {

    @SuppressWarnings("squid:S00112")
    double getAsDoubleLenient() throws Exception;

    @Override
    default double getAsDouble() {
        try {
            return getAsDoubleLenient();
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

}