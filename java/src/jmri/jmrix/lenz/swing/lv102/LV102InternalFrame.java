// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrix.lenz.swing.lv102;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import jmri.AddressedProgrammer;
import jmri.ProgListener;
import jmri.Programmer;
import jmri.ProgrammerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Internal Frame displaying the LV102 configuration utility.
 * <p>
 * This is a configuration utility for the LV102. It allows the user to set the
 * Track Voltage and E-line status.
 * <p>
 * Note that ctor starts a listener thread; if you subclass this class, be sure
 * that initialization is in the right order.
 *
 * @author Paul Bender Copyright (C) 2005
 */
public class LV102InternalFrame extends javax.swing.JInternalFrame {

    private static final String LV_102_STATUS_CHANGED = "LV102StatusChanged";
    private static final String DEBUG_STATUS = "Current Status: {}";
    private static final String LV_102_STATUS_WRITE_RAIL_COM_MODE = "LV102StatusWriteRailComMode";
    private static final String LV_102_STATUS_PROG_MODE = "LV102StatusProgMode";
    private static final String LV_102_STATUS_WRITE_VOLT = "LV102StatusWriteVolt";
    private ProgReplyListener progListener;

    static final int WAIT_VALUE = 1000; // number of ms to wait after a
    // programming operation.  This 
    // should not be more than 15.

    @SuppressFBWarnings(value = "SC_START_IN_CTOR",
            justification = "with existing code structure, we do not expect this to ever be subclassed.")

