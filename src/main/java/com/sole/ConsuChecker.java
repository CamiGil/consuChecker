/**
 * 
 */
package com.sole;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.sole.Settings.SettingsProperty;
import com.sole.ui.ConsuGui;

/**
 * @author soledad.mora
 * 
 */
public class ConsuChecker extends Thread {

	private static final String PASSWORD = "Password";
	private static final String USER_NAME = "UserName";
	private static final String CONSU_URL = "https://prenotaonline.esteri.it/login.aspx?cidsede=100064&returnUrl=//";
	private static final int MAX_LOGIN_TIME = 15;
	private static final String HAVE_READ = "S";
	private static final String RED_CELL = "calendarCellRed";
	private static final String NO_SELECTABLE = "noSelectableDay";
	private static final String OTHER = "otherMonthDay";
	private static final String GREEN_CELL = "calendarCellOpen";
	private static final String ORANGE_CELL = "calendarCellMed";
	private static final String LOGIN = "BtnLogin";
	
	static FirefoxDriver driver;
	static Wait<WebDriver> wait;
	private static Logger LOG = Logger.getLogger(ConsuChecker.class);

	private static ConsuChecker app;
	private static boolean running;

	private ConsuChecker() {
	}

	public static void main(String[] args) throws IOException {
		Settings.useDefaultSettings();
		ConsuChecker.getInstance().start();
	}

	public static ConsuChecker getInstance() {
		if (!running) {
			app = new ConsuChecker();
		}
		return app;
	}

	public static boolean isRunning() {
		return running;
	}

	public void run() {
		running = true;
		if(notificationsEnabled())
			MailSender.sendTestEmail(UserOptions.getEmails());
		LOG.info("En unos instantes se abrira una ventana de Mozilla Firefox. Por favor espere.");
		
		ConsuGui.startStopButton.setEnabled(false);
		while (running) {
			try {
				driver = new FirefoxDriver();
				wait = new WebDriverWait(driver, 1000000);
				driver.get(CONSU_URL);
				driver.findElement(By.id(LOGIN)).click();
				driver.findElement(By.id(USER_NAME)).sendKeys(UserOptions.getUserName());
				driver.findElement(By.id(PASSWORD)).sendKeys(UserOptions.getPassword());
				LOG.info("Ingrese el captcha para poder continuar.");
				ConsuGui.startStopButton.setEnabled(true);
				// Wait for search to complete
				wait.until(new ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver webDriver) {
						try {
							WebElement e = driver.findElement(By.id("FailureText"));
							if (e != null) {
								stopApp();
								LOG.info("Hubo un problema logueandose: " + e.getText());
								LOG.info("Por favor intente de nuevo.");
								return Boolean.TRUE;
							}
						} catch (NoSuchElementException e) {
							// do nothing.
							LOG.debug(e);
						}
						return Boolean.valueOf(findLink("Prenota il servizio") != null);
					}
				});
				while (running) {
					if (checkPrimoAppuntamento()) {
						stopApp();
						LOG.info("Turnos encontrados! La aplicacion ha sido detenida.");
					}
					if (UserOptions.isCheckFigliDiretti())
						if (checkFigliDiretti()) {
							stopApp();
							LOG.info("Turnos encontrados! La aplicacion ha sido detenida.");
						}
					makeTime();
				}
			} catch (Exception e) {
				ConsuGui.startStopButton.setEnabled(true);
				stopApp();
				if (e.getCause() != null && e.getCause().getMessage() != null && e.getCause().getMessage().contains("The browser window may have been closed")) {
					LOG.info("La ventana de Mozilla Firefox ha sido cerrada. Por favor intente de nuevo.");
				} else if (e.getCause() != null && e.getCause().getMessage() != null && e.getCause().getMessage().contains("perhaps the page has changed since it was looked up")) {
					LOG.info("Ha ocurrido un problema. Por favor intente de nuevo. Si el problema persiste, contacte al administrador.");
				} else {
					LOG.error("Problema! " + e.getCause());
					MailSender.report(e);
					LOG.info("El problema ha sido reportado. Por favor intente de nuevo.");
				}
				LOG.debug(e);

			}
		}

	}

	private boolean notificationsEnabled() {
		return UserOptions.getEmails() != null && UserOptions.getEmails().length > 0 && !"".equals(UserOptions.getEmails()[0]);
	}

	private static boolean checkFigliDiretti() {
		LOG.info("Chequeando Figli Diretti...");
		ConsuGui.startStopButton.setEnabled(false);
		click("Prenota On Line");
		click("Cittadinanza");
		click("Figli diretti Maggiorenni");
		fillText("Cellulare", UserOptions.getMobileNo());
		fillText("Ho letto le istruzioni sul sito (S/N)", HAVE_READ);
		fillText("Nome Genitore dal quale prende la cittadinanza", UserOptions.getParentName());
		fillText("Luogo Nascita Genitore", UserOptions.getParentPlaceOfBirth());
		fillText("Data Nascita Genitore", UserOptions.getParentDateOfBirth());
		fillText("Luogo riconoscimento cittadinanza ita genitore", UserOptions.getParentPlaceOfCitizenship());
		click("Conferma");

		boolean success = checkCalendar("Figli Diretti");
		ConsuGui.startStopButton.setEnabled(true);
		if (!success)
			LOG.info("Figli Diretti chequeado. Reintentando en " + UserOptions.getTimeToWait() + " minutos.");
		return success;

	}

	private static boolean checkPrimoAppuntamento() {
		LOG.info("Chequeando Primo Appuntamento...");
		ConsuGui.startStopButton.setEnabled(false);
		click("Prenota il servizio");
		click("Cittadinanza");
		click("Primo Appuntamento");
		fillText("Cellulare", UserOptions.getMobileNo());
		click("Conferma");

		boolean success = checkCalendar("Primo Appuntamento");
		ConsuGui.startStopButton.setEnabled(true);
		if (!success)
			LOG.info("Primo Appuntamento chequeado. Reintentando en " + UserOptions.getTimeToWait() + " minutos.");
		return success;
	}

