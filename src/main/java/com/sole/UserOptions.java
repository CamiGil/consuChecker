/**
 * 
 */
package com.sole;

import com.sole.Settings.SettingsProperty;


/**
 * @author soledad.mora
 *
 */
public class UserOptions {

	/**
   * @return the loginAnonymously
   */
  public static boolean isLoginAnonymously() {
  	return Settings.getBooleanProperty(SettingsProperty.LOGIN_ANONYMOUSLY);
  }


	/**
   * @return the takeTheAppointment
   */
  public static boolean isTakeTheAppointment() {
  	return Settings.getBooleanProperty(SettingsProperty.TAKE_THE_APPOINTMENT);
  }


	/**
   * @return the userName
   */
  public static String getUserName() {
  	if(isLoginAnonymously())
  		return "laspibas@gmail.com";
  	return Settings.getProperty(SettingsProperty.USERNAME);
  }

	/**
   * @return the password
   */
  public static String getPassword() {
  	if(isLoginAnonymously())
  		return "Federico3101";
  	return Settings.getProperty(SettingsProperty.PASSWORD);
  }

	/**
   * @return the mobileNo
   */
  public static String getMobileNo() {
  	if(isLoginAnonymously())
  		return "1551019927";
  	return Settings.getProperty(SettingsProperty.MOBILE);
  }


	/**
   * @return the parentName
   */
  public static String getParentName() {
  	if(isLoginAnonymously())
  		return "Mario Botta";
  	return Settings.getProperty(SettingsProperty.PARENT_NAME);
  }


	/**
   * @return the parentPlaceOfBirth
   */
  public static String getParentPlaceOfBirth() {
  	if(isLoginAnonymously())
  		return "Buenos Aires";
  	return Settings.getProperty(SettingsProperty.PARENT_PLACE_OF_BIRTH);
  }


	/**
   * @return the parentDateOfBirth
   */
  public static String getParentDateOfBirth() {
  	if(isLoginAnonymously())
  		return "29/10/1953";
  	return Settings.getProperty(SettingsProperty.PARENT_DATE_OF_BIRTH);
  }


	/**
   * @return the parentPlaceOfCitizenship
   */
  public static String getParentPlaceOfCitizenship() {
  	if(isLoginAnonymously())
  		return "Buenos Aires";
  	return Settings.getProperty(SettingsProperty.PARENT_PLACE_OF_CITIZENSHIP);
  }


	/**
   * @return the emails
   */
  public static String[] getEmails() {
  	return Settings.getProperty(SettingsProperty.EMAILS).split(",");
  }


	/**
   * @return the checkFigliDiretti
   */
  public static boolean isCheckFigliDiretti() {
  	return Settings.getBooleanProperty(SettingsProperty.CHECK_FIGLI_DIRETTI);
  }

	/**
   * @return the timeToWait
   */
  public static int getTimeToWait() {
  	return Integer.parseInt(Settings.getProperty(SettingsProperty.TIME_TO_WAIT));
  }

	

}
