package tests.evaluation;

import Data.Groups;
import Pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import tests.BaseTest;

// PAGE EVALUATION KONCEPT:
// Napravi se test koji ode na stranicu i proveri za svaki element da li je displayed
// Cilj je da se odmah uhvati ako se neki lokator promenio ili fali (bug) pre nego krenemo u Full Regression job
// Treba ga pustiti pre FR joba
// Kod CI obicno cim se izbilda build, okida se FR job, pa se nema kad pustiti evalutation job
// Ali mozemo dan pred leganje rilis candidata da pustimo evalutation job i sredimo sve lokatore ako su se promenili/Bug

@Test(groups = Groups.EVALUATION)
public class EvaluateLoginPage extends BaseTest {

  private String sTestName = this.getClass().getName();
  private WebDriver driver;

  @BeforeMethod
  public void setUpTest () {
    log.info("[SETUP TEST] " + sTestName );
    driver = setupDriver();
  }

  @Test
  public void evaluationTest() {

    LoginPage loginPage = new LoginPage(driver).open();

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertTrue(loginPage.isUsernameTextFieldDisplayed(), "Username Text Field is NOT displayed on Login Page!");
    softAssert.assertTrue(loginPage.isPasswordTextFieldDisplayed(), "Password Text Field is NOT displayed on Login Page!");
    softAssert.assertTrue(loginPage.isLoginButtonDisplayed(), "Login Button is NOT displayed on Login Page!");
    softAssert.assertTrue(loginPage.isCreateAccountLinkDisplayed(), "Create Account Link is NOT displayed on Login Page!");
    softAssert.assertTrue(loginPage.isResetPasswordLinkDisplayed(), "Reset Password Link is NOT displayed on Login Page!");
    softAssert.assertAll("At least one Web Elements is not displayed on Login Page!");

  }

  @AfterMethod(alwaysRun = true)
  public void tearDownTest (ITestResult testResult) {
    log.info("[END TEST] " + sTestName);
    tearDown(driver, testResult);
  }
}
