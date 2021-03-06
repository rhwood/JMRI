// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrix.loconet.sdfeditor;

import jmri.jmrix.loconet.sdf.SdfMacro;

/**
 * Editor panel for the SKEME_START macro from the Digitrax sound definition
 * language.
 * <p>
 * This nests until the next SKEME_START.
 *
 * @author Bob Jacobsen Copyright (C) 2007
 */
class SkemeStartEditor extends SdfMacroEditor {

    public SkemeStartEditor(SdfMacro inst) {
        super(inst);
        // No editor needed, leave default message in place.
    }

}
