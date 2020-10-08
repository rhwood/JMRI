// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.swing;

import jmri.util.JUnitUtil;

import org.junit.Assert;
import org.junit.jupiter.api.*;

/**
 *
 * @author Paul Bender Copyright (C) 2017
 */
public class RowSorterUtilTest {

    @Test
    public void testCTor() {
        RowSorterUtil t = new RowSorterUtil();
        Assert.assertNotNull("exists",t);
    }

    @BeforeEach
    public void setUp() {
        JUnitUtil.setUp();
    }

    @AfterEach
    public void tearDown() {
        JUnitUtil.tearDown();
    }

    // private final static Logger log = LoggerFactory.getLogger(RowSorterUtilTest.class);

}