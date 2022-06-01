package Pages;

import Data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class AddUserDialogBox extends BasePage {

  // Pravimo posebnu klasu za Add User dialog box, necemo da elemente sa digalog boxa implementiramo unutar UsersPage
  // Razlozi:
  // 1. Add User dialog box se moze otvoriti i sa UsersPage i sa HeroPage
  // 2. Kad je otvoren vise NEMAMO interackciju sa elementima iz UsersPage niti sa TAB-ovima za navigaciju i zato Add sUser dialog box ne treba da nasledjuje metode iz CommonLoggedInPage, vec samo BasePage klasu

  //LOCATOR
  private final String addUserDialogBoxString = "//div[@id='addUserModal']";
  private final By addUserDialogBoxLocator = By.id("addUserModal");
  private final By cancelButtonLocator = By.xpath(addUserDialogBoxString + "//button[contains(@class,'btn-default')]");

  //KONSTRUKTOR
  public AddUserDialogBox(WebDriver driver) {
    super(driver);
    log.trace("new AddUserDialogBox()");
  }

  //--------------------------------------------------------------------------------------------------------------------------------------------

  // Privatne metode jel 'Add User' DialogBox visible/invisible - po jedna sa timeoutom i jedan sa defaultnim timeoutom
  // Potrebno nam je za verifyAddUserDialogBox
  // Osslanja se na BasePage metodu isWebElementVisible / isWebElementInvisible
  private boolean isAddUserDialogBoxOpened(int timeOut) {
    return isWebElementVisible(addUserDialogBoxLocator, timeOut);
  }

  private boolean isAddUserDialogBoxOpened() {
    return isAddUserDialogBoxOpened(Time.TIME_SHORT);
  }

  private boolean isAddUserDialogBoxClosed(int timeOut) {
    return isWebElementInvisible(addUserDialogBoxLocator, timeOut);
  }

  private boolean isAddUserDialogBoxClosed() {
    return isAddUserDialogBoxOpened(Time.TIME_SHORT);
  }

  //--------------------------------------------------------------------------------------------------------------------------------------------

  // ovo cemo da pozivamo u UserPage nakon sto kliknemo na Add New User dugme, da proverimo da je Opened
  public AddUserDialogBox verifyAddUserDialogBox() {
    log.debug("verifyAddUserDialogBox()");
    // necemo proveravati URL
    // mozemo da stavimo da se DOM struktura ucita, iako ostajemo na istoj stranici, da mu damo sansu ako nesto menja tokom fade in, fade out
    waitUntilPageIsReady(Time.TIME_SHORTER);

    // ne mozemo da proveravamo Presence jer ih ima vise odjednom samo nisu svi vidljivi (imaju fade u klasi, aktivni samo ima fade in)
    // moramo da proverimo visibiliti ali mu damo sansu da uradi fadein i fadeout
    // isWebElementDisplayed() - ova metoda nam ovde ne valja jer ako nema elementa odmah ce vrati false, samo u tom trenutku
    // moramo da napravimo metodu da li je webElementVisible i da li je webElementInvisible
    // pravimo metodu isAddUserDialogBoxOpen() koje ce da se oslanja na metodu isWebElementVisible() koju cemo da napravimo u BasePage klasi.
    // koristicu varijantu metode sa defaultnim timeoutom 5 sec

    Assert.assertTrue(isAddUserDialogBoxOpened(), "'Add User' DialogBox is NOT opened!");
    return this;
  }

  //--------------------------------------------------------------------------------------------------------------------------------------------

  //CANCEL BUTTON - isDisplayed, click
  public boolean isCancelButtonDisplayed() {
    log.debug("isCancelButtonDisplayed()");
    return  isWebElementDisplayed(cancelButtonLocator);
  }

  public UsersPage clickCancelButton() {
    log.debug("clickCancelButton()");
    Assert.assertTrue(isCancelButtonDisplayed(), "'Cancel' Button is NOT present on 'Add User' DialogBox!");
    WebElement cancelButton = getWebElement(cancelButtonLocator);
    clickOnWebElement(cancelButton);
    //sacekaj i verifikuj da je postao invisible
    Assert.assertTrue(isAddUserDialogBoxClosed(Time.TIME_SHORTER), "'Add User' DialogBox is NOT closed!");
    UsersPage usersPage = new UsersPage(driver);
    return usersPage.verifyUsersPage();
  }

  //--------------------------------------------------------------------------------------------------------------------------------------------


}
