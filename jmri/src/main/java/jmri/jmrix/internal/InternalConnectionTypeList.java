// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrix.internal;

import jmri.jmrix.ConnectionTypeList;
import org.openide.util.lookup.ServiceProvider;

/**
 * Get a list of valid Virtual (Internal) Connection Types
 *
 * @author Bob Jacobsen Copyright (C) 2010
 * @author Kevin Dickerson Copyright (C) 2010
 */
@ServiceProvider(service = ConnectionTypeList.class)
public class InternalConnectionTypeList implements jmri.jmrix.ConnectionTypeList {

    public static final String NONE = "None";

    @Override
    public String[] getAvailableProtocolClasses() {
        return new String[]{
            "jmri.jmrix.internal.ConnectionConfig"
        };
    }

    @Override
    public String[] getManufacturers() {
        return new String[]{NONE};
    }

}
