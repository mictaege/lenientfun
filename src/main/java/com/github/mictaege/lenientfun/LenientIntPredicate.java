package com.github.mictaege.lenientfun;

import java.util.Objects;
import java.util.function.IntPredicate;

/** @see java.util.function.IntPredicate */
@FunctionalInterface
public interface LenientIntPredicate extends IntPredicate {

    @SuppressWarnings("squid:S00112")
    boolean testLenient(int value) throws Exception ;

    @Override
    default boolean test(final int value) {
        try {
            return testLenient(value);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    default LenientIntPredicate and(final LenientIntPredicate other) {
        Objects.requireNonNull(other);
        return v -> testLenient(v) && other.testLenient(v);
    }

    @Override
    default LenientIntPredicate negate() {
        return v -> !testLenient(v);
    }

    default LenientIntPredicate or(final LenientIntPredicate other) {
        Objects.requireNonNull(other);
        return v -> testLenient(v) || other.testLenient(v);
    }

}
