package com.github.mictaege.lenientfun;

/** @see java.util.function.DoubleBinaryOperator */
@FunctionalInterface
public interface LenientDoubleBinaryOperator {

    @SuppressWarnings("squid:S00112")
    double applyAsDouble(double left, double right) throws Exception;

}