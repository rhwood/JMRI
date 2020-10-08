// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrit.dispatcher;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import jmri.InstanceManager;

/**
 * Swing action to create and register a DispatcherFrame
 *
 * @author Dave Duchamp Copyright (C) 2008
 */
public class DispatcherAction extends AbstractAction {

    public DispatcherAction(String s) {
        super(s);
    }

    public DispatcherAction() {
        this(Bundle.getMessage("TitleDispatcher"));
    }

    DispatcherFrame f = null;

    @Override
    public void actionPerformed(ActionEvent e) {
        // check that Transits have been defined and are available
        if (InstanceManager.getDefault(jmri.TransitManager.class).getNamedBeanSet().size() == 0) {
            // Inform the user that there are no Transits available, and don't open the window
            javax.swing.JOptionPane.showMessageDialog(null, Bundle.getMessage("NoTransitsMessage"));
            return;
        }
        // create a Dispatcher window or activate the existing one
        if (f == null) {
            f = InstanceManager.getDefault(DispatcherFrame.class);
            jmri.util.ThreadingUtil.newThread(new Runnable() {
                @Override
                public void run() {
                    f.loadAtStartup();
                }
            }).start();
        }
        f.setVisible(true);
    }

}