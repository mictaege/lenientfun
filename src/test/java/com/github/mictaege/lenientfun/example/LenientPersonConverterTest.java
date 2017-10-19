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

import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LenientPersonConverterTest {

    @Test
    public void shouldConvertWithLenientStyle() {
        final Person person = new Person("Bruce", "Wayne", LocalDate.of(1939, 04, 12));

        final LenientPersonConverter converter = new LenientPersonConverter();

        final String json = converter.convert(person, p -> "{\n" +
                    "  \"firstName\": \"" + p.getFirstName() + "\",\n" +
                    "  \"surName\": \"" + p.getSurName() + "\",\n" +
                    "  \"birthDay\": \"" + p.formatedBirthday() + "\"\n" +
                    "}");

        final String yaml = converter.convert(person, p ->
                    "firstName: " + p.getFirstName() + "\n" +
                    "surName: " + p.getSurName() + "\n" +
                    "birthDay: " + p.formatedBirthday() + "");

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