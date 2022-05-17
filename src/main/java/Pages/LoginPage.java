package Pages;

import Data.PageUrlPaths;
import Data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends CommonLoggedOutPage {

  // WEBELEMENTI/LOKATORI:
  // ako NE koristimo PageFactory - onda definisemo LOKATORE za WebElemente, a svaka metoda mora da dohvati WebElement u trenutku kad oce da ga koristi, nikako pre jer ce se desiti StaleElemet greska
  // ako koristimo PageFactory - onda su to WEBELEMENTI
  // PRIVATE - da ne moze iz druge stranice/testa da se pristupa webelementu/lokatoru. Moci ce da im se pristupa samo preko metoda. Zasto:
  // odrzavanje - ako se promeni lokator moras da menjas na vise mesta umesto da u Page napravis metode koje rade sa elemetom i ako se promeni menjas ga na 1 mestus
  // da test bude napisan tako da ne zavisi od alata koji cemo mozda zeleti da menjamo
  // FINAL - da niko ne moze da ih dalje menja

  // PRIMER SA LOKATORIMA (ako ne koristimo PageFactory)
  // Ovo su lokatori a webelemente cemo dohvatiti u samoj metodi - neposredno pred koristenje
  //Naming konvecnija:
  // Sta radi element/ Tip elementa / Locator (ako je WebElement onda nista)
  private final By usernameTextFieldLocator = By.id("username");
  private final By passwordTextFieldLocator = By.id("password");

  // posto classname nije uvek jedinstven bolje je da se nadje jedinstven element iznad i onda unutar njega dalje trazimos
  // zbog odrzavanja i ubrzavamo pretragu. to radimo i kad nije neophodno jer ce mozda postati
  private final String loginBoxLocatorString = "//div[@id='loginbox']"; // ovo mozes i gore da definises iznad
  private final By loginButtonLocator = By.xpath(loginBoxLocatorString + "//input[contains(@class,'btn-primary')]");
  // private final By loginButtonLocator = By.xpath(loginBoxLocatorString + + "//input[@type='submit']"); moze i ovako sa atributom type

  // private final WebElement usernameTextFiel = driver.findElement(usernameTextFieldLocator) - BAD PRACTICE!
  // jer ce prilikom inicijalizacije stranice dohvatiti element i ako se izmeni DOM struktura stranice, dobicemo StaleElement gresku, ReferencedElement is not attached to DOM ...

  // Praksa: ako ima id, ako ne onda trazimo xpath ili css, fokusiramo se prvo na atribut class
  // Izbegavamo: linktext, partialLinkText - jer ako se promeni text pada sa greskom nema elementa, umesto sa greskom text nije dobar -expected, actual...

  // Primeri:
  // private final By usernameTextFieldLocator = By.classname("username");
  // private final By usernameTextFieldLocator = By.xpath("//*[contains(@class, 'username')");
  // private final By usernameTextFieldLocator = By.xpath("//div[contains(@class, 'username')"); preciznije
  // private final By usernameTextFieldLocator = By.cssSelector(".username"); - skracenica za classname je .
  // private final By usernameTextFieldLocator = By.cssSelector("#username"); - skracenica za id je #
  // private final By usernameTextFieldLocator = By.cssSelector("div.username");

  //URL:
  private final String LOGIN_PAGE_URL = getPageUrl(PageUrlPaths.LOGIN_PAGE);

  //METODE:
  public LoginPage(WebDriver driver) {
    super(driver);
    log.trace("new LoginPage()");
  }

  public LoginPage open(boolean bVerify) {
    log.debug("Open LoginPage(" + LOGIN_PAGE_URL + ")");
    //driver.get(LOGIN_PAGE_URL); // ovako smo krenuli, ali to cemo da wrapujemo u BasePage metodu - openUrl(). prakticno kad imas driver.* sve ovo treba wrapovati u BasePage
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
    waitForUrlChange(LOGIN_PAGE_URL, Time.TIME_SHORT);
    waitUntilPageIsReady(Time.TIME_SHORT);
    return this;
  }

  //Dobra praksa je JavaDoc na svim metodama koje se zovu iz testa, ne treba na private
  /**
   * Type Username in Username Text Field
   * @param sUsername {String} - Username
   */
  public void typeUsername(String sUsername) {
    log.debug("typeUsername(" + sUsername + ")");
    //WebElement usernameTextField =  driver.findElement(By.id("username")); // Ovo moze, ali je zavisno od Selenium osnovnih metoda i bolje ubaciti u BasePage-getWebElement
    WebElement usernameTextField = getWebElement(usernameTextFieldLocator);
    //usernameTextField.sendKeys(sUsername); //necemo da se oslanjamo na sendKeys metody u LoginPage klasi, mozda ce da se promeni vec je napravimo u BasePage
    clearAndTypeTextToWebElement(usernameTextField, sUsername);
  }

  //napravicemo typeUsernameSlowly-metoda koja kucka slovo po slovo u petlji i preko JS proverava da se text promenio


  public String getUsername() {
    log.debug("getUsername()");
    WebElement usernameTextField  = getWebElement(usernameTextFieldLocator); //uvek iznova getujemo element preko lokatora u trenutku kad zelimo sa njim da radimo
    return getValueFromWebElement(usernameTextField); // ako se desi da Selenium ne moze da getuje ovu vredosti ili vrati staru, pozvacemo JS metodu getValueFromWebElementJS
  }

  public void typePassword_VoidPrimer(String sPassword) {
    log.debug("typePassword_VoidPrimer(" + sPassword + ")");
    WebElement passwordTextField = getWebElement(passwordTextFieldLocator);
    clearAndTypeTextToWebElement(passwordTextField, sPassword);
  }

  //Kozic predlaze ovako - omogucava method chaining - utackavanje LoginPage.typeUserneme().typePassword().clickLoginButton()
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

  // hocemo da nam metoda vrati instancu WelcomePage jer nas tamo vodi klik
  // Prednost - pravimo da driver klizi kroz stranice
  // Mana - treba nam posebna metoda ako znamo da nece da se otvori WelcomePage - npr kad ocekujemo invalid credentials
  public WelcomePage clickLoginButton() {
    log.debug("clickLoginButton()");
    clickLoginButtonNoVerify(); //prvo smo imali WebElement loginButton = getWebElement(loginButtonLocator); clickOnWebElement(loginButton); ali smo to izbacili kao zajednicko za 2 metode
    WelcomePage welcomePage = new WelcomePage(driver);
    return welcomePage.verifyWelcomePage();
  }

  //public WelcomePage clickLoginButtonNoProgress() -implement

  // Zajedncki deo za ove 2 metode gore cemo da stavimo u private metodu
  private void clickLoginButtonNoVerify() {
    log.debug("clickLoginButtonNoVerify()");
    WebElement loginButton = getWebElement(loginButtonLocator);
    clickOnWebElement(loginButton);
  }

  public String getLoginButtonTitle() {
    log.debug("getLoginButtonTitle()");
    WebElement loginButton = getWebElement(loginButtonLocator);
    return getValueFromWebElement(loginButton);
  }

  // High Level Library primer kompleksne metode:
  // Koristimo je svuda gde je login prolazna faza i na njoj nemamo medjuverifikaciju
  // a imacemo poseban test za Login page gde korak po korak unosimo i verifikujemo...
  // Uvek treba da se oslanjaju na vec implementirane metode za pojedinacne elemente a ne da  u kompleksoj metodi getujemo element po element
  // Kompleksne stranice NE treba da setaju po stranicama, vec da sve rade na istoj stranici ( uloguje se i ode na welocome, naprave heroja)
  // Ako ti bas treba da se kreces kroz stranice - onda ga stavi u BaseTest klasi


/*  public WelcomePage login (String username, String password) {
    log.info("login(" + username +"," + password +")");
    this.typeUsername(username)
        .typpepassword
        .clickLoginButton();
  }*/
}
