package jmri.profile;

import jmri.util.JUnitUtil;
import jmri.util.JmriJFrame;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.catchThrowable;

/**
 *
 * @author Paul Bender Copyright (C) 2017
 */
public class ProfileManagerDialogTest {

    @Test
    @Disabled("works locally, causes crash on Travis")
    @DisabledIfSystemProperty(named = "java.awt.headless", matches = "true")
    public void testCTor() {
        jmri.util.JmriJFrame jf = new jmri.util.JmriJFrame();
        ProfileManagerDialog t = new ProfileManagerDialog(jf,false);
        assertThat(t).isNotNull();
        JUnitUtil.dispose(t);
        JUnitUtil.dispose(jf);
    }

    @Test
    @Disabled("does not display a profile, and dot exit automatically")
    @DisabledIfSystemProperty(named = "java.awt.headless", matches = "true")
    public void testGetStartingProfile() {
        jmri.util.JmriJFrame jmriJFrame = new JmriJFrame("profile dialog test");
        Throwable thrown = catchThrowable( () -> {
            Profile profile = ProfileManagerDialog.getStartingProfile(jmriJFrame);
            assertThat(profile).isNotNull();
        });
        assertThat(thrown).isNull();
    }


    @BeforeEach
    public void setUp() {
        JUnitUtil.setUp();
        JUnitUtil.resetProfileManager();
        ProfileManager.getDefault().setActiveProfile(ProfileManager.getDefault().getActiveProfileName());
    }

    @AfterEach
    public void tearDown() {
        JUnitUtil.tearDown();
    }

    // private final static Logger log = LoggerFactory.getLogger(ProfileManagerDialogTest.class);

}
