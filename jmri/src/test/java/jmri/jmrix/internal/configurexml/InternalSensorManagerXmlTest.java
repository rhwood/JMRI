// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrix.internal.configurexml;

import jmri.util.JUnitUtil;

import org.junit.Assert;
import org.junit.jupiter.api.*;

/**
 * InternalSensorManagerXmlTest.java
 *
 * Test for the InternalSensorManagerXml class
 *
 * @author   Paul Bender  Copyright (C) 2016
 */
public class InternalSensorManagerXmlTest {

    @Test
    public void testCtor(){
      Assert.assertNotNull("InternalSensorManagerXml constructor",new InternalSensorManagerXml());
    }

    @BeforeEach
    public void setUp() {
        JUnitUtil.setUp();
    }

    @AfterEach
    public void tearDown() {
        JUnitUtil.tearDown();
    }

}

