package com.github.mictaege.lenientfun;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public final class LenientAdapter {

    private LenientAdapter() {
        super();
    }

    public static <T, U> BiConsumer<T, U> accept(final LenientBiConsumer<T, U> lenient) {
        return (t, u) -> {
            try {
                lenient.accept(t, u);
            } catch (final Exception e) {
                throw new FunctionalRuntimeException(e);
            }
        };
    }

    public static <T, U, R> BiFunction<T, U, R> apply(final LenientBiFunction<T, U, R> lenient) {
        return (t, u) -> {
            try {
                return lenient.apply(t, u);
            } catch (final Exception e) {
                throw new FunctionalRuntimeException(e);
            }
        };
    }

    public static <T> Consumer<T> accept(final LenientConsumer<T> lenient) {
        return t -> {
            try {
                lenient.accept(t);
            } catch (final Exception e) {
                throw new FunctionalRuntimeException(e);
            }
        };
    }

    public static <T, R> Function<T, R> apply(final LenientFunction<T, R> lenient) {
        return t -> {
            try {
                return lenient.apply(t);
            } catch (final Exception e) {
                throw new FunctionalRuntimeException(e);
            }
        };
    }

}
