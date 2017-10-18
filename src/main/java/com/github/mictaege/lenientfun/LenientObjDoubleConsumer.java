package com.github.mictaege.lenientfun;

/** @see java.util.function.ObjDoubleConsumer */
@FunctionalInterface
public interface LenientObjDoubleConsumer<T> {

    @SuppressWarnings("squid:S00112")
    void accept(T t, double value) throws Exception;

}
