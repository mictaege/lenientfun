package com.github.mictaege.lenientfun;

/** @see java.util.function.DoubleFunction */
@FunctionalInterface
public interface LenientDoubleFunction<R> {

    @SuppressWarnings("squid:S00112")
    R apply(double value) throws Exception;

}
