// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrix.nce.cab;

import jmri.util.JUnitUtil;

import org.junit.jupiter.api.*;

/**
 * Test simple functioning of NceShowCabPanel
 *
 * @author Paul Bender Copyright (C) 2016
 */
public class NceShowCabPanelTest extends jmri.util.swing.JmriPanelTest {

    @Override
    @BeforeEach
    public void setUp() {
        JUnitUtil.setUp();
        panel = new NceShowCabPanel();
        helpTarget="package.jmri.jmrix.nce.cab.NceShowCabFrame";
        title="NCE_: " + Bundle.getMessage("Title");
    }

    @Override
    @AfterEach
    public void tearDown() {        JUnitUtil.tearDown();    }
}
