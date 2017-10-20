package com.github.mictaege.lenientfun;

import java.util.function.ObjDoubleConsumer;

/** @see java.util.function.ObjDoubleConsumer */
@FunctionalInterface
public interface LenientObjDoubleConsumer<T> extends ObjDoubleConsumer<T> {

    @SuppressWarnings("squid:S00112")
    void acceptLenient(T t, double value) throws Exception;

    @Override
    default void accept(final T t, final double value) {
        try {
            acceptLenient(t, value);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

}
