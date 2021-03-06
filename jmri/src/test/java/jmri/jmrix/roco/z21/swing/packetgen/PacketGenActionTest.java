// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrix.roco.z21.swing.packetgen;

import jmri.jmrix.roco.z21.Z21InterfaceScaffold;
import jmri.jmrix.roco.z21.Z21SystemConnectionMemo;
import jmri.util.JUnitUtil;

import org.junit.Assert;
import org.junit.jupiter.api.*;

/**
 * Tests for PacketGenAction class.
 *
 * @author Paul Bender Copyright (C) 2016
 **/

public class PacketGenActionTest {
        
   private Z21SystemConnectionMemo memo = null;
   private Z21InterfaceScaffold tc = null; 

   @Test
   public void ConstructorTest(){
      Assert.assertNotNull("PacketGenAction constructor",new PacketGenAction("Z21",memo));
   }

   @Test
   public void MemoConstructorTest(){
      Assert.assertNotNull("PacketGenAction constructor",new PacketGenAction(memo));
   }

   @BeforeEach
   public void setUp() {
        JUnitUtil.setUp();

        jmri.util.JUnitUtil.initDefaultUserMessagePreferences();
        memo = new Z21SystemConnectionMemo();
        tc = new Z21InterfaceScaffold();
        memo.setTrafficController(tc);
   }

   @AfterEach
   public void tearDown(){
        memo=null;
        tc.terminateThreads();
        tc=null;
        JUnitUtil.tearDown();
   }

}
