package com.github.mictaege.lenientfun;

/** @see java.util.function.ToDoubleFunction */
@FunctionalInterface
public interface LenientToDoubleFunction<T> {

    @SuppressWarnings("squid:S00112")
    double applyAsDouble(T value) throws Exception;

}
