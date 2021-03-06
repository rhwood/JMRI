// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrit.display.controlPanelEditor;

import jmri.NamedBean;
import jmri.SignalHead;
import jmri.SignalMast;
import jmri.NamedBean.DisplayOptions;
import jmri.jmrit.logix.Portal;

class SignalPair {
    NamedBean _signal;
    Portal _portal;

    SignalPair(NamedBean signal, Portal portal) {
        _signal = signal;
        _portal = portal;
    }
    
    String getDiscription() {
        return Bundle.getMessage("signalDescription", 
                _signal.getDisplayName(DisplayOptions.QUOTED_DISPLAYNAME),
                _portal.getName());
    }
}
