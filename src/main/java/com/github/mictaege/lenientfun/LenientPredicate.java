package com.github.mictaege.lenientfun;

import java.util.Objects;

/** @see java.util.function.Predicate */
@FunctionalInterface
public interface LenientPredicate<T> {

    @SuppressWarnings("squid:S00112")
    boolean test(T t) throws Exception ;

    default LenientPredicate<T> and(final LenientPredicate<? super T> other) {
        Objects.requireNonNull(other);
        return t -> test(t) && other.test(t);
    }

    default LenientPredicate<T> negate() {
        return t -> !test(t);
    }

    default LenientPredicate<T> or(final LenientPredicate<? super T> other) {
        Objects.requireNonNull(other);
        return t -> test(t) || other.test(t);
    }

    static <T> LenientPredicate<T> isEqual(final Object targetRef) {
        return (null == targetRef)
                ? Objects::isNull
                : targetRef::equals;
    }

}
