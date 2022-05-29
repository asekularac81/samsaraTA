package Pages;

import Data.PageUrlPaths;
import Data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class HomePage extends CommonLoggedInPage{

  //PAGE URL PATHS
  private final String HOME_PAGE_URL=getPageUrl(PageUrlPaths.HOME_PAGE);

  //LOKATORI
  private final By pageTitleLocator = By.xpath("//div[@class='my-jumbotron']/h1)");

  //KONSTRUKTOR
  public HomePage(WebDriver driver) {
    super(driver);
    log.trace("new HomePage()");
  }

  public HomePage open(boolean bVerify) {
    log.debug("Open HomePage(" + HOME_PAGE_URL + ")");
    openUrl(HOME_PAGE_URL);
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

  // PAGE TITLE - override-ujemo metode iz CommonLoggedInPage jer je na HomePage drugaciji lokator
  public boolean isPageTitleDisplayed() {
    log.debug("isPageTitleDisplayed()");
    return isWebElementDisplayed(pageTitleLocator);
  }

  public String getPageTitle() {
    log.debug("getPageTitle()");
    Assert.assertTrue(isPageTitleDisplayed(), "Page Title is NOT displayed!");
    WebElement pageTitle = getWebElement(pageTitleLocator);
    return getTextFromWebElement(pageTitle);
  }

}
