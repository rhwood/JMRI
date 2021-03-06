// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.server.json.light;

import jmri.Light;
import jmri.server.json.JsonConnection;
import jmri.server.json.JsonNamedBeanSocketService;

/**
 *
 * @author Randall Wood
 */
public class JsonLightSocketService extends JsonNamedBeanSocketService<Light, JsonLightHttpService> {

    public JsonLightSocketService(JsonConnection connection) {
        super(connection, new JsonLightHttpService(connection.getObjectMapper()));
    }
}
