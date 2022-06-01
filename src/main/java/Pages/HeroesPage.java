package Pages;

import Data.PageUrlPaths;
import Data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class HeroesPage extends CommonLoggedInPage {

  private final String HEROES_PAGE_URL=getPageUrl(PageUrlPaths.HEROES_PAGE);

  //LOCATORS
  private final By addNewHeroButtonLocator = By.xpath("//a[contains(@class,'btn-info') and contains(@onclick,'openAddHeroModal()')]");

  // KONSTRUKTOR
  public HeroesPage(WebDriver driver) {
    super(driver);
    log.trace("new HeroesPage()");
  }

  public HeroesPage open(boolean bVerify) {
    log.debug("Open HeroesPage(" + HEROES_PAGE_URL + ")");
    openUrl(HEROES_PAGE_URL);
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

  //-----------------------------------------------------------------------------------------------

  // ADD NEW HERO BUTTON -isDisplayed, click, getTitle
  public boolean isAddNewHeroButtonDisplayed() {
    log.debug("isAddNewHeroButtonDisplayed()");
    return isWebElementDisplayed(addNewHeroButtonLocator);
  }

  // Klikemo na Add new Hero button, proverimo da je modal vidljiv i vratimo instancu modala
  public AddHeroDialogBox clickAddNewHeroButton() {
    log.debug("clickAddNewHeroButton()");
    Assert.assertTrue(isAddNewHeroButtonDisplayed(), "'Add New Hero' Button is NOT displayed on Heroes Page!");
    WebElement addNewHeroButtton = getWebElement(addNewHeroButtonLocator);
    clickOnWebElement(addNewHeroButtton);
    //verifikujemo da se otvorio AddHeroDialogBox , poziva se ispod isAddHeroDialogBoxOpened() --isWebElementVisible
    AddHeroDialogBox addHeroDialogBox=new AddHeroDialogBox(driver);
    return addHeroDialogBox.verifyAddHeroDialogBox();

  }

  public String getAddNewHeroButtonTitle() {
    log.debug("getAddNewHeroButtonTitle");
    Assert.assertTrue(isAddNewHeroButtonDisplayed(),"'Add New Hero' Button is NOT displayed on Users Page!");
    WebElement addNewUserButton = getWebElement(addNewHeroButtonLocator);
    return getTextFromWebElement(addNewUserButton);
  }

  //-----------------------------------------------------------------------------------------------

}
