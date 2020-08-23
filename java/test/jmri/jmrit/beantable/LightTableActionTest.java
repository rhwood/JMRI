// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrit.beantable;

import jmri.util.gui.GuiLafPreferencesManager;

import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.JTextField;

import jmri.*;
import jmri.util.JUnitUtil;
import jmri.util.swing.JemmyUtil;

import org.junit.jupiter.api.*;
import org.junit.Assert;
import org.junit.Assume;
import org.netbeans.jemmy.QueueTool;
import org.netbeans.jemmy.operators.JComboBoxOperator;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JLabelOperator;
import org.netbeans.jemmy.operators.JTableOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;
import org.netbeans.jemmy.util.NameComponentChooser;

/**
 * Tests for the jmri.jmrit.beantable.LightTableAction class.
 *
 * @author Paul Bender Copyright (C) 2017
 */
public class LightTableActionTest extends AbstractTableActionBase<Light> {

    private LightManager lightManager;
    private SensorManager sensorManager;
    private TurnoutManager turnoutManager;

    @Test
    public void testCTor() {
        Assert.assertNotNull("exists", a);
    }

    @Override
    public String getTableFrameName() {
        return Bundle.getMessage("TitleLightTable");
    }

    @Override
    @Test
    public void testGetClassDescription() {
        Assert.assertEquals("Light Table Action class description", "Light Table", a.getClassDescription());
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

    /**
     * Check graphic state presentation.
     *
     * @since 4.7.4
     */
    @Test
    public void testAddAndInvoke() {
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());

        a.actionPerformed(null); // show table
        // create 2 lights and see if they exist
        Light il1 = lightManager.provideLight("IL1");
        lightManager.provideLight("IL2");
        il1.setState(Light.ON);
        il1.setState(Light.OFF);

        // set graphic state column display preference to false, read by createModel()
        InstanceManager.getDefault(GuiLafPreferencesManager.class).setGraphicTableState(false);

        LightTableAction _lTable;
        _lTable = new LightTableAction();
        Assert.assertNotNull("found LightTable frame", _lTable);

        // set to true, use icons
        InstanceManager.getDefault(GuiLafPreferencesManager.class).setGraphicTableState(true);
        LightTableAction _l1Table;
        _l1Table = new LightTableAction();
        Assert.assertNotNull("found LightTable1 frame", _l1Table);

        _l1Table.addPressed(null);
        JFrame af = JFrameOperator.waitJFrame(Bundle.getMessage("TitleAddLight"), true, true);
        Assert.assertNotNull("found Add frame", af);

        // close AddPane
        _l1Table.cancelPressed(null);
        // TODO Add more Light Add pane tests in new LightTableWindowTest? see TurnoutEtc

        // clean up
        JUnitUtil.dispose(af);
        _lTable.dispose();
        _l1Table.dispose();
    }

    @Override
    public String getAddFrameName() {
        return Bundle.getMessage("TitleAddLight");
    }

    @Test
    @Override
    public void testEditButton() {
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());
        Assume.assumeTrue(a.includeAddButton());
        a.actionPerformed(null);
        JFrame f = JFrameOperator.waitJFrame(getTableFrameName(), true, true);

        // find the "Add... " button and press it.
        JFrameOperator jfo = new JFrameOperator(f);
        JemmyUtil.pressButton(jfo, Bundle.getMessage("ButtonAdd"));
        new QueueTool().waitEmpty();
        JFrame f1 = JFrameOperator.waitJFrame(getAddFrameName(), true, true);
        //Enter 1 in the text field labeled "Hardware address:"
        JTextField hwAddressField = JTextFieldOperator.findJTextField(f1, new NameComponentChooser("hwAddressTextField"));
        Assert.assertNotNull("hwAddressTextField", hwAddressField);

