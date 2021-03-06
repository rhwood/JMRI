// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.web.servlet.directory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jmri.web.servlet.ServletUtil;
import org.eclipse.jetty.server.ResourceService;
import org.eclipse.jetty.util.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service that returns a directory listing that matches the JMRI web site
 * theme.
 *
 * @author Randall Wood Copyright 2016, 2017
 */
public class DirectoryService extends ResourceService {

    private static final Logger log = LoggerFactory.getLogger(DirectoryService.class);

    @Override
    protected void notFound(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // do nothing so this request gets passed on
    }

    @Override
    protected void sendDirectory(HttpServletRequest request, HttpServletResponse response, Resource resource, String pathInContext) throws IOException {
        if (this.isDirAllowed()) {
            log.debug("Sending !! {} for {} in context {}", request.getRequestURI(), resource.getName(), pathInContext);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType(ServletUtil.UTF8_TEXT_HTML);

            try (DirectoryResource r = new DirectoryResource(request.getLocale(), resource)) {
                String dir = r.getListHTML(request.getRequestURI(),
                        request.getPathInfo().lastIndexOf('/') > 0,
                        request.getRequestURI());
                byte[] data = dir.getBytes(StandardCharsets.UTF_8);
                response.setContentLength(data.length);
                response.getOutputStream().write(data);
            }

        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
