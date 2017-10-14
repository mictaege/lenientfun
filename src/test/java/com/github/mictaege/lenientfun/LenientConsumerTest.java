package com.github.mictaege.lenientfun;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.function.Consumer;

import static com.github.mictaege.lenientfun.LenientAdapter.accept;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class LenientConsumerTest {

    @Mock
    private List value;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void shouldAcceptConsumer() {
        feedLenientConsumer(value, List::size);

        verify(value).size();
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleRaisedException() {
        feedLenientConsumer(value, v -> {
            throw new Exception();
        });
    }

    @Test
    public void shouldChainConsumers() {
        final LenientConsumer<List> consumerOne = List::size;
        final LenientConsumer<List> consumerTwo = List::isEmpty;

        feedLenientConsumer(value, consumerOne.andThen(consumerTwo));

        verify(value).size();
        verify(value).isEmpty();
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInFirstChainedConsumer() {
        final LenientConsumer<List> consumerOne = v -> { throw new Exception(); };
        final LenientConsumer<List> consumerTwo = List::isEmpty;

        feedLenientConsumer(value, consumerOne.andThen(consumerTwo));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInSecondChainedConsumer() {
        final LenientConsumer<List> consumerOne = List::size;
        final LenientConsumer<List> consumerTwo = v -> { throw new Exception(); };

        feedLenientConsumer(value, consumerOne.andThen(consumerTwo));
    }

    @Test
    public void shouldAdaptLenientConsumer() {
        feedJavaConsumer(value, accept(List::size));

        verify(value).size();
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldAdaptThrowingLenientConsumer() {
        feedJavaConsumer(value, accept(v -> {
            throw new Exception();
        }));
    }

    private <T> void feedLenientConsumer(final T value, final LenientConsumer<T> consumer) {
        try {
            consumer.accept(value);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    private <T> void feedJavaConsumer(final T value, final Consumer<T> consumer) {
        consumer.accept(value);
    }

}
