package tests.login;

import Data.CommonStrings;
import Data.Groups;
import Pages.LoginPage;
import Utils.PropertiesUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.BaseTest;

@Test(groups = {Groups.REGRESSION, Groups.SANITY, Groups.LOGIN})
public class UnsuccessfulLoginWrongPasswordTest extends BaseTest {

  private String sTestName = this.getClass().getName();


  private WebDriver driver;

  private String sUserName;
  private String sInvalidPassword;

  @BeforeMethod
  public void setUpTest () {
    log.info("[SETUP TEST] " + sTestName );
    driver = setupDriver();
    sUserName = PropertiesUtils.getAdminUsername();
    sInvalidPassword = PropertiesUtils.getAdminPassword() +"!";
  }

  @Test
  public void testUnsuccessfulLogin() {

    log.info("[START TEST] " + sTestName);
    //String sExpectedLoginErrorMessage = CommonStrings.LOGIN_ERROR_MESSAGE; //ovo je ako nemamo lokalizaciju
    String sExpectedLoginErrorMessage = CommonStrings.getLocaleString(CommonStrings.LOGIN_ERROR_MESSAGE); //ovde koristimo opstu metodu getLocaleString()

    log.info("Open Login page.");
    LoginPage loginPage = new LoginPage(driver).open();

    log.info("Type username and invalid password.");
    loginPage.typeUsername(sUserName);
    loginPage.typePassword(sInvalidPassword);

    // Iako ostajemo na istoj stranici, uvek treba novu instancy vratiti u promenjivu loginPage
    // Time se stitimo od Stale element refrence koja se desava kad jer se osvezila DOM struktura stranice nakon neke nase akcije a mi se oslanjamo na staru referencu
    log.info("Click Login Button and verify error message.");
    loginPage = loginPage.clickLoginButtonNoProgress();
    String sSuccessMessage = loginPage.getErrorMessage();
    Assert.assertEquals(sSuccessMessage, sExpectedLoginErrorMessage, "Wrong Login Error Message!");

  }

  @AfterMethod(alwaysRun = true)
  public void tearDownTest (ITestResult testResult) {
    log.info("[END TEST] " + sTestName);
    tearDown(driver, testResult);
  }
}
