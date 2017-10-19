package com.github.mictaege.lenientfun;

/** @see java.util.function.LongToDoubleFunction */
@FunctionalInterface
public interface LenientLongToDoubleFunction {

    @SuppressWarnings("squid:S00112")
    double applyAsDouble(long value) throws Exception;

}
