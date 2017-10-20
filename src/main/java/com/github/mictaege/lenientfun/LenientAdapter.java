package com.github.mictaege.lenientfun;

import java.util.function.*;

public final class LenientAdapter {

    private LenientAdapter() {
        super();
    }

    public static <T, U, R> BiFunction<T, U, R> biFunc(final LenientBiFunction<T, U, R> lenient) {
        return lenient;
    }

    public static <T, U> BiConsumer<T, U> biConsumer(final LenientBiConsumer<T, U> lenient) {
        return lenient;
    }

    public static <T> BinaryOperator<T> binOp(final LenientBinaryOperator<T> lenient) {
        return lenient;
    }

    public static <T, U> BiPredicate<T, U> biPredicate(final LenientBiPredicate<T, U> lenient) {
        return lenient;
    }

    public static BooleanSupplier boolSupplier(final LenientBooleanSupplier lenient) {
        return lenient;
    }

    public static <T> Consumer<T> consumer(final LenientConsumer<T> lenient) {
        return lenient;
    }

    public static DoubleBinaryOperator doubleBinOp(final LenientDoubleBinaryOperator lenient) {
        return lenient;
    }

    public static DoubleConsumer doubleConsumer(final LenientDoubleConsumer lenient) {
        return lenient;
    }

    public static <R> DoubleFunction<R> doubleFunc(final LenientDoubleFunction<R> lenient) {
        return lenient;
    }

    public static DoublePredicate doublePredicate(final LenientDoublePredicate lenient) {
        return lenient;
    }

    public static DoubleSupplier doubleSupplier(final LenientDoubleSupplier lenient) {
        return lenient;
    }

    public static DoubleToIntFunction doubleToIntFunc(final LenientDoubleToIntFunction lenient) {
        return lenient;
    }

    public static DoubleToLongFunction doubleToLongFunc(final LenientDoubleToLongFunction lenient) {
        return lenient;
    }

    public static DoubleUnaryOperator doubleUnaryOp(final LenientDoubleUnaryOperator lenient) {
        return lenient;
    }

    public static <T, R> Function<T, R> func(final LenientFunction<T, R> lenient) {
        return lenient;
    }

    public static IntBinaryOperator intBinOp(final LenientIntBinaryOperator lenient) {
        return lenient;
    }

    public static IntConsumer intConsumer(final LenientIntConsumer lenient) {
        return lenient;
    }

    public static <R> IntFunction<R> intFunc(final LenientIntFunction<R> lenient) {
        return lenient;
    }

    public static IntPredicate intPredicate(final LenientIntPredicate lenient) {
        return lenient;
    }

    public static IntSupplier intSupplier(final LenientIntSupplier lenient) {
        return lenient;
    }

    public static IntToDoubleFunction intToDoubleFunc(final LenientIntToDoubleFunction lenient) {
        return lenient;
    }

    public static IntToLongFunction intToLongFunc(final LenientIntToLongFunction lenient) {
        return lenient;
    }

    public static IntUnaryOperator intUnaryOp(final LenientIntUnaryOperator lenient) {
        return lenient;
    }

    public static LongBinaryOperator longBinOp(final LenientLongBinaryOperator lenient) {
        return lenient;
    }

    public static LongConsumer longConsumer(final LenientLongConsumer lenient) {
        return lenient;
    }

    public static <R> LongFunction<R> longFunc(final LenientLongFunction<R> lenient) {
        return lenient;
    }

    public static LongPredicate longPredicate(final LenientLongPredicate lenient) {
        return lenient;
    }

    public static LongSupplier longSupplier(final LenientLongSupplier lenient) {
        return lenient;
    }

    public static LongToDoubleFunction longToDoubleFunc(final LenientLongToDoubleFunction lenient) {
        return lenient;
    }

    public static LongToIntFunction longToIntFunc(final LenientLongToIntFunction lenient) {
        return lenient;
    }

    public static LongUnaryOperator longUnaryOp(final LenientLongUnaryOperator lenient) {
        return lenient;
    }

    public static <T> ObjDoubleConsumer<T> objDoubleConsumer(final LenientObjDoubleConsumer<T> lenient) {
        return lenient;
    }

    public static <T> ObjIntConsumer<T> objIntConsumer(final LenientObjIntConsumer<T> lenient) {
        return lenient;
    }

    public static <T> ObjLongConsumer<T> objLongConsumer(final LenientObjLongConsumer<T> lenient) {
        return lenient;
    }

    public static Predicate predicate(final LenientPredicate lenient) {
        return lenient;
    }

    public static <T> Supplier<T> supplier(final LenientSupplier<T> lenient) {
        return lenient;
    }

    public static <T, U> ToDoubleBiFunction<T, U> toDoubleBiFunc(final LenientToDoubleBiFunction<T, U> lenient) {
        return lenient;
    }

    public static <T> ToDoubleFunction<T> toDoubleFunc(final LenientToDoubleFunction<T> lenient) {
        return lenient;
    }

    public static <T, U> ToIntBiFunction<T, U> toIntBiFunc(final LenientToIntBiFunction<T, U> lenient) {
        return lenient;
    }

    public static <T> ToIntFunction<T> toIntFunc(final LenientToIntFunction<T> lenient) {
        return lenient;
    }

    public static <T, U> ToLongBiFunction<T, U> toLongBiFunc(final LenientToLongBiFunction<T, U> lenient) {
        return lenient;
    }

    public static <T> UnaryOperator<T> unaryOp(final LenientUnaryOperator<T> lenient) {
        return lenient;
    }

}
