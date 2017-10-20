package com.github.mictaege.lenientfun;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.function.LongSupplier;

import static com.github.mictaege.lenientfun.LenientAdapter.longSupplier;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class LenientLongSupplierTest {

    @Mock
    private List value;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void shouldAcceptSupplier() {
        askLenientLongSupplier(() -> value.size());

        verify(value).size();
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleRaisedException() {
        askLenientLongSupplier(() -> {
            throw new Exception();
        });
    }

    @Test
    public void shouldUseLenientSupplierInJava() {
        final LenientLongSupplier lenient = () -> value.size();
        askJavaLongSupplier(lenient);

        verify(value).size();
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldUseThrowingLenientSupplierInJava() {
        final LenientLongSupplier lenient = () -> {
            throw new Exception();
        };
        askJavaLongSupplier(lenient);
    }

    @Test
    public void shouldAdaptLenientSupplier() {
        askJavaLongSupplier(longSupplier(() -> value.size()));

        verify(value).size();
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldAdaptThrowingLenientSupplier() {
        askJavaLongSupplier(longSupplier(() -> {
            throw new Exception();
        }));
    }

    private void askLenientLongSupplier(final LenientLongSupplier supplier) {
        try {
            supplier.getAsLong();
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    private void askJavaLongSupplier(final LongSupplier supplier) {
        supplier.getAsLong();
    }

}
