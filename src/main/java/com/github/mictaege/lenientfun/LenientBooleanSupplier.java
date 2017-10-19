package com.github.mictaege.lenientfun;

/** @see java.util.function.BooleanSupplier */
@FunctionalInterface
public interface LenientBooleanSupplier {

    @SuppressWarnings("squid:S00112")
    boolean getAsBoolean() throws Exception;

}