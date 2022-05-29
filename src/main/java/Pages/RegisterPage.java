package Pages;

import Data.PageUrlPaths;
import Data.Time;
import org.openqa.selenium.WebDriver;

public class RegisterPage extends CommonLoggedOutPage{

  // PAGE URL PATHS
  private final String REGISTER_PAGE_URL = getPageUrl(PageUrlPaths.REGISTER_PAGE);

  // KONSTRUKTOR
  public RegisterPage(WebDriver driver) {
    super(driver);
    log.trace("RegisterPage()");
  }

  public RegisterPage open(boolean bVerify) {
    log.debug("Open RegisterPage(" + REGISTER_PAGE_URL + ")");
    openUrl(REGISTER_PAGE_URL);
    if (bVerify) {
      verifyRegisterPage();
    }
    return this;
  }

  public RegisterPage open () {
    return open(true);
  }

  public RegisterPage verifyRegisterPage() {
    log.debug("verifyRegisterPage()");
    waitForUrlChange(REGISTER_PAGE_URL, Time.TIME_SHORTER);
    waitUntilPageIsReady(Time.TIME_SHORT);
    return this;
  }
}
