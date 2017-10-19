package com.github.mictaege.lenientfun;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.function.IntSupplier;

import static com.github.mictaege.lenientfun.LenientAdapter.intSupplier;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class LenientIntSupplierTest {

    @Mock
    private List value;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void shouldAcceptSupplier() {
        askLenientIntSupplier(() -> value.size());

        verify(value).size();
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleRaisedException() {
        askLenientIntSupplier(() -> {
            throw new Exception();
        });
    }

    @Test
    public void shouldAdaptLenientSupplier() {
        askJavaIntSupplier(intSupplier(() -> value.size()));

        verify(value).size();
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldAdaptThrowingLenientSupplier() {
        askJavaIntSupplier(intSupplier(() -> {
            throw new Exception();
        }));
    }

    private void askLenientIntSupplier(final LenientIntSupplier supplier) {
        try {
            supplier.getAsInt();
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    private void askJavaIntSupplier(final IntSupplier supplier) {
        supplier.getAsInt();
    }

}
