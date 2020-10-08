// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrit.display.controlPanelEditor.shape;

import java.io.File;
import java.util.stream.Stream;

import jmri.configurexml.SchemaTestBase;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Checks of JMRI XML Schema
 *
 * @author Bob Jacobsen Copyright 2009
 * @since 2.5.5
 */
public class SchemaTest extends SchemaTestBase {

    public static Stream<Arguments> data() {
        return setTestFilesBelowThisPath("src/test/java/jmri/jmrit/display/controlPanelEditor/shape");
    }

    @ParameterizedTest
    @MethodSource("data")
    public void schemaTest(File file, boolean pass) {
        super.validate(file, pass);
    }
}