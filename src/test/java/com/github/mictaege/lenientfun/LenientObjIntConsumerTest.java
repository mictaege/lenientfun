package com.github.mictaege.lenientfun;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.function.ObjIntConsumer;

import static com.github.mictaege.lenientfun.LenientAdapter.objIntConsumer;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class LenientObjIntConsumerTest {

    @Mock
    private List<Integer> value0;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void shouldAcceptObjIntConsumer() {
        feedLenientObjIntConsumer(value0, 5, (v, i) -> {
            v.add(i);
        });

        verify(value0).add(5);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleRaisedException() {
        feedLenientObjIntConsumer(value0, 5, (v, i) -> {
            throw new Exception();
        });
    }

    @Test
    public void shouldUseLenientObjIntConsumerInJava() {
        final LenientObjIntConsumer<List<Integer>> lenient = (v, i) -> {
            v.add(i);
        };
        feedJavaObjIntConsumer(value0, 5, lenient);

        verify(value0).add(5);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldUseThrowingLenientObjIntConsumerInJava() {
        final LenientObjIntConsumer<List<Integer>> lenient = (v, i) -> {
            throw new Exception();
        };
        feedJavaObjIntConsumer(value0, 5, lenient);
    }

    @Test
    public void shouldAdaptLenientObjIntConsumer() {
        feedJavaObjIntConsumer(value0, 5, objIntConsumer((v, i) -> {
            v.add(i);
        }));

        verify(value0).add(5);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldAdaptThrowingLenientObjIntConsumer() {
        feedJavaObjIntConsumer(value0, 5, objIntConsumer((v, i) -> {
            throw new Exception();
        }));
    }

    private <T> void feedLenientObjIntConsumer(final T value0, final int value1, final LenientObjIntConsumer<T> consumer) {
        try {
            consumer.accept(value0, value1);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    private <T> void feedJavaObjIntConsumer(final T value0, final int value1, final ObjIntConsumer<T> consumer) {
        consumer.accept(value0, value1);
    }

}
