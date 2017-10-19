package com.github.mictaege.lenientfun;

/** @see java.util.function.LongBinaryOperator */
@FunctionalInterface
public interface LenientLongBinaryOperator {

    @SuppressWarnings("squid:S00112")
    long applyAsLong(long left, long right) throws Exception;

}