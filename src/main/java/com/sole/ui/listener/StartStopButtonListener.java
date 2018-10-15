/**
 * 
 */
package com.sole.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import org.apache.commons.lang.StringUtils;

import com.sole.Settings;
import com.sole.Settings.SettingsProperty;
import com.sole.ui.ConsuGui;

/**
 * @author soledad.mora
 * 
 */
public class StartStopButtonListener extends StartStopListener implements ActionListener {

	public void actionPerformed(ActionEvent e) {

		if (StringUtils.isBlank(Settings.getProperty(SettingsProperty.TIME_TO_WAIT))) {
			JOptionPane.showMessageDialog(null, "Por favor especifique cada cuanto chequear.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (Settings.getBooleanProperty(SettingsProperty.EMAILS_ENABLED) && StringUtils.isBlank(ConsuGui.textFieldEmailingList.getText())) {
			JOptionPane.showMessageDialog(null, "Ha seleccionado envio de mails. Por favor especifique uno o mas destinatarios, separados por coma o espacio.", "Error",
			    JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (!Settings.getBooleanProperty(SettingsProperty.LOGIN_ANONYMOUSLY)) {
			if (StringUtils.isBlank(Settings.getProperty(SettingsProperty.USERNAME))) {
				JOptionPane.showMessageDialog(null, "Por favor ingrese el nombre de usuario.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (StringUtils.isBlank(Settings.getProperty(SettingsProperty.PASSWORD))) {
				JOptionPane.showMessageDialog(null, "Por favor ingrese su contrasena.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (StringUtils.isBlank(Settings.getProperty(SettingsProperty.MOBILE))) {
				JOptionPane.showMessageDialog(null, "Por favor ingrese su numero de celular.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(Settings.getBooleanProperty(SettingsProperty.CHECK_FIGLI_DIRETTI)) {
				if(StringUtils.isBlank(Settings.getProperty(SettingsProperty.PARENT_NAME))) {
					JOptionPane.showMessageDialog(null, "Por favor ingrese el nombre de su progenitor.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(StringUtils.isBlank(Settings.getProperty(SettingsProperty.PARENT_PLACE_OF_BIRTH))) {
					JOptionPane.showMessageDialog(null, "Por favor ingrese el lugar de nacimiento de su progenitor.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(StringUtils.isBlank(Settings.getProperty(SettingsProperty.PARENT_DATE_OF_BIRTH))) {
					JOptionPane.showMessageDialog(null, "Por favor ingrese la fecha de nacimiento de su progenitor.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(StringUtils.isBlank(Settings.getProperty(SettingsProperty.PARENT_PLACE_OF_CITIZENSHIP))) {
					JOptionPane.showMessageDialog(null, "Por favor ingrese el lugar en donde su progenitor adquirio la ciudadania.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}

		}

		toggleUIOnStartStop();
	}
}
