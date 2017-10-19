package com.github.mictaege.lenientfun;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.function.LongConsumer;

import static com.github.mictaege.lenientfun.LenientAdapter.longConsumer;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class LenientLongConsumerTest {

    @Mock
    private List<Long> value0;
    @Mock
    private List<Long> value1;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void shouldAcceptConsumer() {
        feedLenientLongConsumer(5L, l -> value0.add(l));

        verify(value0).add(5L);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleRaisedException() {
        feedLenientLongConsumer(5L, l -> {
            throw new Exception();
        });
    }

    @Test
    public void shouldChainConsumers() {
        final LenientLongConsumer consumerOne = l -> value0.add(l);
        final LenientLongConsumer consumerTwo = l -> value1.add(l);

        feedLenientLongConsumer(5, consumerOne.andThen(consumerTwo));

        verify(value0).add(5L);
        verify(value1).add(5L);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInFirstChainedConsumer() {
        final LenientLongConsumer consumerOne = l -> { throw new Exception(); };
        final LenientLongConsumer consumerTwo = l -> value1.add(l);

        feedLenientLongConsumer(5L, consumerOne.andThen(consumerTwo));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInSecondChainedConsumer() {
        final LenientLongConsumer consumerOne = l -> value0.add(l);
        final LenientLongConsumer consumerTwo = l -> { throw new Exception(); };

        feedLenientLongConsumer(5L, consumerOne.andThen(consumerTwo));
    }

    @Test
    public void shouldAdaptLenientConsumer() {
        feedJavaLongConsumer(5L, longConsumer(l -> value0.add(l)));

        verify(value0).add(5L);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldAdaptThrowingLenientConsumer() {
        feedJavaLongConsumer(5L, longConsumer(l -> {
            throw new Exception();
        }));
    }

    private void feedLenientLongConsumer(final long value, final LenientLongConsumer consumer) {
        try {
            consumer.accept(value);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    private void feedJavaLongConsumer(final long value, final LongConsumer consumer) {
        consumer.accept(value);
    }

}
