package Pages;

import Data.PageUrlPaths;
import Data.Time;
import org.openqa.selenium.WebDriver;

public class ResetPasswordPage extends CommonLoggedOutPage {

  // PAGE URL PATHS
  private final String RESET_PASSWORD_URL = getPageUrl(PageUrlPaths.FORGOT_PASSWORD_PAGE);

  // KONSTRUKTOR
  public ResetPasswordPage(WebDriver driver) {
    super(driver);
    log.trace("ResetPasswordPage()");
  }

  public ResetPasswordPage open(boolean bVerify) {
    log.debug("Open RegisterPage(" + RESET_PASSWORD_URL + ")");
    openUrl(RESET_PASSWORD_URL);
    if (bVerify) {
      verifyResetPasswordPage();
    }
    return this;
  }

  public ResetPasswordPage open () {
    return open(true);
  }

  public ResetPasswordPage verifyResetPasswordPage() {
    log.debug("verifyResetPasswordPage()");
    waitForUrlChange(RESET_PASSWORD_URL, Time.TIME_SHORTER);
    waitUntilPageIsReady(Time.TIME_SHORT);
    return this;
  }
}