    public LV102InternalFrame() {

        // Set up the programmer listener
        progListener = new ProgReplyListener();

        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        setTitle(Bundle.getMessage("LV102Power"));

        JPanel pane0 = new JPanel();
        pane0.setLayout(new FlowLayout());
        JLabel voltLabel = new JLabel(Bundle.getMessage("LV102Track"));
        pane0.add(voltLabel);
        voltLabel.setLabelFor(voltBox);
        pane0.add(voltBox);
        pane0.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        getContentPane().add(pane0);

        JPanel pane1 = new JPanel();
        JLabel eLineLabel = new JLabel(Bundle.getMessage("LV102ELine"));
        pane1.add(eLineLabel);
        eLineLabel.setLabelFor(eLineBox);
        pane1.add(eLineBox);
        pane1.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        getContentPane().add(pane1);

        JPanel pane2 = new JPanel();
        pane2.add(new JLabel(Bundle.getMessage("LV102RailCom")));
        pane2.add(railComBox);
        pane2.add(new JLabel(Bundle.getMessage("LV102RailComMode")));
        pane2.add(railComModeBox);
        pane2.add(new JLabel(Bundle.getMessage("LV102RailComTiming")));
        pane2.add(railComTimingBox);
        pane2.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        getContentPane().add(pane2);

        JPanel pane3 = new JPanel();

        // Set the write button tool tip
        writeSettingsButton.setToolTipText(Bundle.getMessage("LV102WriteSettingsButtonToolTip"));

        pane3.add(writeSettingsButton);

        // Set the reset to Defaults button tool tip
        defaultButton.setToolTipText(Bundle.getMessage("ResetDefaultsToolTip"));

        // Set the reset button tool tip
        resetButton.setToolTipText(Bundle.getMessage("LV102ResetButtonToolTip"));

        pane3.add(defaultButton);
        pane3.add(resetButton);
        getContentPane().add(pane3);

        // Initialize the Combo Boxes

        /* configure the voltage selection box */
        voltBox.setVisible(true);
        voltBox.setToolTipText(Bundle.getMessage("LV102TrackTip"));
        for (String item : validVoltage) {
            voltBox.addItem(item);
        }
        voltBox.setSelectedIndex(23);

        /* Configure the E-Line Active/Inactive box */
        eLineBox.setVisible(true);
        eLineBox.setToolTipText(Bundle.getMessage("LV102ELineTip"));
        for (String eLineStatus : validELineStatus) {
            eLineBox.addItem(eLineStatus);
        }
        eLineBox.setSelectedIndex(3);

        /* Configure the RailCom Active/Inactive box */
        railComBox.setVisible(true);
        railComBox.setToolTipText(Bundle.getMessage("LV102RailComTip"));
        for (String railComStatus : validRailComStatus) {
            railComBox.addItem(railComStatus);
        }
        railComBox.setSelectedIndex(2);

        /* Configure the RailCom Mode selection box */
        railComModeBox.setVisible(true);
        railComModeBox.setToolTipText(Bundle.getMessage("LV102RailComModeTip"));
        for (String value : validRailComMode) {
            railComModeBox.addItem(value);
        }
        railComModeBox.setSelectedIndex(2);

        /* Configure the RailCom Timing selection box */
        railComTimingBox.setVisible(true);
        railComTimingBox.setToolTipText(Bundle.getMessage("LV102RailComTimingTip"));
        for (String s : validRailComTiming) {
            railComTimingBox.addItem(s);
        }
        railComTimingBox.setSelectedIndex(4);

        synchronized (currentStatus) {
            currentStatus.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
            currentStatus.setVisible(true);
            currentStatus.setText(" ");
            log.debug("Current Status: ");
            getContentPane().add(currentStatus);
        }

        // and prep for display
        pack();

        writeSettingsButton.addActionListener((ActionEvent a) -> {
                writeLV102Settings();
                writeSettingsButton.setSelected(false);
            });

        // install reset button handler
        resetButton.addActionListener((ActionEvent a) -> {
                resetLV102Settings();
                resetButton.setSelected(false);
            });

        // install reset to defaults button handler
        defaultButton.addActionListener((ActionEvent a) -> {
                defaultLV102Settings();
                defaultButton.setSelected(false);
            });

        // install a handler to set the status line when the selected item 
        // changes in the e-Line box
        eLineBox.addActionListener((ActionEvent a) -> {
                synchronized (currentStatus) {
                    currentStatus.setText(Bundle.getMessage(LV_102_STATUS_CHANGED));
                    log.debug(DEBUG_STATUS, Bundle.getMessage(LV_102_STATUS_CHANGED));
                }
            });

        // install a handler to set the status line when the selected item 
        // changes in the RailCom box
        railComBox.addActionListener((ActionEvent a) -> {
                synchronized (currentStatus) {
                    currentStatus.setText(Bundle.getMessage(LV_102_STATUS_CHANGED));
                    log.debug(DEBUG_STATUS, Bundle.getMessage(LV_102_STATUS_CHANGED));
                }
            });

        // install a handler to set the status line when the selected item 
        // changes in the RailComMode box
        railComModeBox.addActionListener((ActionEvent a) -> {
                synchronized (currentStatus) {
                    currentStatus.setText(Bundle.getMessage(LV_102_STATUS_CHANGED));
                    log.debug(DEBUG_STATUS, Bundle.getMessage(LV_102_STATUS_CHANGED));
                }
            });

        // install a handler to set the status line when the selected item 
        // changes in the RailComTiming box
        railComTimingBox.addActionListener((ActionEvent a) -> {
                synchronized (currentStatus) {
                    currentStatus.setText(Bundle.getMessage(LV_102_STATUS_CHANGED));
                    log.debug(DEBUG_STATUS, Bundle.getMessage(LV_102_STATUS_CHANGED));
                }
            });

        // install a handler to set the status line when the selected item 
        // changes in the volt box
        voltBox.addActionListener((ActionEvent a) -> {
                synchronized (currentStatus) {
                    currentStatus.setText(Bundle.getMessage(LV_102_STATUS_CHANGED));
                    log.debug(DEBUG_STATUS, Bundle.getMessage(LV_102_STATUS_CHANGED));
                }
            });

        // configure internal frame options
        setClosable(false);  // don't let the user close this frame
        setResizable(false);  // don't let the user resize this frame
        setIconifiable(false); // don't let the user minimize this frame
        setMaximizable(false); // don't let the user maximize this frame

        // make the internal frame visible
        this.setVisible(true);
    }

    boolean read = false;

