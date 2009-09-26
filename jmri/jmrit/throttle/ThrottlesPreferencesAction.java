package jmri.jmrit.throttle;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;

import jmri.util.JmriJFrame;

public class ThrottlesPreferencesAction extends AbstractAction {
    /**
     * Constructor
     * @param s Name for the action.
     */
    public ThrottlesPreferencesAction(String s) {
        super(s);
    // disable the ourselves if there is no throttle Manager
        if (jmri.InstanceManager.throttleManagerInstance()==null) {
            setEnabled(false);
        }         
    }
    
    public ThrottlesPreferencesAction() {
	  this("Throttles preferences");         
    }
    
	public void actionPerformed(ActionEvent e) {
		ThrottlesPreferencesPane tpP = new ThrottlesPreferencesPane( );
		JmriJFrame f = new JmriJFrame("Throttles preferences");
		f.add(tpP);
		tpP.setContainer(f);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.pack();
		f.setVisible(true);
	}

}
