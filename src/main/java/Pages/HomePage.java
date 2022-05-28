package Pages;

import Data.PageUrlPaths;
import Data.Time;
import org.openqa.selenium.WebDriver;

public class HomePage extends CommonLoggedInPage{

  private final String HOME_PAGE_URL=getPageUrl(PageUrlPaths.HOME_PAGE);

  public HomePage(WebDriver driver) {
    super(driver);
    log.trace("new HomePage()");
  }

  public HomePage open(boolean bVerify) {
    openUrl(HOME_PAGE_URL);
    log.trace("Open HomePage(" + HOME_PAGE_URL + ")");
    if (bVerify) {
      verifyHomePage();
    }
    return this;
  }

  public HomePage open () {
    return open(true);
  }

  public HomePage verifyHomePage() {
    log.debug("verifyHomePage()");
    waitForUrlChange(HOME_PAGE_URL, Time.TIME_SHORT);
    waitUntilPageIsReady(Time.TIME_SHORT);
    return this;
  }

}
