/**
 * 
 */
package com.sole;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

/**
 * @author soledad.mora
 * 
 */
public class Settings {

	private static final Logger LOG = Logger.getLogger(Settings.class);

	public enum SettingsProperty {
		LOGIN_ANONYMOUSLY("login.anonymously"),
		TAKE_THE_APPOINTMENT("take.the.appointment"),
		CHECK_FIGLI_DIRETTI("check.figli.diretti"),
		TIME_TO_WAIT("time.to.wait"),
		USERNAME("username"),
		PASSWORD("password"),
		MOBILE("mobile"),
		PARENT_NAME("parent.name"),
		PARENT_PLACE_OF_BIRTH("parent.place.of.birth"),
		PARENT_DATE_OF_BIRTH("parent.date.of.birth"),
		PARENT_PLACE_OF_CITIZENSHIP("parent.place.of.citizenship"), 
		EMAILS("emails"), EMAILS_ENABLED("emails.enabled"),
		SHOW_WELCOME_MESSAGE("show.welcome.message");

		private final String name;

		SettingsProperty(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	private static File settingsFile;

	static {
		try {
			String path = "home/camilagil/dev" + getWorkingDirectory() + File.separator +
					"config" + File.separator + "settings.properties";
			settingsFile = new File(path);
			//settingsFile = Files.createFile(Paths.get(path));
			setupSettings();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new RuntimeException("Error found while trying to set the settings.");
		}
	}

	private static void setupSettings() throws IOException {
		if (!settingsFile.exists()) {
			useDefaultSettings();
		}
	}

	public static void useDefaultSettings() throws IOException {

	  FileUtils.copyInputStreamToFile(Settings.class.getClassLoader().getResourceAsStream("default-settings.properties"), settingsFile);
  }

	private static String getWorkingDirectory() {
	  return File.separator + "consu";
  }

	public static String getProperty(SettingsProperty property)  {
		return getProperties().getProperty(property.getName());
	}

	public static void setProperty(SettingsProperty property, String value)  {
		try {
			Properties clientProperties = getProperties();
			clientProperties.setProperty(property.getName(), value);
	    clientProperties.store(new FileOutputStream(settingsFile), "Generated by Consu Checker.");
    } catch (Exception e) {
    	LOG.error(e.getMessage(), e);
    	JOptionPane.showMessageDialog(null, "Error setting property " + property.name, "Error", JOptionPane.ERROR_MESSAGE);
    }
	}

	public static Properties getProperties()  {
		Properties props = new Properties();
		try {
			setupSettings();
	    props.load(new FileInputStream(settingsFile));
    } catch (Exception e) {
    	LOG.error(e.getMessage(), e);
    	JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
		return props;
	}

	public static boolean getBooleanProperty(SettingsProperty prop) {
		return Boolean.parseBoolean(getProperties().getProperty(prop.getName()));
  }

	public static String[] getEmails() {
	  return null;
  }

}
