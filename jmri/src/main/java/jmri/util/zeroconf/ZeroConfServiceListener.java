// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.util.zeroconf;

import java.util.EventListener;

/**
 * Provide an interface for listening to ZeroConfServices.
 *
 * @author Randall Wood
 */
public interface ZeroConfServiceListener extends EventListener {

    public void serviceQueued(ZeroConfServiceEvent se);

    public void servicePublished(ZeroConfServiceEvent se);

    public void serviceUnpublished(ZeroConfServiceEvent se);

}
