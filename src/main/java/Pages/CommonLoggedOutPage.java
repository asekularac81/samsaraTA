package Pages;

import Data.PageUrlPaths;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public abstract class CommonLoggedOutPage extends CommonPage{

  //KONSTRUKTOR
  public CommonLoggedOutPage(WebDriver driver) {
    super(driver);
  }

  private final String headerLocatorString = "//header[@id='headContainer']"; //izvojimo xpath do zajednickog parent elementa - headera na stranici
  private final By samsaraLogoLocator =  By.cssSelector(headerLocatorString + " a.navbar-brand");
  //private final By samsaraLogoLocator =  By.xpath(headerLocatorString + "//a[@class='navbar-brand']"); ovo isto samo preko xpatha
  private final By logInLinkLocator =  By.cssSelector(headerLocatorString + "  [href='" + PageUrlPaths.LOGIN_PAGE + "']");
  //private final By logInLinkLocator =  By.xpath(headerLocatorString + "//a[@href='" + PageUrlPaths.LOGIN_PAGE + "']"); ovo isto samo preko xpatha

  //SAMSARA LOGO- vodi open na LOGIN PAGE - isDisplayed, click
  public boolean isSamsaraLogoDisplayed() {
    log.debug("isSamsaraLogoDisplayed");
    return isWebElementDisplayed(samsaraLogoLocator);
  }

  public LoginPage clickSamsaraLogo() {
    log.debug("clickSamsaraLogo()");
    Assert.assertTrue(isSamsaraLogoDisplayed(), "Samsara Logo is NOT displayed on Navigation Bar!");
    WebElement samsaraLogo = getWebElement(samsaraLogoLocator);
    clickOnWebElement(samsaraLogo);
    LoginPage loginPage = new LoginPage(driver);
    return loginPage.verifyLoginPage();
  }

  //LOGIN LINK - isDisplayed. click, getTitle
  public  boolean isLogInLinkDisplayed() {
    log.debug("isLogInLinkDisplayed");
    return isWebElementDisplayed(logInLinkLocator);
  }

  public LoginPage clickLogInLink() {
    log.debug("clickLogInLink");
    Assert.assertTrue(isLogInLinkDisplayed(), "LogIn Link is NOT displayed on Navigation Bar!");
    WebElement logInLink = getWebElement(logInLinkLocator);
    clickOnWebElement(logInLink);
    LoginPage loginPage = new LoginPage(driver);
    return loginPage.verifyLoginPage();
  }

  public String getLoginLinkTitle() {
    log.debug("getLoginLinkTitle");
    Assert.assertTrue(isLogInLinkDisplayed(), "LogIn Link is NOT displayed on Navigation Bar!");
    WebElement logInLink = getWebElement(logInLinkLocator);
    return getTextFromWebElement(logInLink);
  }

}
