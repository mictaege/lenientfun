package com.github.mictaege.lenientfun;

/** @see java.util.function.ToIntFunction */
@FunctionalInterface
public interface LenientToIntFunction<T> {

    @SuppressWarnings("squid:S00112")
    int applyAsInt(T value) throws Exception;

}
