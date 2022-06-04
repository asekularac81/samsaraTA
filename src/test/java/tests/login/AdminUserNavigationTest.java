package tests.login;

import Data.CommonStrings;
import Data.Groups;
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
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import tests.BaseTest;

@Test(groups = {Groups.REGRESSION, Groups.SANITY, Groups.USERS})
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
    Assert.assertEquals(loginPage.getPageTitle(), CommonStrings.getLocaleString(CommonStrings.LOGIN_PAGE_TITLE), "Wrong page Title on Login Page!");

    log.info("Enter admin username and password.");
    loginPage.typeUsername(sUserName);
    loginPage.typePassword(sUserPassword);

    log.info("Verify Login Button title, click on Login Button and navigate to Welcome Page");
    Assert.assertEquals(loginPage.getLoginButtonTitle(), CommonStrings.getLocaleString(CommonStrings.LOGIN_BUTTON_TITLE), "Wrong Login Button Title on Login Page!");
    WelcomePage welcomePage = loginPage.clickLoginButton();
    Assert.assertEquals(welcomePage.getPageTitle(), CommonStrings.getLocaleString(CommonStrings.WELCOME_PAGE_TITLE), "Wrong page Title on Welcome Page!");

    log.info("Click Home tab, navigate to Home page and verify page Title.");
    HomePage homePage = welcomePage.clickHomeTab();
    Assert.assertEquals(homePage.getPageTitle(), CommonStrings.getLocaleString(CommonStrings.HOME_PAGE_TITLE), "Wrong page Title on Home Page!");

    log.info("Click Users tab, navigate to Users page and verify page Title.");
    UsersPage usersPage = homePage.clickUsersTab();
    Assert.assertEquals(usersPage.getPageTitle(), CommonStrings.getLocaleString(CommonStrings.USERS_PAGE_TITLE), "Wrong page Title on Users Page!");

    log.info("Click Heroes tab, navigate to Heroes page and verify page Title.");
    HeroesPage heroesPage = usersPage.clickHeroesTab();
    Assert.assertEquals(heroesPage.getPageTitle(), CommonStrings.getLocaleString(CommonStrings.HEROES_PAGE_TITLE), "Wrong page Title on Heroes Page!");

    log.info("Click Gallery tab, navigate to Gallery page and verify page Title.");
    GalleryPage galleryPage = usersPage.clickGalleryTab();
    Assert.assertEquals(galleryPage.getPageTitle(), CommonStrings.getLocaleString(CommonStrings.GALLERY_PAGE_TITLE), "Wrong page Title on Gallery Page!");

    log.info("Click API tab, navigate to API page and verify page Title.");
    APIPage apiPage = galleryPage.clickAPITab();
    Assert.assertEquals(apiPage.getPageTitle(), CommonStrings.getLocaleString(CommonStrings.API_PAGE_TITLE), "Wrong page Title on API Page!");

    log.info("Click Practice tab, navigate to Practice page and verify page Title.");
    PracticePage practicePage = galleryPage.clickPracticeTab();
    Assert.assertEquals(practicePage.getPageTitle(), CommonStrings.getLocaleString(CommonStrings.PRACTICE_PAGE_TITLE), "Wrong page Title on Practice Page!");

    log.info("Click Admin tab, navigate to Admin page and verify page Title.");
    Assert.assertTrue(practicePage.isAdminTabDisplayed(),"Admin Tab is NOT displayed on for Admin User!");
    AdminPage adminPage = practicePage.clickAdminTab();
    Assert.assertEquals(adminPage.getPageTitle(), CommonStrings.getLocaleString(CommonStrings.ADMIN_PAGE_TITLE),  "Wrong page Title on Admin Page!");

    log.info("Verify Tab names in Navigation Bar!");
    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals(adminPage.getUsersTabTitle(), CommonStrings.getLocaleString(CommonStrings.USERS_TAB_TITLE), "Wrong Users Tab Title on Navigation Bar!");
    softAssert.assertEquals(adminPage.getHeroesTabTitle(), CommonStrings.getLocaleString(CommonStrings.HEROES_TAB_TITLE), "Wrong Heroes Tab Title on Navigation Bar!");
    softAssert.assertEquals(adminPage.getGalleryTabTitle(), CommonStrings.getLocaleString(CommonStrings.GALLERY_TAB_TITLE), "Wrong Gallery Tab Title on Navigation Bar!");
    softAssert.assertEquals(adminPage.getAPITabTitle(), CommonStrings.getLocaleString(CommonStrings.API_TAB_TITLE), "Wrong API Tab Title on Navigation Bar!");
    softAssert.assertEquals(adminPage.getPracticeTabTitle(), CommonStrings.getLocaleString(CommonStrings.PRACTICE_TAB_TITLE), "Wrong Practice Tab Title on Navigation Bar!");
    softAssert.assertEquals(adminPage.getAdminTabTitle(), CommonStrings.getLocaleString(CommonStrings.ADMIN_TAB_TITLE), "Wrong Admin Tab Title on Navigation Bar!");
    softAssert.assertAll("At least one of Tab names on Navigation Bar is incorrect!");

    log.info("Click Log Out Link and navigate to Login page.");
    Assert.assertEquals(adminPage.getLogoutLinkTitle(), CommonStrings.getLocaleString(CommonStrings.LOGOUT_LINK_TITLE), "Wrong Logout Link Title on Admin Page!");
    loginPage = adminPage.clickLogoutLink();

  }

  @AfterMethod(alwaysRun = true)
  public void tearDownTest (ITestResult testResult) {
    log.info("[END TEST] " + sTestName);
    //tearDown(driver, testResult);
  }

}
