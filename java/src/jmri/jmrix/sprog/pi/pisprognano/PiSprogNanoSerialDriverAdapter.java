// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrix.sprog.pi.pisprognano;

import jmri.jmrix.sprog.SprogConstants.SprogMode;


/**
 * Implements SerialPortAdapter for the Sprog system.
 * <p>
 * This connects an Pi-SPROG Nano via a serial com port or virtual USB serial 
 * com port.
 * <p>
 * The current implementation only handles the 115,200 baud rate, and does not use
 * any other options at configuration time.
 *
 * @author Andrew Crosland Copyright (C) 2016
 */
public class PiSprogNanoSerialDriverAdapter
        extends jmri.jmrix.sprog.serialdriver.SerialDriverAdapter {

    public PiSprogNanoSerialDriverAdapter() {
        super(SprogMode.OPS, 115200);
        options.put("TrackPowerState", new Option(Bundle.getMessage("OptionTrackPowerLabel"),
                new String[]{Bundle.getMessage("PowerStateOff"), Bundle.getMessage("PowerStateOn")},
                true)); // first element (TrackPowerState) NOI18N
        //Set the username to match name, once refactored to handle multiple connections or user setable names/prefixes then this can be removed
        this.getSystemConnectionMemo().setUserName(Bundle.getMessage("PiSprogNanoCSTitle"));
    }

    /**
     * {@inheritDoc}
     * Currently only 115,200 bps
     */
    @Override
    public String[] validBaudRates() {
        return new String[]{"115,200 bps"};
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int[] validBaudNumbers() {
        return new int[]{115200};
    }

    // private final static Logger log = LoggerFactory.getLogger(PiSprogNanoSerialDriverAdapter.class);

}
