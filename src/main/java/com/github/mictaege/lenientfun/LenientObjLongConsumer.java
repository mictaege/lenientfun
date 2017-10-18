package com.github.mictaege.lenientfun;

/** @see java.util.function.ObjLongConsumer */
@FunctionalInterface
public interface LenientObjLongConsumer<T> {

    @SuppressWarnings("squid:S00112")
    void accept(T t, long value) throws Exception;

}
