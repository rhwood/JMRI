// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrix.tams.swing.locodatabase;

import org.junit.Assert;
import org.junit.jupiter.api.*;

import jmri.jmrix.tams.TamsSystemConnectionMemo;
import jmri.jmrix.tams.TamsInterfaceScaffold;
import jmri.jmrix.tams.TamsTrafficController;

/**
 *
 * @author Paul Bender Copyright (C) 2017
 */
public class LocoDataModelTest {

    private TamsSystemConnectionMemo memo = null;

    @Test
    public void testCTor() {
        LocoDataModel t = new LocoDataModel(128,16,memo);
        Assert.assertNotNull("exists",t);
    }

    @BeforeEach
    public void setUp() {
        jmri.util.JUnitUtil.setUp();
        TamsTrafficController tc = new TamsInterfaceScaffold();
        memo = new TamsSystemConnectionMemo(tc);
    }

    @AfterEach
    public void tearDown() {
        memo = null;
        jmri.util.JUnitUtil.clearShutDownManager(); // put in place because AbstractMRTrafficController implementing subclass was not terminated properly
        jmri.util.JUnitUtil.tearDown();
    }

    // private final static Logger log = LoggerFactory.getLogger(LocoDataModelTest.class.getName());

}
