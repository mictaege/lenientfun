package com.github.mictaege.lenientfun;

import java.util.function.UnaryOperator;

/** @see java.util.function.UnaryOperator */
@FunctionalInterface
public interface LenientUnaryOperator<T> extends LenientFunction<T, T>, UnaryOperator<T> {

    static <T> LenientUnaryOperator<T> identity() {
        return t -> t;
    }

}
