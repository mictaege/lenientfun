package com.github.mictaege.lenientfun;

/** @see java.util.function.IntBinaryOperator */
@FunctionalInterface
public interface LenientIntBinaryOperator {

    @SuppressWarnings("squid:S00112")
    int applyAsInt(int left, int right) throws Exception;

}