// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrit.display.controlPanelEditor.shape;

import java.awt.GraphicsEnvironment;

import jmri.jmrit.display.EditorScaffold;

import org.junit.jupiter.api.*;
import org.junit.Assert;
import org.junit.Assume;

/**
 *
 * @author Paul Bender Copyright (C) 2017
 */
public class PositionableRoundRectTest extends PositionableRectangleTest {

    @Test
    @Override
    public void testCTor() {
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());
        Assert.assertNotNull("exists", p);
    }

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        if (!GraphicsEnvironment.isHeadless()) {
            editor = new EditorScaffold();
            p = new PositionableRoundRect(editor);
        }
    }

    // private final static Logger log = LoggerFactory.getLogger(PositionableRoundRectTest.class);

}
