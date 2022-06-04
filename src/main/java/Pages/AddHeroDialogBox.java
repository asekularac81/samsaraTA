package Pages;

import Data.Time;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class AddHeroDialogBox extends BasePage{

  // Pravimo posebnu klasu za Add Hero DigalogBox, necemo da elemente sa DigalogBoxa implementiramo unutar HeroesPage
  // Razlozi:
  // 1. Kad je otvoren vise NEMAMO interackciju sa elementima iz UsersPage niti sa TAB-ovima za navigaciju i zato AddHero DigalogBox ne treba da nasledjuje metode iz CommonLoggedInPage, vec samo BasePage klasu

  // PAGE FACTORY LOCATORS
  @FindBy (id="addHeroModal")
  private WebElement addHeroDialogBox;

  public final String addHeroDialogBoxString = "//div[@id='addHeroModal']";
  @FindBy (xpath=addHeroDialogBoxString + "//button[contains(@class,'btn-default')]")
  private WebElement cancelButton;

  //ako ne koristimo Page Factory:
  //public final String addHeroDialogBoxString = "//div[@id='addHeroModal']";
  //private final By addHeroDialogBoxLocator = By.id("addHeroModal");
  //private final By cancelButtonLocator = By.xpath(addHeroDialogBoxString + "//button[contains(@class,'btn-default')]");

  //KONSTRUKTOR
  public AddHeroDialogBox(WebDriver driver) {
    super(driver);
    log.trace("new AddHeroDialogBox()");
  }

  //--------------------------------------------------------------------------------------------------------------------------------------------

  // Privatne metode da li je 'Add Hero' DialogBox visible/invisible - po jedna sa timeoutom i jedna sa defaultnim timeoutom (5sec)
  // Potrebno nam je za verifyAddHeroDialogBox
  // Oslanja se na BasePage metodu isWebElementVisible / isWebElementInvisible

  private boolean isAddHeroDialogBoxOpened(int timeOut) {
    return isWebElementVisible(addHeroDialogBox, timeOut);
  }

  private boolean isAddHeroDialogBoxOpened() {
    return isAddHeroDialogBoxOpened(Time.TIME_SHORT);
  }

  private boolean isAddHeroDialogBoxClosed(int timeOut) {
    return isWebElementInvisible(addHeroDialogBox, timeOut);
  }

  private boolean isAddHeroDialogBoxClosed() {
    return isAddHeroDialogBoxClosed(Time.TIME_SHORT);
  }

  //--------------------------------------------------------------------------------------------------------------------------------------------

  // ovo cemo da pozivamo u HeroesPage nakon sto kliknemo na Add New Hero dugme, da proverimo da je Opened
  public AddHeroDialogBox verifyAddHeroDialogBox() {
    log.debug("verifyAddHeroDialogBox()");
    // necemo proveravati URL
    // mozemo da stavimo da se DOM struktura ucita, iako ostajemo na istoj stranici, da mu damo sansu ako nesto menja tokom fade in, fade out
    waitUntilPageIsReady(Time.TIME_SHORTER);

    // ne mozemo da proveravamo Presence jer ih ima vise odjednom samo nisu svi vidljivi (imaju fade u klasi, aktivni samo ima fade in)
    // moramo da proverimo visibiliti ali mu damo sansu da uradi fadein i fadeout
    // isWebElementDisplayed() - ova metoda nam ovde ne valja jer ako nema elementa odmah ce vrati false, samo u tom trenutku
    // moramo da napravimo metodu da li je webElementVisible i da li je webElementInvisible
    // pravimo metodu isAddHeroDialogBoxOpen() koje ce da se oslanja na metodu isWebElementVisible() koju cemo da napravimo u BasePage klasi.
    // koristicu varijantu metode sa defaultnim timeoutom 5 sec

    Assert.assertTrue(isAddHeroDialogBoxOpened(), "'Add Hero' DialogBox is NOT opened!");
    return this;
  }

  //--------------------------------------------------------------------------------------------------------------------------------------------

  //CANCEL BUTTON - isDisplayed, click
  public boolean isCancelButtonDisplayed() {
    log.debug("isCancelButtonDisplayed()");
    return  isWebElementDisplayed(cancelButton);
  }

  public HeroesPage clickCancelButton() {
    log.debug("clickCancelButton()");
    Assert.assertTrue(isCancelButtonDisplayed(), "'Cancel' Button is NOT present on 'Add Hero' DialogBox!");
    clickOnWebElement(cancelButton);
    //sacekaj i verifikuj da je postao invisible
    Assert.assertTrue(isAddHeroDialogBoxClosed(Time.TIME_SHORTER), "'Add Hero' DialogBox is NOT closed!");
    HeroesPage heroesPage = new HeroesPage(driver);
    return heroesPage.verifyHeroesPage();
  }

  //--------------------------------------------------------------------------------------------------------------------------------------------

}
