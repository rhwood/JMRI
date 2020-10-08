// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrix.can.cbus.swing;

import jmri.jmrix.can.CanSystemConnectionMemo;
import jmri.jmrix.can.ConfigurationManager;

/**
 * Provide access to Swing components for the Cbus subsystem.
 *
 * @author Bob Jacobsen Copyright (C) 2010
 * 
 * @since 2.99.2
 */
public class CbusComponentFactory extends jmri.jmrix.swing.ComponentFactory {

    public CbusComponentFactory(CanSystemConnectionMemo memo) {
        this.memo = memo;
    }

    CanSystemConnectionMemo memo;

    /**
     * Provide a menu with all items attached to this system connection
     */
    @Override
    public javax.swing.JMenu getMenu() {
        if (memo.getDisabled()) {
            return null;
        }
        if (memo.getProtocol().equals(ConfigurationManager.SPROGCBUS)) {
            return new SprogCbusMenu(memo);
        } else {
            return new CbusMenu(memo);
        }
    }

}