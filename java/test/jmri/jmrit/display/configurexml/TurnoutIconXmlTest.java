// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrit.display.configurexml;

import jmri.util.JUnitUtil;

import org.junit.Assert;
import org.junit.jupiter.api.*;

/**
 * TurnoutIconXmlTest.java
 *
 * Test for the TurnoutIconXml class
 *
 * @author   Paul Bender  Copyright (C) 2016
 */
public class TurnoutIconXmlTest {

    @Test
    public void testCtor(){
      Assert.assertNotNull("TurnoutIconXml constructor",new TurnoutIconXml());
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

