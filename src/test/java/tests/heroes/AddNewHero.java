package tests.heroes;

import Data.Groups;
import Pages.AddHeroDialogBox;
import Pages.HeroesPage;
import Pages.LoginPage;
import Pages.WelcomePage;
import Utils.PropertiesUtils;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.BaseTest;

@Test(groups = {Groups.REGRESSION, Groups.SANITY})
public class AddNewHero extends BaseTest {

  private String sTestName = this.getClass().getName();

  private WebDriver driver;

  private String sUserName;
  private String sPassword;

  @BeforeMethod
  public void setUpTest () {
    log.info("[SETUP TEST] " + sTestName );
    driver = setupDriver();
    sUserName = PropertiesUtils.getAdminUsername();
    sPassword = PropertiesUtils.getAdminPassword();
  }

  @Test
  public void testAddNewHero() {

    log.info("[START TEST] " + sTestName);

    log.info("Open Login page and login as Admin user.");
    LoginPage loginPage = new LoginPage(driver).open();
    WelcomePage welcomePage = loginPage.login(sUserName, sPassword);

    log.info("Click Heroes tab and navigate to Heroes Page.");
    HeroesPage heroesPage = welcomePage.clickHeroesTab();

    log.info("Click 'Add New Hero' button and verify 'Add User' Dialog Box is opened.");
    AddHeroDialogBox addHeroDialogBox = heroesPage.clickAddNewHeroButton();

    log.info("Click 'Cancel' button and verify User Page is opened.");
    heroesPage = addHeroDialogBox.clickCancelButton();

    log.info("Click 'Log Out' Link and navigate to Login page.");
    loginPage = heroesPage.clickLogoutLink();
  }

  @AfterMethod(alwaysRun = true)
  public void tearDownTest (ITestResult testResult) {
    log.info("[END TEST] " + sTestName);
    tearDown(driver, testResult);
  }
}
