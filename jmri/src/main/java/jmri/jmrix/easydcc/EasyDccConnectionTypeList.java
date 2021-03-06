// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrix.easydcc;

import jmri.jmrix.ConnectionTypeList;
import org.openide.util.lookup.ServiceProvider;

/**
 * Returns a list of valid lenz XpressNet Connection Types.
 *
 * @author Bob Jacobsen Copyright (C) 2010
 * @author Kevin Dickerson Copyright (C) 2010
  *
 */
@ServiceProvider(service = ConnectionTypeList.class)
public class EasyDccConnectionTypeList implements jmri.jmrix.ConnectionTypeList {

    public static final String EASYDCC = "Easy DCC";

    @Override
    public String[] getAvailableProtocolClasses() {
        return new String[]{
            "jmri.jmrix.easydcc.serialdriver.ConnectionConfig",
            "jmri.jmrix.easydcc.networkdriver.ConnectionConfig",
            "jmri.jmrix.easydcc.simulator.ConnectionConfig"
        };
    }

    @Override
    public String[] getManufacturers() {
        return new String[]{EASYDCC};
    }

}
