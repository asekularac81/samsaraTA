package Pages;

import Data.PageUrlPaths;
import Data.Time;
import org.openqa.selenium.WebDriver;

public class WelcomePage extends CommonLoggedInPage {

  private final String WELCOME_PAGE_URL = getPageUrl(PageUrlPaths.WELCOME_PAGE); // iz property fajla uzme baseURL i nakalemi relativnu putanju za tu stranicu

  public WelcomePage(WebDriver driver) {
    super(driver);
    log.trace("new WelcomePage()");
  }

  //Prvo sto cemo da naravimo je open() metoda i verifyWelcomePage() metoda
  public WelcomePage open(boolean bVerify) {
    openUrl(WELCOME_PAGE_URL);
    log.debug("Open  WelcomePage( "  + WELCOME_PAGE_URL + ")");
    if (bVerify) {
      verifyWelcomePage();
    }
    return this;
  }

  public WelcomePage open() {
    return open(true);
  }

  public WelcomePage verifyWelcomePage() {
    log.debug("verifyWelcomePage()");
    waitForUrlChangeToExactUrl(WELCOME_PAGE_URL, Time.TIME_SHORT); //ovde idemo sa exact url, ostale su sa contains- waitForUrlChange(WELCOME_PAGE_URL, Time.TIME_SHORT);
    waitUntilPageIsReady(Time.TIME_SHORT); //cekamo da se ucita DOM struktura stranice
    return this;
  }

}
