package Pages;

import Data.PageUrlPaths;
import Data.Time;
import org.openqa.selenium.WebDriver;

public class BrokenPage extends CommonLoggedInPage {

  private final String BROKEN_LINK_PAGE=getPageUrl(PageUrlPaths.BROKEN_LINK_PAGE);

  public BrokenPage (WebDriver driver) {
    super(driver);
    log.trace("new BrokenPage()");
  }

  public BrokenPage open(boolean bVerify) {
    openUrl(BROKEN_LINK_PAGE);
    log.debug("Open BrokenPage(" + BROKEN_LINK_PAGE + ")");
    if (bVerify) {
      verifyBrokenPage();
    }
    return this;
  }

  public BrokenPage open() {
    return open(true);
  }

  public BrokenPage verifyBrokenPage() {
    log.debug("verifyBrokenPage()");
    waitForUrlChange(BROKEN_LINK_PAGE, Time.TIME_SHORT);
    waitUntilPageIsReady(Time.TIME_SHORT);
    return this;
  }
}

