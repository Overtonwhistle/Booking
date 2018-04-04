package by.epam.tstbooking.bookingtest.util;

import static org.testng.Assert.fail;

import java.net.MalformedURLException;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class DriverManager {

	private final static String BASENAME = "test_settings";
	private final static String BROWSER_PARAM = "browser";
	private final static String PATH_PARAM = "path_to_driver";
	private static ResourceBundle bundle = null;// =
	private static WebDriver driver;

	public DriverManager() {
	}

	static {
		try {
			bundle = ResourceBundle.getBundle(BASENAME);
		} catch (java.util.MissingResourceException e) {
			driver = new ChromeDriver(); // default, must be registered in PATH
											// !
		}
	}

	public static WebDriver getDriver() {

		if (driver == null) {
			String browser = bundle.getString(BROWSER_PARAM);
			String path = bundle.getString(PATH_PARAM);

			if (browser.equals("chrome")) {
				if (null == System.getProperty("webdriver.chrome.driver")) {
					System.setProperty("webdriver.chrome.driver", path);
				}
				driver = new ChromeDriver();

			} else if (browser.equals("firefox")) {
				if (null == System.getProperty("webdriver.gecko.driver")) {
					System.setProperty("webdriver.gecko.driver", path);
				}
				driver = new FirefoxDriver();
			} else {
				fail("Incorrect browser type in properties!");
			}
		}

		return driver;
	}

}
