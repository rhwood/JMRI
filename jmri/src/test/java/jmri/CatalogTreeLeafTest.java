// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri;

import org.junit.jupiter.api.*;
import org.junit.Assert;

/**
 *
 * @author Paul Bender Copyright (C) 2017
 */
public class CatalogTreeLeafTest {

    @Test
    public void testCTor() {
        CatalogTreeLeaf t = new CatalogTreeLeaf("testleaf","testpath",1);
        Assert.assertNotNull("exists",t);
    }

    @BeforeEach
    public void setUp() {
        jmri.util.JUnitUtil.setUp();
    }

    @AfterEach
    public void tearDown() {
        jmri.util.JUnitUtil.tearDown();
    }

    // private final static Logger log = LoggerFactory.getLogger(CatalogTreeLeafTest.class);

}
