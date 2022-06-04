package Pages;

import java.util.List;

import Data.PageUrlPaths;
import Data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class UsersPage extends CommonLoggedInPage {

  //PAGE URL PATH
  private final String USERS_PAGE_URL = getPageUrl(PageUrlPaths.USERS_PAGE);

  //LOCATORS
  private final By addNewUserButtonLocator = By.xpath("//a[contains(@class,'btn-info') and contains(@onclick,'openAddUserModal()')]");

  private final By usersTableLocator = By.id(("users-table")); //parent tabele

  //https://www.w3schools.com/xml/xpath_intro.asp
  //https://www.w3schools.com/xml/xpath_axes.asp - locarnje u odnosu na node - preci, potomci, siblings

  //table[@id='users-table']//td    - prolazi kroz sva polja
  //table[@id='users-table']//td[1] - prolazi kroz sva polja u prvoj koloni
  //table[@id='users-table']//td[1]/self::td[text()='dedoje'] - najdes 1.kolonu, referenciras na sebe i trazis da je text=dedoje

  //primer kad pored samog sadrzaja tabela ima i parametre (name, type, level)
  //div[@id='heroesModal']//table[@class='table'] - lociranje tabele User Heroes na Users Page
  //div[@id='heroesModal']//table[@class='table']/tbody//td[@class='name'] - prolazi kroz sva polja u koloni 'Name'
  //div[@id='heroesModal']//table[@class='table']/tbody//td[@class='name' and text()='Tigraine'] - nalazi polje u koloni 'Name' sa sadrzajem 'Tigraine'

  // KONSTRUKTOR
  public UsersPage(WebDriver driver) {
    super(driver);
    log.trace("new UsersPage()");
  }

  public UsersPage open(boolean bVerify) {
    log.debug("Open UsersPage(" + USERS_PAGE_URL + ")");
    openUrl(USERS_PAGE_URL);
    if (bVerify) {
      verifyUsersPage();
    }
    return this;
  }

  public UsersPage open() {
    return open(true);
  }

  public UsersPage verifyUsersPage() {
    log.debug("verifyUsersPage");
    waitForUrlChange(USERS_PAGE_URL, Time.TIME_SHORT);
    waitUntilPageIsReady(Time.TIME_SHORT);
    return this;
  }

  //-----------------------------------------------------------------------------------------------

  // ADD NEW USER BUTTON - isDisplayed, click, getTitle
  public boolean isAddNewUserButtonDisplayed() {
    log.debug("isAddNewUserButtonDisplayed");
    return isWebElementDisplayed(addNewUserButtonLocator);
  }

  // Klikemo na Add new User button, proverimo da je modal vidljiv i vratimo instancu modala
  public AddUserDialogBox clickAddNewUserButton() {
    log.debug("clickAddNewUserButton");
    Assert.assertTrue(isAddNewUserButtonDisplayed(),"'Add New User' Button is NOT displayed on Users Page!");
    WebElement addNewUserButton = getWebElement(addNewUserButtonLocator);
    clickOnWebElement(addNewUserButton);
    //verifikujemo da se otvorio AddUserDialogBox , poziva se ispod isAddUserDialogBoxOpened() --isWebElementVisible
    AddUserDialogBox addUserDialogBox = new AddUserDialogBox(driver);
    return addUserDialogBox.verifyAddUserDialogBox();
  }

  public String getAddNewUserButtonTitle() {
    log.debug("getAddNewUserButtonTitle");
    Assert.assertTrue(isAddNewUserButtonDisplayed(),"'Add New User' Button is NOT displayed on Users Page!");
    WebElement addNewUserButton = getWebElement(addNewUserButtonLocator);
    return getTextFromWebElement(addNewUserButton);
  }

  //-----------------------------------------------------------------------------------------------

  // TABLE
  // Treba nam da svaki red u tabeli ima neku jedinstvenu informaciju kako bi ga locirali
  // Ako to nije slucaj, mi sa test data cemo to naciti jedinstvenim

  // Progremerska metoda sa 2 liste
  public String getDisplayNameByFullName(String sUsername) {
    String sResult = null;
    List<WebElement> webElementsUsernames = driver.findElements(By.xpath("//table[@id='users-table']//td[1]"));
    for (int i = 0; i < webElementsUsernames.size(); i++) {
      if (webElementsUsernames.get(i).getText().equals(sUsername)) {
        List<WebElement> webElementsDisplayNames = driver.findElements(By.xpath("//table[@id='users-table']//td[2]"));
        sResult = webElementsDisplayNames.get(i).getText();
        break;
      }
    }
    return sResult;
  }

  // Ali mi necemo tako vec cemo napraviti kompleksan parametrizovani lokator
  // Private metoda koja pravi dinamicki xPath
  //table[@id='users-table']//td[1]/self::td[text()='dedoje'] - ovo je xpath koji treba da napravimo - najdes 1.kolonu, referenciras na sebe i trazis da je text=dedoje

  private String createXpathForUsernameInUsersTable(String sUsername) {
    return "//table[@id='users-table']//td[1]/self::td[text()='" + sUsername + "']";
  }

/*  public boolean isUserPresentInUsersTable(String sUsername) {
    log.debug("isUserPresentInUsersTable(" + sUsername + ")");
    WebElement usersTable = getWebElement(usersTableLocator);
    String xPath = createXpathForUsernameInUsersTable(sUsername);
    usersTable.findElement(By.xpath(xPath));
  }*/
}



