/**
 * 
 */
package com.sole.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import net.miginfocom.swing.MigLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JButton;

import javax.swing.SwingConstants;

import com.sole.Settings;
import com.sole.Settings.SettingsProperty;

/**
 * @author soledad.mora
 *
 */
public class WelcomeDialog extends JDialog {
	
	/** the serial version UID  */
  private static final long serialVersionUID = 2943601371086621131L;

	public WelcomeDialog() {
		this.setTitle("Bienvenido");
		setBounds(200, 270, 600, 200);
		this.setModal(true);
		getContentPane().setLayout(new MigLayout("", "[600px,center]", "[][][][][][][][][]"));
		
		JLabel lblNewLabel = new JLabel("Consu Checker");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(0, 93, 112));
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 25));
		getContentPane().add(lblNewLabel, "cell 0 0,grow");
		
		final JCheckBox dontShow = new JCheckBox("No mostrar de nuevo");
		dontShow.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				Settings.setProperty(SettingsProperty.SHOW_WELCOME_MESSAGE, Boolean.toString(!dontShow.isSelected()));
			}
		});
		
		JLabel lblV = new JLabel("Version 1");
		lblV.setForeground(new Color(0, 93, 112));
		getContentPane().add(lblV, "cell 0 1");
		
		JLabel bigParagraph = new JLabel("<html>Bienvenido al Consu Checker!</html>");
		bigParagraph.setFont(new Font("SansSerif", Font.PLAIN, 12));
		bigParagraph.setHorizontalAlignment(SwingConstants.LEFT);
		getContentPane().add(bigParagraph, "cell 0 3 1 4,alignx left,aligny top");
		getContentPane().add(dontShow, "cell 0 7,growx");
		
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		getContentPane().add(btnOk, "cell 0 8");
	}

}
