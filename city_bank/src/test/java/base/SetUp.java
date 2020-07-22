package base;

import java.io.File;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import page.GetSearchDetail;
import page.LoginPage;

public class SetUp {

	private String DEPENDENCIES_FOLDER = "./dependencies" + File.separator;
	public WebDriver driver;
	protected String testURL = "https://192.168.249.10/partner/faces/login.jspx";
	private int MAX_WAIT_TIME_IN_SEC = 120;
	public static final String LOGIN_DETAILS_FILE = "./data-provider/Amazon.xls";
	public String userName = "citybank20_city";
	public String password = "123456";
	public LoginPage loginPage;
	public GetSearchDetail getSearchDetail;

	@BeforeSuite
	public void setUp() {
		driver = initChromeDriver();
		driver.get(testURL);
		loginPage = new LoginPage(driver);
		getSearchDetail = new GetSearchDetail(driver);
		loginPage.login(userName, password);
	}

	@AfterSuite
	public void closeBrowser() {
		driver.quit();
	}

	private WebDriver initChromeDriver() {
		System.out.println("Launching google chrome with new profile..");
		System.setProperty("webdriver.chrome.driver", DEPENDENCIES_FOLDER + "chromedriver.exe");
		ChromeOptions option = setChromeOptions();
		return new ChromeDriver(option);
	}

	private ChromeOptions setChromeOptions() {
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", Integer.valueOf(0));
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		options.addArguments("--disable-notifications");
		options.addArguments("--disable-extenstions");
		options.addArguments(new String[] { "disable-infobars" });
		options.setExperimentalOption("prefs", chromePrefs);
		DesiredCapabilities capabilities = setChromeCapabilities();
		options.merge(capabilities);
		return options;
	}

	private DesiredCapabilities setChromeCapabilities() {
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability("acceptSslCerts", true);
		return capabilities;
	}

	public void clickOnElement(By locator) {
		WebElement element = null;
		Map<WebElement, String> elementState = new HashMap<WebElement, String>();
		elementState = waitForElementState(locator);
		for (Map.Entry<WebElement, String> entry : elementState.entrySet()) {
			element = entry.getKey();
		}
		try {
			if (element == null)
				throw new Exception();
			else
				element.click();
		} catch (Exception e) {
			e.printStackTrace();
			exceptionOnFailure(false, e.toString());
		}
	}

	public void exceptionOnFailure(boolean success, String message) {
		if (!success) {
			Assert.assertTrue(false, message);
		}
	}

	private Map<WebElement, String> waitForElementState(By locator) {
		WebElement element;
		Map<WebElement, String> map = new HashMap<WebElement, String>();
		element = getElement(locator);
		String message = "";
		if (element == null) {
			try {
				throw new Exception();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		map.put(element, message);
		return map;

	}

	private WebElement getElement(By by) {
		WebElement element = null;
		WebDriverWait wait = new WebDriverWait(driver, MAX_WAIT_TIME_IN_SEC);
		try {
			element = (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			if (!isVisibleInViewport(element))
				scrollToElement(element);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return element;
	}

	private void scrollToElement(WebElement element) {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, -document.body.scrollHeight);");
		if (!isVisibleInViewport(element)) {
			((JavascriptExecutor) driver).executeScript(
					"window.scrollTo(" + element.getLocation().x + "," + (element.getLocation().y - 80) + ");");
		}
	}

	public String getAttribute(By locator, String attributeName) {
		String attributeValue = null;
		WebElement element = null;
		Map<WebElement, String> elementState = new HashMap<WebElement, String>();
		elementState = waitForElementState(locator);
		for (Map.Entry<WebElement, String> entry : elementState.entrySet()) {
			element = entry.getKey();
		}
		try {
			if (element == null)
				throw new Exception();
			else
				attributeValue = element.getAttribute(attributeName);
		} catch (Exception e) {
			e.printStackTrace();
			exceptionOnFailure(false, e.toString());
		}
		return attributeValue;
	}

	public Instant getCurrentTime() {
		return Instant.now();
	}

	private boolean isVisibleInViewport(WebElement element) {
		return ((Boolean) ((JavascriptExecutor) ((RemoteWebElement) element).getWrappedDriver()).executeScript(
				"var elem = arguments[0],                   box = elem.getBoundingClientRect(),      cx = box.left + box.width / 2,           cy = box.top + box.height / 2,           e = document.elementFromPoint(cx, cy); for (; e; e = e.parentElement) {           if (e === elem)                            return true;                         }                                        return false;                            ",
				new Object[] { element })).booleanValue();
	}

	public void sendKeys(By locator, String data) {
		WebElement element = null;
		Map<WebElement, String> elementState = new HashMap<WebElement, String>();
		elementState = waitForElementState(locator);
		for (Map.Entry<WebElement, String> entry : elementState.entrySet()) {
			element = entry.getKey();
		}
		try {
			if (element == null)
				throw new Exception();
			else {
				element.clear();
				element.sendKeys(data);
			}
		} catch (Exception e) {
			exceptionOnFailure(false, e.toString());
		}
	}

	public boolean verifyVisible(By locator) {
		WebElement element = isDisplayed(locator);
		return element != null ? element.isDisplayed() : false;
	}

	public WebElement isDisplayed(By locator) {
		WebElement element = null;
		Map<WebElement, String> elementState = new HashMap<WebElement, String>();
		elementState = waitForElementState(locator);
		for (Map.Entry<WebElement, String> entry : elementState.entrySet()) {
			element = entry.getKey();
		}
		try {
			if (element == null)
				throw new Exception();
		} catch (Exception e) {
			exceptionOnFailure(false, e.toString());
		}
		return element;
	}

	public void log(String message) {
		Reporter.log(message + "<br/>");
	}

	public void commonWait() {
		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void commonWait(int sec) {
		try {
			Thread.sleep(1000 * sec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
