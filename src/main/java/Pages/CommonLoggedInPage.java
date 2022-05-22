package Pages;

import Data.PageUrlPaths;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class CommonLoggedInPage extends BasePage {

  //LOKATORI:

  private final String headerLocatorString = "//header[@id='headContainer']"; //izvojimo xpath do zajednickog parent elementa - headera na stranici
  private final String headerLocatorStringCSS = "header#headContainer"; //ovo isto ali sa css ( sa # se hvata id)

  //private final By usersTabLocator7 = By.xpath("//header[@id='headContainer']//a[@href='/users']");
  //private final By usersTabLocator8= By.xpath(headerLocatorString + "//a[@href='/users']"); //necemo nista da hardcodujemo vec:
  private final By usersTabLocator = By.xpath(headerLocatorString + "//a[@href='" + PageUrlPaths.USERS_PAGE + "']");
  private final By homeTabLocator = By.xpath(headerLocatorString + "//a[@href='" + PageUrlPaths.HOME_PAGE + "']");
  private final By heroesTabLocator = By.xpath(headerLocatorString + "//a[@href='" + PageUrlPaths.HEROES_PAGE + "']");
  private final By galleryTabLocator = By.xpath(headerLocatorString + "//a[@href='" + PageUrlPaths.GALLERY_PAGE + "']");
  private final By apiTabLocator = By.xpath(headerLocatorString + "//a[@href='" + PageUrlPaths.API_PAGE + "']");
  private final By practiceTabLocator = By.xpath(headerLocatorString + "//a[@href='" + PageUrlPaths.PRACTICE_PAGE + "']");
  private final By brokenLinkTabLocator = By.xpath(headerLocatorString + "//a[@href='" + PageUrlPaths.BROKEN_LINK_PAGE + "']");
  private final By adminTabLocator = By.xpath(headerLocatorString + "//a[@href='" + PageUrlPaths.ADMIN_PAGE + "']");

  private final By logoutLinkLocator = By.xpath(headerLocatorString + "//a[contains(@href,'logoutForm.submit()')]");


  public CommonLoggedInPage(WebDriver driver) {
    super(driver);
  }

  //metoda da kliknemo na Users tab
  public UsersPage clickUsersTab () {
    log.debug("clickUsersTab()");
    WebElement usersTab = getWebElement(usersTabLocator);
    clickOnWebElement(usersTab);
    UsersPage usersPage = new UsersPage(driver);
    return usersPage.verifyUsersPage();
  }

  public HeroesPage clickHeroesTab() {
    log.debug("clickHeroesTab()");
    WebElement heroesTab = getWebElement(heroesTabLocator);
    clickOnWebElement(heroesTab);
    HeroesPage heroesPage = new HeroesPage(driver);
    return heroesPage.verifyHeroesPage();
  }

  public GalleryPage clickGalleryTab() {
    log.debug("clickGalleryTab()");
    WebElement galleryTab = getWebElement(galleryTabLocator);
    clickOnWebElement(galleryTab);
    GalleryPage galleryPage = new GalleryPage(driver);
    return galleryPage.verifyGalleryPage();
  }

  public APIPage clickAPITab() {
    log.debug("clickAPITab()");
    WebElement APITab = getWebElement(apiTabLocator);
    clickOnWebElement(APITab);
    APIPage apiPage = new APIPage(driver);
    return apiPage.verifyAPIPage();
  }

  public PracticePage clickPracticeTab() {
    log.debug("clickPracticeTab()");
    WebElement practiceTab = getWebElement(practiceTabLocator);
    clickOnWebElement(practiceTab);
    PracticePage practicePage = new PracticePage(driver);
    return practicePage.verifyPracticePage();
  }

  public BrokenLinkPage clickBrokenLinkTab() {
    log.debug("clickBrokenLinkTab()");
    WebElement brokenLinkTab = getWebElement(brokenLinkTabLocator);
    clickOnWebElement(brokenLinkTab);
    BrokenLinkPage brokenLinkPage = new BrokenLinkPage(driver);
    return brokenLinkPage.verifyBrokenLinkPage();
  }

  public AdminPage clickAdminTab() {
    log.debug("clickAdminTab()");
    WebElement adminTab = getWebElement(adminTabLocator);
    clickOnWebElement(adminTab);
    AdminPage adminPage = new AdminPage(driver);
    return adminPage.verifyAdminPage();
  }

  public LoginPage clickLogoutLink() {
    log.debug("clickLogoutLink()");
    WebElement logoutLink = getWebElement(logoutLinkLocator);
    clickOnWebElement(logoutLink);
    LoginPage loginPage = new LoginPage(driver);
    return loginPage.verifyLoginPage();
  }
}
