package Pages;

import Data.PageUrlPaths;
import Data.Time;
import org.openqa.selenium.WebDriver;

public class BrokenLinkPage extends CommonLoggedInPage {

  private final String BROKEN_LINK_PAGE=getPageUrl(PageUrlPaths.BROKEN_LINK_PAGE);

  // KONSTRUKTOR
  public BrokenLinkPage(WebDriver driver) {
    super(driver);
    log.trace("new BrokenLinkPage()");
  }

  public BrokenLinkPage open(boolean bVerify) {
    log.debug("Open BrokenLinkPage(" + BROKEN_LINK_PAGE + ")");
    openUrl(BROKEN_LINK_PAGE);
    if (bVerify) {
      verifyBrokenLinkPage();
    }
    return this;
  }

  public BrokenLinkPage open() {
    return open(true);
  }

  public BrokenLinkPage verifyBrokenLinkPage() {
    log.debug("verifyBrokenLinkPage()");
    waitForUrlChange(BROKEN_LINK_PAGE, Time.TIME_SHORT);
    waitUntilPageIsReady(Time.TIME_SHORT);
    return this;
  }
}

