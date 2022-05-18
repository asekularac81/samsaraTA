package Pages;

import Data.PageUrlPaths;
import Data.Time;
import org.openqa.selenium.WebDriver;

public class HeroesPage extends CommonLoggedInPage {

  private final String HEROES_PAGE_URL=getPageUrl(PageUrlPaths.HEROES_PAGE);

  public HeroesPage(WebDriver driver) {
    super(driver);
    log.trace("new HeroesPage()");
  }

  public HeroesPage open(boolean bVerify) {
    openUrl(HEROES_PAGE_URL);
    log.trace("Open HeroesPage(" + HEROES_PAGE_URL + ")");
    if (bVerify) {
      verifyHeroesPage();
    }
    return this;
  }

  public HeroesPage open () {
    return open(true);
  }

  public HeroesPage verifyHeroesPage() {
    log.debug("verifyHeroesPage()");
    waitForUrlChange(HEROES_PAGE_URL, Time.TIME_SHORT);
    waitUntilPageIsReady(Time.TIME_SHORT);
    return this;
  }
}
