package com.github.mictaege.lenientfun;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.function.Supplier;

import static com.github.mictaege.lenientfun.LenientAdapter.supplier;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class LenientSupplierTest {

    @Mock
    private List value;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void shouldAcceptSupplier() {
        askLenientSupplier(() -> value.size());

        verify(value).size();
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleRaisedException() {
        askLenientSupplier(() -> {
            throw new Exception();
        });
    }

    @Test
    public void shouldAdaptLenientSupplier() {
        askJavaSupplier(supplier(() -> value.size()));

        verify(value).size();
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldAdaptThrowingLenientSupplier() {
        askJavaSupplier(supplier(() -> {
            throw new Exception();
        }));
    }

    private void askLenientSupplier(final LenientSupplier supplier) {
        try {
            supplier.get();
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    private void askJavaSupplier(final Supplier supplier) {
        supplier.get();
    }

}
