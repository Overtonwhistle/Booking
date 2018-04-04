package by.epam.tstbooking.bookingtest.pageobjects;

import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import by.epam.tstbooking.bookingtest.util.ResultParser;

public class MainPageObject {

	private final static String MAIN_PAGE_TITLE = "Booking.com";
	private final static String DESTINATION_FIELD_VALUE = "value";

	@FindBy(how = How.XPATH, using = "//*[@id='ss']")
	static WebElement destination;

	@FindBy(how = How.XPATH,
		using = "//div[contains(@class,'sb-date-field__controls')]/input[@name = 'checkin_monthday']")
	static WebElement checkinDay;

	@FindBy(how = How.XPATH,
		using = "//div[contains(@class,'sb-date-field__controls')]/input[@name = 'checkin_month']")
	static WebElement checkinMonth;

	@FindBy(how = How.XPATH,
		using = "//div[contains(@class,'sb-date-field__controls')]/input[@name = 'checkin_year']")
	static WebElement checkinYear;

	@FindBy(how = How.XPATH,
		using = "//div[contains(@class,'sb-date-field__controls')]/input[@name = 'checkout_monthday']")
	static WebElement checkoutDay;

	@FindBy(how = How.XPATH,
		using = "//div[contains(@class,'sb-date-field__controls')]/input[@name = 'checkout_month']")
	static WebElement checkoutMonth;

	@FindBy(how = How.XPATH,
		using = "//div[contains(@class,'sb-date-field__controls')]/input[@name = 'checkout_year']")
	static WebElement checkoutYear;

	// @FindBy(how = How.XPATH, using =
	// "//div[@data-component='search/dates/dates-errors']")
	@FindBy(how = How.XPATH, using = "//form[@id='frm']/div[3]/div/div[3]/div/div")
	static List<WebElement> longPeriod;

	@FindBy(how = How.ID, using = "group_adults")
	static WebElement persons;

	@FindBy(how = How.ID, using = "group_children")
	static WebElement children;

	@FindBy(how = How.XPATH, using = "//div[@class='sb-group-children-age']")
	static List<WebElement> childrenList;

	@FindBy(how = How.ID, using = "no_rooms")
	static WebElement rooms;

	@FindBy(how = How.XPATH,
		using = "//div[contains(@class,'sb-booker-type-checkbox')]/label/input")
	static WebElement workTravel;

	// @FindBy(how = How.CLASS_NAME, using = "sb-searchbox__button ") not works
	// with firefox :(
	@FindBy(how = How.XPATH, using = "//*[@id='frm']/div[5]/div[2]/button")
	static WebElement submitButton;

	@FindBy(how = How.XPATH, using = "//*[@id=\"frm\"]/div[2]/div/div[3]")
	static List<WebElement> destinationError;

	@FindBy(how = How.ID, using = "destination__error")
	static WebElement destinationEmpty;

	@FindBy(how = How.XPATH, using = "//div[@id='right']/div[3]/div/div/div/h1")
	static List<WebElement> searchResult;

	@FindBy(how = How.XPATH, using = "//*[@id=\"right\"]/div[4]/div/div/div/h1")
	static List<WebElement> searchResult2;

	private WebDriver driver;

	public MainPageObject(WebDriver driver) {
		if (!driver.getTitle().contains(MAIN_PAGE_TITLE)) {
			throw new IllegalStateException("Wrong main page!");
		}
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}

	public MainPageObject setCityName(String cityName) {
		destination.clear();
		destination.sendKeys(cityName);
		destination.sendKeys(Keys.TAB);
		return this;
	}

	public boolean checkCityName(String name) {
		return (destination.getAttribute(DESTINATION_FIELD_VALUE)).equals(name);
	}

	public MainPageObject setCheckInDay(String day) {
		checkinDay.clear();
		checkinDay.sendKeys(day);
		return this;
	}

	public MainPageObject setCheckInMonth(String month) {
		checkinMonth.clear();
		checkinMonth.sendKeys(month);
		return this;
	}

	public MainPageObject setCheckInYear(String year) {
		checkinYear.clear();
		checkinYear.sendKeys(year);
		return this;
	}

	public MainPageObject setCheckOutDay(String day) {
		checkoutDay.clear();
		checkoutDay.sendKeys(day);
		return this;
	}

	public MainPageObject setCheckOutMonth(String month) {
		checkoutMonth.clear();
		checkoutMonth.sendKeys(month);
		return this;
	}

	public MainPageObject setCheckOutYear(String year) {
		checkoutYear.clear();
		checkoutYear.sendKeys(year);
		return this;
	}

	public boolean checkForLongPeriod() {
		return longPeriod.size() > 0;
	}

	public MainPageObject setPersonsNumber(int numOfPerson) {
		WebElement selectElem = persons;
		Select select = new Select(selectElem);
		select.selectByIndex(numOfPerson - 1);
		return this;
	}

	public boolean checkPersonsNumber(int numOfPerson) {
		WebElement selectElem = persons;
		Select select = new Select(selectElem);
		String textSelected = select.getFirstSelectedOption().getText().trim();
		return textSelected.startsWith(Integer.toString(numOfPerson));
	}

	public MainPageObject setChildrenNumber(int numOfChildren) {
		WebElement selectElem = children;
		Select select = new Select(selectElem);
		select.selectByIndex(numOfChildren);
		return this;
	}

	public boolean checkChildrenNumber(int numOfChildren) {
		WebElement selectElem = children;
		Select select = new Select(selectElem);
		String textSelected = select.getFirstSelectedOption().getText().trim();
		return textSelected.startsWith(Integer.toString(numOfChildren));
	}

	public int getChildrenumber() {

		return childrenList.size();
	}

	public MainPageObject setRooms(int numOfRooms) {
		WebElement selectElem = rooms;
		Select select = new Select(selectElem);
		select.selectByIndex(numOfRooms - 1);
		return this;
	}

	public boolean checkRoomsNumber(int numOfRooms) {
		WebElement selectElem = rooms;
		Select select = new Select(selectElem);
		String textSelected = select.getFirstSelectedOption().getText().trim();
		return textSelected.startsWith(Integer.toString(numOfRooms));
	}

	public MainPageObject setWorkTravel() {
		workTravel.click();
		return this;
	}

	public boolean checkWorkTravel() {
		WebElement selectElem = workTravel;
		return selectElem.isSelected();
	}

	public boolean checkBadDestinaion() {
		return destinationError.size() > 0;
	}

	public boolean checkEmptyDestinaion() {
		return destinationEmpty.isDisplayed();
	}

	public void submitForm() { // OR check for destination inside?
		submitButton.click();
	}

	public String getResultText() {
		if (searchResult.size() > 0) {
			return searchResult.get(0).getText();
		} else
			return searchResult.get(2).getText();
	}

	public int getResultNumber() {
		String resultText = this.getResultText();
		return ResultParser.getIntResult(resultText);
	}

}
