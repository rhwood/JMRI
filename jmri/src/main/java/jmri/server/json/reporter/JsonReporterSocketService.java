// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.server.json.reporter;

import jmri.Reporter;
import jmri.server.json.JsonConnection;
import jmri.server.json.JsonNamedBeanSocketService;

/**
 *
 * @author Randall Wood (C) 2016, 2019
 */
public class JsonReporterSocketService extends JsonNamedBeanSocketService<Reporter, JsonReporterHttpService> {

    public JsonReporterSocketService(JsonConnection connection) {
        super(connection, new JsonReporterHttpService(connection.getObjectMapper()));
    }

}
