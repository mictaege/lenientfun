package com.github.mictaege.lenientfun;

/** @see java.util.function.IntToDoubleFunction */
@FunctionalInterface
public interface LenientIntToDoubleFunction {

    @SuppressWarnings("squid:S00112")
    double applyAsDouble(int value) throws Exception;

}
