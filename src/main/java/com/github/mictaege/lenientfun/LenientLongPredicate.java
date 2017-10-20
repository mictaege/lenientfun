package com.github.mictaege.lenientfun;

import java.util.Objects;
import java.util.function.LongPredicate;

/** @see java.util.function.LongPredicate */
@FunctionalInterface
public interface LenientLongPredicate extends LongPredicate {

    @SuppressWarnings("squid:S00112")
    boolean testLenient(long v) throws Exception;

    @Override
    default boolean test(final long v) {
        try {
            return testLenient(v);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    default LenientLongPredicate and(final LenientLongPredicate other) {
        Objects.requireNonNull(other);
        return v -> testLenient(v) && other.testLenient(v);
    }

    @Override
    default LenientLongPredicate negate() {
        return v -> !testLenient(v);
    }

    default LenientLongPredicate or(final LenientLongPredicate other) {
        Objects.requireNonNull(other);
        return v -> testLenient(v) || other.testLenient(v);
    }

}
