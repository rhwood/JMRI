// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrit.ctc;

import org.junit.Assert;
import org.junit.jupiter.api.*;

/*
* Tests for the CTCMain Class
* @author  Dave Sand   Copyright (C) 2019
*/
public class CTCMainTest {

    @Test
    public void testCtor() {
        Assert.assertNotNull("CTCMain Constructor Return", new CTCMain());
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
