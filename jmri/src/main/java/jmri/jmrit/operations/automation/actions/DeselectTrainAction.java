// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrit.operations.automation.actions;

import jmri.jmrit.operations.trains.Train;

public class DeselectTrainAction extends Action {

    private static final int _code = ActionCodes.DESELECT_TRAIN;

    @Override
    public int getCode() {
        return _code;
    }

    @Override
    public String getName() {
        return Bundle.getMessage("DeselectTrain");
    }

    @Override
    public void doAction() {
        if (getAutomationItem() != null) {
            Train train = getAutomationItem().getTrain();
            if (train != null) {
                train.setBuildEnabled(false);
            }
            finishAction(train != null);
        }
    }

    @Override
    public void cancelAction() {
        // no cancel for this action
    }

}
