// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrit.tracker;

import org.junit.jupiter.api.Test;

import jmri.Block;

/**
 * Tests for the StoppingBlock class
 *
 * @author Bob Jacobsen Copyright (C) 2006
 */
public class StoppingBlockTest {

    @Test
    public void testDirectCreate() {
        // check for exception in ctor
        new StoppingBlock(new Block("dummy"));
    }
}
