package com.github.mictaege.lenientfun;

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

}
