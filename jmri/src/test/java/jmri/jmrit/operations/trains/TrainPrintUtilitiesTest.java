// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrit.operations.trains;

import jmri.jmrit.operations.OperationsTestCase;
import org.junit.Assert;
import org.junit.jupiter.api.*;

/**
 *
 * @author Paul Bender Copyright (C) 2017
 */
public class TrainPrintUtilitiesTest extends OperationsTestCase {

    @Test
    public void testCTor() {
        TrainPrintUtilities t = new TrainPrintUtilities();
        Assert.assertNotNull("exists",t);
    }

    // private final static Logger log = LoggerFactory.getLogger(TrainPrintUtilitiesTest.class);

}
