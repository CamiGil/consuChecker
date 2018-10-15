/**
 * 
 */
package com.sole.ui.listener;


import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.sole.ConsuChecker;
import com.sole.ui.ConsuGui;

/**
 * @author soledad.mora
 * 
 */
public class LoggingChangeListener extends StartStopListener implements DocumentListener {

	public void insertUpdate(DocumentEvent e) {
		if (!ConsuChecker.isRunning() && ConsuGui.STOP.equals(ConsuGui.startStopButton.getText()))
			toggleUIOnStop();
	}

	public void changedUpdate(DocumentEvent arg0) {}

	public void removeUpdate(DocumentEvent arg0) {}
	

}
