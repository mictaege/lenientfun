package com.github.mictaege.lenientfun;

/** @see java.util.function.UnaryOperator */
@FunctionalInterface
public interface LenientUnaryOperator<T> extends LenientFunction<T, T> {

    static <T> LenientUnaryOperator<T> identity() {
        return t -> t;
    }

}