    final JComboBox<String> voltBox = new javax.swing.JComboBox<>();
    final JComboBox<String> eLineBox = new javax.swing.JComboBox<>();
    final JComboBox<String> railComBox = new javax.swing.JComboBox<>();
    final JComboBox<String> railComModeBox = new javax.swing.JComboBox<>();
    final JComboBox<String> railComTimingBox = new javax.swing.JComboBox<>();

    final JLabel currentStatus = new JLabel(" ");

    final JToggleButton writeSettingsButton = new JToggleButton(Bundle.getMessage("LV102WriteSettingsButtonLabel"));
    final JButton defaultButton = new JButton(Bundle.getMessage("ButtonResetDefaults"));
    final JButton resetButton = new JButton(Bundle.getMessage("LV102ResetButtonLabel"));

    protected final String[] validVoltage = new String[]{"11V", "11.5V", "12V", "12.5V", "13V", "13.5V", "14V", "14.5V", "15V", "15.5V", Bundle.getMessage("Voltage16VDefault"), "16.5V", "17V", "17.5V", "18V", "18.5V", "19V", "19.5V", "20V", "20.5V", "21V", "21.5V", "22V", ""};
    protected final int[] validVoltageValues = new int[]{22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 0};

    protected final String[] validELineStatus = new String[]{Bundle.getMessage("LV102ELineActive"), Bundle.getMessage("LV102ELineInactive"), Bundle.getMessage("UseDefaultValue"), ""};
    protected final int[] validELineStatusValues = new int[]{90, 91, 99, 0};

    protected final String[] validRailComStatus = new String[]{Bundle.getMessage("LV102RailComActive"), Bundle.getMessage("LV102RailComInactive"), ""};
    protected final int[] validRailComStatusValues = new int[]{93, 92, 0};

    protected final String[] validRailComMode = new String[]{Bundle.getMessage("LV102RailCom3BitMode"), Bundle.getMessage("LV102RailCom4BitMode"), ""};
    protected final int[] validRailComModeValues = new int[]{94, 95, 0};

    protected final String[] validRailComTiming = new String[]{Bundle.getMessage("LV102RailComDefaultTiming"), Bundle.getMessage("LV102RailComNCETiming"), Bundle.getMessage("LV102RailComIncreaseTiming"), Bundle.getMessage("LV102RailComDecreaseTiming"), ""};
    protected final int[] validRailComTimingValues = new int[]{88, 89, 70, 71, 0};

    //Send Power Station settings
    void writeLV102Settings() {

        // obtain the programmer Manager
        jmri.AddressedProgrammerManager pm = jmri.InstanceManager.getNullableDefault(jmri.AddressedProgrammerManager.class);
        if(pm == null) {
           // no addressed programmer manager, cannot proceed.
           currentStatus.setText(Bundle.getMessage("LV102StatusNoPM"));
           log.error("No Programmer Manager Available, cannot configure LV102");
           return;
        }

        // Obtain an ops mode programmer instance
        AddressedProgrammer opsProg = pm.getAddressedProgrammer(false, 0);

        if(opsProg == null) {
           // no ops mode programmer programmer, cannot proceed.
           currentStatus.setText(Bundle.getMessage("LV102StatusNoPOM"));
           log.error("Failed to obtain Operations Mode Programmer, cannot configure LV102");
           return;
        }

        // write the values to the power station.
        writeVoltSetting(opsProg);
        writeELineSetting(opsProg);
        writeRailComSetting(opsProg);
        writeRailComModeSetting(opsProg);
        writeRailComTimingSetting(opsProg);

        // we're done now, so we can release the programmer.
        pm.releaseAddressedProgrammer(opsProg);
    }

