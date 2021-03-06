// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri;

import org.junit.Assert;
import org.junit.jupiter.api.*;

/**
 * Tests for the ProgrammingMode class
 *
 * @author Bob Jacobsen Copyright (C) 2014
 */
public class ProgrammingModeTest {

    @Test
    public void testStateCtors() {
        // tests that statics exist, are not equal
        Assert.assertTrue(ProgrammingMode.PAGEMODE.equals(ProgrammingMode.PAGEMODE));
        Assert.assertTrue(!ProgrammingMode.REGISTERMODE.equals(ProgrammingMode.PAGEMODE));
    }

}