//	private static boolean checkPensioni() {
//		LOG.info("Chequeando Pensioni...");
//		click("Prenota On Line");
//		click("Pensioni");
//		click("Conferma");
//		boolean success = checkCalendar("Pensioni");
//		if (!success)
//			LOG.info("Primo Appuntamento chequeado. Reintentando en " + UserOptions.getTimeToWait() + " minutos.");
//		return success;
//	}
 
	private static boolean checkCalendar(String appointmentType) {
		for (int i = 0; i < 13; i++) {
			List<WebElement> elements = driver.findElements(By.tagName("td"));
			for (WebElement e : elements) {
				String cellColor = e.getAttribute("class");
				if (GREEN_CELL.equalsIgnoreCase(cellColor) || ORANGE_CELL.equalsIgnoreCase(cellColor)) {
					LOG.info("Hay turnos Disponibles!");
					sendMail(appointmentType);
					takeTheAppointment(e);
					LOG.info("Turno obtenido!");
					return true;
				}
				if (!(RED_CELL.equalsIgnoreCase(cellColor) || NO_SELECTABLE.equalsIgnoreCase(cellColor) || OTHER.equalsIgnoreCase(cellColor)
				    || GREEN_CELL.equalsIgnoreCase(cellColor) || ORANGE_CELL.equalsIgnoreCase(cellColor))) {
					LOG.info("Puede que haya turnos disponibles! Encontre una celda de color " +cellColor);
					sendWarningMail(appointmentType);
					takeTheAppointment(e);
					return true;
				}
			}
			driver.findElement(By.name("ctl00$ContentPlaceHolder1$acc_Calendario1$myCalendario1$ctl01")).click(); // Mese precedente, 2014
		}
		for (int i = 0; i < 13; i++) {
			driver.findElement(By.name("ctl00$ContentPlaceHolder1$acc_Calendario1$myCalendario1$ctl03")).click(); // Mese successivo, 2015
		}
		for (int i = 0; i < 13; i++) {
			List<WebElement> elements = driver.findElements(By.tagName("td"));
			for (WebElement e : elements) {
				String cellColor = e.getAttribute("class");
				if (GREEN_CELL.equalsIgnoreCase(cellColor) || ORANGE_CELL.equalsIgnoreCase(cellColor)) {
					LOG.info("Hay turnos Disponibles!");
					sendMail(appointmentType);
					takeTheAppointment(e);
					return true;
				}
				if (!(RED_CELL.equalsIgnoreCase(cellColor) || NO_SELECTABLE.equalsIgnoreCase(cellColor) || OTHER.equalsIgnoreCase(cellColor)
				    || GREEN_CELL.equalsIgnoreCase(cellColor) || ORANGE_CELL.equalsIgnoreCase(cellColor))) {
					LOG.info("Puede que haya turnos disponibles! Encontre una celda de color " +cellColor);
					sendWarningMail(appointmentType);
					takeTheAppointment(e);
					return true;
				}
			}
			driver.findElement(By.name("ctl00$ContentPlaceHolder1$acc_Calendario1$myCalendario1$ctl03")).click(); // Mese successivo, 2015
		}
		return false;
	}

	private static void takeTheAppointment(WebElement e) {
		if (Settings.getBooleanProperty(SettingsProperty.TAKE_THE_APPOINTMENT)) {
			WebElement numberLink = e.findElement(By.tagName("input"));
			numberLink.click();
			click("Conferma");
			click("Conferma");
			LOG.info("Turno obtenido!");
			// stopApp();
		}
	}

	private static void fillText(String fieldName, String text) {
		List<WebElement> inputs = driver.findElements(By.tagName("input"));

		CICLO_OUTER: for (WebElement input : inputs) {
			if ("text".equalsIgnoreCase(input.getAttribute("type"))) {
				List<WebElement> labels = driver.findElements(By.tagName("label"));
				for (WebElement label : labels) {
					if (fieldName.equalsIgnoreCase(label.getText()) && label.getAttribute("for").equalsIgnoreCase(input.getAttribute("id"))) {
						input.sendKeys(text);
						break CICLO_OUTER;
					}
				}
			}
		}
	}

	private static WebElement findLink(String linkValue) {
		List<WebElement> firstMenu = driver.findElements(By.tagName("input"));
		for (WebElement e : firstMenu) {
			if ("submit".equalsIgnoreCase(e.getAttribute("type")) && linkValue.equalsIgnoreCase(e.getAttribute("value"))) {
				return e;
			}
		}
		return null;
	}

	private static void click(String linkValue) {
		List<WebElement> firstMenu = driver.findElements(By.tagName("input"));

		for (WebElement e : firstMenu) {
			if ("submit".equalsIgnoreCase(e.getAttribute("type")) && linkValue.equalsIgnoreCase(e.getAttribute("value"))) {
				e.click();
				break;
			}
		}
	}

	private static void sendWarningMail(String appointmentType) {
		if (Settings.getBooleanProperty(SettingsProperty.EMAILS_ENABLED))
			MailSender.sendWarningEmail(appointmentType, UserOptions.getEmails());
	}

	private static void sendMail(String appointmentType) {
		if (Settings.getBooleanProperty(SettingsProperty.EMAILS_ENABLED))
			LOG.info("Notificando a " + UserOptions.getEmails() + " por " + appointmentType);
			MailSender.sendEmail(appointmentType, UserOptions.getEmails());
	}

	private static void makeTime() throws InterruptedException {
		int timeToWait = UserOptions.getTimeToWait();

		int times = (timeToWait / MAX_LOGIN_TIME);
		int rem = (timeToWait % MAX_LOGIN_TIME);
		if (rem != 0)
			Thread.sleep((long) (60000D * (rem)));
		for (int i = 0; i < times; i++) {
			LOG.debug("Haciendo Tiempo...");
			click("Prenota On Line");
			Thread.sleep((long) (60000D * (15D)));
		}
	}

	public static void stopApp() {
		running = false;
		LOG.info("Aplicacion detenida.");
		try {
			if (driver != null)
				driver.close();
		} catch (Exception e) { }
	}

}
