package com.github.mictaege.lenientfun;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.function.DoubleConsumer;

import static com.github.mictaege.lenientfun.LenientAdapter.doubleConsumer;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class LenientDoubleConsumerTest {

    @Mock
    private List<Double> value0;
    @Mock
    private List<Double> value1;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void shouldAcceptConsumer() {
        feedLenientDoubleConsumer(5.0, d -> value0.add(d));

        verify(value0).add(5.0);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleRaisedException() {
        feedLenientDoubleConsumer(5.0, d -> {
            throw new Exception();
        });
    }

    @Test
    public void shouldChainConsumers() {
        final LenientDoubleConsumer consumerOne = d -> value0.add(d);
        final LenientDoubleConsumer consumerTwo = d -> value1.add(d);

        feedLenientDoubleConsumer(5.0, consumerOne.andThen(consumerTwo));

        verify(value0).add(5.0);
        verify(value1).add(5.0);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInFirstChainedConsumer() {
        final LenientDoubleConsumer consumerOne = d-> { throw new Exception(); };
        final LenientDoubleConsumer consumerTwo = d -> value1.add(d);

        feedLenientDoubleConsumer(5.0, consumerOne.andThen(consumerTwo));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInSecondChainedConsumer() {
        final LenientDoubleConsumer consumerOne = d -> value0.add(d);
        final LenientDoubleConsumer consumerTwo = d -> { throw new Exception(); };

        feedLenientDoubleConsumer(5.0, consumerOne.andThen(consumerTwo));
    }

    @Test
    public void shouldAdaptLenientConsumer() {
        feedJavaDoubleConsumer(5.0, doubleConsumer(d -> value0.add(d)));

        verify(value0).add(5.0);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldAdaptThrowingLenientConsumer() {
        feedJavaDoubleConsumer(5.0, doubleConsumer(d -> {
            throw new Exception();
        }));
    }

    private void feedLenientDoubleConsumer(final double value, final LenientDoubleConsumer consumer) {
        try {
            consumer.accept(value);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    private void feedJavaDoubleConsumer(final double value, final DoubleConsumer consumer) {
        consumer.accept(value);
    }

}
