/*
 * This file is part of a zITecs GmbH & Co. KG project.
 *
 * Copyright (c)
 *    zITecs GmbH & Co. KG
 *    All rights reserved.
 *
 * Any use of this file as part of a software system by none Copyright holders
 * is subject to license terms.
 */
package com.github.mictaege.lenientfun.example;

import com.github.mictaege.lenientfun.LenientAdapter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

public class JavaPersonConverterTest {

    @Mock
    private SimpleLogger logger;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void shouldConvertWithJavaStyleWithoutExceptions() {
        final Person person = new Person("Bruce", "Wayne", LocalDate.of(1939, 04, 12));

        final JavaPersonConverter converter = new JavaPersonConverter();

        final String json = converter.convert(person, p -> "{\n" +
                "  \"firstName\": \"" + p.getFirstName() + "\",\n" +
                "  \"surName\": \"" + p.getSurName() + "\"\n" +
                "}");

        final String yaml = converter.convert(person, p ->
                "firstName: " + p.getFirstName() + "\n" +
                "surName: " + p.getSurName() + "");

        assertThat(json, is("{\n" +
                "  \"firstName\": \"Bruce\",\n" +
                "  \"surName\": \"Wayne\"\n" +
                "}"));

        assertThat(yaml, is(
                "firstName: Bruce\n" +
                        "surName: Wayne"));
    }

    @Test
    public void shouldConvertWithJavaStyleAndExceptions() {
        final Person person = new Person("Bruce", "Wayne", LocalDate.of(1939, 04, 12));

        final JavaPersonConverter converter = new JavaPersonConverter();

        final String json = converter.convert(person, p -> {
            try {
                return "{\n" +
                        "  \"firstName\": \"" + p.getFirstName() + "\",\n" +
                        "  \"surName\": \"" + p.getSurName() + "\",\n" +
                        "  \"birthDay\": \"" + p.formatedBirthday() + "\"\n" + // throws checked exception
                        "}";
            } catch (final Exception e) {
                logger.error(e);
                throw new IllegalArgumentException("Conversion fails", e);
            }
        });

        final String yaml = converter.convert(person, p -> {
            try {
                return "firstName: " + p.getFirstName() + "\n" +
                        "surName: " + p.getSurName() + "\n" +
                        "birthDay: " + p.formatedBirthday() + ""; // throws checked exception
            } catch (final Exception e) {
                logger.error(e);
                throw new IllegalArgumentException("Conversion fails", e);
            }
        });

        assertThat(json, is("{\n" +
                "  \"firstName\": \"Bruce\",\n" +
                "  \"surName\": \"Wayne\",\n" +
                "  \"birthDay\": \"1939-04-12\"\n" +
                "}"));

        assertThat(yaml, is(
                "firstName: Bruce\n" +
                "surName: Wayne\n" +
                "birthDay: 1939-04-12"));
    }

    @Test
    public void shouldConvertWithLenientStyle() {
        final Person person = new Person("Bruce", "Wayne", LocalDate.of(1939, 04, 12));

        final JavaPersonConverter converter = new JavaPersonConverter();

        final String json = converter.convert(person, LenientAdapter.func(p -> "{\n" + // converter expects java.util.function.Function
                "  \"firstName\": \"" + p.getFirstName() + "\",\n" +
                "  \"surName\": \"" + p.getSurName() + "\",\n" +
                "  \"birthDay\": \"" + p.formatedBirthday() + "\"\n" + // throws checked exception
                "}"));

        final String yaml = converter.convert(person, LenientAdapter.func(p ->
                "firstName: " + p.getFirstName() + "\n" +
                "surName: " + p.getSurName() + "\n" +
                "birthDay: " + p.formatedBirthday() + ""));

        assertThat(json, is("{\n" +
                "  \"firstName\": \"Bruce\",\n" +
                "  \"surName\": \"Wayne\",\n" +
                "  \"birthDay\": \"1939-04-12\"\n" +
                "}"));

        assertThat(yaml, is(
                "firstName: Bruce\n" +
                        "surName: Wayne\n" +
                        "birthDay: 1939-04-12"));
    }

}