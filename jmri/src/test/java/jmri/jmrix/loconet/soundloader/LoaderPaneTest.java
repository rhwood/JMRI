// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrix.loconet.soundloader;

import jmri.util.JUnitUtil;

import org.junit.jupiter.api.*;

/**
 *
 * @author Paul Bender Copyright (C) 2017
 */
public class LoaderPaneTest extends jmri.util.swing.JmriPanelTest {

    @Override
    @BeforeEach
    public void setUp() {
        JUnitUtil.setUp();
        panel = new LoaderPane();
        helpTarget = "package.jmri.jmrix.loconet.soundloader.LoaderFrame";
        title = "Download Sounds";
    }

    @Override
    @AfterEach
    public void tearDown() {
        JUnitUtil.tearDown();
    }

    // private final static Logger log = LoggerFactory.getLogger(LoaderPaneTest.class);

}
