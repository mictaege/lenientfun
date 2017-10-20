package com.github.mictaege.lenientfun;

import java.util.function.ObjIntConsumer;

/** @see java.util.function.ObjIntConsumer */
@FunctionalInterface
public interface LenientObjIntConsumer<T> extends ObjIntConsumer<T> {

    @SuppressWarnings("squid:S00112")
    void acceptLenient(T t, int value) throws Exception;

    @Override
    default void accept(final T t, final int value) {
        try {
            acceptLenient(t, value);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

}
