package testcase;

import org.testng.annotations.Test;

import base.SetUp;

public class TestCase extends SetUp {
	// @Test(dataProvider = "Login", dataProviderClass = TestDataImport.class,
	// description = "Id: Login, Author: Dishant Doshi")
	@Test
	public void getSearchDetail() {
		getSearchDetail.verifyUserDetail("7333139397", "1977/10/16");
		String imagePath = getSearchDetail.getUserProfileDetail();
		System.out.println(imagePath);
	}

}
