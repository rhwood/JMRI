// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri;

/**
 * Represents a failure to read when programming.
 *
 * @author Bob Jacobsen Copyright (C) 2001, 2008
 */
public class ProgReadException extends ProgrammerException {

    public ProgReadException(String s) {
        super(s);
    }

    public ProgReadException() {
    }

}
