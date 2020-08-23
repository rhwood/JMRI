// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrix.mrc;

import jmri.jmrix.ConnectionTypeList;
import org.openide.util.lookup.ServiceProvider;

/**
 * Returns a list of valid MRC Connection Types
 *
 * @author Bob Jacobsen Copyright (C) 2010
 * @author Kevin Dickerson Copyright (C) 2014
 *
 */
@ServiceProvider(service = ConnectionTypeList.class)
public class MrcConnectionTypeList implements jmri.jmrix.ConnectionTypeList {

    public static final String MRC = "MRC";

    @Override
    public String[] getAvailableProtocolClasses() {
        return new String[]{
            "jmri.jmrix.mrc.serialdriver.ConnectionConfig",
            "jmri.jmrix.mrc.simulator.ConnectionConfig",}; // NOI18N
    }

    @Override
    public String[] getManufacturers() {
        return new String[]{MRC};
    }

}
