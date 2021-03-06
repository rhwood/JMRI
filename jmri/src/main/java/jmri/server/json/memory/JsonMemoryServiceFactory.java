// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.server.json.memory;

import static jmri.server.json.memory.JsonMemory.MEMORIES;
import static jmri.server.json.memory.JsonMemory.MEMORY;

import com.fasterxml.jackson.databind.ObjectMapper;
import jmri.server.json.JsonConnection;
import jmri.spi.JsonServiceFactory;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Randall Wood
 */
@ServiceProvider(service = JsonServiceFactory.class)
public class JsonMemoryServiceFactory implements JsonServiceFactory<JsonMemoryHttpService, JsonMemorySocketService> {


    @Override
    public String[] getTypes(String version) {
        return new String[]{MEMORY, MEMORIES};
    }

    @Override
    public JsonMemorySocketService getSocketService(JsonConnection connection, String version) {
        return new JsonMemorySocketService(connection);
    }

    @Override
    public JsonMemoryHttpService getHttpService(ObjectMapper mapper, String version) {
        return new JsonMemoryHttpService(mapper);
    }

}
