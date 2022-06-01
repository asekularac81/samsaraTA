package tests.users;

import Data.Groups;
import Pages.AddUserDialogBox;
import Pages.LoginPage;
import Pages.UsersPage;
import Pages.WelcomePage;
import Utils.PropertiesUtils;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.BaseTest;

@Test(groups = {Groups.REGRESSION, Groups.SANITY})
public class AddNewUser extends BaseTest {

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
  public void testAddNewUser() {

    log.info("[START TEST] " + sTestName);

    log.info("Open Login page and login as Admin user.");
    LoginPage loginPage = new LoginPage(driver).open();
    WelcomePage welcomePage = loginPage.login(sUserName, sPassword);

    log.info("Click Users tab and navigate to Users Page.");
    UsersPage usersPage = welcomePage.clickUsersTab();

    log.info("Click 'Add New User' button and verify 'Add User' Dialog Box is opened.");
    AddUserDialogBox addUserDialogBox = usersPage.clickAddNewUserButton();

    log.info("Click 'Cancel' button and verify User Page is opened.");
    usersPage = addUserDialogBox.clickCancelButton(); //vratimo u UsersPage da izbegnemo StaleElement Reference

    log.info("Click 'Log Out' Link and navigate to Login page.");
    loginPage = usersPage.clickLogoutLink();
  }

  @AfterMethod(alwaysRun = true)
  public void tearDownTest (ITestResult testResult) {
    log.info("[END TEST] " + sTestName);
    tearDown(driver, testResult);
  }
}
