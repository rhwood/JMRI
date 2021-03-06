// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmri.swing;

import javax.swing.ListModel;

/**
 *
 * @author Randall Wood
 */
public interface EditableListModel<E> extends ListModel<E> {

    public boolean isCellEditable(int index);

    public void setValueAt(E value, int index);
}
