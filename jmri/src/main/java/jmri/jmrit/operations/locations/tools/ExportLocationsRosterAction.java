// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrit.operations.locations.tools;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * Starts the export locations action
 *
 * @author Dan Boudreau Copyright (C) 2018
 */
public class ExportLocationsRosterAction extends AbstractAction {

    public ExportLocationsRosterAction() {
        super(Bundle.getMessage("TitleExportLocations"));
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        new ExportLocations().writeOperationsLocationFile();
    }
}
