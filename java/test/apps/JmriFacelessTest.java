// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package apps;

import jmri.util.JUnitUtil;

import org.junit.jupiter.api.*;
import org.junit.Assert;

/**
 *
 * Tests for the JmriFaceless application.
 *
 * @author Paul Bender Copyright (C) 2016
 */
public class JmriFacelessTest {

    @Test
    public void testCtor() {
        String Args[] = {};
        AppsBase a = new JmriFaceless(Args) {
            // force the application to not actually start.
            // Just checking construction.
            @Override
            public void start() {
            }

            @Override
            protected void configureProfile() {
                JUnitUtil.resetInstanceManager();
            }

            @Override
            protected void installConfigurationManager() {
                JUnitUtil.initConfigureManager();
                JUnitUtil.initDefaultUserMessagePreferences();
            }

            @Override
            protected void installManagers() {
                JUnitUtil.initInternalTurnoutManager();
                JUnitUtil.initInternalLightManager();
                JUnitUtil.initInternalSensorManager();
                JUnitUtil.initRouteManager();
                JUnitUtil.initMemoryManager();
                JUnitUtil.initDebugThrottleManager();
            }

            @Override
            protected void installShutDownManager() {
                // done automatically now as part of InstanceManager default handling
            }
        };
        Assert.assertNotNull(a);
        // shutdown the application
        AppsBase.handleQuit();
    }

    @BeforeEach
    public void setUp() {
        JUnitUtil.setUp();
        JUnitUtil.resetApplication();
        JUnitUtil.resetProfileManager();
    }

    @AfterEach
    public void tearDown() {
        JUnitUtil.resetApplication();
        JUnitUtil.clearShutDownManager();
        JUnitUtil.tearDown();
    }

}
