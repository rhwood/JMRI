// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrit.sample.swing;

import org.junit.jupiter.api.*;

/**
 * Tests for the SampleConfigPane class
 *
 * @author Bob Jacobsen
 */
public class SampleConfigPaneTest {

    @Test
    // test creation
    public void testCreate() {
        new SampleConfigPane();
    }

    @BeforeEach
    public void setUp() {
        jmri.util.JUnitUtil.setUp();
    }

    @AfterEach
    public void tearDown() {
        jmri.util.JUnitUtil.tearDown();
    }
}
