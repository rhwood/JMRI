// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrit.operations.routes.tools;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;


/**
 * Export Routes to a CSV file
 *
 * @author Dan Boudreau Copyright (C) 2019
 * 
 */
public class ExportRoutesAction extends AbstractAction {

    public ExportRoutesAction() {
        super(Bundle.getMessage("MenuItemExportRoutes"));
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        ExportRoutes ex = new ExportRoutes();
        ex.writeOperationsRoutesFile();
    }
}
