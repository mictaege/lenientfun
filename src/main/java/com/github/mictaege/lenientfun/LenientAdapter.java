package com.github.mictaege.lenientfun;

import java.util.function.*;

public final class LenientAdapter {

    private LenientAdapter() {
        super();
    }

    public static <T, U> BiConsumer<T, U> biConsumer(final LenientBiConsumer<T, U> lenient) {
        return (t, u) -> {
            try {
                lenient.accept(t, u);
            } catch (final Exception e) {
                throw new FunctionalRuntimeException(e);
            }
        };
    }

    public static <T, U, R> BiFunction<T, U, R> biFunc(final LenientBiFunction<T, U, R> lenient) {
        return (t, u) -> {
            try {
                return lenient.apply(t, u);
            } catch (final Exception e) {
                throw new FunctionalRuntimeException(e);
            }
        };
    }

    public static <T> BinaryOperator<T> binOp(final LenientBinaryOperator<T> lenient) {
        return (t, u) -> {
            try {
                return lenient.apply(t, u);
            } catch (final Exception e) {
                throw new FunctionalRuntimeException(e);
            }
        };
    }

    public static <T, U> BiPredicate<T, U> biPredicate(final LenientBiPredicate<T, U> lenient) {
        return (t, u) -> {
            try {
                return lenient.test(t, u);
            } catch (final Exception e) {
                throw new FunctionalRuntimeException(e);
            }
        };
    }

    public static BooleanSupplier boolSupplier(final LenientBooleanSupplier lenient) {
        return () -> {
            try {
                return lenient.getAsBoolean();
            } catch (final Exception e) {
                throw new FunctionalRuntimeException(e);
            }
        };
    }

    public static <T> Consumer<T> consumer(final LenientConsumer<T> lenient) {
        return t -> {
            try {
                lenient.accept(t);
            } catch (final Exception e) {
                throw new FunctionalRuntimeException(e);
            }
        };
    }

    public static DoubleBinaryOperator doubleBinOp(final LenientDoubleBinaryOperator lenient) {
        return (l, r) -> {
            try {
                return lenient.applyAsDouble(l, r);
            } catch (final Exception e) {
                throw new FunctionalRuntimeException(e);
            }
        };
    }

    public static DoubleConsumer doubleConsumer(final LenientDoubleConsumer lenient) {
        return d -> {
            try {
                lenient.accept(d);
            } catch (final Exception e) {
                throw new FunctionalRuntimeException(e);
            }
        };
    }

    public static <R> DoubleFunction<R> doubleFunc(final LenientDoubleFunction<R> lenient) {
        return d -> {
            try {
                return lenient.apply(d);
            } catch (final Exception e) {
                throw new FunctionalRuntimeException(e);
            }
        };
    }

    public static DoublePredicate doublePredicate(final LenientDoublePredicate lenient) {
        return d -> {
            try {
                return lenient.test(d);
            } catch (final Exception e) {
                throw new FunctionalRuntimeException(e);
            }
        };
    }

    public static DoubleSupplier doubleSupplier(final LenientDoubleSupplier lenient) {
        return () -> {
            try {
                return lenient.getAsDouble();
            } catch (final Exception e) {
                throw new FunctionalRuntimeException(e);
            }
        };
    }

    public static DoubleToIntFunction doubleToIntFunc(final LenientDoubleToIntFunction lenient) {
        return d -> {
            try {
                return lenient.applyAsInt(d);
            } catch (final Exception e) {
                throw new FunctionalRuntimeException(e);
            }
        };
    }

    public static DoubleToLongFunction doubleToLongFunc(final LenientDoubleToLongFunction lenient) {
        return d -> {
            try {
                return lenient.applyAsLong(d);
            } catch (final Exception e) {
                throw new FunctionalRuntimeException(e);
            }
        };
    }

    public static DoubleUnaryOperator doubleUnaryOp(final LenientDoubleUnaryOperator lenient) {
        return d -> {
            try {
                return lenient.applyAsDouble(d);
            } catch (final Exception e) {
                throw new FunctionalRuntimeException(e);
            }
        };
    }

    public static <T, R> Function<T, R> func(final LenientFunction<T, R> lenient) {
        return t -> {
            try {
                return lenient.apply(t);
            } catch (final Exception e) {
                throw new FunctionalRuntimeException(e);
            }
        };
    }

    public static IntBinaryOperator intBinOp(final LenientIntBinaryOperator lenient) {
        return (l, r) -> {
            try {
                return lenient.applyAsInt(l, r);
            } catch (final Exception e) {
                throw new FunctionalRuntimeException(e);
            }
        };
    }

    public static IntConsumer intConsumer(final LenientIntConsumer lenient) {
        return i -> {
            try {
                lenient.accept(i);
            } catch (final Exception e) {
                throw new FunctionalRuntimeException(e);
            }
        };
    }

    public static <R> IntFunction<R> intFunc(final LenientIntFunction<R> lenient) {
        return i -> {
            try {
                return lenient.apply(i);
            } catch (final Exception e) {
                throw new FunctionalRuntimeException(e);
            }
        };
    }

    public static IntPredicate intPredicate(final LenientIntPredicate lenient) {
        return i -> {
            try {
                return lenient.test(i);
            } catch (final Exception e) {
                throw new FunctionalRuntimeException(e);
            }
        };
    }

    public static IntSupplier intSupplier(final LenientIntSupplier lenient) {
        return () -> {
            try {
                return lenient.getAsInt();
            } catch (final Exception e) {
                throw new FunctionalRuntimeException(e);
            }
        };
    }

    public static IntToDoubleFunction intToDoubleFunc(final LenientIntToDoubleFunction lenient) {
        return i -> {
            try {
                return lenient.applyAsDouble(i);
            } catch (final Exception e) {
                throw new FunctionalRuntimeException(e);
            }
        };
    }

}
