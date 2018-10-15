/**
 * 
 */
package com.sole;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

/**
 * @author soledad.mora
 * 
 */
public class MailSender {
	
	private static Logger LOG = Logger.getLogger(MailSender.class);

	public static void sendWarningEmail(String appointmentType, String... recipients) {
		sendEmail(recipients, appointmentType + " - Puede que haya turnos... el programa no esta 100% seguro.",
		    "Chequealo YA: https://prenotaonline.esteri.it/login.aspx?cidsede=100064&returnUrl=// \n\n Suerte!!!");

	}

	public static void sendEmail(String appointmentType, String... recipients) {
		sendEmail(recipients, appointmentType + " - Hay Turnos!",
		    "Sacalo YA: https://prenotaonline.esteri.it/login.aspx?cidsede=100064&returnUrl=//");
	}
	
	public static void sendTestEmail(String... recipients) {
		LOG.info("Creando e-mail de prueba.");
		sendEmail(recipients, "Este es un mail de prueba. Asegurate de agregarme a la lista de contactos.", "Mail de prueba.");			
	}

	/**
	 * Send a single email.
	 */
	public static void sendEmail(String[] to, String subject, String body) {
		Authenticator auth = new Authenticator() {
			
			public PasswordAuthentication getPasswordAuthentication() { 
				String username = "laspibas@gmail.com"; String password = "canasvieiras"; 
				return new PasswordAuthentication(username, password); } 
		};
		Session session = Session.getDefaultInstance(getMailProperties(), auth);
		MimeMessage message = new MimeMessage(session);
		try {
			LOG.debug("maillll");
			LOG.info("Creando e-mail para ser enviado a " + getRecipients(to));
			for (String email : to)
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
			message.setSubject(subject);
			message.setText(body);
			LOG.info("Mandando e-mail...");
			Transport.send(message);
			LOG.info("E-email enviado con exito.");
			
		} catch (MessagingException e) {
			LOG.error("Advertencia: Hubo un error al intentar enviar mail. Por favor contacta al administrador.");
			LOG.debug("Problema al mandar mails!", e);
		}
	}

	private static String getRecipients(String[] to) {
		StringBuffer sb = new StringBuffer();
		for (String email : to)
			sb.append(email).append(", ");
		return sb.toString();
	}

	public static Properties getMailProperties() {
		Properties props = new Properties();
		props.put("mail.smtp.port", Integer.valueOf(587)); //or 465
	  props.put("mail.smtp.auth", "true");
	  props.put("mail.smtp.starttls.enable","true");
		props.put("mail.host", "smtp.gmail.com");
		props.put("mail.from", "laspibas@gmail.com");
		return props;
	}
	/**
	 * Some sites:
	 * http://www.oracle.com/technetwork/java/javamail/faq/index.html#gmail
	 * http://www.mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example/

	public static Properties getMailProperties() {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		return props;
	}
	 */

	public static void report(Exception e) {
	  sendEmail(new String[] {"solcin@gmail.com"}, "Error Found in Consu Checker!", e.getMessage() + " , stack: " + e);
  }
}
