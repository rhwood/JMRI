// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrit.operations.setup;

import jmri.jmrit.operations.OperationsTestCase;
import org.junit.Assert;
import org.junit.jupiter.api.*;

/**
 *
 * @author Paul Bender Copyright (C) 2017
 */
public class EditSwitchListTextPanelTest extends OperationsTestCase {

    @Test
    public void testCTor() {
        EditSwitchListTextPanel t = new EditSwitchListTextPanel();
        Assert.assertNotNull("exists",t);
    }

    // private final static Logger log = LoggerFactory.getLogger(EditSwitchListTextPanelTest.class);

}