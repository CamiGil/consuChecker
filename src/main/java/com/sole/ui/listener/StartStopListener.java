/**
 * 
 */
package com.sole.ui.listener;

import static com.sole.ui.ConsuGui.START;
import static com.sole.ui.ConsuGui.STOP;

import com.sole.ConsuChecker;
import com.sole.ui.ConsuGui;

/**
 * @author soledad.mora
 * 
 */
public class StartStopListener {


	protected void toggleUIOnStartStop() {
		Thread thread = new Thread() {
			public void run() {
				ConsuGui.startStopButton.setText(START.equals(ConsuGui.startStopButton.getText()) ? STOP : START);
				if (STOP.equals(ConsuGui.startStopButton.getText())) {
					ConsuChecker.getInstance().start();
					enableComponents(false);
				}
				if (START.equals(ConsuGui.startStopButton.getText())) {
					ConsuChecker.stopApp();
					enableComponents(true);
				}
			}
		};
		thread.start();
	}
	
	protected void toggleUIOnStop() {
		Thread thread = new Thread() {
			public void run() {
				ConsuGui.startStopButton.setText(START);
				enableComponents(true);
			}
		};
		thread.start();
	}
	

	private void enableComponents(boolean enable) {
		ConsuGui.textFieldUsername.setEnabled(enable);
		ConsuGui.textFieldPassword.setEnabled(enable);
		ConsuGui.textFieldMobile.setEnabled(enable);
	//	ConsuGui.textFieldProgenitorName.setEnabled(enable);
	//	ConsuGui.textFieldProgenitorBirthPlace.setEnabled(enable);
	//	ConsuGui.textFieldProgenitorBirthDate.setEnabled(enable);
	//	ConsuGui.textFieldPlaceCitizenshipAcquired.setEnabled(enable);
		//	ConsuGui.checkBoxCheckFigliDiretti.setEnabled(enable);
		ConsuGui.textFieldTimeToWait.setEnabled(enable);
		ConsuGui.checkBoxEnableMails.setEnabled(enable);
		ConsuGui.checkBoxAnonymously.setEnabled(enable);
		ConsuGui.checkBoxTakeTheAppointment.setEnabled(enable);
		ConsuGui.textFieldEmailingList.setEnabled(enable && ConsuGui.checkBoxEnableMails.isSelected());
	}

}
