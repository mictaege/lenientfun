package com.github.mictaege.lenientfun;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.function.DoubleSupplier;

import static com.github.mictaege.lenientfun.LenientAdapter.doubleSupplier;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class LenientDoubleSupplierTest {

    @Mock
    private List value;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void shouldAcceptSupplier() {
        askLenientDoubleSupplier(() -> value.size());

        verify(value).size();
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleRaisedException() {
        askLenientDoubleSupplier(() -> {
            throw new Exception();
        });
    }

    @Test
    public void shouldUseLenientSupplierInJava() {
        final LenientDoubleSupplier lenient = () -> value.size();
        askJavaDoubleSupplier(lenient);

        verify(value).size();
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldUseThrowingLenientSupplierInJava() {
        final LenientDoubleSupplier lenient = () -> {
            throw new Exception();
        };
        askJavaDoubleSupplier(lenient);
    }

    @Test
    public void shouldAdaptLenientSupplier() {
        askJavaDoubleSupplier(doubleSupplier(() -> value.size()));

        verify(value).size();
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldAdaptThrowingLenientSupplier() {
        askJavaDoubleSupplier(doubleSupplier(() -> {
            throw new Exception();
        }));
    }

    private void askLenientDoubleSupplier(final LenientDoubleSupplier supplier) {
        try {
            supplier.getAsDouble();
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    private void askJavaDoubleSupplier(final DoubleSupplier supplier) {
        supplier.getAsDouble();
    }

}
