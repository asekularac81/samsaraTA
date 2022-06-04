package tests.register;

import Data.CommonStrings;
import Data.Groups;
import Pages.HomePage;
import Pages.LoginPage;
import Pages.RegisterPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.BaseTest;

@Test(groups = {Groups.REGRESSION, Groups.SANITY, Groups.REGISTER})
public class SuccessfulRegisterTest extends BaseTest {

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

    log.info("Open Login page.");
    LoginPage loginPage = new LoginPage(driver).open();

    log.info("Click 'Create Account' link, navigate to Register page and verify page Title.");
    RegisterPage registerPage = loginPage.clickCreateAccountLink();
    Assert.assertEquals(registerPage.getPageTitle(), CommonStrings.getLocaleString(CommonStrings.REGISTER_PAGE_TITLE), "Wrong page Title on Register Page!");

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
