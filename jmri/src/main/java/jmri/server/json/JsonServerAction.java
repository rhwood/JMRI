// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.server.json;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import jmri.InstanceManager;

public class JsonServerAction extends AbstractAction {

    public JsonServerAction(String s) {
        super(s);
    }

    public JsonServerAction() {
        this(Bundle.getMessage("MenuItemStartServer"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        InstanceManager.getDefault(JsonServer.class).start();
    }
}
