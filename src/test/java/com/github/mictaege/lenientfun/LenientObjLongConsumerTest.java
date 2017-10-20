package com.github.mictaege.lenientfun;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.function.ObjLongConsumer;

import static com.github.mictaege.lenientfun.LenientAdapter.objLongConsumer;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class LenientObjLongConsumerTest {

    @Mock
    private List<Long> value0;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void shouldAcceptObjLongConsumer() {
        feedLenientObjLongConsumer(value0, 5L, (v, l) -> {
            v.add(l);
        });

        verify(value0).add(5L);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleRaisedException() {
        feedLenientObjLongConsumer(value0, 5L, (v, l) -> {
            throw new Exception();
        });
    }

    @Test
    public void shouldUseLenientObjLongConsumerInJava() {
        final LenientObjLongConsumer<List<Long>> lenient = (v, l) -> {
            v.add(l);
        };
        feedJavaObjLongConsumer(value0, 5L, lenient);

        verify(value0).add(5L);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldUseThrowingLenientObjLongConsumerInJava() {
        final LenientObjLongConsumer<List<Long>> lenient = (v, l) -> {
            throw new Exception();
        };
        feedJavaObjLongConsumer(value0, 5L, lenient);
    }

    @Test
    public void shouldAdaptLenientObjLongConsumer() {
        feedJavaObjLongConsumer(value0, 5L, objLongConsumer((v, l) -> {
            v.add(l);
        }));

        verify(value0).add(5L);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldAdaptThrowingLenientObjLongConsumer() {
        feedJavaObjLongConsumer(value0, 5L, objLongConsumer((v, l) -> {
            throw new Exception();
        }));
    }

    private <T> void feedLenientObjLongConsumer(final T value0, final long value1, final LenientObjLongConsumer<T> consumer) {
        try {
            consumer.accept(value0, value1);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    private <T> void feedJavaObjLongConsumer(final T value0, final long value1, final ObjLongConsumer<T> consumer) {
        consumer.accept(value0, value1);
    }

}
