// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrit.display;

import java.awt.GraphicsEnvironment;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.jupiter.api.*;

/**
 * Test simple functioning of PositionableIcon.
 * 
 * @author Paul Bender Copyright (C) 2016
 */
public class PositionableIconTest extends PositionableTestBase {

    @Test
    public void testCtor() {
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());
        Assert.assertNotNull("PositionableIcon Constructor", p);
    }

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        if (!GraphicsEnvironment.isHeadless()) {
            editor = new EditorScaffold();
            p = new PositionableIcon(editor);
        }
    }

}