        // set to "1"
        new JTextFieldOperator(hwAddressField).setText("1");
        //and press create
        JemmyUtil.pressButton(new JFrameOperator(f1), Bundle.getMessage("ButtonCreate"));
        new QueueTool().waitEmpty();
        JTableOperator tbl = new JTableOperator(jfo, 0);
        // find the "Edit" button and press it.  This is in the table body.
        tbl.clickOnCell(0, tbl.getColumnCount() - 1); // edit column is last in light table.
        new QueueTool().waitEmpty();
        JFrame f2 = JFrameOperator.waitJFrame(getEditFrameName(), true, true);
        JemmyUtil.pressButton(new JFrameOperator(f2), Bundle.getMessage("ButtonCancel"));
        JUnitUtil.dispose(f2);
        JUnitUtil.dispose(f1);
    }

    @Override
    public String getEditFrameName() {
        return Bundle.getMessage("TitleEditLight");
    }

    @Test
    public void testAddEditSensorLightControl() throws jmri.JmriException {

        Assume.assumeFalse(GraphicsEnvironment.isHeadless());

        // create 2 Sensors to pick from
        Sensor sOne = sensorManager.provideSensor("S1");
        Sensor sTwo = sensorManager.provideSensor("S2");

        a.actionPerformed(null); // show table

        JFrame f = JFrameOperator.waitJFrame(getTableFrameName(), true, true);

        // find the "Add... " button and press it.
        JFrameOperator jfo = new JFrameOperator(f);
        JemmyUtil.pressButton(jfo, Bundle.getMessage("ButtonAdd"));
        new QueueTool().waitEmpty();
        JFrame f1 = JFrameOperator.waitJFrame(getAddFrameName(), true, true);
        //Enter 1234 in the text field labeled "Hardware address:"
        JTextField hwAddressField = JTextFieldOperator.findJTextField(f1, new NameComponentChooser("hwAddressTextField"));
        // set to "1234"
        new JTextFieldOperator(hwAddressField).setText("1234");
        JFrameOperator jfob = new JFrameOperator(f1);
        JemmyUtil.pressButton(jfob, Bundle.getMessage("LightAddControlButton"));
        JFrame fControl = JFrameOperator.waitJFrame(Bundle.getMessage("TitleAddLightControl"), true, true);
        JFrameOperator jfoc = new JFrameOperator(fControl);

        Assert.assertEquals("No Sensor selected ", Bundle.getMessage("LightNoControl"),
                new JComboBoxOperator(jfoc, 0).getSelectedItem());

        // create a new LightControl by Sensor S2
        new JComboBoxOperator(jfoc, 0).selectItem(Bundle.getMessage("LightSensorControl"));

        new JComboBoxOperator(jfoc, 1).setSelectedItem(sTwo); // select Sensor S2
        new JComboBoxOperator(jfoc, 2).selectItem(Bundle.getMessage("SensorStateActive"));
        JemmyUtil.pressButton(jfoc, Bundle.getMessage("ButtonCreate"));
        // light control frame closes

        JemmyUtil.pressButton(jfob, Bundle.getMessage("ButtonCreate"));
        // new / edit light frame stays open

        // confirm light has been created with correct control
        Light created = lightManager.getLight("IL1234");
        Assert.assertNotNull(created);
        sOne.setState(Sensor.ON);
        Assert.assertEquals("OFF state, light does not react to sOne", Light.OFF, created.getState());
        sTwo.setState(Sensor.ON);
        JUnitUtil.waitFor(() -> {
            return created.getState() == Light.ON;
        }, "Light should go ON");

        Assert.assertEquals("Correct Light Control Type", "ON when ISS2 is Active.",
                LightTableAction.getDescriptionText(created.getLightControlList().get(0),
                        created.getLightControlList().get(0).getControlType()));

        // now we find the Light in the table and edit it
        JTableOperator tbl = new JTableOperator(jfo, 0);
        // find the "Edit" button and press it.  This is in the table body.
        tbl.clickOnCell(0, tbl.getColumnCount() - 1); // edit column is last in light table.

        new QueueTool().waitEmpty();
        JFrame f2 = JFrameOperator.waitJFrame(getEditFrameName(), true, true);
        JFrameOperator jfoce = new JFrameOperator(f2);
        JTableOperator controltbl = new JTableOperator(jfoce, 0);
        // find the "Edit" button and press it.  This is in the table body.
        controltbl.clickOnCell(0, 2); // click edit button in column 2.

        // find edit control window
        JFrame f3 = JFrameOperator.waitJFrame(Bundle.getMessage("TitleEditLightControl"), true, true);
        JFrameOperator jfof3 = new JFrameOperator(f3);

        Assert.assertEquals("Sensor Control type selected ", Bundle.getMessage("LightSensorControl"),
                new JComboBoxOperator(jfof3, 0).getSelectedItem());

        Assert.assertEquals("Sensor selected ", sTwo,
                new JComboBoxOperator(jfof3, 1).getSelectedItem());

        Assert.assertEquals("Sensor active ", Bundle.getMessage("SensorStateActive"),
                new JComboBoxOperator(jfof3, 2).getSelectedItem());

        new JComboBoxOperator(jfof3, 1).setSelectedItem(sOne); // select Sensor S1
        JemmyUtil.pressButton(jfof3, Bundle.getMessage("ButtonUpdate"));
        // light control edit frame closes

        sOne.setState(Sensor.OFF);
        Assert.assertEquals("Light still ON", Light.ON, created.getState());
        JemmyUtil.pressButton(jfob, Bundle.getMessage("ButtonUpdate"));
        // light edit frame closes

        // light should now be updaed to S1
        Assert.assertEquals("Correct Light Control Type", "ON when ISS1 is Active.",
                LightTableAction.getDescriptionText(created.getLightControlList().get(0),
                        created.getLightControlList().get(0).getControlType()));
        JUnitUtil.waitFor(() -> {
            return created.getState() == Light.OFF;
        }, "Light should go OFF to match sOne");
        sOne.setState(Sensor.ON);
        JUnitUtil.waitFor(() -> {
            return created.getState() == Light.ON;
        }, "Light should go ON to match sOne");
        Assert.assertEquals("1 Control", 1, created.getLightControlList().size());

        JUnitUtil.dispose(f1);
    }

    @Test
    public void testAddEditFastClockFollowerLightControl() throws jmri.JmriException {
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());

        a.actionPerformed(null); // show table

        JFrame f = JFrameOperator.waitJFrame(getTableFrameName(), true, true);

        // find the "Add... " button and press it.
        JFrameOperator jfo = new JFrameOperator(f);
        JemmyUtil.pressButton(jfo, Bundle.getMessage("ButtonAdd"));
        new QueueTool().waitEmpty();
        JFrame f1 = JFrameOperator.waitJFrame(getAddFrameName(), true, true);
        //Enter 4321 in the text field labeled "Hardware address:"
        JTextField hwAddressField = JTextFieldOperator.findJTextField(f1, new NameComponentChooser("hwAddressTextField"));
        // set to "4321"
        new JTextFieldOperator(hwAddressField).setText("4321");
        JFrameOperator jfob = new JFrameOperator(f1);
        JemmyUtil.pressButton(jfob, Bundle.getMessage("LightAddControlButton"));
        JFrame fControl = JFrameOperator.waitJFrame(Bundle.getMessage("TitleAddLightControl"), true, true);
        JFrameOperator jfoc = new JFrameOperator(fControl);

        // create a new Light Controlled by Sensor S2
        new JComboBoxOperator(jfoc, 0).setSelectedItem(Bundle.getMessage("LightFastClockControl"));

        Assert.assertEquals("default Hour ON Time ", "00", new JTextFieldOperator(jfoc, 0).getText());
        Assert.assertEquals("default Minute ON Time ", "00", new JTextFieldOperator(jfoc, 1).getText());
        Assert.assertEquals("default Hour OFF Time ", "00", new JTextFieldOperator(jfoc, 2).getText());
        Assert.assertEquals("default Minute OFF Time ", "00", new JTextFieldOperator(jfoc, 3).getText());

        new JTextFieldOperator(jfoc, 0).setText("01");
        new JTextFieldOperator(jfoc, 1).setText("02");
        new JTextFieldOperator(jfoc, 2).setText("03");
        new JTextFieldOperator(jfoc, 3).setText("04");

        JemmyUtil.pressButton(jfoc, Bundle.getMessage("ButtonCreate"));
        // light control frame closes

        JemmyUtil.pressButton(jfob, Bundle.getMessage("ButtonCreate"));
        // new light frame closes

        Light created = lightManager.provideLight("IL4321");
        Assert.assertEquals("1 Control", 1, created.getLightControlList().size());
        Assert.assertEquals("Correct Light Control Type and Times", "ON at 01:02, OFF at 03:04.",
                LightTableAction.getDescriptionText(created.getLightControlList().get(0),
                        created.getLightControlList().get(0).getControlType()));

        // now we find the Light in the table and edit it
        JTableOperator tbl = new JTableOperator(jfo, 0);
        // find the "Edit" button and press it.  This is in the table body.
        tbl.clickOnCell(0, tbl.getColumnCount() - 1); // edit column is last in light table.

        new QueueTool().waitEmpty();
        JFrame f2 = JFrameOperator.waitJFrame(getEditFrameName(), true, true);

        JFrameOperator jfoce = new JFrameOperator(f2);
        JTableOperator controltbl = new JTableOperator(jfoce, 0);
        // find the "Edit" button and press it.  This is in the table body.

        controltbl.clickOnCell(0, 2); // click edit button in column 2.

        // find edit control window
        JFrame f3 = JFrameOperator.waitJFrame(Bundle.getMessage("TitleEditLightControl"), true, true);
        JFrameOperator jfof3 = new JFrameOperator(f3);
        // edit window should have the FastClock Follower control selected
        Assert.assertEquals("Sensor Control type selected ", Bundle.getMessage("LightFastClockControl"),
                new JComboBoxOperator(jfof3, 0).getSelectedItem());

        Assert.assertEquals("Correct Hour ON Time ", "01", new JTextFieldOperator(jfof3, 0).getText());
        Assert.assertEquals("Correct Minute ON Time ", "02", new JTextFieldOperator(jfof3, 1).getText());
        Assert.assertEquals("Correct Hour OFF Time ", "03", new JTextFieldOperator(jfof3, 2).getText());
        Assert.assertEquals("Correct Minute OFF Time ", "04", new JTextFieldOperator(jfof3, 3).getText());

        new JTextFieldOperator(jfof3, 0).setText("21");
        new JTextFieldOperator(jfof3, 1).setText("22");
        new JTextFieldOperator(jfof3, 2).setText("23");
        new JTextFieldOperator(jfof3, 3).setText("24");

        JemmyUtil.pressButton(jfof3, Bundle.getMessage("ButtonUpdate"));
        // light control edit frame closes

        JemmyUtil.pressButton(jfob, Bundle.getMessage("ButtonUpdate"));
        // light edit frame closes

        Assert.assertEquals("Correct Light Control Type and Times", "ON at 21:22, OFF at 23:24.",
                LightTableAction.getDescriptionText(created.getLightControlList().get(0),
                        created.getLightControlList().get(0).getControlType()));

        // now we edit the control then cancel the edit
        tbl.clickOnCell(0, tbl.getColumnCount() - 1); // edit column is last in light table.
        new QueueTool().waitEmpty();
        JFrame f4 = JFrameOperator.waitJFrame(getEditFrameName(), true, true);

        JFrameOperator jfocef4 = new JFrameOperator(f4);
        new JTableOperator(jfocef4, 0).clickOnCell(0, 2); // click edit button in column 2.
        // find the "Edit" Control button and press it.  This is in the table body.

        // find edit control window
        JFrame f5 = JFrameOperator.waitJFrame(Bundle.getMessage("TitleEditLightControl"), true, true);
        JFrameOperator jfof5 = new JFrameOperator(f5);
        // edit window should have the FastClock Follower control selected

        Assert.assertEquals("Correct Hour ON Time ", "21", new JTextFieldOperator(jfof5, 0).getText());
        Assert.assertEquals("Correct Minute ON Time ", "22", new JTextFieldOperator(jfof5, 1).getText());
        Assert.assertEquals("Correct Hour OFF Time ", "23", new JTextFieldOperator(jfof5, 2).getText());
        Assert.assertEquals("Correct Minute OFF Time ", "24", new JTextFieldOperator(jfof5, 3).getText());

        new JTextFieldOperator(jfof5, 0).setText("01");
        new JTextFieldOperator(jfof5, 1).setText("02");
        new JTextFieldOperator(jfof5, 2).setText("01");
        new JTextFieldOperator(jfof5, 3).setText("02");
        
        JemmyUtil.pressButton(jfof5, Bundle.getMessage("ButtonUpdate"));
        // light control edit frame does not close as the on and off times are the same
        
        checkEditLightFeedback( Bundle.getMessage("LightWarn11"), jfocef4);
        
        new JTextFieldOperator(jfof5, 2).setText("03");
        new JTextFieldOperator(jfof5, 3).setText("04");
        
        JemmyUtil.pressButton(jfof5, Bundle.getMessage("ButtonUpdate"));
        // light control edit frame closes
        
        // now attempt to create a new Light Control with the same time
        
        JemmyUtil.pressButton(jfocef4, Bundle.getMessage("LightAddControlButton"));
        fControl = JFrameOperator.waitJFrame(Bundle.getMessage("TitleAddLightControl"), true, true);
        jfoc = new JFrameOperator(fControl);
        
        new JComboBoxOperator(jfoc, 0).setSelectedItem(Bundle.getMessage("LightFastClockControl"));
        
        new JTextFieldOperator(jfoc, 0).setText("03");
        new JTextFieldOperator(jfoc, 1).setText("04");
        new JTextFieldOperator(jfoc, 2).setText("01");
        new JTextFieldOperator(jfoc, 3).setText("02");
        
        JemmyUtil.pressButton(jfoc, Bundle.getMessage("ButtonCreate"));
        // light control edit frame does not close as the on and off times are the same
        
        checkEditLightFeedback( Bundle.getMessage("LightWarn12"), jfocef4);
        
        new JTextFieldOperator(jfoc, 0).setText("05");
        new JTextFieldOperator(jfoc, 1).setText("06");
        new JTextFieldOperator(jfoc, 2).setText("07");
        new JTextFieldOperator(jfoc, 3).setText("08");
        
        JemmyUtil.pressButton(jfoc, Bundle.getMessage("ButtonCreate"));
        
        checkEditLightFeedback( Bundle.getMessage("LightUpdateInst"), jfocef4);

        // now we click cancel on the edit light and ensure changes from the edited control are not passed
        JemmyUtil.pressButton(new JFrameOperator(f4), Bundle.getMessage("ButtonCancel"));

        Assert.assertEquals("Unchanged Light Control Type and Times", "ON at 21:22, OFF at 23:24.",
                LightTableAction.getDescriptionText(created.getLightControlList().get(0),
                        created.getLightControlList().get(0).getControlType()));
    }
    
    @Test
    public void testAddEditTurnoutLightControl() throws jmri.JmriException {
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());

        Turnout tOne = turnoutManager.provideTurnout("T1");
        Turnout tTwo = turnoutManager.provideTurnout("T2");

        Assert.assertNotNull("exists", tOne);
        Assert.assertNotNull("exists", tTwo);

        a.actionPerformed(null); // show table

        JFrame f = JFrameOperator.waitJFrame(getTableFrameName(), true, true);

        // find the "Add... " button and press it.
        JFrameOperator jfo = new JFrameOperator(f);
        JemmyUtil.pressButton(jfo, Bundle.getMessage("ButtonAdd"));
        new QueueTool().waitEmpty();
        JFrame f1 = JFrameOperator.waitJFrame(getAddFrameName(), true, true);
        //Enter 333 in the text field labeled "Hardware address:"
        JTextField hwAddressField = JTextFieldOperator.findJTextField(f1, new NameComponentChooser("hwAddressTextField"));
        new JTextFieldOperator(hwAddressField).setText("333");
        JFrameOperator jfob = new JFrameOperator(f1);
        JemmyUtil.pressButton(jfob, Bundle.getMessage("LightAddControlButton"));
        JFrame fControl = JFrameOperator.waitJFrame(Bundle.getMessage("TitleAddLightControl"), true, true);
        JFrameOperator jfoc = new JFrameOperator(fControl);

        new JComboBoxOperator(jfoc, 0).selectItem(Bundle.getMessage("LightTurnoutStatusControl"));

        new JComboBoxOperator(jfoc, 1).setSelectedItem(tTwo); // select Turnout T2
        new JComboBoxOperator(jfoc, 2).selectItem(
                turnoutManager.getThrownText());

        JemmyUtil.pressButton(jfoc, Bundle.getMessage("ButtonCreate"));
        // light new control frame closes
        JemmyUtil.pressButton(jfob, Bundle.getMessage("ButtonCreate"));
        JemmyUtil.pressButton(jfob, Bundle.getMessage("ButtonClose"));

        Light created = lightManager.getLight("IL333");
        Assert.assertNotNull(created);
        Assert.assertEquals("1 Control", 1, created.getLightControlList().size());

        // light should now be updaed to S1
        Assert.assertEquals("Correct LightControl Type Turnout", "ON when ITT2 is Thrown.",
                LightTableAction.getDescriptionText(created.getLightControlList().get(0),
                        created.getLightControlList().get(0).getControlType()));

        // now we find the Light in the table and edit it
        JTableOperator tbl = new JTableOperator(jfo, 0);
        // find the "Edit" button and press it.  This is in the table body.
        tbl.clickOnCell(0, tbl.getColumnCount() - 1); // edit column is last in light table.

        new QueueTool().waitEmpty();
        JFrame f2 = JFrameOperator.waitJFrame(getEditFrameName(), true, true);
        // JemmyUtil.pressButton(new JFrameOperator(f2),Bundle.getMessage("ButtonCancel"));
        JFrameOperator jfoce = new JFrameOperator(f2);
        JTableOperator controltbl = new JTableOperator(jfoce, 0);
        // find the "Edit" button and press it.  This is in the table body.
        controltbl.clickOnCell(0, 2); // click edit button in column 2.

        // find edit control window
        JFrame f3 = JFrameOperator.waitJFrame(Bundle.getMessage("TitleEditLightControl"), true, true);
        JFrameOperator jfof3 = new JFrameOperator(f3);

        Assert.assertEquals("Turnout type selected ", Bundle.getMessage("LightTurnoutStatusControl"),
                new JComboBoxOperator(jfof3, 0).getSelectedItem());
        Assert.assertEquals("Turnout selected ", tTwo,
                new JComboBoxOperator(jfof3, 1).getSelectedItem());
        Assert.assertEquals("Turnout thrown ", turnoutManager.getThrownText(),
                new JComboBoxOperator(jfof3, 2).getSelectedItem());

        new JComboBoxOperator(jfof3, 1).setSelectedItem(tOne); // select Turnout T1
        new JComboBoxOperator(jfof3, 2).selectItem(turnoutManager.getClosedText());

        JemmyUtil.pressButton(jfof3, Bundle.getMessage("ButtonUpdate"));
        // light new control frame closes

        JemmyUtil.pressButton(jfoce, Bundle.getMessage("ButtonUpdate"));
        // light edit frame closes

        // confirm light has been created with correct control        
        Assert.assertEquals("Correct LightControl", "ON when ITT1 is Closed.",
                LightTableAction.getDescriptionText(created.getLightControlList().get(0),
                        created.getLightControlList().get(0).getControlType()));
    }

    @Test
    public void testAddEditTimedOnLightControl() throws jmri.JmriException {
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());

        // create 2 Sensors to pick from
        Sensor sOne = sensorManager.provideSensor("S1");
        Sensor sTwo = sensorManager.provideSensor("S2");

        Assert.assertNotNull("exists", sOne);
        Assert.assertNotNull("exists", sTwo);

        a.actionPerformed(null); // show table

        JFrame f = JFrameOperator.waitJFrame(getTableFrameName(), true, true);

        // find the "Add... " button and press it.
        JFrameOperator jfo = new JFrameOperator(f);
        JemmyUtil.pressButton(jfo, Bundle.getMessage("ButtonAdd"));
        new QueueTool().waitEmpty();
        JFrame f1 = JFrameOperator.waitJFrame(getAddFrameName(), true, true);
        //Enter 444 in the text field labeled "Hardware address:"
        JTextField hwAddressField = JTextFieldOperator.findJTextField(f1, new NameComponentChooser("hwAddressTextField"));
        new JTextFieldOperator(hwAddressField).setText("444");
        JFrameOperator jfob = new JFrameOperator(f1);
        JemmyUtil.pressButton(jfob, Bundle.getMessage("LightAddControlButton"));
        JFrame fControl = JFrameOperator.waitJFrame(Bundle.getMessage("TitleAddLightControl"), true, true);
        JFrameOperator jfoc = new JFrameOperator(fControl);

        // create a new Light Control
        new JComboBoxOperator(jfoc, 0).selectItem(Bundle.getMessage("LightTimedOnControl"));
        new JComboBoxOperator(jfoc, 1).setSelectedItem(sTwo); // select Sensor S2
        new JTextFieldOperator(jfoc, 0).setText("20");

        JemmyUtil.pressButton(jfoc, Bundle.getMessage("ButtonCreate"));
        // light control frame closes

        JemmyUtil.pressButton(jfob, Bundle.getMessage("ButtonCreate"));
        // new light frame closes

        Light created = lightManager.provideLight("IL444");
        Assert.assertEquals("1 Control", 1, created.getLightControlList().size());
        Assert.assertEquals("Correct Light Control Type and Settings", "ON for 20 msec. when ISS2 goes Active.",
                LightTableAction.getDescriptionText(created.getLightControlList().get(0),
                        created.getLightControlList().get(0).getControlType()));

        // now we find the Light in the table and edit it
        JTableOperator tbl = new JTableOperator(jfo, 0);
        // find the "Edit" button and press it.  This is in the table body.
        tbl.clickOnCell(0, tbl.getColumnCount() - 1); // edit column is last in light table.

        new QueueTool().waitEmpty();
        JFrame f2 = JFrameOperator.waitJFrame(getEditFrameName(), true, true);

        JFrameOperator jfoce = new JFrameOperator(f2);
        JTableOperator controltbl = new JTableOperator(jfoce, 0);
        // find the "Edit" button and press it.  This is in the table body.

        controltbl.clickOnCell(0, 2); // click edit button in column 2.

        // find edit control window
        JFrame f3 = JFrameOperator.waitJFrame(Bundle.getMessage("TitleEditLightControl"), true, true);
        JFrameOperator jfof3 = new JFrameOperator(f3);

        // edit window should have the Timed On control selected
        Assert.assertEquals("Timed On Control type selected ", Bundle.getMessage("LightTimedOnControl"),
                new JComboBoxOperator(jfof3, 0).getSelectedItem());
        Assert.assertEquals("Correct duration ", "20", new JTextFieldOperator(jfof3, 0).getText());

        new JComboBoxOperator(jfof3, 1).setSelectedItem(sOne); // select Sensor S1

        new JTextFieldOperator(jfof3, 0).clearText();
        new JTextFieldOperator(jfof3, 0).setText("777");

        JemmyUtil.pressButton(jfof3, Bundle.getMessage("ButtonUpdate"));
        // light control edit frame closes

        JemmyUtil.pressButton(jfob, Bundle.getMessage("ButtonUpdate"));
        // light edit frame closes

        // light should now be updaed to S1
        Assert.assertEquals("Correct Light Control Type", "ON for 777 msec. when ISS1 goes Active.",
                LightTableAction.getDescriptionText(created.getLightControlList().get(0),
                        created.getLightControlList().get(0).getControlType()));
    }

    @Test
    public void testAddEditTwoSensorLightControl() throws jmri.JmriException {

        Assume.assumeFalse(GraphicsEnvironment.isHeadless());

        // create 3 Sensors to pick from
        Sensor sOne = sensorManager.provideSensor("S1");
        Sensor sTwo = sensorManager.provideSensor("S2");
        Sensor sThree = sensorManager.provideSensor("S3");

        Assert.assertNotNull("exists", sOne);
        Assert.assertNotNull("exists", sTwo);
        Assert.assertNotNull("exists", sThree);

        a.actionPerformed(null); // show table

        JFrame f = JFrameOperator.waitJFrame(getTableFrameName(), true, true);

        // find the "Add... " button and press it.
        JFrameOperator jfo = new JFrameOperator(f);
        JemmyUtil.pressButton(jfo, Bundle.getMessage("ButtonAdd"));
        new QueueTool().waitEmpty();
        JFrame f1 = JFrameOperator.waitJFrame(getAddFrameName(), true, true);
        //Enter 555 in the text field labeled "Hardware address:"
        JTextField hwAddressField = JTextFieldOperator.findJTextField(f1, new NameComponentChooser("hwAddressTextField"));
        new JTextFieldOperator(hwAddressField).setText("555");
        JFrameOperator jfob = new JFrameOperator(f1);
        JemmyUtil.pressButton(jfob, Bundle.getMessage("LightAddControlButton"));
        JFrame fControl = JFrameOperator.waitJFrame(Bundle.getMessage("TitleAddLightControl"), true, true);
        JFrameOperator jfoc = new JFrameOperator(fControl);

        // create a new LightControl
        new JComboBoxOperator(jfoc, 0).selectItem(Bundle.getMessage("LightTwoSensorControl"));
        new JComboBoxOperator(jfoc, 1).setSelectedItem(sThree); // select Sensor S3
        new JComboBoxOperator(jfoc, 2).setSelectedItem(sOne); // select Sensor S1
        new JComboBoxOperator(jfoc, 3).selectItem(Bundle.getMessage("SensorStateActive"));

        JemmyUtil.pressButton(jfoc, Bundle.getMessage("ButtonCreate"));
        // light control frame closes

        JemmyUtil.pressButton(jfob, Bundle.getMessage("ButtonCreate"));
        // new / edit light frame stays open

        // so we close it
        JemmyUtil.pressButton(jfob, Bundle.getMessage("ButtonClose"));

        // confirm light has been created with correct control
        Light created = lightManager.provideLight("IL555");

        Assert.assertEquals("Correct Light Control Type", "ON when either ISS3 or ISS1 is Active.",
                LightTableAction.getDescriptionText(created.getLightControlList().get(0),
                        created.getLightControlList().get(0).getControlType()));

        // now we find the Light in the table and edit it
        JTableOperator tbl = new JTableOperator(jfo, 0);
        // find the "Edit" button and press it.  This is in the table body.
        tbl.clickOnCell(0, tbl.getColumnCount() - 1); // edit column is last in light table.

        new QueueTool().waitEmpty();
        JFrame f2 = JFrameOperator.waitJFrame(getEditFrameName(), true, true);

        JFrameOperator jfoce = new JFrameOperator(f2);
        JTableOperator controltbl = new JTableOperator(jfoce, 0);
        // find the "Edit" button and press it.  This is in the table body.
        controltbl.clickOnCell(0, 2); // click edit button in column 2.

        // find edit control window
        JFrame f3 = JFrameOperator.waitJFrame(Bundle.getMessage("TitleEditLightControl"), true, true);
        JFrameOperator jfof3 = new JFrameOperator(f3);

        // edit window should have the 2 sensor control selected
        Assert.assertEquals("2 Sensor Control type selected ", Bundle.getMessage("LightTwoSensorControl"),
                new JComboBoxOperator(jfof3, 0).getSelectedItem());
        Assert.assertEquals("Sensor slot 1 selected ", sThree,
                new JComboBoxOperator(jfof3, 1).getSelectedItem());
        Assert.assertEquals("Sensor slot 2 selected ", sOne,
                new JComboBoxOperator(jfof3, 2).getSelectedItem());

        Assert.assertEquals("Sensor active", Bundle.getMessage("SensorStateActive"),
                new JComboBoxOperator(jfof3, 3).getSelectedItem());

        new JComboBoxOperator(jfof3, 1).setSelectedItem(sTwo); // select Sensor S2
        new JComboBoxOperator(jfof3, 2).setSelectedItem(sThree); // select Sensor S3

        new JComboBoxOperator(jfof3, 3).selectItem(Bundle.getMessage("SensorStateInactive"));

        JemmyUtil.pressButton(jfof3, Bundle.getMessage("ButtonUpdate"));
        // light control edit frame closes

        JemmyUtil.pressButton(jfoce, Bundle.getMessage("ButtonUpdate"));
        // light edit frame closes

        // light should now be updaed
        Assert.assertEquals("Correct Light Control Type", "ON when either ISS2 or ISS3 is Inactive.",
                LightTableAction.getDescriptionText(created.getLightControlList().get(0),
                        created.getLightControlList().get(0).getControlType()));
    }

    // sensors with usernames
    @Test
    public void testSensorUserNamesLightControl() throws jmri.JmriException {

        Assume.assumeFalse(GraphicsEnvironment.isHeadless());

        // create 3 Sensors to pick from
        Sensor sOne = sensorManager.provideSensor("S1");
        Sensor sTwo = sensorManager.provideSensor("S2");

        sOne.setUserName("My Sensor One");
        sTwo.setUserName("My Sensor Two");

        a.actionPerformed(null); // show table

        JFrame f = JFrameOperator.waitJFrame(getTableFrameName(), true, true);

        // find the "Add... " button and press it.
        JFrameOperator jfo = new JFrameOperator(f);
        JemmyUtil.pressButton(jfo, Bundle.getMessage("ButtonAdd"));
        new QueueTool().waitEmpty();
        JFrame f1 = JFrameOperator.waitJFrame(getAddFrameName(), true, true);
        //Enter 777 in the text field labeled "Hardware address:"
        JTextField hwAddressField = JTextFieldOperator.findJTextField(f1, new NameComponentChooser("hwAddressTextField"));
        new JTextFieldOperator(hwAddressField).setText("777");
        JFrameOperator jfob = new JFrameOperator(f1);
        JemmyUtil.pressButton(jfob, Bundle.getMessage("LightAddControlButton"));
        JFrame fControl = JFrameOperator.waitJFrame(Bundle.getMessage("TitleAddLightControl"), true, true);
        JFrameOperator jfoc = new JFrameOperator(fControl);

        // create a new LightControl
        new JComboBoxOperator(jfoc, 0).selectItem(Bundle.getMessage("LightTwoSensorControl"));
        new JComboBoxOperator(jfoc, 1).setSelectedItem(sTwo); // select Sensor S2
        new JComboBoxOperator(jfoc, 2).setSelectedItem(sOne); // select Sensor S1
        new JComboBoxOperator(jfoc, 3).selectItem(Bundle.getMessage("SensorStateActive"));

        JemmyUtil.pressButton(jfoc, Bundle.getMessage("ButtonCreate"));
        // light control frame closes

        JemmyUtil.pressButton(jfob, Bundle.getMessage("ButtonCreate"));
        // new / edit light frame stays open

        // so we close it
        JemmyUtil.pressButton(jfob, Bundle.getMessage("ButtonClose"));

        // confirm light has been created with correct control
        Light created = lightManager.provideLight("IL777");

        Assert.assertEquals("Correct Light Control Type", "ON when either My Sensor Two or My Sensor One is Active.",
                LightTableAction.getDescriptionText(created.getLightControlList().get(0),
                        created.getLightControlList().get(0).getControlType()));
    }

    // test the feedback message displayed in-pane for an Edit / New Light pane WITH variable intensity
    private void checkEditLightFeedback( String toTest, JFrameOperator jfo){
        JLabelOperator lblFeedback = new JLabelOperator(jfo, 11);
        Assert.assertEquals("Message did not appear", toTest, lblFeedback.getText());
    }

    @BeforeEach
    @Override
    public void setUp() {
        jmri.util.JUnitUtil.setUp();
        jmri.util.JUnitUtil.resetInstanceManager();
        jmri.util.JUnitUtil.resetProfileManager();
        jmri.util.JUnitUtil.initInternalLightManager();
        jmri.util.JUnitUtil.initInternalSensorManager();
        jmri.util.JUnitUtil.initInternalTurnoutManager();
        jmri.util.JUnitUtil.initDefaultUserMessagePreferences();
        helpTarget = "package.jmri.jmrit.beantable.LightTable";
        a = new LightTableAction();
        lightManager = InstanceManager.getDefault(LightManager.class);
        sensorManager = InstanceManager.getDefault(SensorManager.class);
        turnoutManager = InstanceManager.getDefault(TurnoutManager.class);
    }

    @AfterEach
    @Override
    public void tearDown() {
        JFrame f = a.getFrame();
        if (f != null) {
            JUnitUtil.dispose(f);
        }
        a = null;
        lightManager = null;
        sensorManager = null;
        turnoutManager = null;
        jmri.util.JUnitUtil.tearDown();
    }

    // private final static Logger log = LoggerFactory.getLogger(LightTableActionTest.class);
}
