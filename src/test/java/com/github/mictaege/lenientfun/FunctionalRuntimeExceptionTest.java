package com.github.mictaege.lenientfun;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

public class FunctionalRuntimeExceptionTest {

    @Mock
    private Throwable throwable;

    @Before
    public void context() {
        initMocks(this);
    }

    @Test
    public void shouldConstructWithThrowable() {
        final FunctionalRuntimeException exc = new FunctionalRuntimeException(throwable);

        assertThat(exc.getCause(), is(throwable));
    }

    @Test
    public void shouldConstructWithThrowableAndMessage() {
        final FunctionalRuntimeException exc = new FunctionalRuntimeException("Oopps", throwable);

        assertThat(exc.getMessage(), is("Oopps"));
        assertThat(exc.getCause(), is(throwable));
    }

    @Test
    public void shouldConstructWithThrowableMessageAndFlags() {
        final FunctionalRuntimeException exc = new FunctionalRuntimeException("Oopps", throwable, true, true);

        assertThat(exc.getMessage(), is("Oopps"));
        assertThat(exc.getCause(), is(throwable));
    }

}