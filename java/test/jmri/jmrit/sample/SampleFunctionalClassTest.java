// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrit.sample;

import org.junit.jupiter.api.*;

/**
 * Tests for the SampleFunctionalClass class
 *
 * @author Bob Jacobsen
 */
public class SampleFunctionalClassTest {

    @Test
    // test creation
    public void testCreate() {
        new SampleFunctionalClass("foo");
    }

    @BeforeEach
    public void setUp() {
        jmri.util.JUnitUtil.setUp();
        jmri.util.JUnitUtil.resetInstanceManager();
        jmri.util.JUnitUtil.resetProfileManager();
        jmri.util.JUnitUtil.initConfigureManager();
    }

    @AfterEach
    public void tearDown() {
        jmri.util.JUnitUtil.tearDown();
    }
}
