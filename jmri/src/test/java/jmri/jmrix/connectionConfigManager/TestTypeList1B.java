// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrix.connectionConfigManager;

import static jmri.jmrix.ConnectionConfigManagerTest.MFG1;
import static jmri.jmrix.ConnectionConfigManagerTest.TYPE_B;

import jmri.jmrix.ConnectionTypeList;
import org.openide.util.lookup.ServiceProvider;

/**
 * Test ConnectionTypeList
 *
 * @author Randall Wood (C) 2016
 */
@ServiceProvider(service = ConnectionTypeList.class)
public class TestTypeList1B implements ConnectionTypeList {

    @Override
    public String[] getAvailableProtocolClasses() {
        return new String[]{TYPE_B};
    }

    @Override
    public String[] getManufacturers() {
        return new String[]{MFG1};
    }

}