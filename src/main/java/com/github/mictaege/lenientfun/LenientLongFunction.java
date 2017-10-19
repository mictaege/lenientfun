package com.github.mictaege.lenientfun;

/** @see java.util.function.LongFunction */
@FunctionalInterface
public interface LenientLongFunction<R> {

    @SuppressWarnings("squid:S00112")
    R apply(long value) throws Exception;

}
