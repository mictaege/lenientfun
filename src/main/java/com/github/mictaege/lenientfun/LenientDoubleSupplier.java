package com.github.mictaege.lenientfun;


/** @see java.util.function.DoubleSupplier */
@FunctionalInterface
public interface LenientDoubleSupplier {

    @SuppressWarnings("squid:S00112")
    double getAsDouble() throws Exception;

}