// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrix.tmcc;

import jmri.SystemConnectionMemo;

/**
 * Abstract base for classes representing a TMCC communications port.
 *
 * @author Bob Jacobsen Copyright (C) 2001, 2006
 */
public abstract class SerialPortController extends jmri.jmrix.AbstractSerialPortController {
    // Base class. Implementations will provide InputStream and OutputStream
    // objects to SerialTrafficController classes, who in turn will deal in messages.

    protected SerialPortController(SystemConnectionMemo connectionMemo) {
        super(connectionMemo);
    }

    @Override
    public TmccSystemConnectionMemo getSystemConnectionMemo() {
        return (TmccSystemConnectionMemo) super.getSystemConnectionMemo();
    }

}