    // Write the voltage setting
    void writeVoltSetting(Programmer opsProg) {
        if (!(voltBox.getSelectedItem().equals(""))
                && voltBox.getSelectedItem() != null) {

            log.debug("Selected Voltage: {}", voltBox.getSelectedItem());
            synchronized (currentStatus) {
                currentStatus.setText(Bundle.getMessage(LV_102_STATUS_PROG_MODE));
                currentStatus.doLayout();
                log.debug(DEBUG_STATUS, Bundle.getMessage(LV_102_STATUS_PROG_MODE));
                /* Pause briefly to give the user a chance to see what is 
                 happening */
                new jmri.util.WaitHandler(this,WAIT_VALUE);

                /* First, send the ops mode programming command to enter
                 programming mode */
                try {
                    opsProg.writeCV("7", 50, progListener);
                } catch (ProgrammerException e) {
                    // Don't do anything with this yet
                }

                /* Pause briefly to give the booster a chance to change 
                 into It's programming mode */
                new jmri.util.WaitHandler(this,WAIT_VALUE);

                currentStatus.setText(Bundle.getMessage(LV_102_STATUS_WRITE_VOLT));
                currentStatus.doLayout();
                log.debug(DEBUG_STATUS, Bundle.getMessage(LV_102_STATUS_WRITE_VOLT));

                /* Next, send the ops mode programming command for the voltage
                 we want */
                try {
                    opsProg.writeCV("7", validVoltageValues[voltBox.getSelectedIndex()], progListener);
                } catch (ProgrammerException e) {
                    // Don't do anything with this yet
                }

                /* Pause briefly to wait for the programmer to send back a 
                 reply */
                new jmri.util.WaitHandler(this,WAIT_VALUE);

            }  // End of synchronized(currentStatus) block for voltage setting
        } else {
            log.debug("No Voltage Selected");
        }
    }

    // Write the E-Line setting
    void writeELineSetting(Programmer opsProg) {
        if (!(eLineBox.getSelectedItem().equals(""))
                && eLineBox.getSelectedItem() != null) {

            log.debug("E-Line Setting: {}", eLineBox.getSelectedItem());
            synchronized (currentStatus) {
                currentStatus.setText(Bundle.getMessage(LV_102_STATUS_PROG_MODE));
                currentStatus.doLayout();
                log.debug(DEBUG_STATUS, Bundle.getMessage(LV_102_STATUS_PROG_MODE));

                /* Pause briefly to give the user a chance to see what is 
                 happening */
                new jmri.util.WaitHandler(this,WAIT_VALUE);

                /* First, send the ops mode programming command to enter
                 programming mode */
                try {
                    opsProg.writeCV("7", 50, progListener);
                } catch (ProgrammerException e) {
                    // Don't do anything with this yet
                }

                /* Pause briefly to give the booster a chance to change 
                 into It's programming mode */
                new jmri.util.WaitHandler(this,WAIT_VALUE);

                currentStatus.setText(Bundle.getMessage("LV102StatusWriteELine"));
                currentStatus.doLayout();
                log.debug(DEBUG_STATUS,Bundle.getMessage("LV102StatusWriteELine"));

                /* Next, send the ops mode programming command for the E line
                 Status we want */
                try {
                    opsProg.writeCV("7", validELineStatusValues[eLineBox.getSelectedIndex()], progListener);
                } catch (ProgrammerException e) {
                    // Don't do anything with this yet
                }

                /* Pause briefly to wait for the programmer to send back a 
                 reply */
                new jmri.util.WaitHandler(this,WAIT_VALUE);

            } // End of synchronized(currentStatus) block for E-line setting
        } else {
            log.debug("No E-Line value Selected");
        }

    }

