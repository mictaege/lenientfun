package com.github.mictaege.lenientfun;

/** @see java.util.function.ObjIntConsumer */
@FunctionalInterface
public interface LenientObjIntConsumer<T> {

    @SuppressWarnings("squid:S00112")
    void accept(T t, int value) throws Exception;

}
