// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package apps.swing;

import java.util.*;

import javax.swing.JMenuItem;
import javax.swing.UIManager;

import apps.*;

import jmri.jmrit.XmlFileLocationAction;
import jmri.util.*;

import org.openide.util.lookup.ServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import apps.util.issuereporter.swing.IssueReporterAction;

/**
 * Common utility methods for working with Java Help.
 * <p>
 * This class was created to contain common Java Help information.
 * <p>
 * It assumes that Java Help 1.1.8 is in use
 *
 * @author Bob Jacobsen Copyright 2007
 */
@ServiceProvider(service = HelpUtil.MenuProvider.class)
public class HelpMenuProvider implements HelpUtil.MenuProvider {

    public HelpMenuProvider() {
        // do nothing
    }

    @Override
    public List<JMenuItem> getHelpMenuItems() {
        List<JMenuItem> items = new ArrayList<>();

        JMenuItem item = new JMenuItem(Bundle.getMessage("MenuItemHelp"));
        HelpUtil.getGlobalHelpBroker().enableHelpOnButton(item, "index", null);
        items.add(item);

        JMenuItem license = new JMenuItem(Bundle.getMessage("MenuItemLicense"));
        items.add(license);
        license.addActionListener(new LicenseAction());

        JMenuItem directories = new JMenuItem(Bundle.getMessage("MenuItemLocations"));
        items.add(directories);
        directories.addActionListener(new XmlFileLocationAction());

        JMenuItem updates = new JMenuItem(Bundle.getMessage("MenuItemCheckUpdates"));
        items.add(updates);
        updates.addActionListener(new CheckForUpdateAction());

        JMenuItem context = new JMenuItem(Bundle.getMessage("MenuItemContext"));
        items.add(context);
        context.addActionListener(new ReportContextAction());

        JMenuItem console = new JMenuItem(Bundle.getMessage("MenuItemConsole"));
        items.add(console);
        console.addActionListener(new SystemConsoleAction());

        items.add(new JMenuItem(new IssueReporterAction()));

        items.add(null);
        JMenuItem about = new JMenuItem(Bundle.getMessage("MenuItemAbout") + " " + jmri.Application.getApplicationName());
        items.add(about);
        about.addActionListener(new AboutAction());

        return items;
    }

    // initialize logging
    private static final Logger log = LoggerFactory.getLogger(HelpMenuProvider.class);
}