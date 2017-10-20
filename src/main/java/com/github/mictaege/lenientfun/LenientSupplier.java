package com.github.mictaege.lenientfun;


import java.util.function.Supplier;

/** @see java.util.function.Supplier */
@FunctionalInterface
public interface LenientSupplier<T> extends Supplier<T> {

    @SuppressWarnings("squid:S00112")
    T getLenient() throws Exception;

    @Override
    default T get() {
        try {
            return getLenient();
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

}