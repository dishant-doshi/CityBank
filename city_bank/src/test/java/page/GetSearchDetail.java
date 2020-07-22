package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import base.SetUp;

public class GetSearchDetail extends SetUp {
	By txtNID = By.id("pt1:it1::content");
	By txtDOB = By.id("pt1:id1::content");
	By btnVerify = By.xpath("//*[text()='Verify']");
	By verifySuccess = By.xpath("//*[text()='Verification is Successful']");
	By userProfilePhoto;
	By userProfileDetailPhoto = By.id("pt1:i5");
	By btnLogout = By.xpath("//*[text()='Logout']");

	public GetSearchDetail(WebDriver driver) {
		this.driver = driver;
	}

	public void enterNID(String data) {
		sendKeys(txtNID, data);
	}

	public void enterDOB(String data) {
		sendKeys(txtDOB, data);
	}

	public void clickOnVerifyButton() {
		clickOnElement(btnVerify);
	}

	public boolean verifySuccess() {
		return verifyVisible(verifySuccess);
	}

	public String getUserProfilePhoto() {
		return getAttribute(userProfilePhoto, "src");
	}

	public String getUserProfileDetail() {
		return getAttribute(userProfileDetailPhoto, "src");
	}

	public void verifyUserDetail(String nid, String dob) {
		enterNID(nid);
		enterDOB(dob);
		clickOnVerifyButton();
	}

	public void clickOnLogOut() {
		clickOnElement(btnLogout);
	}
}
