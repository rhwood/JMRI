// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri;

/**
 * Base for exceptions indicating problems in {@link Programmer} operations.
 *
 * @author Bob Jacobsen Copyright (C) 2001
 */
public class ProgrammerException extends JmriException {

    public ProgrammerException(String s) {
        super(s);
    }

    public ProgrammerException() {
    }

}
