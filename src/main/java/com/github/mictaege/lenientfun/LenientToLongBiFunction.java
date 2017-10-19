package com.github.mictaege.lenientfun;

/** @see java.util.function.ToLongBiFunction */
@FunctionalInterface
public interface LenientToLongBiFunction<T, U> {

    @SuppressWarnings("squid:S00112")
    long applyAsLong(T t, U u) throws Exception;

}
