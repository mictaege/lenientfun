package com.github.mictaege.lenientfun;

import java.util.Objects;

/** @see java.util.function.DoublePredicate */
@FunctionalInterface
public interface LenientDoublePredicate {

    @SuppressWarnings("squid:S00112")
    boolean test(double value) throws Exception ;

    default LenientDoublePredicate and(final LenientDoublePredicate other) {
        Objects.requireNonNull(other);
        return v -> test(v) && other.test(v);
    }

    default LenientDoublePredicate negate() {
        return v -> !test(v);
    }

    default LenientDoublePredicate or(final LenientDoublePredicate other) {
        Objects.requireNonNull(other);
        return v -> test(v) || other.test(v);
    }

}
