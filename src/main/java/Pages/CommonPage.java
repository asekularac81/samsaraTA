package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

// CommonPage - Parent klasa koja sadrzi ono zajedinicko za SVE STRANICE za vezano je za nasu APPLIKACIJU, cilj da ne dupliramo code
// Nivo iznad je - BasePage - sadrzi wrapere osnovnih Selenium metoda, nezavisna od aplikacije i moze da se koristi i za drugi projekat
// Za sada nam je zajednicko za CommonLoggedInPage i CommonLoggedOutPage: PageTitle locator i metode za pageTitle

public class CommonPage extends BasePage{

  private final By pageTitleLocator = By.cssSelector(("div.panel-title"));
  //private final By pageTitleLocator = By.xpath(("//div[@class='panel-heading']//div[contains(@class,'panel-title')]"));

  // KONSTRUKTOR
  public CommonPage(WebDriver driver) {
    super(driver);
  }

  // PAGE TITLE - iDisplayed, getTitle
  // Page title je isti element na svim ulogovanim stranicama OSIM HomePage gde izgleda drugacije tj ima drugi lokator
  // Mi cemo napraviti zajednicke metode za title u CommonLoggedInPage
  // A zatim cemo da ih overridujemo samo u HomePage klasi
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