    // Write the RailCom setting
    void writeRailComSetting(Programmer opsProg) {
        if (!(railComBox.getSelectedItem().equals(""))
                && railComBox.getSelectedItem() != null) {

            log.debug("RailCom Setting: {}", railComBox.getSelectedItem());
            synchronized (currentStatus) {
                currentStatus.setText(Bundle.getMessage(LV_102_STATUS_PROG_MODE));
                currentStatus.doLayout();
                log.debug(DEBUG_STATUS, Bundle.getMessage(LV_102_STATUS_PROG_MODE));

                /* Pause briefly to give the user a chance to see what is 
                 happening */
                new jmri.util.WaitHandler(this,WAIT_VALUE);

                /* First, send the ops mode programming command to enter
                 programming mode */
                try {
                    opsProg.writeCV("7", 50, progListener);
                } catch (ProgrammerException e) {
                    // Don't do anything with this yet
                }

                /* Pause briefly to give the booster a chance to change 
                 into It's programming mode */
                new jmri.util.WaitHandler(this,WAIT_VALUE);

                currentStatus.setText(Bundle.getMessage("LV102StatusWriteRailCom"));
                currentStatus.doLayout();
                log.debug(DEBUG_STATUS, Bundle.getMessage("LV102StatusWriteRailCom"));

                /* Next, send the ops mode programming command for the RailComm
                 Status we want */
                try {
                    opsProg.writeCV("7", validRailComStatusValues[railComBox.getSelectedIndex()], progListener);
                } catch (ProgrammerException e) {
                    // Don't do anything with this yet
                }

                /* Pause briefly to wait for the programmer to send back a 
                 reply */
                new jmri.util.WaitHandler(this,WAIT_VALUE);

            } // End of synchronized(currentStatus) block for RailCom Setting
        } else {
            log.debug("No RailCom value Selected");
        }
    }

    // Write the RailCom Mode setting
    void writeRailComModeSetting(Programmer opsProg) {
        if (!(railComModeBox.getSelectedItem().equals(""))
                && railComModeBox.getSelectedItem() != null) {

            log.debug("RailCom Setting: {}", railComModeBox.getSelectedItem());
            synchronized (currentStatus) {
                currentStatus.setText(Bundle.getMessage(LV_102_STATUS_PROG_MODE));
                currentStatus.doLayout();
                log.debug(DEBUG_STATUS, Bundle.getMessage(LV_102_STATUS_PROG_MODE));

                /* Pause briefly to give the user a chance to see what is 
                 happening */
                new jmri.util.WaitHandler(this,WAIT_VALUE);

                /* First, send the ops mode programming command to enter
                 programming mode */
                try {
                    opsProg.writeCV("7", 50, progListener);
                } catch (ProgrammerException e) {
                    // Don't do anything with this yet
                }

                /* Pause briefly to give the booster a chance to change 
                 into It's programming mode */
                new jmri.util.WaitHandler(this,WAIT_VALUE);

                currentStatus.setText(Bundle.getMessage(LV_102_STATUS_WRITE_RAIL_COM_MODE));
                currentStatus.doLayout();
                log.debug(DEBUG_STATUS, Bundle.getMessage(LV_102_STATUS_WRITE_RAIL_COM_MODE));

                /* Next, send the ops mode programming command for the RailCom Mode
                 Status we want */
                try {
                    opsProg.writeCV("7", validRailComModeValues[railComModeBox.getSelectedIndex()], progListener);
                } catch (ProgrammerException e) {
                    // Don't do anything with this yet
                }

                /* Pause briefly to wait for the programmer to send back a 
                 reply */
                new jmri.util.WaitHandler(this,WAIT_VALUE);

            } // End of synchronized(currentStatus) block for RailCom Mode
        } else {
            log.debug("No RailCom Mode Selected");
        }
    }

    // Write the RailCom Mode setting
    void writeRailComTimingSetting(Programmer opsProg) {
        if (!(railComTimingBox.getSelectedItem().equals(""))
                && railComTimingBox.getSelectedItem() != null) {

            log.debug("RailCom Timing Setting: {}", railComTimingBox.getSelectedItem());
            synchronized (currentStatus) {
                currentStatus.setText(Bundle.getMessage(LV_102_STATUS_PROG_MODE));
                currentStatus.doLayout();
                log.debug(DEBUG_STATUS, Bundle.getMessage(LV_102_STATUS_PROG_MODE));

                /* Pause briefly to give the user a chance to see what is 
                 happening */
                new jmri.util.WaitHandler(this,WAIT_VALUE);

                /* First, send the ops mode programming command to enter
                 programming mode */
                try {
                    opsProg.writeCV("7", 50, progListener);
                } catch (ProgrammerException e) {
                    // Don't do anything with this yet
                }

                /* Pause briefly to give the booster a chance to change 
                 into It's programming mode */
                new jmri.util.WaitHandler(this,WAIT_VALUE);

                currentStatus.setText(Bundle.getMessage(LV_102_STATUS_WRITE_RAIL_COM_MODE));
                currentStatus.doLayout();
                log.debug(DEBUG_STATUS, Bundle.getMessage(LV_102_STATUS_WRITE_RAIL_COM_MODE));

                /* Next, send the ops mode programming command for the RailCom
                 Timing we want */
                try {
                    opsProg.writeCV("7", validRailComTimingValues[railComTimingBox.getSelectedIndex()], progListener);
                } catch (ProgrammerException e) {
                    // Don't do anything with this yet
                }

                /* Pause briefly to wait for the programmer to send back a 
                 reply */
                new jmri.util.WaitHandler(this,WAIT_VALUE);

            } // End of synchronized(currentStatus) block for RailCom Mode
        } else {
            log.debug("No RailCom Timing Mode Selected");
        }
    }

