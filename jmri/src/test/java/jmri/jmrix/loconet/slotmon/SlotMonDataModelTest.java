// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrix.loconet.slotmon;

import jmri.jmrix.loconet.LnTrafficController;
import jmri.jmrix.loconet.LocoNetInterfaceScaffold;
import jmri.jmrix.loconet.LocoNetSystemConnectionMemo;
import jmri.jmrix.loconet.SlotManager;
import jmri.util.JUnitUtil;

import org.junit.Assert;
import org.junit.jupiter.api.*;
/**
 *
 * @author Paul Bender Copyright (C) 2017
 */
public class SlotMonDataModelTest {

    @Test
    public void testCTor() {
        LnTrafficController lnis = new LocoNetInterfaceScaffold();
        SlotManager slotmanager = new SlotManager(lnis);
        LocoNetSystemConnectionMemo memo = new LocoNetSystemConnectionMemo(lnis,slotmanager);
        SlotMonDataModel t = new SlotMonDataModel(1,19,memo);
        Assert.assertNotNull("exists",t);
        memo.dispose();
    }

    @BeforeEach
    public void setUp() {
        JUnitUtil.setUp();
    }

    @AfterEach
    public void tearDown() {
        JUnitUtil.tearDown();
    }

    // private final static Logger log = LoggerFactory.getLogger(SlotMonDataModelTest.class);

}