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

    log.info("Open Login page and click 'Create Account' link");
    LoginPage loginPage = new LoginPage(driver).open();
    RegisterPage registerPage = loginPage.clickCreateAccountLink();

    log.info("Enter all text fields on Register Page");
    registerPage.typeUsername("ladybug");
    registerPage.typeFirstName("Lady");
    registerPage.typeLastName("Bug");
    registerPage.typeEmail("hello@gmail.com");
    registerPage.typeAbout("Something about me.");
    registerPage.typeSecretQuestion("What is your favorite colour");
    registerPage.typeSecretAnswer("Red");
    registerPage.typePassword("password123!@#");
    registerPage.typeConfirmPassword("password123!@#");

    log.info("Click Sign up and verify Welcome page is opened.");

  }

  @AfterMethod(alwaysRun = true)
  public void tearDownTest (ITestResult testResult) {
    log.info("[END TEST] " + sTestName);
    //tearDown(driver, testResult);
  }
}
