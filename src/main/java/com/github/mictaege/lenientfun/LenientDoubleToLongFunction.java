package com.github.mictaege.lenientfun;

/** @see java.util.function.DoubleToLongFunction */
@FunctionalInterface
public interface LenientDoubleToLongFunction {

    @SuppressWarnings("squid:S00112")
    long applyAsLong(double value) throws Exception;

}
