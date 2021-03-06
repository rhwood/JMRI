// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.util.swing.mdi;

import java.awt.GraphicsEnvironment;

import jmri.util.JUnitUtil;

import org.junit.Assume;
import org.junit.jupiter.api.*;

/**
 *
 * @author Bob Jacobsen Copyright 2003, 2010
 */
public class MdiMainFrameTest {

    @Test
    public void testShow() {
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());
        MdiMainFrame f = new MdiMainFrame("Test of MDI Frame",
                "src/test/java/jmri/util/swing/xml/Gui3LeftTree.xml",
                "src/test/java/jmri/util/swing/xml/Gui3Menus.xml",
                "src/test/java/jmri/util/swing/xml/Gui3MainToolBar.xml"
        );
        f.setSize(new java.awt.Dimension(400, 400));
        f.setVisible(true);

        // close
        JUnitUtil.dispose(f);
    }

    @BeforeEach
    public void setUp() throws Exception {
        JUnitUtil.setUp();
        JUnitUtil.resetProfileManager();
        JUnitUtil.initDefaultUserMessagePreferences();
    }

    @AfterEach
    public void tearDown() {
        JUnitUtil.tearDown();
    }

}
