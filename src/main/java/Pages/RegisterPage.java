package Pages;

import Data.PageUrlPaths;
import Data.Time;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class RegisterPage extends CommonLoggedOutPage{

  // PAGE URL PATHS
  private final String REGISTER_PAGE_URL = getPageUrl(PageUrlPaths.REGISTER_PAGE);

  // PAGE FACTORY LOCATORS
  // Kao atribute u klasi imamo reference na WebElemente a ne lokatore pa da posle dohvatamo webelemente posle u metodama
  // To je potpuni POM. Implementira se:
  // Webelementi se definisu sa sa anotacijom @FindBy (xpath/id/name...)
  // Staticka metoda koja inicijalizuje webelemente PageFactory.initElements(driver,this);

  // WebElement se trazi tek kad zelimo da mu pristupimo, svaki put iznova (izbegavamo StaleElement Reference)
  // Ako je los xpath nece puci prilikom inicijalizacije stranice vec samo ako pokusamo interakciju sa WebElementom

  // Prednosti: brze i lakse dohvatanje elementa, izbegavamo StaleElement reference. Framework bude pouzdaniji
  // Mane:
  // @FindBy proverava samo da li element postoji/ne postoji a ne moze da proveri jel visible ili ne
  // Ispod haube koristi implicit wait ( ovde nam je 3 sec), mada moze da se definise i poseban wait na nivou Page Factory-a ali je opet fiksan za sve lokatore, isto kao implicit wait
  // Nije dobar za elemente koje treba duze da cekamo - takvih ima manje i za njih cemo da koristimo By.locator i metode waitUntilPresenceOfWebElement
  // Nije dobar za elemente koji se dinamicki pojavljuju na stranicama (pretraga tabela). u tomu slucaju celu tabelu lociramo preko PF a pojedinacne elemente pomocu findElement

  @FindBy (id="username")
  private WebElement usernameTextField;

  @FindBy (id="firstName")
  private WebElement firstNameTextField;

  @FindBy (id="lastName")
  private WebElement lastNameTextField;

  @FindBy (id="email")
  private WebElement emailTextField;

  @FindBy (id="about")
  private WebElement aboutTextField;

  @FindBy (id="secretQuestion")
  private WebElement secretQuestionTextField;

  @FindBy (id="secretAnswer")
  private WebElement secretAnswerTextField;

  @FindBy (id="password")
  private WebElement passwordTextField;

  @FindBy (id="repassword")
  private WebElement confirmPasswordTextField;

  @FindBy(id="submitButton")
  private WebElement signUpButton;

  // KONSTRUKTOR
  public RegisterPage(WebDriver driver) {
    super(driver);
    // Moramo u konstruktoru da pozovemo staticku metodu koja inicira sve WebElemenete koji su definisani sa @FindBy
    // E sad da nebi u svakoj klasi koja koristi Page Factory pozivali ovu metodu, mi cemo da ovo pozovemo u BasePage klasi
    // PageFactory.initElements(driver, this);
    log.trace("RegisterPage()");
  }

  public RegisterPage open(boolean bVerify) {
    log.debug("Open RegisterPage(" + REGISTER_PAGE_URL + ")");
    openUrl(REGISTER_PAGE_URL);
    if (bVerify) {
      verifyRegisterPage();
    }
    return this;
  }

  public RegisterPage open () {
    return open(true);
  }

  public RegisterPage verifyRegisterPage() {
    log.debug("verifyRegisterPage()");
    waitForUrlChange(REGISTER_PAGE_URL, Time.TIME_SHORTER);
    waitUntilPageIsReady(Time.TIME_SHORT);
    return this;
  }

  //--------------------------------------------------------------------------------------------------------------------------------------------

  // USERNAME TEXT FIELD - isDisplayed, type, getText
  public boolean isUsernameTextFieldDisplayed () {
    log.debug("isUsernameTextFieldDisplayed()");
    return isWebElementDisplayed(usernameTextField);
  }

  public RegisterPage typeUsername(String sUsername) {
    log.debug("typeUsername()");
    Assert.assertTrue(isUsernameTextFieldDisplayed(), "'Username' Text Field is NOT displayed on Register page!");
    clearAndTypeTextToWebElement(usernameTextField, sUsername);
    return this;
  }

  public String getUsername() {
    log.debug("getUsername()");
    Assert.assertFalse(isUsernameTextFieldDisplayed(), "'Username' Text Field is NOT displayed on Register page!");
    return getValueFromWebElement(usernameTextField);
  }
  //--------------------------------------------------------------------------------------------------------------------------------------------

  // FIRSTNAME TEXT FIELD - isDisplayed, type, getText
  public boolean isFirstNameTextFieldDisplayed () {
    log.debug("isFirstNameTextFieldDisplayed()");
    return isWebElementDisplayed(firstNameTextField);
  }

  public RegisterPage typeFirstName(String sFirstName) {
    log.debug("typeFirstName(" + sFirstName + ")");
    Assert.assertTrue(isFirstNameTextFieldDisplayed(), "'First Name' Text Field is NOT displayed on Register page!");
    clearAndTypeTextToWebElement(firstNameTextField, sFirstName);
    return this;
  }

  public String getFirstName() {
    log.debug("getFirstName()");
    Assert.assertFalse(isFirstNameTextFieldDisplayed(), "'First Name' Text Field is NOT displayed on Register page!");
    return getValueFromWebElement(firstNameTextField);
  }

  //--------------------------------------------------------------------------------------------------------------------------------------------

  // LAST NAME TEXT FIELD - isDisplayed, type, getText
  public boolean isLastNameTextFieldDisplayed() {
    log.debug("isLastNameTextFieldDisplayed()");
    return isWebElementDisplayed(firstNameTextField);
  }

  public RegisterPage  typeLastName(String sLastName) {
    log.debug("typeLastName(" + sLastName + ")");
    Assert.assertTrue(isLastNameTextFieldDisplayed(), "'Last Name' Text Field is NOT displayed on Register page!");
    clearAndTypeTextToWebElement(lastNameTextField, sLastName);
    return this;
  }

  public String getLastName() {
    log.debug("getLastName()");
    Assert.assertFalse(isLastNameTextFieldDisplayed(), "'Last Name' Text Field is NOT displayed on Register page!");
    return getValueFromWebElement(firstNameTextField);
  }
  //--------------------------------------------------------------------------------------------------------------------------------------------

  // EMAIL TEXT FIELD - isDisplayed, type, getText

  public boolean isEmailTextFieldDisplayed() {
    log.debug("isEmailTextFieldDisplayed()");
    return isWebElementDisplayed(emailTextField);
  }

  public RegisterPage typeEmail(String sEmail) {
    log.debug("typeLastName(" + sEmail + ")");
    Assert.assertTrue(isEmailTextFieldDisplayed(), "'Email' Text Field is NOT displayed on Register page!");
    clearAndTypeTextToWebElement(emailTextField, sEmail);
    return this;
  }

  public String getEmail() {
    log.debug("getEmail()");
    Assert.assertFalse(isEmailTextFieldDisplayed(), "'Email' Text Field is NOT displayed on Register page!");
    return getValueFromWebElement(firstNameTextField);
  }

  //--------------------------------------------------------------------------------------------------------------------------------------------
  // ABOUT TEXT FIELD - isDisplayed, type, getText

  public boolean isAboutTextFieldDisplayed() {
    log.debug("isAboutTextFieldDisplayed()");
    return isWebElementDisplayed(aboutTextField);
  }

  public RegisterPage typeAbout(String sEmail) {
    log.debug("typeAbout(" + sEmail + ")");
    Assert.assertTrue(isAboutTextFieldDisplayed(), "'About' Text Field is NOT displayed on Register page!");
    clearAndTypeTextToWebElement(aboutTextField, sEmail);
    return this;
  }

  public String getAbout() {
    log.debug("getAbout()");
    Assert.assertFalse(isAboutTextFieldDisplayed(), "'About' Text Field is NOT displayed on Register page!");
    return getValueFromWebElement(aboutTextField);
  }

  //--------------------------------------------------------------------------------------------------------------------------------------------

  // SECRET QUESTION TEXT FIELD - isDisplayed, type, getText
  public boolean isSecretQuestionTextFieldDisplayed() {
    log.debug("isSecretQuestionTextFieldDisplayed()");
    return isWebElementDisplayed(secretQuestionTextField);
  }

  public RegisterPage typeSecretQuestion(String sSecretQuestion) {
    log.debug("typeSecretQuestion(" + sSecretQuestion + ")");
    Assert.assertTrue(isSecretQuestionTextFieldDisplayed(), "'Secret Question' Text Field is NOT displayed on Register page!");
    clearAndTypeTextToWebElement(secretQuestionTextField, sSecretQuestion);
    return this;
  }

  public String getSecretQuestion() {
    log.debug("getSecretQuestion()");
    Assert.assertFalse(isSecretQuestionTextFieldDisplayed(), "'Secret Question' Text Field is NOT displayed on Register page!");
    return getValueFromWebElement(secretQuestionTextField);
  }
  //--------------------------------------------------------------------------------------------------------------------------------------------

  // SECRET ANSWER TEXT FIELD - isDisplayed, type, getText
  public boolean isSecretAnswerTextFieldDisplayed() {
    log.debug("isSecretAnswerTextFieldDisplayed()");
    return isWebElementDisplayed(secretAnswerTextField);
  }

  public RegisterPage typeSecretAnswer(String sSecretQuestion) {
    log.debug("typeSecretAnswer(" + sSecretQuestion + ")");
    Assert.assertTrue(isSecretAnswerTextFieldDisplayed(), "'Secret Answer' Text Field is NOT displayed on Register page!");
    clearAndTypeTextToWebElement(secretAnswerTextField, sSecretQuestion);
    return this;
  }

  public String getSecretAnswer() {
    log.debug("getSecretAnswer()");
    Assert.assertFalse(isSecretAnswerTextFieldDisplayed(), "'Secret Answer' Text Field is NOT displayed on Register page!");
    return getValueFromWebElement(secretAnswerTextField);
  }

  //--------------------------------------------------------------------------------------------------------------------------------------------

  // PASSWORD TEXT FIELD - isDisplayed, type, getText
  public boolean isPasswordTextFieldDisplayed() {
    log.debug("isPasswordTextFieldDisplayed()");
    return isWebElementDisplayed(passwordTextField);
  }

  public RegisterPage typePassword(String sPassword) {
    log.debug("typePassword(" + sPassword + ")");
    Assert.assertTrue(isPasswordTextFieldDisplayed(), "'Password' Text Field is NOT displayed on Register page!");
    clearAndTypeTextToWebElement(passwordTextField, sPassword);
    return this;
  }

  public String getPassword() {
    log.debug("getPassword()");
    Assert.assertFalse(isPasswordTextFieldDisplayed(), "'Password' Text Field is NOT displayed on Register page!");
    return getValueFromWebElement(passwordTextField);
  }

  //--------------------------------------------------------------------------------------------------------------------------------------------

  // CONFIRM PASSWORD TEXT FIELD - isDisplayed, type, getText
  public boolean isConfirmPasswordTextFieldDisplayed() {
    log.debug("isConfirmPasswordTextFieldDisplayed()");
    return isWebElementDisplayed(confirmPasswordTextField);
  }

  public RegisterPage typeConfirmPassword(String sConfirmPassword) {
    log.debug("typeConfirmPassword(" + sConfirmPassword + ")");
    Assert.assertTrue(isConfirmPasswordTextFieldDisplayed(), "'Confirm Password' Text Field is NOT displayed on Register page!");
    clearAndTypeTextToWebElement(confirmPasswordTextField, sConfirmPassword);
    return this;
  }

  public String getConfirmPassword() {
    log.debug("getConfirmPassword()");
    Assert.assertFalse(isConfirmPasswordTextFieldDisplayed(), "'Confirm Password' Text Field is NOT displayed on Register page!");
    return getValueFromWebElement(confirmPasswordTextField);
  }

  //--------------------------------------------------------------------------------------------------------------------------------------------

  // SIGN UP BUTTON - isDisplayed, isEnabled/Clickable, click, getTitle ?

  // U DATOM TRENUTKU
  // Da li se vidi?
  public boolean isSignUpButtonDisplayed() {
    log.debug("isSignUpButtonDisplayed()");
    return isWebElementDisplayed(signUpButton); //ovde koristimo isDisplayed() koja proverava u datom trenutku
  }

  // U DATOM TRENUTKU
  // Da li je klikabilan?
  public boolean isSignUpButtonEnabled() {
    log.debug("isSignUpButtonEnabled()");
    Assert.assertTrue(isSignUpButtonDisplayed(), "'Sign Up' button is NOT displayed on Register page!"); //isEnabled() podrazumeva da element postoji zato cemo prvo uraditi assert
    return isWebElementEnabled(signUpButton); // ovde koristimo isEnabled() koja proverava u datom trenutku
  }

  // Overload, CEKAJUCA METODA
  // Da li je klikabilan?
  public boolean isSignUpButtonEnabled(int timeOut) {
    log.debug("isSignUpButtonEnabled(" + timeOut + ")");
    Assert.assertTrue(isSignUpButtonDisplayed(), "'Sign Up' button is NOT displayed on Register page!");
    return isWebElementEnabled(signUpButton, timeOut);  // ovde ulazimo u cekajucu metodu koja koristi elementToBeClickable()
  }

  // Click metoda koja NE ceka. Pre nego kliknemo proverimo da li je Enabled u datom truenutku
  public LoginPage clickSignUpButton() {
    log.debug("clickSignUpButton()");
    Assert.assertTrue(isSignUpButtonEnabled(), "'Sign Up' button is NOT enabled on Register page!"); //ovde smo koristili metodu koja NE ceka - isEnabled()
    clickOnWebElement(signUpButton);
    LoginPage loginPage = new LoginPage(driver);
    return loginPage.verifyLoginPage();
  }

  // Overload, Click metoda koja CEKA da Button postane Enabled - opcija 1.
  // Ova je bolja zbog greske u negativnim slucajevima
  public LoginPage clickSignUpButton(int timeOut)  {
    log.debug("clickSignUpButton(" + timeOut + ")");
    Assert.assertTrue(isSignUpButtonEnabled(timeOut), "'Sign Up' button is NOT enabled on Register page!"); //ovde smo koristili metodu koja CEKA - elementToBeClickable
    clickOnWebElement(signUpButton);
    LoginPage loginPage = new LoginPage(driver);
    return loginPage.verifyLoginPage();
  }

  // Overload, Click metoda koja CEKA da Button postane Enabled - opcija 2.
  public LoginPage clickSignUpButton1(int timeOut)  {
    log.debug("clickSignUpButton(" + timeOut + ")");
    clickOnWebElement(signUpButton, timeOut ); //ovde smo koristili metodu koja CEKA - elementToBeClickable
    LoginPage loginPage = new LoginPage(driver);
    return loginPage.verifyLoginPage();
  }
}
