// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri;

/**
 * Base for JMRI-specific exceptions. No functionality, just used to confirm
 * type-safety.
 *
 * @author Bob Jacobsen Copyright (C) 2001, 2008, 2010
 */
public class JmriException extends Exception {

    public JmriException(String s, Throwable t) {
        super(s, t);
    }

    public JmriException(String s) {
        super(s);
    }

    public JmriException(Throwable t) {
        super(t);
    }

    public JmriException() {
    }

}
