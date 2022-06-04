package tests.login;

import Data.CommonStrings;
import Data.Groups;
import Data.Time;
import Pages.LoginPage;
import Pages.WelcomePage;
import Utils.DateTimeUtils;
import Utils.PropertiesUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.BaseTest;

@Test(groups = {Groups.REGRESSION, Groups.SANITY, Groups.LOGIN})
public class SuccessfulLoginLogoutTest extends BaseTest {

  private String sTestName = this.getClass().getName(); //moze i getSimpleName() za ime same klase, bez putanje

  // driver treba da bude private da ne moze neko drugi da pristupi nastoj test klasi i koristi isti driver
  // kao i promenjive...
  private WebDriver driver;

  private String sUserName;
  private String sPassword;

  //U Before Method generisemo podatke o userima jer to nema veze sa koracima testa, da ako padne test padne zbog ficera koji testira a ne pripreme
  @BeforeMethod
  public void setUpTest () {
    log.info("[SETUP TEST] " + sTestName );
    driver = setupDriver();
    sUserName = PropertiesUtils.getAdminUsername();
    sPassword = PropertiesUtils.getAdminPassword();
  }

  @Test
  public void testSuccessfulLoginLogout() {

    log.info("[START TEST] " + sTestName);
    String sExpectedLogoutSuccessMessage = CommonStrings.getLogoutSuccessMessage(); // ako imamo lokalizaciju i posebnu metodu za svaki String
    //String sExpectedLogoutSuccessMessage1 = CommonStrings.getLocaleString(CommonStrings.LOGOUT_SUCCESS_MESSAGE); // ako imamo lokalizaciju ali ne pravimo posebne metode za svaki string vec koristimo opstu
    //String sExpectedLogoutSuccessMessage = CommonStrings.LOGOUT_SUCCESS_MESSAGE; // ako nemamo lokaclizaciju vec sve stringove definisemo u CommonStrings

    DateTimeUtils.wait(Time.DEMO_TIMEOUT);

    log.info("Open Login page and verify there is no Success or Error message.");
    LoginPage loginPage = new LoginPage(driver).open();
    //ovo proverimo samo u jednom testu da po defaultu nema greska ili poruka
    Assert.assertFalse(loginPage.isSuccessMessageDisplayed(),"Success Message should NOT be displayed!");
    Assert.assertFalse(loginPage.isErrorMessageDisplayed(),"Error Message should NOT be displayed!");

    log.info("Type username and password.");
    loginPage.typeUsername(sUserName);
    loginPage.typePassword(sPassword);

    log.info("Click Login Button and navigate to Welcome Page.");
    WelcomePage welcomePage = loginPage.clickLoginButton();

    log.info("Click Logout link and verify Logout success message on Welcome Page.");
    loginPage = welcomePage.clickLogoutLink();
    String sSuccessMessage = loginPage.getSuccessMessage();
    Assert.assertEquals(sSuccessMessage, sExpectedLogoutSuccessMessage, "Wrong Logout Success Message!");

  }

  @AfterMethod(alwaysRun = true)
  public void tearDownTest (ITestResult testResult) {
    log.info("[END TEST] " + sTestName);
    tearDown(driver, testResult);
  }
}
