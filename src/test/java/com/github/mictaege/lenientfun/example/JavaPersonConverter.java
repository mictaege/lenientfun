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

import java.util.function.Function;

class JavaPersonConverter {

    public String convert(final Person person, final Function<Person, String> converter) {
        return converter.apply(person);
    }

}
