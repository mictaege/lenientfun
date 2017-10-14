package com.github.mictaege.lenientfun;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class LenientAdapter {

    private LenientAdapter() {
        super();
    }

    public static <T> Consumer<T> lenient(final LenientConsumer<T> lenient) {
        return t -> {
            try {
                lenient.accept(t);
            } catch (final Exception e) {
                throw new FunctionalRuntimeException(e);
            }
        };
    }

    public static <T, U> BiConsumer<T, U> lenient(final LenientBiConsumer<T, U> lenient) {
        return (t, u) -> {
            try {
                lenient.accept(t, u);
            } catch (final Exception e) {
                throw new FunctionalRuntimeException(e);
            }
        };
    }

}
