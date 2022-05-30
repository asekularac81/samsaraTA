package tests.login;

import Data.CommonStrings;
import Data.Groups;
import Pages.APIPage;
import Pages.AdminPage;
import Pages.GalleryPage;
import Pages.HeroesPage;
import Pages.LoginPage;
import Pages.PracticePage;
import Pages.UsersPage;
import Pages.WelcomePage;
import Utils.PropertiesUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import tests.BaseTest;

@Test(groups = Groups.REGRESSION)
public class AdminUserNavigationTest extends BaseTest {


  private String sTestName = this.getClass().getName();

  private WebDriver driver;

  private String sUserName;
  private String sUserPassword;

  @BeforeMethod
  public void setUpTest () {
    log.info("[SETUP TEST] " + sTestName );
    driver = setupDriver();
    sUserName = PropertiesUtils.getAdminUsername();
    sUserPassword = PropertiesUtils.getAdminPassword();
  }

  public void testAdminUserNavigation() {
    log.info("[START TEST] " + sTestName);

    log.info("Open Login page and verify Page title.");
    LoginPage loginPage = new LoginPage(driver).open();
    Assert.assertEquals(loginPage.getPageTitle(), "Samsara", "Wrong page Title on Login Page!");

    log.info("Enter admin usernam and password. Click Login Button and navigate to Welcome Page");
    loginPage.typeUsername(sUserName);
    loginPage.typePassword(sUserPassword);

    Assert.assertEquals(loginPage.getLoginButtonTitle(), CommonStrings.LOGIN_BUTTON_TITLE, "Wrong Login Button Title on Login Page!");
    WelcomePage welcomePage = loginPage.clickLoginButton();
    Assert.assertEquals(welcomePage.getPageTitle(), CommonStrings.WELCOME_PAGE_TITLE, "Wrong page Title on Welcome Page!");

    log.info("Click Users tab, navigate to Users page and verify page Title.");
    UsersPage usersPage = welcomePage.clickUsersTab();
    Assert.assertEquals(usersPage.getPageTitle(), CommonStrings.USERS_PAGE_TITLE, "Wrong page Title on Users Page!");

    log.info("Click Heroes tab, navigate to Heroes page and verify page Title.");
    HeroesPage heroesPage = usersPage.clickHeroesTab();
    Assert.assertEquals(heroesPage.getPageTitle(), CommonStrings.HEROES_PAGE_TITLE, "Wrong page Title on Heroes Page!");

    log.info("Click Gallery tab, navigate to Gallery page and verify page Title.");
    GalleryPage galleryPage = usersPage.clickGalleryTab();
    Assert.assertEquals(galleryPage.getPageTitle(), CommonStrings.GALLERY_PAGE_TITLE, "Wrong page Title on Gallery Page!");

    log.info("Click Gallery tab, navigate to API page and verify page Title.");
    APIPage apiPage = galleryPage.clickAPITab();
    Assert.assertEquals(apiPage.getPageTitle(), CommonStrings.API_PAGE_TITLE, "Wrong page Title on API Page!");

    log.info("Click Practice tab, navigate to Practice page and verify page Title.");
    PracticePage practicePage = galleryPage.clickPracticeTab();
    Assert.assertEquals(practicePage.getPageTitle(), CommonStrings.PRACTICE_PAGE_TITLE, "Wrong page Title on Practice Page!");

    log.info("Click Admin tab, navigate to Admin page and verify page Title.");
    Assert.assertTrue(practicePage.isAdminTabDisplayed(),"Admin Tab is NOT displayed on for Admin User!");
    AdminPage adminPage = practicePage.clickAdminTab();
    Assert.assertEquals(adminPage.getPageTitle(), CommonStrings.ADMIN_PAGE_TITLE,  "Wrong page Title on Admin Page!");

    log.info("Verify Tab names in Navigation Bar!");
    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals(adminPage.getUsersTabTitle(), CommonStrings.USERS_TAB_TITLE, "Wrong Users Tab Title on Navigation Bar!");
    softAssert.assertEquals(adminPage.getHeroesTabTitle(), CommonStrings.HEROES_TAB_TITLE, "Wrong Heroes Tab Title on Navigation Bar!");
    softAssert.assertEquals(adminPage.getGalleryTabTitle(), CommonStrings.GALLERY_TAB_TITLE, "Wrong Gallery Tab Title on Navigation Bar!");
    softAssert.assertEquals(adminPage.getAPITabTitle(), CommonStrings.API_TAB_TITLE, "Wrong API Tab Title on Navigation Bar!");
    softAssert.assertEquals(adminPage.getPracticeTabTitle(), CommonStrings.PRACTICE_TAB_TITLE, "Wrong Practice Tab Title on Navigation Bar!");
    softAssert.assertEquals(adminPage.getAdminTabTitle(), CommonStrings.ADMIN_TAB_TITLE, "Wrong Admin Tab Title on Navigation Bar!");
    softAssert.assertAll("At least one of Tab names on  Navigation Bar is incorrect!");

  }

  @AfterMethod(alwaysRun = true)
  public void tearDownTest (ITestResult testResult) {
    log.info("[END TEST] " + sTestName);
    //tearDown(driver, testResult);
  }

}
