package com.github.mictaege.lenientfun;

import java.util.Objects;

/** @see java.util.function.IntPredicate */
@FunctionalInterface
public interface LenientIntPredicate {

    @SuppressWarnings("squid:S00112")
    boolean test(int value) throws Exception ;

    default LenientIntPredicate and(final LenientIntPredicate other) {
        Objects.requireNonNull(other);
        return v -> test(v) && other.test(v);
    }

    default LenientIntPredicate negate() {
        return v -> !test(v);
    }

    default LenientIntPredicate or(final LenientIntPredicate other) {
        Objects.requireNonNull(other);
        return v -> test(v) || other.test(v);
    }

}
