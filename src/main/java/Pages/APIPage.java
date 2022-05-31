package Pages;

import Data.PageUrlPaths;
import Data.Time;
import org.openqa.selenium.WebDriver;

public class APIPage extends CommonLoggedInPage{

  private final String API_PAGE_URL = getPageUrl(PageUrlPaths.API_PAGE);

  // KONSTRUKTOR
  public APIPage(WebDriver driver) {
    super(driver);
    log.debug("new APIPage()");
  }

  public APIPage open(boolean bVerify) {
    openUrl(API_PAGE_URL);
    log.debug("Open APIPage(" + API_PAGE_URL + ")");
    if (bVerify) {
      verifyAPIPage();
    }
    return this;
  }

  public APIPage open() {
    return open(true);
  }

  public APIPage verifyAPIPage() {
    log.debug("verifyAPIPage()");
    waitForUrlChange(API_PAGE_URL, Time.TIME_SHORT);
    waitUntilPageIsReady(Time.TIME_SHORT);
    return this;
  }
}
