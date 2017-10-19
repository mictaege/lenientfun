package com.github.mictaege.lenientfun;

import java.util.Objects;

/** @see java.util.function.LongPredicate */
@FunctionalInterface
public interface LenientLongPredicate {

    @SuppressWarnings("squid:S00112")
    boolean test(long v) throws Exception ;

    default LenientLongPredicate and(final LenientLongPredicate other) {
        Objects.requireNonNull(other);
        return v -> test(v) && other.test(v);
    }

    default LenientLongPredicate negate() {
        return v -> !test(v);
    }

    default LenientLongPredicate or(final LenientLongPredicate other) {
        Objects.requireNonNull(other);
        return v -> test(v) || other.test(v);
    }

}
