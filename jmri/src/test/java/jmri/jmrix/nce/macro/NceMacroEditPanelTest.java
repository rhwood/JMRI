// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrix.nce.macro;

import jmri.util.JUnitUtil;

import org.junit.jupiter.api.*;

/**
 * Test simple functioning of NceMacroEditPanel
 *
 * @author Paul Bender Copyright (C) 2016
 */
public class NceMacroEditPanelTest extends jmri.util.swing.JmriPanelTest {

    @Override
    @BeforeEach
    public void setUp() {
        JUnitUtil.setUp();
        panel = new NceMacroEditPanel();
        helpTarget="package.jmri.jmrix.nce.macro.NceMacroEditFrame";
        title="NCE_: " + Bundle.getMessage("TitleEditNCEMacro");
    }

    @Override
    @AfterEach
    public void tearDown() {        JUnitUtil.tearDown();    }
}
