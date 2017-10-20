package com.github.mictaege.lenientfun;

import java.util.function.ToDoubleBiFunction;

/** @see java.util.function.ToDoubleBiFunction */
@FunctionalInterface
public interface LenientToDoubleBiFunction<T, U> extends ToDoubleBiFunction<T, U> {

    @SuppressWarnings("squid:S00112")
    double applyAsDoubleLenient(T t, U u) throws Exception;

    @Override
    default double applyAsDouble(final T t, final U u) {
        try {
            return applyAsDoubleLenient(t, u);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

}
