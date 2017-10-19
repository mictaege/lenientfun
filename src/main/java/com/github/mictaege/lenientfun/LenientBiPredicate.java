package com.github.mictaege.lenientfun;

import java.util.Objects;

/** @see java.util.function.BiPredicate*/
@FunctionalInterface
public interface LenientBiPredicate<T, U> {

    @SuppressWarnings("squid:S00112")
    boolean test(T t, U u) throws Exception;

    default LenientBiPredicate<T, U> and(final LenientBiPredicate<? super T, ? super U> other) {
        Objects.requireNonNull(other);
        return (T t, U u) -> test(t, u) && other.test(t, u);
    }

    default LenientBiPredicate<T, U> negate() {
        return (T t, U u) -> !test(t, u);
    }

    default LenientBiPredicate<T, U> or(final LenientBiPredicate<? super T, ? super U> other) {
        Objects.requireNonNull(other);
        return (T t, U u) -> test(t, u) || other.test(t, u);
    }

}
