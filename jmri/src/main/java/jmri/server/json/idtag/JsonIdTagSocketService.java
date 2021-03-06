// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.server.json.idtag;

import jmri.IdTag;
import jmri.server.json.JsonConnection;
import jmri.server.json.JsonNamedBeanSocketService;

/**
 *
 * @author Randall Wood Copyright 2019
 */
public class JsonIdTagSocketService extends JsonNamedBeanSocketService<IdTag, JsonIdTagHttpService> {

    public JsonIdTagSocketService(JsonConnection connection) {
        super(connection, new JsonIdTagHttpService(connection.getObjectMapper()));
    }
}
