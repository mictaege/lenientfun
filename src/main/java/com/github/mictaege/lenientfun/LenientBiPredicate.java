package com.github.mictaege.lenientfun;

import java.util.Objects;
import java.util.function.BiPredicate;

/** @see java.util.function.BiPredicate*/
@FunctionalInterface
public interface LenientBiPredicate<T, U> extends BiPredicate<T, U> {

    @SuppressWarnings("squid:S00112")
    boolean testLenient(T t, U u) throws Exception;

    @Override
    default boolean test(final T t, final U u) {
        try {
            return testLenient(t, u);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    default LenientBiPredicate<T, U> and(final LenientBiPredicate<? super T, ? super U> other) {
        Objects.requireNonNull(other);
        return (T t, U u) -> testLenient(t, u) && other.testLenient(t, u);
    }

    @Override
    default LenientBiPredicate<T, U> negate() {
        return (T t, U u) -> !testLenient(t, u);
    }

    default LenientBiPredicate<T, U> or(final LenientBiPredicate<? super T, ? super U> other) {
        Objects.requireNonNull(other);
        return (T t, U u) -> testLenient(t, u) || other.testLenient(t, u);
    }

}
