// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.util.swing;

import jmri.util.JUnitUtil;

import org.junit.Assert;
import org.junit.jupiter.api.*;

/**
 *
 * @author Paul Bender Copyright (C) 2017
 */
public class ButtonSwatchColorChooserPanelTest {

    protected ButtonSwatchColorChooserPanel panel = null;

    @Test
    public void testCTor() {
        Assert.assertNotNull("exists",panel);
    }

    @Test
    public void testGetDisplayName(){
        Assert.assertEquals("display name",Bundle.getMessage("ButtonSwatchColorChooserName"),panel.getDisplayName());
    }

    @BeforeEach
    public void setUp() {
        JUnitUtil.setUp();
        panel = new ButtonSwatchColorChooserPanel();
    }

    @AfterEach
    public void tearDown() {
        JUnitUtil.tearDown();
    }

    // private final static Logger log = LoggerFactory.getLogger(ButtonSwatchColorChooserPanelTest.class);

}
