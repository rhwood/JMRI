// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.util.startup;

import java.io.File;

import jmri.JmriException;
import jmri.script.JmriScriptEngineManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A PerformScriptModel object runs a script when the program is started.
 *
 * @author Bob Jacobsen Copyright 2003
 * @author Randall Wood (c) 2016
 * @see jmri.util.startup.PerformScriptModelFactory
 */
public class PerformScriptModel extends AbstractStartupModel {

    private final static Logger log = LoggerFactory.getLogger(PerformScriptModel.class);

    public String getFileName() {
        return this.getName();
    }

    public void setFileName(String n) {
        this.setName(n);
    }

    @Override
    public void performAction() throws JmriException {
        log.info("Running script {}", this.getFileName());
        JmriScriptEngineManager.getDefault().runScript(new File(this.getFileName()));
    }
}
