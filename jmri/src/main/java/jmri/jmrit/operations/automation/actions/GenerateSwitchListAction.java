// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.jmrit.operations.automation.actions;

public class GenerateSwitchListAction extends GenerateSwitchListChangesAction {

    private static final int _code = ActionCodes.GENERATE_SWITCHLIST;

    @Override
    public int getCode() {
        return _code;
    }

    @Override
    public String getName() {
        return Bundle.getMessage("GenerateSwitchList");
    }

    @Override
    public void doAction() {
        doAction(!IS_CHANGED);
    }

    @Override
    public void cancelAction() {
        // no cancel for this action     
    }

//    private final static Logger log = LoggerFactory.getLogger(RunSwitchListAction.class);

}
