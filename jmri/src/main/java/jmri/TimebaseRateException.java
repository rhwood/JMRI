// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri;

/**
 * Thrown to indicate that a Timebase can't handle a particular rate setting
 * that's been requested.
 *
 * @author Bob Jacobsen Copyright (C) 2004
 */
public class TimebaseRateException extends JmriException {

    public TimebaseRateException(String s) {
        super(s);
    }

    public TimebaseRateException() {
    }

}
