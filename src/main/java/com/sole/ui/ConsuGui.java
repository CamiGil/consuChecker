/**
 * 
 */
package com.sole.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.WriterAppender;

import com.sole.Settings;
import com.sole.Settings.SettingsProperty;
import com.sole.SettingsPropertyChangeListener;
import com.sole.ui.listener.LoggingChangeListener;
import com.sole.ui.listener.StartStopButtonListener;

/**
 * @author sole
 * 
 */
public class ConsuGui extends JFrame {

	public static String STOP = "Detener";
	public static String START = "Comenzar";
	
	public static JButton startStopButton;
	
	public static JCheckBox checkBoxEnableMails;
	public static JCheckBox checkBoxAnonymously;
	public static JCheckBox checkBoxTakeTheAppointment;
	public static JCheckBox checkBoxCheckFigliDiretti;
	private static JPanel panelUserPasswordAndMobile;
//	private static JPanel panelFigliDirettiDetails;
	
	/** Serial VUID */
	private static final long serialVersionUID = 4785580678430385271L;
	private static JPanel contentPane;
	public static JTextField textFieldEmailingList;
	public static JTextField textFieldUsername;
	public static JPasswordField textFieldPassword;
	public static JTextField textFieldMobile;
	public static JTextField textFieldProgenitorName;
	public static JTextField textFieldProgenitorBirthPlace;
	public static JTextField textFieldProgenitorBirthDate;
	public static JTextField textFieldPlaceCitizenshipAcquired;
	public static JTextField textFieldTimeToWait;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new ConsuGui().setVisible(true);
				if(Settings.getBooleanProperty(SettingsProperty.SHOW_WELCOME_MESSAGE)) {
					new WelcomeDialog().setVisible(true);
				}
			}
		});
	}
	
	public ConsuGui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Consu Checker");
		setBounds(100, 100, 800, 650);
		contentPane = new JPanel();
		contentPane.setToolTipText("Consu Checker");
		contentPane.setBackground(new Color(204, 204, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[200px,grow][grow][200px,grow][][][][grow][grow][][200px][200px,grow]", "[70px][40px][30px][10px,grow][30px,grow][30px][][30px][][280px,grow]"));

		JLabel consuCheckerLabel = new JLabel("Consu Checker");
		consuCheckerLabel.setForeground(new Color(0, 93, 112));
		consuCheckerLabel.setFont(new Font("SansSerif", Font.BOLD, 25));
		contentPane.add(consuCheckerLabel, "cell 0 0 11 1,growx,aligny center");

		JLabel lblChequearAnonimamente = new JLabel("Chequear Anonimamente");
		lblChequearAnonimamente.setForeground(new Color(0, 93, 112));
		lblChequearAnonimamente.setFont(new Font("SansSerif", Font.BOLD, 11));
		contentPane.add(lblChequearAnonimamente, "cell 0 1");
		
		checkBoxAnonymously = new JCheckBox("");
		checkBoxAnonymously.addActionListener(new SettingsPropertyChangeListener(SettingsProperty.LOGIN_ANONYMOUSLY, checkBoxAnonymously));
		checkBoxAnonymously.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(checkBoxAnonymously.isSelected())
					checkBoxTakeTheAppointment.setSelected(false);
				handleFieldPanels();
			}
		});
		contentPane.add(checkBoxAnonymously, "cell 1 1");
		
		JLabel lblIntentarSacarEl = new JLabel("Intentar sacar el turno");
		lblIntentarSacarEl.setForeground(new Color(0, 93, 112));
		lblIntentarSacarEl.setFont(new Font("SansSerif", Font.BOLD, 11));
		contentPane.add(lblIntentarSacarEl, "cell 2 1");
		
		checkBoxTakeTheAppointment = new JCheckBox("");
		checkBoxTakeTheAppointment.addActionListener(new SettingsPropertyChangeListener(SettingsProperty.TAKE_THE_APPOINTMENT, checkBoxTakeTheAppointment));
		checkBoxTakeTheAppointment.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(checkBoxTakeTheAppointment.isSelected())
				checkBoxAnonymously.setSelected(false);
				handleFieldPanels();
			}
		});
		contentPane.add(checkBoxTakeTheAppointment, "cell 3 1");
		/*
		JLabel lblCheckearFigliDiretti = new JLabel("Checkear Figli Diretti");
		lblCheckearFigliDiretti.setForeground(new Color(0, 93, 112));
		lblCheckearFigliDiretti.setFont(new Font("SansSerif", Font.BOLD, 11));
		contentPane.add(lblCheckearFigliDiretti, "cell 4 1");
		
		checkBoxCheckFigliDiretti = new JCheckBox("");
		checkBoxCheckFigliDiretti.addActionListener(new SettingsPropertyChangeListener(SettingsProperty.CHECK_FIGLI_DIRETTI, checkBoxCheckFigliDiretti));
		checkBoxCheckFigliDiretti.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						handleFieldPanels();
					}
				});
		contentPane.add(checkBoxCheckFigliDiretti, "cell 5 1");
		*/
		panelUserPasswordAndMobile = new JPanel();
		contentPane.add(panelUserPasswordAndMobile, "cell 0 2 10 1,alignx left,growy");
		panelUserPasswordAndMobile.setLayout(new MigLayout("", "[40px][140px][64px][140px][][]", "[19px]"));
		
		JLabel lblUsername = new JLabel("Usuario");
		panelUserPasswordAndMobile.add(lblUsername, "cell 0 0,alignx left,aligny center");
		lblUsername.setForeground(new Color(0, 93, 112));
		lblUsername.setFont(new Font("SansSerif", Font.BOLD, 11));
		
		textFieldUsername = new JTextField();
		panelUserPasswordAndMobile.add(textFieldUsername, "cell 1 0,alignx left,aligny top");
		textFieldUsername.getDocument().addDocumentListener(new SettingsPropertyChangeListener(SettingsProperty.USERNAME, textFieldUsername));
		textFieldUsername.setColumns(20);
		
		JLabel lblPassword = new JLabel("Contrasena");
		panelUserPasswordAndMobile.add(lblPassword, "cell 2 0,alignx left,aligny center");
		lblPassword.setForeground(new Color(0, 93, 112));
		lblPassword.setFont(new Font("SansSerif", Font.BOLD, 11));
		
		textFieldPassword = new JPasswordField();
		panelUserPasswordAndMobile.add(textFieldPassword, "cell 3 0,alignx left,aligny top");
		textFieldPassword.getDocument().addDocumentListener(new SettingsPropertyChangeListener(SettingsProperty.PASSWORD, textFieldPassword));
		textFieldPassword.setColumns(30);
		
		JLabel lblCelular = new JLabel("Celular");
		lblCelular.setForeground(new Color(0, 93, 112));
		lblCelular.setFont(new Font("SansSerif", Font.BOLD, 11));
		panelUserPasswordAndMobile.add(lblCelular, "cell 4 0");
		
		textFieldMobile = new JTextField();
		panelUserPasswordAndMobile.add(textFieldMobile, "cell 5 0,alignx left");
		textFieldMobile.getDocument().addDocumentListener(new SettingsPropertyChangeListener(SettingsProperty.MOBILE, textFieldMobile));
		textFieldMobile.setColumns(10);
		/*
		panelFigliDirettiDetails = new JPanel();
		contentPane.add(panelFigliDirettiDetails, "cell 0 4 8 2,grow");
		panelFigliDirettiDetails.setLayout(new MigLayout("", "[42px][grow]", "[15px][][][]"));
		
		JLabel lblNombreDelProgenitor = new JLabel("Nombre del progenitor");
		lblNombreDelProgenitor.setForeground(new Color(0, 93, 112));
		lblNombreDelProgenitor.setFont(new Font("SansSerif", Font.BOLD, 11));
		panelFigliDirettiDetails.add(lblNombreDelProgenitor, "cell 0 0,alignx left,aligny top");
		
		textFieldProgenitorName = new JTextField();
		textFieldProgenitorName.getDocument().addDocumentListener(new SettingsPropertyChangeListener(SettingsProperty.PARENT_NAME, textFieldProgenitorName));
		panelFigliDirettiDetails.add(textFieldProgenitorName, "cell 1 0,growx");
		textFieldProgenitorName.setColumns(10);
		
		JLabel lblLugarDeNacimiento = new JLabel("Lugar de Nacimiento del Progenitor");
		lblLugarDeNacimiento.setForeground(new Color(0, 93, 112));
		lblLugarDeNacimiento.setFont(new Font("SansSerif", Font.BOLD, 11));
		panelFigliDirettiDetails.add(lblLugarDeNacimiento, "cell 0 1,alignx left");
		
		textFieldProgenitorBirthPlace = new JTextField();
		textFieldProgenitorBirthPlace.getDocument().addDocumentListener(new SettingsPropertyChangeListener(SettingsProperty.PARENT_PLACE_OF_BIRTH, textFieldProgenitorBirthPlace));
		panelFigliDirettiDetails.add(textFieldProgenitorBirthPlace, "cell 1 1,growx");
		textFieldProgenitorBirthPlace.setColumns(10);
		
		JLabel lblFechaDeNacimiento = new JLabel("Fecha de Nacimiento del Progenitor");
		lblFechaDeNacimiento.setForeground(new Color(0, 93, 112));
		lblFechaDeNacimiento.setFont(new Font("SansSerif", Font.BOLD, 11));
		panelFigliDirettiDetails.add(lblFechaDeNacimiento, "cell 0 2,alignx left");
		
		textFieldProgenitorBirthDate = new JTextField();
		textFieldProgenitorBirthDate.getDocument().addDocumentListener(new SettingsPropertyChangeListener(SettingsProperty.PARENT_DATE_OF_BIRTH, textFieldProgenitorBirthDate));
		panelFigliDirettiDetails.add(textFieldProgenitorBirthDate, "cell 1 2,growx");
		textFieldProgenitorBirthDate.setColumns(10);
		
		JLabel lblLugarDeAdquisicion = new JLabel("Lugar de Adquisicion de ciudadania italiana");
		lblLugarDeAdquisicion.setForeground(new Color(0, 93, 112));
		lblLugarDeAdquisicion.setFont(new Font("SansSerif", Font.BOLD, 11));
		panelFigliDirettiDetails.add(lblLugarDeAdquisicion, "cell 0 3,alignx left");
		
		textFieldPlaceCitizenshipAcquired = new JTextField();
		textFieldPlaceCitizenshipAcquired.getDocument().addDocumentListener(new SettingsPropertyChangeListener(SettingsProperty.PARENT_PLACE_OF_CITIZENSHIP, textFieldPlaceCitizenshipAcquired));
		textFieldPlaceCitizenshipAcquired.setColumns(10);
		panelFigliDirettiDetails.add(textFieldPlaceCitizenshipAcquired, "cell 1 3,growx");
		*/
		JLabel lblSendMails = new JLabel("Notificar por E-mail");
		lblSendMails.setForeground(new Color(0, 93, 112));
		lblSendMails.setFont(new Font("SansSerif", Font.BOLD, 11));
		contentPane.add(lblSendMails, "cell 0 6");

		textFieldEmailingList = new JTextField();
		textFieldEmailingList.getDocument().addDocumentListener(new SettingsPropertyChangeListener(SettingsProperty.EMAILS, textFieldEmailingList));
		contentPane.add(textFieldEmailingList, "cell 2 6 8 1,growx");
		textFieldEmailingList.setColumns(10);
		
		checkBoxEnableMails = new JCheckBox("");
		checkBoxEnableMails.addActionListener(new SettingsPropertyChangeListener(SettingsProperty.EMAILS_ENABLED, checkBoxEnableMails));
		checkBoxEnableMails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textFieldEmailingList.setEnabled(checkBoxEnableMails.isSelected());
				if(!checkBoxEnableMails.isSelected()) {
					textFieldEmailingList.setText("");
				}
			}
		});
		contentPane.add(checkBoxEnableMails, "cell 1 6");
		
		JLabel lblChequearCada = new JLabel("Chequear cada");
		lblChequearCada.setHorizontalAlignment(SwingConstants.LEFT);
		lblChequearCada.setForeground(new Color(0, 93, 112));
		lblChequearCada.setFont(new Font("SansSerif", Font.BOLD, 11));
		contentPane.add(lblChequearCada, "cell 0 7,alignx left");
		
		textFieldTimeToWait = new JTextField();
		contentPane.add(textFieldTimeToWait, "cell 1 7,growx");
		textFieldTimeToWait.getDocument().addDocumentListener(new SettingsPropertyChangeListener(SettingsProperty.TIME_TO_WAIT, textFieldTimeToWait));
		textFieldTimeToWait.setColumns(10);
		
		JLabel lblMinutos = new JLabel("minuto(s).");
		lblMinutos.setForeground(new Color(0, 93, 112));
		lblMinutos.setFont(new Font("SansSerif", Font.BOLD, 11));
		contentPane.add(lblMinutos, "cell 2 7");
		
		buildConsoleSection();
		
		fillFieldsFromProperties();

		startStopButton = new JButton("Comenzar");
		startStopButton.setHorizontalAlignment(SwingConstants.LEFT);
		startStopButton.setToolTipText("Comenzar/Dejar de chequear");
		startStopButton.addActionListener(new StartStopButtonListener());
		contentPane.add(startStopButton, "cell 2 8 6 1,alignx center,aligny center");

	}


	private static void buildConsoleSection() {
		final JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Courier New", Font.PLAIN, 11));
		textArea.setBackground(new Color(230, 255, 230));
		textArea.setColumns(130);
		textArea.setLineWrap(true);
		textArea.setRows(16);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		textArea.getDocument().addDocumentListener(new LoggingChangeListener());
		
		JScrollPane jScrollPane = new JScrollPane(textArea);
		contentPane.add(jScrollPane, "cell 0 9 12 1,grow");

		WriterAppender appender = new WriterAppender(new PatternLayout("%d{dd/MM/yyyy HH:mm:ss} %5p - %m%n"), new TextAreaWriter(textArea,
		    jScrollPane));
		appender.setThreshold(org.apache.log4j.Level.INFO);
		Logger.getRootLogger().addAppender(appender);

		JButton btnClearConsole = new JButton("Limpiar Consola");
		btnClearConsole.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText(StringUtils.EMPTY);
			}
		});
		contentPane.add(btnClearConsole, "cell 10 8,alignx right");
	}
	
	private static void fillFieldsFromProperties() {
		checkBoxAnonymously.setSelected(Settings.getBooleanProperty(SettingsProperty.LOGIN_ANONYMOUSLY));
	//	checkBoxCheckFigliDiretti.setSelected(Settings.getBooleanProperty(SettingsProperty.CHECK_FIGLI_DIRETTI));
		checkBoxTakeTheAppointment.setSelected(Settings.getBooleanProperty(SettingsProperty.TAKE_THE_APPOINTMENT));
		textFieldUsername.setText(Settings.getProperty(SettingsProperty.USERNAME));
		textFieldPassword.setText(Settings.getProperty(SettingsProperty.PASSWORD));
		textFieldMobile.setText(Settings.getProperty(SettingsProperty.MOBILE));
	//	textFieldProgenitorName.setText(Settings.getProperty(SettingsProperty.PARENT_NAME));
	//	textFieldProgenitorBirthPlace.setText(Settings.getProperty(SettingsProperty.PARENT_PLACE_OF_BIRTH));
	//	textFieldProgenitorBirthDate.setText(Settings.getProperty(SettingsProperty.PARENT_DATE_OF_BIRTH));
	//	textFieldPlaceCitizenshipAcquired.setText(Settings.getProperty(SettingsProperty.PARENT_PLACE_OF_CITIZENSHIP));
		textFieldTimeToWait.setText(Settings.getProperty(SettingsProperty.TIME_TO_WAIT));
		checkBoxEnableMails.setSelected(Settings.getBooleanProperty(SettingsProperty.EMAILS_ENABLED));
		textFieldEmailingList.setText(Settings.getProperty(SettingsProperty.EMAILS));
		handleFieldPanels();
	}
	
	private static void handleFieldPanels() {
		panelUserPasswordAndMobile.setVisible(!checkBoxAnonymously.isSelected());
	//	panelFigliDirettiDetails.setVisible(!checkBoxAnonymously.isSelected() && checkBoxCheckFigliDiretti.isSelected());
	}

}
