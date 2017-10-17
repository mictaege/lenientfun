package com.github.mictaege.lenientfun;


/** @see java.util.function.IntSupplier */
@FunctionalInterface
public interface LenientIntSupplier {

    @SuppressWarnings("squid:S00112")
    int getAsInt() throws Exception;

}