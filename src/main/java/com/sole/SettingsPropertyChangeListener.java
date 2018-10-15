/**
 * 
 */
package com.sole;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import com.sole.Settings.SettingsProperty;

/**
 * @author soledad.mora
 * 
 */
public class SettingsPropertyChangeListener implements ActionListener, DocumentListener {

	private final SettingsProperty property;
	private final Component component;

	public SettingsPropertyChangeListener(SettingsProperty property, Component component) {
		this.property = property;
		this.component = component;
	}

	public void actionPerformed(ActionEvent e) {
		setNewProperty();
	}

	public void changedUpdate(DocumentEvent arg0) {
		setNewProperty();
	}

	public void insertUpdate(DocumentEvent e) {
		setNewProperty();
  }

	public void removeUpdate(DocumentEvent arg0) {
		setNewProperty();
	}
	
	public void setNewProperty() {
		if (component instanceof JCheckBox) {
			Settings.setProperty(property, Boolean.toString(((JCheckBox) component).isSelected()));
		} else if (component instanceof JTextComponent) {
			Settings.setProperty(property, ((JTextComponent) component).getText());
		}
	}
}
