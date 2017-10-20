package com.github.mictaege.lenientfun;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.function.ObjDoubleConsumer;

import static com.github.mictaege.lenientfun.LenientAdapter.objDoubleConsumer;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class LenientObjDoubleConsumerTest {

    @Mock
    private List<Double> value0;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void shouldAcceptObjDoubleConsumer() {
        feedLenientObjDoubleConsumer(value0, 5.0, (v, d) -> {
            v.add(d);
        });

        verify(value0).add(5.0);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldHandleRaisedException() {
        feedLenientObjDoubleConsumer(value0, 5.0, (v, d) -> {
            throw new Exception();
        });
    }

    @Test
    public void shouldUseLenientObjDoubleConsumerInJava() {
        final LenientObjDoubleConsumer<List<Double>> lenient = (v, d) -> {
            v.add(5.0);
        };
        feedJavaObjDoubleConsumer(value0, 5.0, lenient);

        verify(value0).add(5.0);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldUseThrowingLenientObjDoubleConsumerInJava() {
        final LenientObjDoubleConsumer<List<Double>> lenient = (v, d) -> {
            throw new Exception();
        };
        feedJavaObjDoubleConsumer(value0, 5.0, lenient);
    }

    @Test
    public void shouldAdaptLenientObjDoubleConsumer() {
        feedJavaObjDoubleConsumer(value0, 5.0, objDoubleConsumer((v, d) -> {
            v.add(5.0);
        }));

        verify(value0).add(5.0);
    }

    @Test(expected = FunctionalRuntimeException.class)
    public void shouldAdaptThrowingLenientObjDoubleConsumer() {
        feedJavaObjDoubleConsumer(value0, 5.0, objDoubleConsumer((v, d) -> {
            throw new Exception();
        }));
    }

    private <T> void feedLenientObjDoubleConsumer(final T value0, final double value1, final LenientObjDoubleConsumer<T> consumer) {
        try {
            consumer.accept(value0, value1);
        } catch (final Exception e) {
            throw new FunctionalRuntimeException(e);
        }
    }

    private <T> void feedJavaObjDoubleConsumer(final T value0, final double value1, final ObjDoubleConsumer<T> consumer) {
        consumer.accept(value0, value1);
    }

}
