package com.github.mictaege.lenientfun;

import java.util.Comparator;
import java.util.Objects;

/** @see java.util.function.BinaryOperator */
@FunctionalInterface
public interface LenientBinaryOperator<T> extends LenientBiFunction<T,T,T> {

    static <T> LenientBinaryOperator<T> minBy(final Comparator<? super T> comparator) {
        Objects.requireNonNull(comparator);
        return (a, b) -> comparator.compare(a, b) <= 0 ? a : b;
    }

    static <T> LenientBinaryOperator<T> maxBy(Comparator<? super T> comparator) {
        Objects.requireNonNull(comparator);
        return (a, b) -> comparator.compare(a, b) >= 0 ? a : b;
    }

}