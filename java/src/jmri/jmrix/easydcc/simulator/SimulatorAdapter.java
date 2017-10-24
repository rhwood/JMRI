package jmri.jmrix.easydcc.simulator;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
//import jmri.jmrix.easydcc.EasyDccCommandStation;
import jmri.jmrix.easydcc.EasyDccMessage;
import jmri.jmrix.easydcc.EasyDccPortController; // no special xSimulatorController
import jmri.jmrix.easydcc.EasyDccReply;
import jmri.jmrix.easydcc.EasyDccSystemConnectionMemo;
import jmri.jmrix.easydcc.EasyDccTrafficController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provide access to a simulated EasyDCC system.
 * <p>
 * Currently, the EasyDCC SimulatorAdapter reacts to commands sent from the user interface
 * with messages an appropriate reply message.
 * Based on jmri.jmrix.lenz.xnetsimulator.XNetSimulatorAdapter / DCCppSimulatorAdapter 2017
 * <p>
 * NOTE: Some material in this file was modified from other portions of the
 * support infrastructure.
 *
 * @author Paul Bender, Copyright (C) 2009-2010
 * @author Mark Underwood, Copyright (C) 2015
 * @author Egbert Broerse, Copyright (C) 2017
 */
public class SimulatorAdapter extends EasyDccPortController implements jmri.jmrix.SerialPortAdapter, Runnable {

    // private control members
    private boolean opened = false;
    private Thread sourceThread;

    final static int SENSOR_MSG_RATE = 10;

    private boolean outputBufferEmpty = true;
    private boolean checkBuffer = true;
    private boolean trackPowerState = false;

    // Simulator responses
    char EDC_OPS = 'O';
    char EDC_PROG = 'P';
    char EDC_ERROR = '!';

    char ACC_CMD = 'S'; // Send general command

    public SimulatorAdapter() {
        super(new EasyDccSystemConnectionMemo("E", "EasyDCC Simulator")); // pass customized user name
    }

    @Override
    public String openPort(String portName, String appName) {
        try {
            PipedOutputStream tempPipeI = new PipedOutputStream();
            log.debug("tempPipeI created {}", tempPipeI != null);
            pout = new DataOutputStream(tempPipeI);
            inpipe = new DataInputStream(new PipedInputStream(tempPipeI));
            log.debug("inpipe created {}", inpipe != null);
            PipedOutputStream tempPipeO = new PipedOutputStream();
            outpipe = new DataOutputStream(tempPipeO);
            pin = new DataInputStream(new PipedInputStream(tempPipeO));
        } catch (java.io.IOException e) {
            log.error("init (pipe): Exception: " + e.toString());
        }
        opened = true;
        return null; // indicates OK return
    }

    /**
     * Set if the output buffer is empty or full. This should only be set to
     * false by external processes.
     *
     * @param s true if output buffer is empty; false otherwise
     */
    synchronized public void setOutputBufferEmpty(boolean s) {
        outputBufferEmpty = s;
    }

    /**
     * Can the port accept additional characters? The state of CTS determines
     * this, as there seems to be no way to check the number of queued bytes and
     * buffer length. This might go false for short intervals, but it might also
     * stick off if something goes wrong.
     *
     * @return true if port can accept additional characters; false otherwise
     */
    public boolean okToSend() {
        if (checkBuffer) {
            log.debug("Buffer Empty: " + outputBufferEmpty);
            return (outputBufferEmpty);
        } else {
            log.debug("No Flow Control or Buffer Check");
            return (true);
        }
    }

