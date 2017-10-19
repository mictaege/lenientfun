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

import com.github.mictaege.lenientfun.LenientFunction;

class LenientPersonConverter {

    private final SimpleLogger logger;

    LenientPersonConverter() {
        super();
        logger = new SimpleLogger();
    }

    public String convert(final Person person, final LenientFunction<Person, String> converter) {
        try {
            return converter.apply(person);
        } catch (final Exception e) {
            logger.error(e);
            throw new IllegalArgumentException("Conversion fails", e);
        }
    }

}
