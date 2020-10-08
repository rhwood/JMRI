// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrix.loconet.sdf;

import org.junit.jupiter.api.Test;

/**
 * Tests for the jmri.jmrix.loconet.sdf.InitiateSound class.
 *
 * @author Bob Jacobsen Copyright 2007
 */
public class InitiateSoundTest {

    @Test
    public void testCtor() {
        new InitiateSound((byte) 0, (byte) 0);
    }

}