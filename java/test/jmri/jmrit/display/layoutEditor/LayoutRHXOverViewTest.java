// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrit.display.layoutEditor;

import org.junit.jupiter.api.*;

/**
 * Test simple functioning of LayoutRHXOverView
 *
 * @author Bob Jacobsen Copyright (C) 2020
 */
public class  LayoutRHXOverViewTest extends  LayoutXOverViewTest {

    @Test
    public void testCtor() {
        new LayoutRHXOverView(null);
    }

}
