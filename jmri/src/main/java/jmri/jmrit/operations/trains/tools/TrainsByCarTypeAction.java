// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrit.operations.trains.tools;

import java.awt.Frame;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * Swing action to create and register a TrainsByCarTypeFrame object.
 *
 * @author Daniel Boudreau Copyright (C) 2009
 */
public class TrainsByCarTypeAction extends AbstractAction {

    public TrainsByCarTypeAction() {
        super(Bundle.getMessage("TitleModifyTrains"));
    }

    TrainsByCarTypeFrame f = null;

    @Override
    public void actionPerformed(ActionEvent e) {
        // create a frame
        if (f == null || !f.isVisible()) {
            f = new TrainsByCarTypeFrame();
            f.initComponents("");
        }
        f.setExtendedState(Frame.NORMAL);
        f.setVisible(true); // this also brings the frame into focus
    }
}


