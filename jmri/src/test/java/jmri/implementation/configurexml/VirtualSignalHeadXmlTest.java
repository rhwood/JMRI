// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.implementation.configurexml;

import jmri.util.JUnitUtil;

import org.junit.jupiter.api.*;
import org.junit.Assert;

/**
 * VirtualSignalHeadXmlTest.java
 *
 * Test for the VirtualSignalHeadXml class
 *
 * @author   Paul Bender  Copyright (C) 2016
 */
public class VirtualSignalHeadXmlTest {

    @Test
    public void testCtor(){
      Assert.assertNotNull("VirtualSignalHeadXml constructor",new VirtualSignalHeadXml());
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

