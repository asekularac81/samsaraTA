package tests.login;

import Data.CommonStrings;
import Data.Groups;
import Objects.User;
import Pages.APIPage;
import Pages.AdminPage;
import Pages.GalleryPage;
import Pages.HeroesPage;
import Pages.HomePage;
import Pages.LoginPage;
import Pages.PracticePage;
import Pages.UsersPage;
import Pages.WelcomePage;
import Utils.PropertiesUtils;
import Utils.RestApiUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import tests.BaseTest;

public class DemoTest extends BaseTest {


  private String sTestName = this.getClass().getName();

  private WebDriver driver;

  private String sUserName;
  private String sUserPassword;

  @BeforeMethod
  public void setUpTest () {
    log.info("[SETUP TEST] " + sTestName );
    //driver = setupDriver();
    //sUserName = PropertiesUtils.getAdminUsername();
    //sUserPassword = PropertiesUtils.getAdminPassword();
  }

  @Test
  public void testDemoTest() {
    log.info("[START TEST] " + sTestName);

    log.info("'dedoje' user exists:" + RestApiUtils.checkIfUserExists("dedoje"));
    log.info("'baboje' user exists:" + RestApiUtils.checkIfUserExists("baboje"));
    log.info("'dedoje' user details: \n" + RestApiUtils.getUserJsonFormat("dedoje"));

    User user = RestApiUtils.getUser("admin");
    log.info("'dedoje' User: \n" + user);


  }

  @AfterMethod(alwaysRun = true)
  public void tearDownTest (ITestResult testResult) {
    log.info("[END TEST] " + sTestName);
    tearDown(driver, testResult);
  }

}
