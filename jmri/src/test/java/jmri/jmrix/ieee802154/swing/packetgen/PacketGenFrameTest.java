// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrix.ieee802154.swing.packetgen;

import java.awt.GraphicsEnvironment;

import jmri.util.JUnitUtil;

import org.junit.jupiter.api.*;

/**
 * Test simple functioning of PacketGenFrame
 *
 * @author Paul Bender Copyright (C) 2016
 */
public class PacketGenFrameTest extends jmri.util.JmriJFrameTestBase {

    // private IEEE802154TrafficController tc = null;

    @BeforeEach
    @Override
    public void setUp() {
        JUnitUtil.setUp();
//         tc = new IEEE802154TrafficController() {
//             @Override
//             public void setInstance() {
//             }
//             @Override
//             protected jmri.jmrix.AbstractMRReply newReply() {
//                 return null;
//             }
//             @Override
//             public jmri.jmrix.ieee802154.IEEE802154Node newNode() {
//                 return null;
//             }
//         };
        if(!GraphicsEnvironment.isHeadless()){
           frame = new PacketGenFrame();
        }
    }

    @AfterEach
    @Override
    public void tearDown() {
        // tc = null;
        JUnitUtil.clearShutDownManager(); // put in place because AbstractMRTrafficController implementing subclass was not terminated properly
        super.tearDown();
    }
}
