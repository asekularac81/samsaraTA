package Pages;

import Data.PageUrlPaths;
import Data.Time;
import org.openqa.selenium.WebDriver;

public class LoginPage extends CommonLoggedOutPage {

  private final String LOGIN_PAGE_URL = getPageUrl(PageUrlPaths.LOGIN_PAGE);
  //private final String LOGIN_PAGE_URL = "https://www.google.com/";

  public LoginPage (WebDriver driver) {
    super(driver);
    log.trace("new LoginPage()");
  }

  public LoginPage open(boolean bVerify) {
    log.debug("Open LoginPage(" + LOGIN_PAGE_URL+ ")");
    driver.get(LOGIN_PAGE_URL);
    if(bVerify) {
      verifyLoginPage();
    }
    return this;
  }

  public LoginPage open() {
    log.debug("Open LoginPage(" + LOGIN_PAGE_URL+ ")");
    return open(true);
  }

  public void verifyLoginPage() {
    log.debug("verifyLoginPage()");
    waitForUrlChange(LOGIN_PAGE_URL, Time.TIME_SHORT);
    waitUntilPageIsReady(Time.TIME_SHORT);
  }
}
