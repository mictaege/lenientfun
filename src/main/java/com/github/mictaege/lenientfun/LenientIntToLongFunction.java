package com.github.mictaege.lenientfun;

/** @see java.util.function.IntToLongFunction */
@FunctionalInterface
public interface LenientIntToLongFunction {

    @SuppressWarnings("squid:S00112")
    long applyAsLong(int value) throws Exception;

}
