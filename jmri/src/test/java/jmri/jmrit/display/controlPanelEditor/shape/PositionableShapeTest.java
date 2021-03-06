// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrit.display.controlPanelEditor.shape;

import java.awt.GraphicsEnvironment;
import java.awt.Shape;

import jmri.jmrit.display.EditorScaffold;
import jmri.jmrit.display.Positionable;
import jmri.jmrit.display.PositionableJComponentTest;

import org.junit.jupiter.api.*;
import org.junit.Assert;
import org.junit.Assume;

/**
 *
 * @author Paul Bender Copyright (C) 2017
 */
public class PositionableShapeTest extends PositionableJComponentTest {

    @Override
    @Test
    public void testCTor() {
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());
        Assert.assertNotNull("exists", p);
    }

    @Override
    @Test
    public void testGetAndSetShowToolTip() {
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());
        Assert.assertFalse("Defalt ShowToolTip", p.showToolTip());
        p.setShowToolTip(true);
        Assert.assertTrue("showToolTip after set true", p.showToolTip());
        p.setShowToolTip(false);
        Assert.assertFalse("showToolTip after set false", p.showToolTip());
    }

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        if (!GraphicsEnvironment.isHeadless()) {
            editor = new EditorScaffold();
            p = new PositionableShape(editor) {

                @Override
                protected DrawFrame makeEditFrame(boolean create) {
                    // bogus body, not used in tests
                    return null;
                }

                @Override
                protected Shape makeShape() {
                    // bogus body, not used in tests
                    return null;
                }

                @Override
                public Positionable deepClone() {
                    // bogus body, not used in tests
                    return null;
                }
            };
        }
    }

    // private final static Logger log = LoggerFactory.getLogger(PositionableShapeTest.class);
}
