// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrit.beantable;

import java.awt.GraphicsEnvironment;
import javax.swing.JFrame;
import javax.swing.JTextField;
import jmri.InstanceManager;
import jmri.SignalGroup;
import jmri.SignalHead;
import jmri.Turnout;
import jmri.util.JUnitUtil;
import jmri.util.junit.annotations.*;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.jupiter.api.*;
import org.netbeans.jemmy.operators.*;

/**
 * Tests for the jmri.jmrit.beantable.SignalGroupTableAction class
 *
 * @author Egbert Broerse Copyright 2017
 */
public class SignalGroupTableActionTest extends AbstractTableActionBase<SignalGroup> {

    @Test
    public void testCreate() {
        Assert.assertNotNull(a);
    }

    @Override
    public String getTableFrameName() {
        return Bundle.getMessage("TitleSignalGroupTable");
    }

    @Override
    @Test
    public void testGetClassDescription() {
        Assert.assertEquals("Signal Group Table Action class description", "Signal Group Table", a.getClassDescription());
    }

    /**
     * Check the return value of includeAddButton. The table generated by this
     * action includes an Add Button.
     */
    @Override
    @Test
    public void testIncludeAddButton() {
        Assert.assertTrue("Default include add button", a.includeAddButton());
    }

    @Test
    public void testAdd() throws Exception {
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());
        new jmri.implementation.VirtualSignalMast("IF$vsm:AAR-1946:CPL($0002)", "VM1");
        // create a Turnout
        Turnout it1 = InstanceManager.turnoutManagerInstance().provideTurnout("IT1");
        // create a signal head
        SignalHead sh = new jmri.implementation.SingleTurnoutSignalHead("IH1",
                new jmri.NamedBeanHandle<>("IT1", it1),
                SignalHead.LUNAR, SignalHead.DARK); // on state + off state
        InstanceManager.getDefault(jmri.SignalHeadManager.class).register(sh);

        // open Signal Group Table
        SignalGroupTableAction _sGroupTable;
        _sGroupTable = new SignalGroupTableAction();
        Assert.assertNotNull("found SignalGroupTable frame", _sGroupTable);

        _sGroupTable.addPressed(null);
        JFrame af = JFrameOperator.waitJFrame(Bundle.getMessage("AddSignalGroup"), true, true);
        Assert.assertNotNull("found Add frame", af);

        // create a new signal group
        _sGroupTable._userName.setText("TestGroup");
        Assert.assertEquals("user name", "TestGroup", _sGroupTable._userName.getText());
        _sGroupTable._systemName.setText("IF1");
        Assert.assertEquals("system name", "IF1", _sGroupTable._systemName.getText());
        _sGroupTable.mainSignalComboBox.setSelectedItemByName("VM1");
        SignalGroup g = _sGroupTable.checkNamesOK();
        _sGroupTable.setValidSignalMastAspects();
        // add the head to the group:
        g.addSignalHead(sh);

        // NPE when bypassing the GUI to open an Edit Head pane: was fixed by registering sh in SignalHeadManager (line 63)
        // open Edit head pane
        SignalGroupSubTableAction editSignalHead = new SignalGroupSubTableAction();
        editSignalHead.editHead(g, "IH1");
        editSignalHead.cancelSubPressed(null); // close edit head pane

        _sGroupTable.cancelPressed(null); // calling updatePressed() complains about duplicate group name

        // clean up
        (new JFrameOperator(af)).requestClose();
        JUnitUtil.dispose(af);
        g.dispose();
        _sGroupTable.dispose();
        sh.dispose();
        JUnitUtil.dispose(editSignalHead.addSubFrame);
    }

    @Override
    public String getAddFrameName() {
        return "Add Signal Group";
    }

    @Test
    @Override
    public void testAddThroughDialog() {
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());
        Assume.assumeTrue(a.includeAddButton());
        a.actionPerformed(null);
        JFrame f = JFrameOperator.waitJFrame(getTableFrameName(), true, true);

        // find the "Add... " button and press it.
        jmri.util.swing.JemmyUtil.pressButton(new JFrameOperator(f), Bundle.getMessage("ButtonAdd"));
        new org.netbeans.jemmy.QueueTool().waitEmpty();
        JFrame f1 = JFrameOperator.waitJFrame(getAddFrameName(), true, true);
        JFrameOperator jf = new JFrameOperator(f1);
        //Enter 1 in the text field labeled "System Name:"
        JLabelOperator jlo = new JLabelOperator(jf, Bundle.getMessage("LabelSystemName"));
        ((JTextField) jlo.getLabelFor()).setText("1");
        //and press create
        jmri.util.swing.JemmyUtil.pressButton(jf, Bundle.getMessage("ButtonCreate"));
        JUnitUtil.dispose(f1);
        JUnitUtil.dispose(f);
    }

    @Test
    @Disabled("needs further setup")
    @ToDo("To Edit, the signal group needs a mast added to it")
    @Override
    public void testEditButton() {
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());
        Assume.assumeTrue(a.includeAddButton());
        a.actionPerformed(null);
        JFrame f = JFrameOperator.waitJFrame(getTableFrameName(), true, true);

        // find the "Add... " button and press it.
        jmri.util.swing.JemmyUtil.pressButton(new JFrameOperator(f), Bundle.getMessage("ButtonAdd"));
        JFrame f1 = JFrameOperator.waitJFrame(getAddFrameName(), true, true);
        JFrameOperator jf = new JFrameOperator(f1);
        //Enter 1 in the text field labeled "System Name:"

        JLabelOperator jlo = new JLabelOperator(jf, Bundle.getMessage("LabelSystemName"));
        ((JTextField) jlo.getLabelFor()).setText("1");
        //and press create
        jmri.util.swing.JemmyUtil.pressButton(jf, Bundle.getMessage("ButtonCreate"));

        new org.netbeans.jemmy.QueueTool().waitEmpty();
        // find the "Edit" button and press it.  This may be in the table body.
        jmri.util.swing.JemmyUtil.pressButton(new JFrameOperator(f), Bundle.getMessage("ButtonEdit"));
        JFrame f2 = JFrameOperator.waitJFrame(getAddFrameName(), true, true);
        jmri.util.swing.JemmyUtil.pressButton(new JFrameOperator(f2), Bundle.getMessage("ButtonCancel"));
        JUnitUtil.dispose(f2);
        JUnitUtil.dispose(f1);
        JUnitUtil.dispose(f);
    }

    @BeforeEach
    @Override
    public void setUp() {
        JUnitUtil.setUp();
        JUnitUtil.resetProfileManager();
        JUnitUtil.initDefaultUserMessagePreferences();
        JUnitUtil.initInternalSignalHeadManager();
        helpTarget = "package.jmri.jmrit.beantable.SignalGroupTable";
        a = new SignalGroupTableAction();
    }

    @AfterEach
    @Override
    public void tearDown() {
        a = null;
        JUnitUtil.resetWindows(false, false);
        JUnitUtil.tearDown();
    }
}