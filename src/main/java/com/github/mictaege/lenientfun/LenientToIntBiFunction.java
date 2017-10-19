package com.github.mictaege.lenientfun;

/** @see java.util.function.ToIntBiFunction */
@FunctionalInterface
public interface LenientToIntBiFunction<T, U> {

    @SuppressWarnings("squid:S00112")
    int applyAsInt(T t, U u) throws Exception;

}
