package com.github.mictaege.lenientfun;

/** @see java.util.function.ToDoubleBiFunction */
@FunctionalInterface
public interface LenientToDoubleBiFunction<T, U> {

    @SuppressWarnings("squid:S00112")
    double applyAsDouble(T t, U u) throws Exception;

}
