// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrit.display.palette;

import java.awt.GraphicsEnvironment;

import org.junit.jupiter.api.*;

/**
 *
 * @author Paul Bender Copyright (C) 2017
 */
public class ItemDialogTest extends jmri.util.JmriJFrameTestBase {

    @BeforeEach
    @Override
    public void setUp() {
        jmri.util.JUnitUtil.setUp();
        if(!GraphicsEnvironment.isHeadless()){
           frame = new ItemDialog("Sensors", "Icons", null);
        }
    }

    @AfterEach
    @Override
    public void tearDown() {
        super.tearDown();
    }

    // private final static Logger log = LoggerFactory.getLogger(ItemDialogTest.class);

}
