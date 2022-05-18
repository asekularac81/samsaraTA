package Pages;

import Data.PageUrlPaths;
import Data.Time;
import org.openqa.selenium.WebDriver;

public class AdminPage extends CommonLoggedInPage{

  private final String ADMIN_PAGE_URL = getPageUrl(PageUrlPaths.ADMIN_PAGE);

  public AdminPage (WebDriver driver) {
    super(driver);
    log.trace("new AdminPage()");
  }

  public AdminPage open(boolean bVerify) {
    openUrl(ADMIN_PAGE_URL);
    log.debug("Open Admin page(" + ADMIN_PAGE_URL + ")");
    if (bVerify) {
      verifyAdminPage();
    }
    return this;
  }

  public AdminPage open() {
    return open(true);
  }

  public AdminPage verifyAdminPage(){
    log.debug("verifyAdminPage");
    waitForUrlChange(ADMIN_PAGE_URL, Time.TIME_SHORT);
    waitUntilPageIsReady(Time.TIME_SHORT);
    return this;
  }
}
