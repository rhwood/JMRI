// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrit.catalog;

import jmri.util.JUnitUtil;

import org.junit.jupiter.api.*;
import org.junit.Assert;

/**
 *
 * @author Paul Bender Copyright (C) 2017
 */
public class DefaultCatalogTreeManagerTest extends jmri.managers.AbstractManagerTestBase<jmri.CatalogTreeManager, jmri.CatalogTree> {

    @Test
    public void testCTor() {
        Assert.assertNotNull("exists", l);
    }

    @BeforeEach
    public void setUp() {
        JUnitUtil.setUp();
        l = new DefaultCatalogTreeManager();
    }

    @AfterEach
    public void tearDown() {
        l.dispose();
        l = null;
        JUnitUtil.tearDown();
    }

    // private final static Logger log = LoggerFactory.getLogger(DefaultCatalogTreeManagerTest.class);

}
