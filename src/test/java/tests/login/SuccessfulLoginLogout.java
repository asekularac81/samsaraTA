package tests.login;

import Pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.BaseTest;

public class SuccessfulLoginLogout extends BaseTest {

  WebDriver driver;

  @BeforeMethod
  public void setUpTest () {
    log.debug("[SETUP TEST]");
    driver = setupDriver();
  }

  @Test
  public void testSuccessfulLoginLogout() {
    LoginPage loginPage = new LoginPage(driver).open();
  }


  @AfterMethod(alwaysRun = true)
  public void tearDownTest (ITestResult testResult) {
    log.debug("[END TEST]");
    tearDown(driver, testResult);
  }
}
