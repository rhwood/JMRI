// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.implementation;

import jmri.SignalHead;
import jmri.util.JUnitUtil;

import org.junit.jupiter.api.*;
import org.junit.Assert;

/**
 *
 * @author Paul Bender Copyright (C) 2017
 */
public class VirtualSignalHeadTest extends AbstractSignalHeadTestBase {

    @Test
    public void testCTor() {
        VirtualSignalHead t = new VirtualSignalHead("Virtual Signal Head Test");
        Assert.assertNotNull("exists",t);
    }

    @Override
    public SignalHead getHeadToTest() {
        return new VirtualSignalHead("Virtual Signal Head Test");
    }

    @BeforeEach
    public void setUp() {
        JUnitUtil.setUp();
    }

    @AfterEach
    public void tearDown() {
        JUnitUtil.tearDown();
    }

    //private final static Logger log = LoggerFactory.getLogger(VirtualSignalHeadTest.class);

}
