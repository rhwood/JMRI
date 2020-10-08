// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrix.rfid.merg.concentrator;

import jmri.jmrix.rfid.RfidSystemConnectionMemo;
import jmri.util.JUnitUtil;

import org.junit.Assert;
import org.junit.jupiter.api.*;

/**
 * ConcentratorSensorManagerTest.java
 * <p>
 * Test for the ConcentratorSensorManager class
 *
 * @author Paul Bender Copyright (C) 2012,2016
 */
public class ConcentratorSensorManagerTest extends jmri.managers.AbstractSensorMgrTestBase {

    ConcentratorTrafficController tc = null;

    @Override
    public String getSystemName(int i) {
        return "RS" + i;
    }

    @Test
    public void testCtor() {
        Assert.assertNotNull(l);
    }

    @BeforeEach
    @Override
    public void setUp() {
        JUnitUtil.setUp();
        RfidSystemConnectionMemo memo = new RfidSystemConnectionMemo();
        tc = new ConcentratorTrafficController(memo, "A-H") {
            @Override
            public void sendInitString() {
            }
        };
        memo.setRfidTrafficController(tc);
        memo.setSystemPrefix("R");
        l = new ConcentratorSensorManager(tc.getAdapterMemo()) {
            @Override
            public void message(jmri.jmrix.rfid.RfidMessage m) {
            }

            @Override
            public void reply(jmri.jmrix.rfid.RfidReply m) {
            }

        };
    }

    @AfterEach
    public void tearDown() {
        tc = null;
        JUnitUtil.clearShutDownManager(); // put in place because AbstractMRTrafficController implementing subclass was not terminated properly
        JUnitUtil.tearDown();

    }

}