    /**
     * Set up all of the other objects to operate with an EasyDccSimulator
     * connected to this port.
     */
    @Override
    public void configure() {
        // connect to the traffic controller
        log.debug("set tc for memo {}", getSystemConnectionMemo().getUserName());
        EasyDccTrafficController control = new EasyDccTrafficController(getSystemConnectionMemo());
        control.connectPort(this);
        this.getSystemConnectionMemo().setEasyDccTrafficController(control);
        // do the common manager config
        this.getSystemConnectionMemo().configureManagers();
        // start the simulator
        sourceThread = new Thread(this);
        sourceThread.setName("EasyDCC Simulator");
        sourceThread.setPriority(Thread.MIN_PRIORITY);
        sourceThread.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void connect() throws java.io.IOException {
        log.debug("connect called");
        super.connect();
    }

    // Base class methods for the EasyDccSimulatorPortController interface

    @Override
    public DataInputStream getInputStream() {
        if (!opened || pin == null) {
            log.error("getInputStream called before load(), stream not available");
        }
        log.debug("DataInputStream pin returned");
        return pin;
    }

    @Override
    public DataOutputStream getOutputStream() {
        if (!opened || pout == null) {
            log.error("getOutputStream called before load(), stream not available");
        }
        log.debug("DataOutputStream pout returned");
        return pout;
    }

    @Override
    public boolean status() {
        return opened;
    }

    /**
     * Get an array of valid baud rates. This is currently just a message saying
     * its fixed.
     *
     * @return null
     */
    @Override
    public String[] validBaudRates() {
        log.debug("validBaudRates should not have been invoked");
        return null;
    }

    @Override
    public String getCurrentBaudRate() {
        return "";
    }

    @Override
    public void run() { // start a new thread
        // This thread has one task. It repeatedly reads from the input pipe
        // and writes an appropriate response to the output pipe. This is the heart
        // of the EasyDCC command station simulation.
        log.info("EasyDCC Simulator Started");
        while (true) {
            try {
                synchronized (this) {
                    wait(50);
                }
            } catch (InterruptedException e) {
                log.debug("interrupted, ending");
                return;
            }
            EasyDccMessage m = readMessage();
            EasyDccReply r;
            if (log.isDebugEnabled()) {
                StringBuffer buf = new StringBuffer();
                buf.append("EasyDCC Simulator Thread received message: ");
                if (m != null) {
                    for (int i = 0; i < m.getNumDataElements(); i++) {
                        buf.append(Integer.toHexString(0xFF & m.getElement(i)) + " ");
                    }
                } else {
                    buf.append("null message buffer");
                }
//                log.debug(buf.toString());
            }
            if (m != null) {
                r = generateReply(m);
                writeReply(r);
                if (log.isDebugEnabled() && r != null) {
                    StringBuffer buf = new StringBuffer();
                    buf.append("EasyDCC Simulator Thread sent reply: ");
                    for (int i = 0; i < r.getNumDataElements(); i++) {
                        buf.append(Integer.toHexString(0xFF & r.getElement(i)) + " ");
                    }
                    log.debug(buf.toString());
                }
            }
        }
    }

    /**
     * Read one incoming message from the buffer
     * and set outputBufferEmpty to true.
     */
    private EasyDccMessage readMessage() {
        EasyDccMessage msg = null;
//        log.debug("Simulator reading message");
        try {
            if (inpipe != null && inpipe.available() > 0) {
                msg = loadChars();
            }
        } catch (java.io.IOException e) {
            // should do something meaningful here.
        }
        setOutputBufferEmpty(true);
        return (msg);
    }

    /**
     * This is the heart of the simulation. It translates an
     * incoming EasyDccMessage into an outgoing EasyDccReply.
     *
     * As yet, not all messages receive a meaningful reply. TODO: Throttle, Program
     */
    @SuppressWarnings("fallthrough")
    private EasyDccReply generateReply(EasyDccMessage msg) {
        log.debug("Generate Reply to message type {} (string = {})", msg.toString().charAt(0), msg.toString());

        String s, r;
        EasyDccReply reply = new EasyDccReply();
        int i = 0;
        char command = msg.toString().charAt(0);
        log.debug("Message type = " + command);
        switch (command) {

            case 'S':
                reply.setElement(i++, 0x4f); // capital O for Operation
                break;

            case 'M':
                reply.setElement(i++, 0x50); // capital P for Programming
                break;

            case 'R':
                reply.setElement(i++, 0x50); // capital P for Programming
                break;

            case 'A': // accessory command
                accessoryCommand(msg, reply);
                break;

            case 'E':
                log.debug("TRACK_POWER_ON detected");
                trackPowerState = true;
                reply.setElement(i++, 0x4f); // capital O for Operation
                break;

            case 'K':
                log.debug("TRACK_POWER_OFF detected");
                trackPowerState = false;
                reply.setElement(i++, 0x4f); // capital O for Operation
                break;

            case 'V':
                log.debug("Read_CS_Version detected");
                String replyString = "V999 01 01 1999";
                reply = new EasyDccReply(replyString); // fake version number reply
                i = replyString.length();
//                reply.setElement(i++, 0x4f); // capital O for Operation
                break;

//            case DCCppConstants.THROTTLE_CMD:
//                log.debug("THROTTLE_CMD detected");
//                s = msg.toString();
//                try {
//                    p = Pattern.compile(DCCppConstants.THROTTLE_CMD_REGEX);
//                    msg = p.matcher(s);
//                    if (!msg.matches()) {
//                        log.error("Malformed Throttle Command: {}", s);
//                        return (null);
//                    }
//                    r = "T " + msg.group(1) + " " + msg.group(3) + " " + msg.group(4);
//                    reply = new EasyDccReply(r);
//                    log.debug("Reply generated = {}", reply.toString());
//                } catch (PatternSyntaxException e) {
//                    log.error("Malformed pattern syntax! ");
//                    return (null);
//                } catch (IllegalStateException e) {
//                    log.error("Group called before match operation executed string= " + s);
//                    return (null);
//                } catch (IndexOutOfBoundsException e) {
//                    log.error("Index out of bounds string= " + s);
//                    return (null);
//                }
//                break;


//            case DCCppConstants.PROG_WRITE_CV_BYTE:
//                log.debug("PROG_WRITE_CV_BYTE detected");
//                s = msg.toString();
//                try {
//                    p = Pattern.compile(DCCppConstants.PROG_WRITE_BYTE_REGEX);
//                    msg = p.matcher(s);
//                    if (!msg.matches()) {
//                        log.error("Malformed ProgWriteCVByte Command: {}", s);
//                        return (null);
//                    }
//                    // CMD: <W CV Value CALLBACKNUM CALLBACKSUB>
//                    // Response: <r CALLBACKNUM|CALLBACKSUB|CV Value>
//                    r = "r " + msg.group(3) + "|" + msg.group(4) + "|" + msg.group(1) +
//                            " " + msg.group(2);
//                    CVs[Integer.parseInt(msg.group(1))] = Integer.parseInt(msg.group(2));
//                    reply = new EasyDccReply(r);
//                    log.debug("Reply generated = {}", reply.toString());
//                } catch (PatternSyntaxException e) {
//                    log.error("Malformed pattern syntax! ");
//                    return (null);
//                } catch (IllegalStateException e) {
//                    log.error("Group called before match operation executed string= " + s);
//                    return (null);
//                } catch (IndexOutOfBoundsException e) {
//                    log.error("Index out of bounds string= " + s);
//                    return (null);
//                }
//                break;

//            case DCCppConstants.PROG_WRITE_CV_BIT:
//                log.debug("PROG_WRITE_CV_BIT detected");
//                s = msg.toString();
//                try {
//                    p = Pattern.compile(DCCppConstants.PROG_WRITE_BIT_REGEX);
//                    msg = p.matcher(s);
//                    if (!msg.matches()) {
//                        log.error("Malformed ProgWriteCVBit Command: {}", s);
//                        return (null);
//                    }
//                    // CMD: <B CV BIT Value CALLBACKNUM CALLBACKSUB>
//                    // Response: <r CALLBACKNUM|CALLBACKSUB|CV BIT Value>
//                    r = "r " + msg.group(4) + "|" + msg.group(5) + "|" + msg.group(1) + " "
//                            + msg.group(2) + msg.group(3);
//                    int idx = Integer.parseInt(msg.group(1));
//                    int bit = Integer.parseInt(msg.group(2));
//                    int v = Integer.parseInt(msg.group(3));
//                    if (v == 1) {
//                        CVs[idx] = CVs[idx] | (0x0001 << bit);
//                    } else {
//                        CVs[idx] = CVs[idx] & ~(0x0001 << bit);
//                    }
//                    reply = new EasyDccReply(r);
//                    log.debug("Reply generated = {}", reply.toString());
//                } catch (PatternSyntaxException e) {
//                    log.error("Malformed pattern syntax! ");
//                    return (null);
//                } catch (IllegalStateException e) {
//                    log.error("Group called before match operation executed string= " + s);
//                    return (null);
//                } catch (IndexOutOfBoundsException e) {
//                    log.error("Index out of bounds string= " + s);
//                    return (null);
//                }
//                break;

//            case 'R':
//                log.debug("PROG_READ_CV detected");
//                s = msg.toString();
//                try {
//                    p = Pattern.compile(DCCppConstants.PROG_READ_REGEX);
//                    msg = p.matcher(s);
//                    if (!msg.matches()) {
//                        log.error("Malformed PROG_READ_CV Command: {}", s);
//                        return (null);
//                    }
//                    // TODO: Work Magic Here to retrieve stored value.
//                    // Make sure that CV exists
//                    int cv = Integer.parseInt(msg.group(1));
//                    int cvVal = 0; // Default to 0 if they're reading out of bounds.
//                    if (cv < CVs.length) {
//                        cvVal = CVs[Integer.parseInt(msg.group(1))];
//                    }
//                    // CMD: <R CV CALLBACKNUM CALLBACKSUB>
//                    // Response: <r CALLBACKNUM|CALLBACKSUB|CV Value>
//                    r = "r " + msg.group(2) + "|" + msg.group(3) + "|" + msg.group(1) + " "
//                            + Integer.toString(cvVal);
//
//                    reply = new EasyDccReply(r);
//                    log.debug("Reply generated = {}", reply.toString());
//                } catch (PatternSyntaxException e) {
//                    log.error("Malformed pattern syntax! ");
//                    return (null);
//                } catch (IllegalStateException e) {
//                    log.error("Group called before match operation executed string= " + s);
//                    return (null);
//                } catch (IndexOutOfBoundsException e) {
//                    log.error("Index out of bounds string= " + s);
//                    return (null);
//                }
//                break;

            default:
                log.debug("non-reply message detected");
                reply.setElement(i++, 0x4f); // capital O for Operation
                // Send no reply.
                // return (null);
        }
        log.debug("Reply generated = {}", reply.toString());
        reply.setElement(i++, 0x0d); // add CR
        return (reply);
    }

    /**
     * Extract item address from a message.
     * <p>
     * Copied from jmri.jmrix.nce.simulator.SimulatorAdapter.
     *
     * @param m received message
     * @return address from the message
     */
    private int getEasyDccAddress(EasyDccMessage m) {
        int addr = m.getElement(1);
        addr = addr * 256;
        addr = addr + m.getElement(2);
        return addr;
    }

    private byte[] turnoutMemory = new byte[256]; // copied from DCC, remember last state

    private EasyDccReply accessoryCommand(EasyDccMessage m, EasyDccReply reply) {
        if (m.getElement(3) == 0x03 || m.getElement(3) == 0x04) {  // 0x03 = close, 0x04 = throw
            String operation = "close";
            if (m.getElement(3) == 0x04) {
                operation = "throw";
            }
            int _accessoryAddress = getEasyDccAddress(m);
            log.debug("Accessory command {} to {}T{}", operation, getSystemPrefix(), _accessoryAddress);
            if (_accessoryAddress > 2044) { // NMRA limit
                log.error("Turnout address greater than 2044, address: {}", _accessoryAddress);
                return null;
            }
            int bit = (_accessoryAddress - 1) & 0x07;
            int setMask = 0x01;
            for (int i = 0; i < bit; i++) {
                setMask = setMask << 1;
            }
            int clearMask = 0x0FFF - setMask;
            //log.debug("setMask:" + Integer.toHexString(setMask) + " clearMask:" + Integer.toHexString(clearMask));
            int offset = (_accessoryAddress - 1) >> 3;
            int read = turnoutMemory[offset];
            byte write = (byte) (read & clearMask & 0xFF);

            if (operation.equals("close")) {
                write = (byte) (write + setMask); // set bit if closed
            }
            turnoutMemory[offset] = write;
            log.debug("wrote:" + Integer.toHexString(write));
        }
        reply.setElement(0, EDC_OPS);   // Operations ready reply!
        return reply;
    }

    /**
     * Write reply to output.
     * <p>
     * Copied from jmri.jmrix.nce.simulator.SimulatorAdapter.
     *
     * @param r reply on message
     */
    private void writeReply(EasyDccReply r) {
        if (r == null) {
            return; // there is no reply to be sent
        }
        for (int i = 0; i < r.getNumDataElements(); i++) {
            try {
                outpipe.writeByte((byte) r.getElement(i));
            } catch (java.io.IOException ex) {
            }
        }
        try {
            outpipe.flush();
        } catch (java.io.IOException ex) {
        }
    }

    /**
     * Get characters from the input source.
     * <p>
     * Only used in the Receive thread.
     *
     * @returns filled message, only when the message is complete.
     * @throws IOException when presented by the input source.
     */
    private EasyDccMessage loadChars() throws java.io.IOException {
        int nchars;
        byte[] rcvBuffer = new byte[32];

        nchars = inpipe.read(rcvBuffer, 0, 32);
        //log.debug("new message received");
        EasyDccMessage msg = new EasyDccMessage(nchars);

        for (int i = 0; i < nchars; i++) {
            msg.setElement(i, rcvBuffer[i] & 0xFF);
        }
        return msg;
    }

    // streams to share with user class
    private DataOutputStream pout = null; // this is provided to classes who want to write to us
    private DataInputStream pin = null; // this is provided to classes who want data from us
    // internal ends of the pipes
    private DataOutputStream outpipe = null;  // feed pin
    private DataInputStream inpipe = null; // feed pout

    private final static Logger log = LoggerFactory.getLogger(SimulatorAdapter.class);

}
