package Pages;

import Data.PageUrlPaths;
import Data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class UsersPage extends CommonLoggedInPage {

  //PAGE URL PATH
  private final String USERS_PAGE_URL = getPageUrl(PageUrlPaths.USERS_PAGE);

  //LOCATORS
  private final By addNewUserButtonLocator = By.xpath("//a[contains(@class,'btn-info') and contains(@onclick,'openAddUserModal()')]");

  // KONSTRUKTOR
  public UsersPage(WebDriver driver) {
    super(driver);
    log.trace("new UsersPage()");
  }

  public UsersPage open(boolean bVerify) {
    log.debug("Open UsersPage(" + USERS_PAGE_URL + ")");
    openUrl(USERS_PAGE_URL);
    if (bVerify) {
      verifyUsersPage();
    }
    return this;
  }

  public UsersPage open() {
    return open(true);
  }

  public UsersPage verifyUsersPage() {
    log.debug("verifyUsersPage");
    waitForUrlChange(USERS_PAGE_URL, Time.TIME_SHORT);
    waitUntilPageIsReady(Time.TIME_SHORT);
    return this;
  }

  //-----------------------------------------------------------------------------------------------

  // ADD NEW USER BUTTON - isDisplayed, click, getTitle
  public boolean isAddNewUserButtonDisplayed() {
    log.debug("isAddNewUserButtonDisplayed");
    return isWebElementDisplayed(addNewUserButtonLocator);
  }

  // Klikemo na Add new User button, proverimo da je modal vidljiv i vratimo instancu modala
  public AddUserDialogBox clickAddNewUserButton() {
    log.debug("clickAddNewUserButton");
    Assert.assertTrue(isAddNewUserButtonDisplayed(),"'Add New User' Button is NOT displayed on Users Page!");
    WebElement addNewUserButton = getWebElement(addNewUserButtonLocator);
    clickOnWebElement(addNewUserButton);
    //verifikujemo da se otvorio AddUserDialogBox , poziva se ispod isAddUserDialogBoxOpened() --isWebElementVisible
    AddUserDialogBox addUserDialogBox = new AddUserDialogBox(driver);
    return addUserDialogBox.verifyAddUserDialogBox();
  }

  public String getAddNewUserButtonTitle() {
    log.debug("getAddNewUserButtonTitle");
    Assert.assertTrue(isAddNewUserButtonDisplayed(),"'Add New User' Button is NOT displayed on Users Page!");
    WebElement addNewUserButton = getWebElement(addNewUserButtonLocator);
    return getTextFromWebElement(addNewUserButton);
  }

  //-----------------------------------------------------------------------------------------------
}
