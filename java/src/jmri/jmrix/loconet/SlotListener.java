// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrix.loconet;

/**
 * Interface for objects that want to be notified when a
 * {@link jmri.jmrix.loconet.LocoNetSlot} is modified.
 *
 * @author Bob Jacobsen Copyright (C) 2001, 2008
 */
public interface SlotListener extends java.util.EventListener {

    public void notifyChangedSlot(LocoNetSlot s);
}
