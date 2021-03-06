package com.github.mictaege.lenientfun;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.function.BiConsumer;

import static com.github.mictaege.lenientfun.LenientAdapter.biConsumer;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class LenientBiConsumerTest {

    @Mock
    private List value0;
    @Mock
    private List value1;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void shouldAcceptBiConsumer() {
        feedLenientBiConsumer(value0, value1, (v0, v1) -> {
            v0.size();
            v1.size();
        });

        verify(value0).size();
        verify(value1).size();
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleRaisedException() {
        feedLenientBiConsumer(value0, value1, (v0, v1) -> {
            throw new Exception();
        });
    }

    @Test
    public void shouldChainBiConsumers() {
        final LenientBiConsumer<List, List> consumerOne = (v0, v1) -> {
            v0.size();
            v1.size();
        };
        final LenientBiConsumer<List, List> consumerTwo = (v0, v1) -> {
            v0.isEmpty();
            v1.isEmpty();
        };

        feedLenientBiConsumer(value0, value1, consumerOne.andThen(consumerTwo));

        verify(value0).size();
        verify(value1).size();
        verify(value0).isEmpty();
        verify(value1).isEmpty();
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInFirstChainedBiConsumer() {
        final LenientBiConsumer<List, List> consumerOne = (v0, v1) -> { throw new Exception(); };
        final LenientBiConsumer<List, List> consumerTwo = (v0, v1) -> {
            v0.isEmpty();
            v1.isEmpty();
        };

        feedLenientBiConsumer(value0, value1, consumerOne.andThen(consumerTwo));
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleExceptionInSecondChainedBiConsumer() {
        final LenientBiConsumer<List, List> consumerOne = (v0, v1) -> {
            v0.size();
            v1.size();
        };
        final LenientBiConsumer<List, List> consumerTwo = (v0, v1) -> { throw new Exception(); };

        feedLenientBiConsumer(value0, value1, consumerOne.andThen(consumerTwo));
    }

    @Test
    public void shouldUseLenientBiConsumerInJava() {
        final LenientBiConsumer<List, List> lenient = (v0, v1) -> {
            v0.size();
            v1.size();
        };
        feedJavaBiConsumer(value0, value1, lenient);

        verify(value0).size();
        verify(value1).size();
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldUseThrowingLenientBiConsumerInJava() {
        final LenientBiConsumer<List, List> lenient = (v0, v1) -> {
            throw new Exception();
        };
        feedJavaBiConsumer(value0, value1, lenient);
    }

    @Test
    public void shouldAdaptLenientBiConsumer() {
        feedJavaBiConsumer(value0, value1, biConsumer((v0, v1) -> {
            v0.size();
            v1.size();
        }));

        verify(value0).size();
        verify(value1).size();
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldAdaptThrowingLenientBiConsumer() {
        feedJavaBiConsumer(value0, value1, biConsumer((v0, v1) -> {
            throw new Exception();
        }));
    }

    private <T, U> void feedLenientBiConsumer(final T value0, final U value1, final LenientBiConsumer<T, U> consumer) {
        try {
            consumer.accept(value0, value1);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    private <T, U> void feedJavaBiConsumer(final T value0, final U value1, final BiConsumer<T, U> consumer) {
        consumer.accept(value0, value1);
    }

}
