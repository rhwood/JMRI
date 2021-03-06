// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrix.can.cbus.swing.modeswitcher;

import java.awt.GraphicsEnvironment;

import jmri.GlobalProgrammerManager;
import jmri.jmrix.can.CanSystemConnectionMemo;
import jmri.jmrix.can.TrafficControllerScaffold;
import jmri.jmrix.can.cbus.CbusDccProgrammer;
import jmri.jmrix.can.cbus.CbusDccProgrammerManager;
import jmri.jmrix.can.cbus.CbusPreferences;
import jmri.util.JUnitUtil;

import org.junit.jupiter.api.*;

/**
 * Tests for the ModeSwitcherPane class
 *
 * @author Andrew Crosland (C) 2020
 */
public class SprogCbusModeSwitcherFrameTest extends jmri.util.JmriJFrameTestBase {

    CanSystemConnectionMemo memo;
    CbusDccProgrammer prog;
    jmri.jmrix.can.TrafficController tc;
    
    @BeforeEach
    @Override
    public void setUp() {
        JUnitUtil.setUp();
        
        jmri.InstanceManager.setDefault(jmri.jmrix.can.cbus.CbusPreferences.class,new CbusPreferences() );

        tc = new TrafficControllerScaffold();
        memo = new CanSystemConnectionMemo();
        memo.setTrafficController(tc);
        prog = new CbusDccProgrammer(tc);
        
        jmri.InstanceManager.setDefault(GlobalProgrammerManager.class,new CbusDccProgrammerManager(prog, memo) );
        if (!GraphicsEnvironment.isHeadless()) {
            frame = new SprogCbusModeSwitcherFrame(memo);
        }
    }

    @AfterEach
    @Override
    public void tearDown() {
        prog = null;
        tc.terminateThreads();
        memo.dispose();
        tc = null;
        memo = null;
        super.tearDown();
    }
}
