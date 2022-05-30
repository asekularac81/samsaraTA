package Pages;

import Data.PageUrlPaths;
import Data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class LoginPage extends CommonLoggedOutPage {

  // PAGE URL PATHS
  private final String LOGIN_PAGE_URL = getPageUrl(PageUrlPaths.LOGIN_PAGE);

  // WEB ELEMENTI/LOKATORI:
  // ako NE koristimo PageFactory - definisemo LOKATORE za WebElemente, a svaka metoda mora da dohvati WebElement u trenutku kad oce da ga koristi, nikako pre jer se desava StaleElemet Reference greska
  // ako koristimo PageFactory - onda su to WEB ELEMENTI
  // PRIVATE - da ne moze iz druge stranice/testa da se pristupa webelementu/lokatoru. Moci ce da im se pristupa samo preko metoda. Zasto:
  // odrzavanje - ako se promeni lokator moras da menjas na vise mesta umesto da u Page napravis metode koje rade sa elemetom i ako se promeni menjas ga na 1 mestu
  // da test bude napisan tako da ne zavisi od alata koji cemo mozda zeleti da menjamo
  // FINAL - da niko ne moze da ih dalje menja

  // PRIMER SA LOKATORIMA (NE koristimo PageFactory)
  // Ovo su lokatori a webelemente cemo dohvatiti u samoj metodi - neposredno pred koristenje
  // Naming konvencija:
  // Sta radi element/ Tip elementa / Locator (ako je WebElement onda nista)
  private final By usernameTextFieldLocator = By.id("username");
  private final By passwordTextFieldLocator = By.id("password");

  // private final WebElement usernameTextFiel = driver.findElement(usernameTextFieldLocator) - BAD PRACTICE!
  // jer ce prilikom inicijalizacije stranice dohvatiti element i ako se izmeni DOM struktura stranice (kad se refreshuje, cekiras checkbox...), dobicemo StaleElemen, ReferencedElement is not attached to DOM gresku...

  // posto classname nije uvek jedinstven bolje je da se nadje jedinstven element iznad i onda unutar njega dalje trazimo
  // zbog odrzavanja i ubrzavamo pretragu. to bolje da uradimo i kad nije neophodno jer ce mozda postati!
  private final String loginBoxLocatorString = "//div[@id='loginbox']"; // xpath za zajednicki parent element za login Box
  private final By loginButtonLocator = By.xpath(loginBoxLocatorString + "//input[contains(@class,'btn-primary')]");
  // private final By loginButtonLocator = By.xpath(loginBoxLocatorString + + "//input[@type='submit']"); moze i ovako sa atributom type

  //Register i Forgot password links
  //private final By createAccountLinkLocator = By.xpath( loginBoxLocatorString + "//a[@href='/register']"); -moze ali cemo da parametrizujemo pomocu PageUrlPaths
  //private final By resetPasswordLinkLocator = By.xpath( loginBoxLocatorString + "//a[@href='/forgotPassword']");
  private final By createAccountLinkLocator = By.xpath( loginBoxLocatorString + "//a[@href='"+ PageUrlPaths.REGISTER_PAGE + "']");
  private final By resetPasswordLinkLocator = By.xpath( loginBoxLocatorString + "//a[@href='" + PageUrlPaths.FORGOT_PASSWORD_PAGE + "']");

  // Praksa: ako ima id, ako ne onda trazimo xpath ili css, fokusiramo se prvo na atribut class
  // Izbegavamo: linktext, partialLinkText - jer ako se promeni text pada sa greskom nema elementa, umesto sa greskom text nije dobar - expected, actual...

  // Primeri:
  // private final By usernameTextFieldLocator = By.classname("username");
  // private final By usernameTextFieldLocator = By.xpath("//*[contains(@class, 'username')");
  // private final By usernameTextFieldLocator = By.xpath("//div[contains(@class, 'username')"); preciznije
  // private final By usernameTextFieldLocator = By.cssSelector(".username"); - skracenica za classname je .
  // private final By usernameTextFieldLocator = By.cssSelector("#username"); - skracenica za id je #
  // private final By usernameTextFieldLocator = By.cssSelector("div.username");

  private final By successMessageLocator = By.xpath( loginBoxLocatorString + "//div[contains(@class, 'alert-success')]");

  // KONSTRUKTOR
  public LoginPage(WebDriver driver) {
    super(driver);
    log.trace("new LoginPage()");
  }

  public LoginPage open(boolean bVerify) {
    log.debug("Open LoginPage(" + LOGIN_PAGE_URL + ")");
    //driver.get(LOGIN_PAGE_URL); // ovako smo krenuli, ali to cemo da wrapujemo u BasePage metodu - openUrl(). Prakticno, kad imas driver.* sve ovo treba wrapovati u BasePage
    openUrl(LOGIN_PAGE_URL);
    if (bVerify) {
      verifyLoginPage();
    }
    return this;
  }

  public LoginPage open() {
    log.debug("Open LoginPage(" + LOGIN_PAGE_URL + ")");
    return open(true);
  }

  public LoginPage verifyLoginPage() {
    log.debug("verifyLoginPage()");
    waitForUrlChange(LOGIN_PAGE_URL, Time.TIME_SHORT); // cekamo da se promeni URL. vraca boolean T/PUCA
    waitUntilPageIsReady(Time.TIME_SHORT); // cekamo da se ucita DOM struktura
    // opciono moze 3.provera -cekamo da se pojavi neki webelement koji se sporo ucitvava (iframe,widget, slika) waitfowebelementtobevisible
    // svakako ne treba stavljati samo proveru za element, jer njega moramo najduze da cekamo pa bolje da odmah padna ako nije na pravom URLu
    // a i ako se ucita, mozda nismo na pravoj stranici, vec taj element postoji na vise stranica
    return this;
  }

  // USERNAME
  public boolean isUsernameTextFieldDisplayed() {
    log.debug("isUsernameTextFieldDisplayed()");
    return isWebElementDisplayed(usernameTextFieldLocator);
  }

  // Dobra praksa je JavaDoc na svim metodama koje se zovu iz testa, ne treba na private
  /**
   * Type Username in Username Text Field
   * @param sUsername {String} - Username
   * @return {LoginPage}
   */
  public LoginPage typeUsername(String sUsername) {
    log.debug("typeUsername(" + sUsername + ")");
    //Assert.assertTrue((isUsernameTextFieldDisplayed()), "Username Text Field is NOT displayed on Login Page!");
    //WebElement usernameTextField =  driver.findElement(By.id("username")); // Ovo moze, ali je zavisno od Selenium osnovnih metoda i bolje ubaciti u BasePage-getWebElement
    WebElement usernameTextField = getWebElement(usernameTextFieldLocator);
    //usernameTextField.sendKeys(sUsername); //necemo da se oslanjamo na sendKeys metody u LoginPage klasi, mozda ce da se promeni vec je napravimo u BasePage
    clearAndTypeTextToWebElement(usernameTextField, sUsername);
    return this;
  }

  // napravicemo typeUsernameSlowly-metoda koja kucka slovo po slovo u petlji i preko JS proverava da se text promenio

  public String getUsername() {
    log.debug("getUsername()");
    WebElement usernameTextField  = getWebElement(usernameTextFieldLocator); //uvek iznova getujemo element preko lokatora u trenutku kad zelimo sa njim da radimo
    return getValueFromWebElement(usernameTextField); // ako se desi da Selenium ne moze da getuje ovu vredosti ili vrati staru, pozvacemo JS metodu getValueFromWebElementJS
  }

  // PASSWORD
  public boolean isPasswordTextFieldDisplayed() {
    log.debug("isPasswordTextFieldDisplayed()");
    return isWebElementDisplayed(passwordTextFieldLocator);
  }

  public void typePassword_VoidPrimer(String sPassword) {
    log.debug("typePassword_VoidPrimer(" + sPassword + ")");
    WebElement passwordTextField = getWebElement(passwordTextFieldLocator);
    clearAndTypeTextToWebElement(passwordTextField, sPassword);
  }

  // Kozic predlaze - Ako metoda radi nesto i ostaje na istoj stranici, umesto da je stavimo da bude void, mozemo da vratimo instancu te iste stranice
  // To omogucava method chaining - utackavanje LoginPage.typeUserneme().typePassword().clickLoginButton();
  public LoginPage typePassword(String sPassword) {
    log.debug("typePassword(" + sPassword + ")");
    WebElement passwordTextField = getWebElement(passwordTextFieldLocator);
    clearAndTypeTextToWebElement(passwordTextField, sPassword);
    return this;
  }

  public String getPassword () {
    log.debug("getPassword()");
    WebElement passwordTextField = getWebElement(passwordTextFieldLocator);
    return getValueFromWebElement(passwordTextField);
  }

  // LOGIN BUTTON
  // Klik na Login Buton moze da vodi na Welcome page ILI ostajemo na Login ako je login bio neuspesan
  // Zato cemo imati 2 metode - clickLoginButton() i clickLoginButtonNoProgress()
  // Zajednicki deo za ove 2 metode dole cemo da stavimo u PRIVATE metodu, da ne dupliramo kod

  public boolean isLoginButtonDisplayed() {
    log.debug("isLoginButtonDisplayed()");
    return isWebElementDisplayed(loginButtonLocator);
  }

  private void clickLoginButtonNoVerify() {
    log.debug("clickLoginButtonNoVerify()");
    Assert.assertTrue(isLoginButtonDisplayed(), "Login Button is NOT displayed on Login Page!");
    WebElement loginButton = getWebElement(loginButtonLocator);
    clickOnWebElement(loginButton);
  }

  // hocemo da nam metoda vrati instancu WelcomePage jer nas tamo vodi klik
  // Prednost - pravimo da driver klizi kroz stranice
  // Mana - treba nam posebna metoda ako znamo da nece da se otvori WelcomePage - npr kad ocekujemo invalid credentials
  public WelcomePage clickLoginButton() {
    log.debug("clickLoginButton()");
    clickLoginButtonNoVerify(); //prvo smo imali WebElement loginButton = getWebElement(loginButtonLocator); clickOnWebElement(loginButton); ali smo to izbacili kao zajednicko za 2 metode
    WelcomePage welcomePage = new WelcomePage(driver);
    return welcomePage.verifyWelcomePage();
  }


  // Metoda kad ne ocekujemo da se otvori Welcome page - npr invalid credentials
  public LoginPage clickLoginButtonNoProgress() {
    log.debug("clickLoginButtonNoProgress()");
    clickLoginButtonNoVerify();
    LoginPage loginPage = new LoginPage(driver);
    return loginPage.verifyLoginPage();//ovde proverimo da se login page refreshovala i DOM struktura ponovo ucitala
  }

  public String getLoginButtonTitle() {
    log.debug("getLoginButtonTitle()");
    WebElement loginButton = getWebElement(loginButtonLocator);
    return getValueFromWebElement(loginButton);
  }

  // Moglo bi ovako - True ako je vidljiv, False ako postoji, ali je nevidljiv.
  // Mana: puca ako element ne postoji
  // Bolje da napravimo opstu metodu isWebElementDisplayed (By locator), stavimo je u try catch da ne puca ako ne postoji element
  // Jer zelimo da je koristimo i za negativne scenarije
  public boolean isSuccessMessageDisplayed1 () {
    log.debug("isSuccessMessageDisplayed()");
    WebElement successMessage = getWebElement(successMessageLocator);
    return successMessage.isDisplayed();
  }

  // Koristimo opstu metodu isWebElementDisplayed()
  public boolean isSuccessMessageDisplayed () {
    log.debug("isSuccessMessageDisplayed()");
    return isWebElementDisplayed(successMessageLocator);
  }

  public String getSuccessMessage(){
    log.debug("getSuccessMessage()");
    Assert.assertTrue(isSuccessMessageDisplayed(), "Success Message is NOT present on Login Page!");
    WebElement successMessage = getWebElement(successMessageLocator);
    return getTextFromWebElement(successMessage);
  }

  //Metode za ERROR MESSAGE


  // CREATE ACCOUNT LINK
  public boolean isCreateAccountLinkDisplayed() {
    log.debug("isCreateAccountLinkDisplayed()");
    return isWebElementDisplayed(createAccountLinkLocator);
  }

  public RegisterPage clickCreateAccountLink() {
    log.debug("clickCreateAccountLink()");
    Assert.assertTrue(isCreateAccountLinkDisplayed(), "'Create Account' Link is NOT displayed on Login Page!");
    WebElement createAccountLink = getWebElement(createAccountLinkLocator);
    clickOnWebElement(createAccountLink);
    RegisterPage registerPage = new RegisterPage(driver);
    return registerPage.verifyRegisterPage();
  }

  public String getCreateAccountLinkTitle() {
    log.debug("getCreateAccountLinkTitle()");
    Assert.assertTrue(isCreateAccountLinkDisplayed(), "'Create Account' Link is NOT displayed on Login Page!");
    WebElement createAccountLink = getWebElement(createAccountLinkLocator);
    return getTextFromWebElement(createAccountLink);
  }

  // RESET PASSWORD LINK
  public boolean isResetPasswordLinkDisplayed() {
    log.debug("isResetPasswordLinkDisplayed()");
    return isWebElementDisplayed(resetPasswordLinkLocator);
  }

  public ResetPasswordPage clickResetPasswordLink() {
    log.debug("clickResetPasswordLink()");
    Assert.assertTrue(isResetPasswordLinkDisplayed(), "'Reset password' Link is NOT displayed on Login Page!");
    WebElement resetPasswordLink = getWebElement(resetPasswordLinkLocator);
    clickOnWebElement(resetPasswordLink);
    ResetPasswordPage resetPasswordPage = new ResetPasswordPage(driver);
    return resetPasswordPage.verifyResetPasswordPage();
  }

  public String getResetPasswordAccountLinkTitle() {
    log.debug("getResetPasswordAccountLinkTitle()");
    Assert.assertTrue(isResetPasswordLinkDisplayed(), "'Reset Password' Link is NOT displayed on Login Page!");
    WebElement resetPasswordLink = getWebElement(resetPasswordLinkLocator);
    return getTextFromWebElement(resetPasswordLink);
  }

  // Stranicu u POM mozemo logicki da podelimo na:
  // low level library - private dohvatanje lokatora/WebElemenata
  // middle level library - osnovne metode za rad sa elementima (typeUsername, getUsername ,...)
  // high level library - kompleksne metode koje objedinjuju vise akcija koje cine logicku celinu.
  // HL- uvek se oslanjaju na vec implementirane metode za pojedinacne elemente a ne da  u kompleksoj metodi getujemo element po element

  // high level library primer, INFO logovanje za njih
  // nadalje koristimo ovu metodu uvek kad nam je login samo prolazna faza i nemamo medjuverifikaciju na njoj
  // ako hocemo da verifikujemo korak po korak onda tako pisemo u testu
  // Kompleksne metode NE treba da setaju po stranicama (uloguje se, ode na WelcomePage, napravi Heroja), vec da sve rade na istoj stranici da bi ispostovali POM
  // Ako ti bas treba da se kreces kroz stranice - onda ga stavi u BaseTest klasi

  // Evo primera sa kompleksne metode (high level lib) sa method chainingom - ako nam pojedinacne metode vracaju stranicu, dakle nisu void.
  /**
   * Login to Samsara
   * @param sUsername {String} - Username
   * @param sPassword {String} - Password
   * @return {WelcomePage}
   */
  public WelcomePage login(String sUsername, String sPassword) {
    log.info("login(" + sUsername +"," + sPassword +")");
    return this.typeUsername(sUsername)
        .typePassword(sPassword)
        .clickLoginButton();
  }
}
