package by.epam.tstbooking.bookingtest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import by.epam.tstbooking.bookingtest.pageobjects.MainPageObject;
import by.epam.tstbooking.bookingtest.util.DriverManager;

public class tstBookingCom {

	private String baseUrl;
	private WebDriver driver = null;
	private StringBuffer verificationErrors = new StringBuffer();

	@BeforeClass(alwaysRun = true)
	public void setUp() throws Exception {

		driver = DriverManager.getDriver();
		baseUrl = "https://booking.com";
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS).setScriptTimeout(400,
				TimeUnit.MILLISECONDS);
		driver.get(baseUrl);
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
		driver.quit();
		driver = null;
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	@Test(enabled = true, priority = 1)
	public void DestnationSetTest() {
		driver.get(baseUrl);
		MainPageObject page = new MainPageObject(driver);

		SoftAssert asert = new SoftAssert();

		page.setCityName("минск");
		asert.assertEquals(page.checkCityName("минск"), true, "Destination value incorrect,");

		page.setCityName("гомель");
		asert.assertEquals(page.checkCityName("бобруйск"), false, "Destination value incorrect,");

		asert.assertAll();
	}

	@Test(enabled = true, priority = 2)
	public void emptyAndWrongDestinationTest() {
		driver.get(baseUrl);
		MainPageObject page = new MainPageObject(driver);

		SoftAssert asert = new SoftAssert();

		page.submitForm();
		asert.assertEquals(page.checkEmptyDestinaion(), true, "Empty destination check error 1,");

		page.setCityName("some strange destination...");
		page.submitForm();
		asert.assertEquals(page.checkBadDestinaion(), true, "Wrong destination check error 1, ");

		page.setCityName("minsk");
		page.submitForm();
		asert.assertEquals(page.checkBadDestinaion(), false, "Wrong destination check error 2, ");
		asert.assertEquals(page.checkEmptyDestinaion(), false, "Empty destination check error 2,");

		asert.assertAll();

	}

	@Test(enabled = true, priority = 3)
	public void resultStringTest() {
		driver.get(baseUrl);
		MainPageObject page = new MainPageObject(driver);

		SoftAssert asert = new SoftAssert();

		page.setCityName("minsk");
		page.submitForm();

		String result = page.getResultText();
		int resultNumber = page.getResultNumber();
		asert.assertEquals(result.contains("Минск: найдено"), true,
				"Result not contains requested destination,");
		asert.assertEquals(result.contains("Витебск: найдено"), false,
				"Result not contains requested destination,");
		asert.assertEquals(resultNumber > 100, true, "No results found,");

		asert.assertAll();

	}

	@Test(enabled = true, priority = 4)
	public void childrenOptionListTest() {
		driver.get(baseUrl);
		MainPageObject page = new MainPageObject(driver);

		SoftAssert asert = new SoftAssert();

		asert.assertEquals(page.getChildrenumber() == 0, true,
				"Wrong children's option number (must be 0),");

		page.setChildrenNumber(3);
		asert.assertEquals(page.getChildrenumber() == 3, true,
				"Wrong children's option number (must be 3),");

		page.setChildrenNumber(6);
		asert.assertEquals(page.getChildrenumber() == 6, true,
				"Wrong children's option number (must be 3),");

		page.setChildrenNumber(0);
		asert.assertEquals(page.getChildrenumber() == 6, false,
				"Wrong children's option number (0),");

		asert.assertAll();

	}

	@Test(enabled = true, priority = 5)
	public void DatesTest() {
		driver.get(baseUrl);
		MainPageObject page = new MainPageObject(driver);

		SoftAssert asert = new SoftAssert();

		page.setCityName("Moscow");

		page.setCheckInDay("4").setCheckInMonth("4").setCheckInYear("2018");
		page.setCheckOutDay("17").setCheckOutMonth("5").setCheckOutYear("2018");
		page.submitForm();
		asert.assertEquals(page.checkForLongPeriod(), true,
				"Long period warning should be displayed,");

		page.setCheckInDay("4").setCheckInMonth("4").setCheckInYear("2018");
		page.setCheckOutDay("2").setCheckOutMonth("5").setCheckOutYear("2018");
		page.submitForm();
		asert.assertEquals(page.checkForLongPeriod(), false,
				"Long period warning should be not displayed,");

		asert.assertAll();

	}

	@Test(enabled = true, priority = 6)
	public void inputDataSubmitTest() {
		driver.get(baseUrl);
		MainPageObject page = new MainPageObject(driver);

		SoftAssert asert = new SoftAssert();

		page.setCityName("минск");
		page.setCheckInDay("4").setCheckInMonth("5").setCheckInYear("2018");
		page.setChildrenNumber(2);
		page.setPersonsNumber(3);
		page.setRooms(4);
		page.setWorkTravel();
		page.submitForm();

		asert.assertEquals(page.checkRoomsNumber(1), true, "Wrong rooms, must be reset to '1'");
		asert.assertEquals(page.checkPersonsNumber(3), true, "Wrong persons,");
		asert.assertEquals(page.checkChildrenNumber(2), true, "Wrong children,");
		asert.assertEquals(page.checkWorkTravel(), true, "Wrong work travel");

		page.setRooms(3);
		asert.assertEquals(page.checkRoomsNumber(3), true, "Wrong rooms,");
		page.submitForm();

		String result = page.getResultText();
		asert.assertEquals(result.contains("Минск: найдено"), true,
				"Result not contains requested destination,");

		asert.assertAll();
	}

	@Test(enabled = true, priority = 7)
	public void ConcreteResultTest() {
		driver.get(baseUrl);
		MainPageObject page = new MainPageObject(driver);

		page.setCityName("Лепель");
		page.submitForm();
		System.out.println(page.getResultNumber());
		assertEquals((page.getResultNumber() > 3) && (page.getResultNumber() < 10), true,
				"Must be from 3 to 10 results,");
	}

}
