// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrit.operations.rollingstock.cars.tools;

import org.junit.Assert;
import org.junit.jupiter.api.*;

import jmri.jmrit.operations.OperationsTestCase;

/**
 * @author Paul Bender Copyright (C) 2017
 */
public class ShowCheckboxesCarsTableActionTest extends OperationsTestCase {

    @Test
    public void testCTor() {
        ShowCheckboxesCarsTableAction t = new ShowCheckboxesCarsTableAction(null);
        Assert.assertNotNull("exists", t);
    }
}
