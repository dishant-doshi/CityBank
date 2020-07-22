package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import base.SetUp;

public class LoginPage extends SetUp {
	By txtUserName = By.id("pt1:username::content");
	By txtPassword = By.id("pt1:password::content");
	By btnLogin = By.xpath("//*[text()='Login']");

	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}

	public void enterUserName(String userName) {
		sendKeys(txtUserName, userName);
	}

	public void enterPassword(String password) {
		sendKeys(txtPassword, password);
	}

	public void clickOnLoginButton() {
		clickOnElement(btnLogin);
	}

	public void login(String userName, String password) {
		enterUserName(userName);
		enterPassword(password);
		clickOnLoginButton();
	}
}
