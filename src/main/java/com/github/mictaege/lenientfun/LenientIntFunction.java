package com.github.mictaege.lenientfun;

/** @see java.util.function.IntFunction */
@FunctionalInterface
public interface LenientIntFunction<R> {

    @SuppressWarnings("squid:S00112")
    R apply(int value) throws Exception;

}
