// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.server.json.block;

import jmri.Block;
import jmri.server.json.JsonConnection;
import jmri.server.json.JsonNamedBeanSocketService;

/**
 *
 * @author mstevetodd Copyright (C) 2018 (copied from JsonMemorySocketService)
 * @author Randall Wood
 */
public class JsonBlockSocketService extends JsonNamedBeanSocketService<Block, JsonBlockHttpService> {

    public JsonBlockSocketService(JsonConnection connection) {
        super(connection, new JsonBlockHttpService(connection.getObjectMapper()));
    }
}
