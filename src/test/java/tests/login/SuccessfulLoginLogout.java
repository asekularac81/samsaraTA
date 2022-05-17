package tests.login;

import Data.Time;
import Pages.LoginPage;
import Pages.WelcomePage;
import Utils.DateTimeUtils;
import Utils.PropertiesUtils;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.BaseTest;

public class SuccessfulLoginLogout extends BaseTest {

  private String sTestName = this.getClass().getName(); //moze i getSimpleName() za ime same klase, bez putanje

  //driver treba da bude private da ne moze neko drugi da pristupi nastoj test klasi i koristi isti driver
  // kao i promenjive...
  private WebDriver driver;

  private String sUserName;
  private String sUserPassword;

  //U Before Method generisemo podatke o userima jer to nema veze sa koracima testa, da ako padne test padne zbog ficera koji testira a ne pripreme
  @BeforeMethod
  public void setUpTest () {
    log.info("[SETUP TEST] " + sTestName );
    driver = setupDriver();
    sUserName = PropertiesUtils.getAdminUsername();
    sUserPassword = PropertiesUtils.getAdminPassword();
  }

  @Test
  public void testSuccessfulLoginLogout() {
    log.info("[START TEST] " + sTestName);
    DateTimeUtils.wait(Time.DEMO_TIMEOUT);
    LoginPage loginPage = new LoginPage(driver).open();
    loginPage.typeUsername(sUserName);
    loginPage.typePassword(sUserPassword);
    WelcomePage welcomePage = loginPage.clickLoginButton();


  }

  @AfterMethod(alwaysRun = true)
  public void tearDownTest (ITestResult testResult) {
    log.info("[END TEST] " + sTestName);
    tearDown(driver, testResult);
  }
}
