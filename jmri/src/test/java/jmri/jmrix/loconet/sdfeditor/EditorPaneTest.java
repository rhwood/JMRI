// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrix.loconet.sdfeditor;

import java.awt.GraphicsEnvironment;

import jmri.jmrix.loconet.sdf.SdfBuffer;
import jmri.util.JUnitUtil;

import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.Assume;

/**
 * Tests for the jmri.jmrix.loconet.sdfeditor.EditorPane class.
 *
 * @author Bob Jacobsen Copyright 2007
 */
public class EditorPaneTest {

    @Test
    public void testShowPane() throws java.io.IOException {
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());
        SdfBuffer buff = new SdfBuffer("src/test/java/jmri/jmrix/loconet/sdf/test2.sdf");
        Assert.assertNotNull(buff);
        EditorFrame f = new EditorFrame(buff);
        f.setVisible(true);
        f.dispose();
    }

    @BeforeEach
    public void setUp() {
        JUnitUtil.setUp();
        jmri.util.JUnitUtil.resetProfileManager();
    }

    @AfterEach
    public void tearDown() {
        JUnitUtil.tearDown();
    }

}
