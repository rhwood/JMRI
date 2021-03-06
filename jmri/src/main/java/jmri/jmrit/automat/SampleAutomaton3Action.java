// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrit.automat;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 * Swing action to create and register a SampleAutomaton3 object
 *
 * @author Bob Jacobsen Copyright (C) 2003
 */
public class SampleAutomaton3Action extends AbstractAction {

    public SampleAutomaton3Action(String s) {
        super(s);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // create a SampleAutomaton2
        AbstractAutomaton f = new SampleAutomaton3();
        f.start();
    }
}
