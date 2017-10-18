package com.github.mictaege.lenientfun;


/** @see java.util.function.Supplier */
@FunctionalInterface
public interface LenientSupplier<T> {

    @SuppressWarnings("squid:S00112")
    T get() throws Exception;

}