// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrix.loconet.sdfeditor;

import jmri.jmrix.loconet.sdf.SdfMacro;

/**
 * Editor panel for the SDL_VERSION macro from the Digitrax sound definition
 * language
 *
 * @author Bob Jacobsen Copyright (C) 2007
 */
class SdlVersionEditor extends SdfMacroEditor {

    public SdlVersionEditor(SdfMacro inst) {
        super(inst);
        // No editor needed, leave default message in place.
    }
}
