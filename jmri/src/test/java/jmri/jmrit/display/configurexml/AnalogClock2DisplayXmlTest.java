// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrit.display.configurexml;

import jmri.util.JUnitUtil;

import org.junit.jupiter.api.*;
import org.junit.Assert;

/**
 * AnalogClock2DisplayXmlTest.java
 *
 * Test for the AnalogClock2DisplayXml class
 *
 * @author   Paul Bender  Copyright (C) 2016
 */
public class AnalogClock2DisplayXmlTest {

    @Test
    public void testCtor(){
      Assert.assertNotNull("AnalogClock2DisplayXml constructor",new AnalogClock2DisplayXml());
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

