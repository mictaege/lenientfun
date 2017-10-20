package com.github.mictaege.lenientfun;

import java.util.function.ObjLongConsumer;

/** @see java.util.function.ObjLongConsumer */
@FunctionalInterface
public interface LenientObjLongConsumer<T> extends ObjLongConsumer<T> {

    @SuppressWarnings("squid:S00112")
    void acceptLenient(T t, long value) throws Exception;

    default void accept(final T t, final long value) {
        try {
            acceptLenient(t, value);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

}
