// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrit.operations;

import org.junit.Assert;
import org.junit.jupiter.api.*;

/**
 * Tests for the CommonConductorYardmasterPanel class
 *
 * @author Paul Bender Copyright (C) 2017
 */
public class CommonConductorYardmasterPanelTest extends OperationsTestCase {

    protected CommonConductorYardmasterPanel p = null;

    @Test
    public void testCtor() {
        Assert.assertNotNull("exists", p);
    }

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        p = new CommonConductorYardmasterPanel() {
            @Override
            public void update() {
            }
        };
    }

}
