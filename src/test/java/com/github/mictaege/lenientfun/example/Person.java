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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.util.Optional.ofNullable;

class Person {

    private final String firstName;
    private final String surName;
    private final LocalDate birthDay;

    Person(final String firstName, final String surName, final LocalDate birthDay) {
        super();
        this.firstName = firstName;
        this.surName = surName;
        this.birthDay = birthDay;
    }

    String getFirstName() {
        return firstName;
    }

    String getSurName() {
        return surName;
    }

    LocalDate getBirthDay() {
        return birthDay;
    }

    String formatedBirthday() throws Exception {
        return ofNullable(getBirthDay())
                .map(b -> b.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .orElseThrow(() -> new Exception("Birthday is missing"));
    }

}
