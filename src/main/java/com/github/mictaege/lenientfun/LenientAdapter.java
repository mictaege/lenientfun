package com.github.mictaege.lenientfun;

import java.util.function.*;

public final class LenientAdapter {

    private LenientAdapter() {
        super();
    }

    public static <T, U> BiConsumer<T, U> biConsumer(final LenientBiConsumer<T, U> lenient) {
        return (t, u) -> {
            try {
                lenient.accept(t, u);
            } catch (final Exception e) {
                throw new FunctionalRuntimeException(e);
            }
        };
    }

    public static <T, U, R> BiFunction<T, U, R> biFunction(final LenientBiFunction<T, U, R> lenient) {
        return (t, u) -> {
            try {
                return lenient.apply(t, u);
            } catch (final Exception e) {
                throw new FunctionalRuntimeException(e);
            }
        };
    }

    public static <T> BinaryOperator<T> binOperator(final LenientBinaryOperator<T> lenient) {
        return (t, u) -> {
            try {
                return lenient.apply(t, u);
            } catch (final Exception e) {
                throw new FunctionalRuntimeException(e);
            }
        };
    }

    public static <T, U> BiPredicate<T, U> biPredicate(final LenientBiPredicate<T, U> lenient) {
        return (t, u) -> {
            try {
                return lenient.test(t, u);
            } catch (final Exception e) {
                throw new FunctionalRuntimeException(e);
            }
        };
    }

    public static BooleanSupplier booleanSupplier(final LenientBooleanSupplier lenient) {
        return () -> {
            try {
                return lenient.getAsBoolean();
            } catch (final Exception e) {
                throw new FunctionalRuntimeException(e);
            }
        };
    }

    public static <T> Consumer<T> consumer(final LenientConsumer<T> lenient) {
        return t -> {
            try {
                lenient.accept(t);
            } catch (final Exception e) {
                throw new FunctionalRuntimeException(e);
            }
        };
    }

    public static DoubleBinaryOperator doubleBinOperator(final LenientDoubleBinaryOperator lenient) {
        return (l, r) -> {
            try {
                return lenient.applyAsDouble(l, r);
            } catch (final Exception e) {
                throw new FunctionalRuntimeException(e);
            }
        };
    }

    public static <T, R> Function<T, R> function(final LenientFunction<T, R> lenient) {
        return t -> {
            try {
                return lenient.apply(t);
            } catch (final Exception e) {
                throw new FunctionalRuntimeException(e);
            }
        };
    }

}
