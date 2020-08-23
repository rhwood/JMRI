// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrix.dccpp;

import org.junit.jupiter.api.*;
import org.junit.Assert;

/**
 * DCCppInitializationManagerTest.java
 *
 * Test for the jmri.jmrix.dccpp.DCCppInitializationManager class
 *
 * @author Paul Bender
 * @author Mark Underwood
 */
public class DCCppInitializationManagerTest {

    @Test
    public void testCtor() {

        // infrastructure objects
        DCCppInterfaceScaffold t = new DCCppInterfaceScaffold(new DCCppCommandStation());
        DCCppListenerScaffold l = new DCCppListenerScaffold();
        
        DCCppSystemConnectionMemo memo = new DCCppSystemConnectionMemo(t);

        DCCppInitializationManager m = new DCCppInitializationManager(memo) {
                @Override
                protected int getInitTimeout() {
                    return 50;   // shorten, because this will fail & delay test
                }
            };
        Assert.assertNotNull("exists", t);
        Assert.assertNotNull("exists", l);
        Assert.assertNotNull("exists", m);
        Assert.assertNotNull("exists", memo);
        //jmri.util.JUnitAppender.assertWarnMessage("Command Station disconnected, or powered down");
    }
    
    @BeforeEach
    public void setUp() throws Exception {
        jmri.util.JUnitUtil.setUp();
    }

    @AfterEach
    public void tearDown() throws Exception {
        jmri.util.JUnitUtil.clearShutDownManager(); // put in place because AbstractMRTrafficController implementing subclass was not terminated properly
        jmri.util.JUnitUtil.tearDown();

    }

}
