package com.github.mictaege.lenientfun;

import java.util.Objects;
import java.util.function.DoublePredicate;

/** @see java.util.function.DoublePredicate */
@FunctionalInterface
public interface LenientDoublePredicate extends DoublePredicate {

    @SuppressWarnings("squid:S00112")
    boolean testLenient(double value) throws Exception;

    @Override
    default boolean test(final double value) {
        try {
            return testLenient(value);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    default LenientDoublePredicate and(final LenientDoublePredicate other) {
        Objects.requireNonNull(other);
        return v -> testLenient(v) && other.testLenient(v);
    }

    @Override
    default LenientDoublePredicate negate() {
        return v -> !testLenient(v);
    }

    default LenientDoublePredicate or(final LenientDoublePredicate other) {
        Objects.requireNonNull(other);
        return v -> testLenient(v) || other.testLenient(v);
    }

}
