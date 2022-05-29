package Pages;

import Data.PageUrlPaths;
import Data.Time;
import org.openqa.selenium.WebDriver;

public class UsersPage extends CommonLoggedInPage {

  private final String USERS_PAGE_URL = getPageUrl(PageUrlPaths.USERS_PAGE);

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
}
