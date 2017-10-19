package com.github.mictaege.lenientfun;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.function.IntConsumer;

import static com.github.mictaege.lenientfun.LenientAdapter.intConsumer;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class LenientIntConsumerTest {

    @Mock
    private List<Integer> value0;
    @Mock
    private List<Integer> value1;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void shouldAcceptConsumer() {
        feedLenientIntConsumer(5, i -> value0.add(i));

        verify(value0).add(5);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleRaisedException() {
        feedLenientIntConsumer(5, i -> {
            throw new Exception();
        });
    }

    @Test
    public void shouldChainConsumers() {
        final LenientIntConsumer consumerOne = i -> value0.add(i);
        final LenientIntConsumer consumerTwo = i -> value1.add(i);

        feedLenientIntConsumer(5, consumerOne.andThen(consumerTwo));

        verify(value0).add(5);
        verify(value1).add(5);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInFirstChainedConsumer() {
        final LenientIntConsumer consumerOne = i -> { throw new Exception(); };
        final LenientIntConsumer consumerTwo = i -> value1.add(i);

        feedLenientIntConsumer(5, consumerOne.andThen(consumerTwo));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInSecondChainedConsumer() {
        final LenientIntConsumer consumerOne = i -> value0.add(i);
        final LenientIntConsumer consumerTwo = i -> { throw new Exception(); };

        feedLenientIntConsumer(5, consumerOne.andThen(consumerTwo));
    }

    @Test
    public void shouldAdaptLenientConsumer() {
        feedJavaIntConsumer(5, intConsumer(i -> value0.add(i)));

        verify(value0).add(5);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldAdaptThrowingLenientConsumer() {
        feedJavaIntConsumer(5, intConsumer(i -> {
            throw new Exception();
        }));
    }

    private void feedLenientIntConsumer(final int value, final LenientIntConsumer consumer) {
        try {
            consumer.accept(value);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    private void feedJavaIntConsumer(final int value, final IntConsumer consumer) {
        consumer.accept(value);
    }

}
