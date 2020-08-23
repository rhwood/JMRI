// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrit.withrottle;

import java.awt.GraphicsEnvironment;

import jmri.InstanceManager;
import jmri.util.JUnitUtil;

import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.Assume;

/**
 * Test simple functioning of UserInterface
 *
 * @author Paul Bender Copyright (C) 2016
 */
public class UserInterfaceTest {

    private UserInterface panel = null;

    @Test
    public void testCtor() {
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());
        Assert.assertNotNull("exists", panel);
    }

    @BeforeEach
    public void setUp() throws Exception {
        JUnitUtil.setUp();
        JUnitUtil.resetProfileManager();
        JUnitUtil.initRosterConfigManager();
        JUnitUtil.initInternalTurnoutManager();
        JUnitUtil.initInternalLightManager();
        JUnitUtil.initInternalSensorManager();
        JUnitUtil.initDebugThrottleManager();
        JUnitUtil.initDefaultUserMessagePreferences();
        InstanceManager.setDefault(DeviceManager.class, new FacelessServer() {
            @Override
            public void listen() {
            }
        });
        if (!GraphicsEnvironment.isHeadless()) {
            panel = new UserInterface();
        }
    }

    @AfterEach
    public void tearDown() throws Exception {
        if (!GraphicsEnvironment.isHeadless()) {
            try {
                panel.disableServer();
                JUnitUtil.waitFor(() -> {
                    return panel.isListen;
                });
                JUnitUtil.dispose(panel);
            } catch (java.lang.NullPointerException npe) {
                // not all tests fully configure the server, so an
                // NPE here is ok.
            }
        }
        JUnitUtil.clearShutDownManager();
        JUnitUtil.tearDown();
    }
}
