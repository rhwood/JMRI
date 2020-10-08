// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.util;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.TransferHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple TransferHandler that overwrites the text in a JTextField component.
 * Use JTextField default handler if you want insertion.
 *
 * @author Pete Cressman Copyright 2010
 */
public class DnDStringImportHandler extends TransferHandler {

    /////////////////////import
    @Override
    public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {
        //if (log.isDebugEnabled()) log.debug("DnDStringImportHandler.canImport ");
        for (int k = 0; k < transferFlavors.length; k++) {
            if (transferFlavors[k].equals(DataFlavor.stringFlavor)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean importData(JComponent comp, Transferable tr) {
        //if (log.isDebugEnabled()) log.debug("DnDStringImportHandler.importData ");
        DataFlavor[] flavors =  tr.getTransferDataFlavors();

        if ((!canImport(comp, flavors)) || (!(comp instanceof JTextField)) ) {
            return false;
        }
        try {
            String data = (String) tr.getTransferData(DataFlavor.stringFlavor);
            JTextField field = (JTextField) comp;
            field.setText(data);
            //Notify listeners drop happened
            field.firePropertyChange("DnDrop", 0, 1);
            return true;
        } catch (UnsupportedFlavorException | IOException ufe) {
            log.warn("DnDStringImportHandler.importData: {}", ufe.getMessage());
        }
        return false;
    }
    private final static Logger log = LoggerFactory.getLogger(DnDStringImportHandler.class);
}