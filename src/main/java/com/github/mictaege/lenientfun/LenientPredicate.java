package com.github.mictaege.lenientfun;

import java.util.Objects;
import java.util.function.Predicate;

/** @see java.util.function.Predicate */
@FunctionalInterface
public interface LenientPredicate<T> extends Predicate<T> {

    @SuppressWarnings("squid:S00112")
    boolean testLenient(T t) throws Exception;

    @Override
    default boolean test(final T t) {
        try {
            return testLenient(t);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    default LenientPredicate<T> and(final LenientPredicate<? super T> other) {
        Objects.requireNonNull(other);
        return t -> testLenient(t) && other.testLenient(t);
    }

    @Override
    default LenientPredicate<T> negate() {
        return t -> !testLenient(t);
    }

    default LenientPredicate<T> or(final LenientPredicate<? super T> other) {
        Objects.requireNonNull(other);
        return t -> testLenient(t) || other.testLenient(t);
    }

    static <T> LenientPredicate<T> isEqual(final Object targetRef) {
        return (null == targetRef)
                ? Objects::isNull
                : targetRef::equals;
    }

}
