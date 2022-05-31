package Pages;

import Data.PageUrlPaths;
import Data.Time;
import org.openqa.selenium.WebDriver;

public class PracticePage extends CommonLoggedInPage{

  private final String PRACTICE_PAGE_URL= getPageUrl(PageUrlPaths.PRACTICE_PAGE);

  // KONSTRUKTOR
  public PracticePage(WebDriver driver) {
    super(driver);
    log.trace("new PracticePage()");
  }

  public PracticePage open(boolean bVerify) {
    log.debug("Open PracticePage(" + PRACTICE_PAGE_URL + ")");
    openUrl(PRACTICE_PAGE_URL);
    if (bVerify){
      verifyPracticePage();
    }
    return this;
  }

  public PracticePage open() {
    return open(true);
  }

  public PracticePage verifyPracticePage() {
    log.debug("verifyPracticePage()");
    waitForUrlChangeToExactUrl(PRACTICE_PAGE_URL, Time.TIME_SHORT);
    waitUntilPageIsReady(Time.TIME_SHORT);
    return this;
  }

}
