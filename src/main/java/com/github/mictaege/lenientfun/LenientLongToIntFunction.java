package com.github.mictaege.lenientfun;

/** @see java.util.function.LongToIntFunction */
@FunctionalInterface
public interface LenientLongToIntFunction {

    @SuppressWarnings("squid:S00112")
    int applyAsInt(long value) throws Exception;

}