    // Set to LV102 default values.  Voltage is 16, E Line is Active, 
    // Railcom is inactive, Railcom Mode is 3 bit cutout, Railcom Timing is 435 uS.
    void defaultLV102Settings() {
        voltBox.setSelectedIndex(10);
        eLineBox.setSelectedIndex(0);
        railComBox.setSelectedIndex(1);
        railComModeBox.setSelectedIndex(0);
        // also set RailCom Timing, 435uS is named Default
        railComTimingBox.setSelectedIndex(0);
        synchronized (currentStatus) {
            currentStatus.setText(Bundle.getMessage("LV102StatusInitial"));
            log.debug("Current Status: Factory Default Settings. May Not match actual configuration");
        }
    }

    // Set to initial values.
    void resetLV102Settings() {
        voltBox.setSelectedIndex(23);
        eLineBox.setSelectedIndex(3);
        railComBox.setSelectedIndex(2);
        railComModeBox.setSelectedIndex(2);
        synchronized (currentStatus) {
            currentStatus.setText(Bundle.getMessage("StatusOK"));
            log.debug("Current Status: OK");
        }
    }

    private class ProgReplyListener implements jmri.ProgListener {

        ProgReplyListener() {
            // no speciifc configuration required.
        }

        /**
         * This class is a programmer listener, so we implement the
         * programmingOpReply() function
         */
        @SuppressFBWarnings(value = "NO_NOTIFY_NOT_NOTIFYALL", justification = "There should only ever be one thread waiting for this method.")

        @Override
        public void programmingOpReply(int value, int status) {
            log.debug("Programming Operation reply received, value is {}, status is {}", value, status);
            if (status == ProgListener.ProgrammerBusy) {
                synchronized (currentStatus) {
                    currentStatus.setText(Bundle.getMessage("LV102StatusBUSY"));
                    log.debug(DEBUG_STATUS, Bundle.getMessage("LV102StatusBUSY"));
                    currentStatus.notify();
                }
            } else if (status == ProgListener.OK) {
                if (currentStatus.getText().equals(Bundle.getMessage(LV_102_STATUS_PROG_MODE))) {
                    synchronized (currentStatus) {
                        currentStatus.setText(Bundle.getMessage("LV102StatusReadyProg"));
                        log.debug(DEBUG_STATUS, Bundle.getMessage("LV102StatusReadyProg"));
                        currentStatus.notify();
                    }
                } else {
                    synchronized (currentStatus) {
                        currentStatus.setText(Bundle.getMessage("LV102StatusWritten"));
                        log.debug(DEBUG_STATUS, Bundle.getMessage("LV102StatusWritten"));
                        currentStatus.notify();
                    }
                }
            } else {
                synchronized (currentStatus) {
                    currentStatus.setText(Bundle.getMessage("LV102StatusUnknown"));
                    log.debug(DEBUG_STATUS, Bundle.getMessage("LV102StatusUnknown"));
                    currentStatus.notify();
                }
            }
        }
    }

    private static final Logger log = LoggerFactory.getLogger(LV102InternalFrame.class);

}
