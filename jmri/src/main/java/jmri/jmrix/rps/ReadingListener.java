// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrix.rps;

/**
 * Connect to a source of Readings.
 *
 * @author Bob Jacobsen Copyright (C) 2006
 */
public interface ReadingListener {

    public void notify(Reading r);

}
