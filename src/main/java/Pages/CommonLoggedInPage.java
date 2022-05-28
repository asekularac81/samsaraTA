package Pages;

import Data.PageUrlPaths;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public abstract class CommonLoggedInPage extends BasePage {

  //LOKATORI:

  private final String headerLocatorString = "//header[@id='headContainer']"; //izvojimo xpath do zajednickog parent elementa - headera na stranici
  private final String headerLocatorStringCSS = "header#headContainer"; //ovo isto ali sa css ( sa # se hvata id)

  //private final By usersTabLocator7 = By.xpath("//header[@id='headContainer']//a[@href='/users']");
  //private final By usersTabLocator8= By.xpath(headerLocatorString + "//a[@href='/users']"); //necemo nista da hardcodujemo vec:
  private final By homeTabLocator = By.xpath(headerLocatorString + "//a[@href='" + PageUrlPaths.HOME_PAGE + "']");
  private final By usersTabLocator = By.xpath(headerLocatorString + "//a[@href='" + PageUrlPaths.USERS_PAGE + "']");
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

  //HOME TAB
  public boolean isHomeTabDisplayed() {
    log.debug("isHomeTabDisplayed()");
    return isWebElementDisplayed(homeTabLocator);
  }

  public HomePage clickHomeTab() {
    log.debug("clickHomeTab()");
    Assert.assertTrue(isHomeTabDisplayed(), "Home Tab is NOT displayed on Navigation Bar!");
    WebElement homeTab = getWebElement(homeTabLocator);
    clickOnWebElement(homeTab);
    HomePage homePage = new HomePage(driver);
    return homePage.verifyHomePage();
  }

  public String getHomeTabTitle() {
    log.debug("getHomeTabTitle()");
    WebElement homeTab = getWebElement(homeTabLocator);
    return getTextFromWebElement(homeTab);
  }

  //USERS TAB
  public boolean isUsersTabDisplayed() {
    log.debug("isUsersTabDisplayed()");
    return isWebElementDisplayed(usersTabLocator);
  }

  public UsersPage clickUsersTab () {
    log.debug("clickUsersTab()");
    Assert.assertTrue(isUsersTabDisplayed(),"Users Tab is NOT displayed on Navigation Bar!");
    WebElement usersTab = getWebElement(usersTabLocator);
    clickOnWebElement(usersTab);
    UsersPage usersPage = new UsersPage(driver);
    return usersPage.verifyUsersPage();
  }

  public String getUsersTabTitle() {
    log.debug("getUsersTabTitle()");
    WebElement usersTab = getWebElement(usersTabLocator);
    return getTextFromWebElement(usersTab);
  }

  //HEROES TAB
  public boolean isHeroesTabDisplayed() {
    log.debug("isHeroesTabDisplayed()");
    return isWebElementDisplayed(heroesTabLocator);
  }

  public HeroesPage clickHeroesTab() {
    log.debug("clickHeroesTab()");
    Assert.assertTrue(isHeroesTabDisplayed(),"Heroes Tab is NOT displayed on Navigation Bar!");
    WebElement heroesTab = getWebElement(heroesTabLocator);
    clickOnWebElement(heroesTab);
    HeroesPage heroesPage = new HeroesPage(driver);
    return heroesPage.verifyHeroesPage();
  }

  public String getHeroesTabTitle() {
    log.debug("getHeroesTabTitle()");
    WebElement heroesPage = getWebElement(heroesTabLocator);
    return getTextFromWebElement(heroesPage);
  }

  //GALLERY TAB
  public boolean isGalleryTabDisplayed() {
    log.debug("isGalleryTabDisplayed()");
    return isWebElementDisplayed(galleryTabLocator);
  }

  public GalleryPage clickGalleryTab() {
    log.debug("clickGalleryTab()");
    Assert.assertTrue(isGalleryTabDisplayed(),"Gallery Tab is NOT displayed on Navigation Bar!");
    WebElement galleryTab = getWebElement(galleryTabLocator);
    clickOnWebElement(galleryTab);
    GalleryPage galleryPage = new GalleryPage(driver);
    return galleryPage.verifyGalleryPage();
  }

  public String getGalleryTabTitle() {
    log.debug("getGalleryTabTitle()");
    WebElement galleryTab = getWebElement(galleryTabLocator);
    return getTextFromWebElement(galleryTab);
  }

  //API TAB
  public boolean isAPITabDisplayed() {
    log.debug("isAPITabDisplayed()");
    return isWebElementDisplayed(apiTabLocator);
  }

  public APIPage clickAPITab() {
    log.debug("clickAPITab()");
    Assert.assertTrue(isAPITabDisplayed(),"API Tab is NOT displayed on Navigation Bar!");
    WebElement APITab = getWebElement(apiTabLocator);
    clickOnWebElement(APITab);
    APIPage apiPage = new APIPage(driver);
    return apiPage.verifyAPIPage();
  }

  public String getAPITabTitle() {
    log.debug("getAPITabTitle()");
    WebElement APITab = getWebElement(apiTabLocator);
    return getTextFromWebElement(APITab);
  }

  //PRACTICE TAB
  public boolean isPracticeTabDisplayed() {
    log.debug("isPracticeTabDisplayed()");
    return isWebElementDisplayed(practiceTabLocator);
  }

  public PracticePage clickPracticeTab() {
    log.debug("clickPracticeTab()");
    Assert.assertTrue(isPracticeTabDisplayed(),"Practice Tab is NOT displayed on Navigation Bar!");
    WebElement practiceTab = getWebElement(practiceTabLocator);
    clickOnWebElement(practiceTab);
    PracticePage practicePage = new PracticePage(driver);
    return practicePage.verifyPracticePage();
  }

  public String getPracticeTabTitle() {
    log.debug("getPracticeTabTitle()");
    WebElement practiceTab = getWebElement(practiceTabLocator);
    return getTextFromWebElement(practiceTab);
  }

  //BROKEN LINK TAB
  public boolean isBrokenLinkTabDisplayed() {
    log.debug("isBrokenLinkTabDisplayed()");
    return isWebElementDisplayed(brokenLinkTabLocator);
  }

  public BrokenLinkPage clickBrokenLinkTab() {
    log.debug("clickBrokenLinkTab()");
    Assert.assertTrue(isBrokenLinkTabDisplayed(),"Broken Link Tab is NOT displayed on Navigation Bar!");
    WebElement brokenLinkTab = getWebElement(brokenLinkTabLocator);
    clickOnWebElement(brokenLinkTab);
    BrokenLinkPage brokenLinkPage = new BrokenLinkPage(driver);
    return brokenLinkPage.verifyBrokenLinkPage();
  }

  public String getBrokenLinkTabTitle() {
    log.debug("getBrokenLinkTabTitle()");
    WebElement brokenLinkTab = getWebElement(brokenLinkTabLocator);
    return getTextFromWebElement(brokenLinkTab);
  }

  //ADMIN TAB
  public boolean isAdminTabDisplayed() {
    log.debug("isAdminTabDisplayed()");
    return isWebElementDisplayed(adminTabLocator);
  }
  public AdminPage clickAdminTab() {
    log.debug("clickAdminTab()");
    Assert.assertTrue(isAdminTabDisplayed(),"Admin Tab is NOT displayed on Navigation Bar!");
    WebElement adminTab = getWebElement(adminTabLocator);
    clickOnWebElement(adminTab);
    AdminPage adminPage = new AdminPage(driver);
    return adminPage.verifyAdminPage();
  }

  public String getAdminTabTitle() {
    log.debug("getAdminTabTitle()");
    WebElement adminTab = getWebElement(adminTabLocator);
    return getTextFromWebElement(adminTab);
  }



  public LoginPage clickLogoutLink() {
    log.debug("clickLogoutLink()");
    WebElement logoutLink = getWebElement(logoutLinkLocator);
    clickOnWebElement(logoutLink);
    LoginPage loginPage = new LoginPage(driver);
    return loginPage.verifyLoginPage();
  }
}
