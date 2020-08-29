// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package apps.util;

import jmri.util.JUnitAppender;
import jmri.util.JUnitUtil;

import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for the apps.util.Log4JUtil class.
 *
 * @author Bob Jacobsen Copyright 2003, 2009, 2010, 2015
 */
public class Log4JUtilTest {

    @Test
    public void testLog4JWarnMessage() {
        log.warn("WARN message succeeds");
        JUnitAppender.assertWarnMessage("WARN message succeeds");

        log.debug("DEBUG message"); // should be suppressed see tests.lcf

        assertThat(JUnitAppender.verifyNoBacklog()).isTrue();
    }

    @Test
    public void testSendJavaUtilLogInfoMessage() {
        // test that java.util.logging is getting to Log4J
        java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(Log4JUtilTest.class.getName());
        logger.log(java.util.logging.Level.WARNING, "j.u.l WARNING message");
        JUnitAppender.assertWarnMessage("j.u.l WARNING message");

        logger.log(java.util.logging.Level.FINER, "j.u.l FINER message"); // should be suppressed see tests.lcf

        assertThat(JUnitAppender.verifyNoBacklog()).isTrue();
    }
    
    @BeforeEach
    public void setUp() {
        JUnitUtil.setUp();
    }

    @AfterEach
    public void tearDown() {
        JUnitUtil.tearDown();
    }

    private final static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Log4JUtilTest.class);

}
