package com.github.mictaege.lenientfun;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.function.BooleanSupplier;

import static com.github.mictaege.lenientfun.LenientAdapter.booleanSupplier;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class LenientBooleanSupplierTest {

    @Mock
    private List value;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void shouldAcceptSupplier() {
        askLenientBooleanSupplier(() -> value.isEmpty());

        verify(value).isEmpty();
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleRaisedException() {
        askLenientBooleanSupplier(() -> {
            throw new Exception();
        });
    }

    @Test
    public void shouldAdaptLenientConsumer() {
        askJavaBooleanSupplier(booleanSupplier(() -> value.isEmpty()));

        verify(value).isEmpty();
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldAdaptThrowingLenientConsumer() {
        askJavaBooleanSupplier(booleanSupplier(() -> {
            throw new Exception();
        }));
    }

    private void askLenientBooleanSupplier(final LenientBooleanSupplier supplier) {
        try {
            supplier.getAsBoolean();
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    private void askJavaBooleanSupplier(final BooleanSupplier supplier) {
        supplier.getAsBoolean();
    }

}
