// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrit.throttle;

/**
 *
 * @author glen Copyright (C) 2002
 */
public interface ControlPanelListener extends java.util.EventListener {

    public void notifySpeedChanged(int speed);

    public void notifyDirectionChanged(boolean isForward);

}
