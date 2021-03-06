// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrit.operations.trains.configurexml;

import org.junit.Assert;
import org.junit.Test;

import jmri.jmrit.operations.OperationsTestCase;

/**
 * TrainIconXmlTest.java
 *
 * Description: tests for the TrainIconXml class
 *
 * @author Paul Bender Copyright (C) 2016
 */
public class TrainIconXmlTest extends OperationsTestCase {

    @Test
    public void testCtor() {
        Assert.assertNotNull("TrainIconXml constructor", new TrainIconXml());
    }
}
