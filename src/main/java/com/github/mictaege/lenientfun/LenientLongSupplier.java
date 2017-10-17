package com.github.mictaege.lenientfun;


/** @see java.util.function.LongSupplier */
@FunctionalInterface
public interface LenientLongSupplier {

    @SuppressWarnings("squid:S00112")
    long getAsLong() throws Exception;

}