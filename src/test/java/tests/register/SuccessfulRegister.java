package tests.register;

import Data.Groups;
import Pages.LoginPage;
import Pages.RegisterPage;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.BaseTest;

@Test(groups = {Groups.REGRESSION, Groups.SANITY})
public class SuccessfulRegister extends BaseTest {

  private String sTestName = this.getClass().getName();

  private WebDriver driver;

  @BeforeMethod
  public void setUpTest () {
    log.info("[SETUP TEST] " + sTestName );
    driver = setupDriver();
  }

  @Test
  public void testSuccessfulRegister() {

    log.info("[START TEST] " + sTestName);

    log.info("Open Login page and login as Admin user.");
    LoginPage loginPage = new LoginPage(driver).open();
    RegisterPage registerPage = loginPage.clickCreateAccountLink();

    registerPage.typeUsername("blabla");
  }

  @AfterMethod(alwaysRun = true)
  public void tearDownTest (ITestResult testResult) {
    log.info("[END TEST] " + sTestName);
    //tearDown(driver, testResult);
  }
}
