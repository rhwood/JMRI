// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrit.operations.trains.excel;

import jmri.InstanceManagerAutoDefault;

public class TrainCustomManifest extends TrainCustomCommon implements InstanceManagerAutoDefault {

    public TrainCustomManifest() {
        super("csvManifests", Xml.MANIFEST_CREATOR); // NOI18N
    }
}