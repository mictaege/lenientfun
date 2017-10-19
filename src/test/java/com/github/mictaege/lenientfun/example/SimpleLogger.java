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

class SimpleLogger {

    void error(Exception exc) {
        System.out.println("[Error]: " + exc.getLocalizedMessage());
    }

}
