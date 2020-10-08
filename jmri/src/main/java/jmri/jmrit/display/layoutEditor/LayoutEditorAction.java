// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrit.display.layoutEditor;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import jmri.InstanceManager;
import jmri.jmrit.display.EditorManager;

/**
 * Start a LayoutEditor.
 *
 * @author Bob Jacobsen Copyright (C) 2008
 * @see jmri.jmrit.display.panelEditor.PanelEditorAction
 */
public class LayoutEditorAction extends AbstractAction {

    public LayoutEditorAction(String s) {
        super(s);
    }

    public LayoutEditorAction() {
        this("New Panel");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = "My Layout";
        for (int i = 2; i < 100; i++) {
            if (InstanceManager.getDefault(EditorManager.class).contains(name)) {
                name = "My Layout " + i;
            }
        }
        LayoutEditor panel = new LayoutEditor(name);
        panel.setLayoutName(name);
        panel.pack();
        panel.setVisible(true);
        panel.setAllEditable(true);
        panel.setCurrentPositionAndSize();
        InstanceManager.getDefault(EditorManager.class).add(panel);
        panel.newPanelDefaults();
    }
}