package com.github.mictaege.lenientfun;

/** @see java.util.function.DoubleToIntFunction */
@FunctionalInterface
public interface LenientDoubleToIntFunction {

    @SuppressWarnings("squid:S00112")
    int applyAsInt(double value) throws Exception;

}
