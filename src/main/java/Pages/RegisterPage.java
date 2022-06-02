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

  // LOCATORS
  // Ovde cemo da koristimo PAGE FACTORY i to je potpuni POM
  // Implementira se:
  // Webelementi se definisu sa sa anotacijom @FindBy (xpath/id/name...)
  // staticka metoda koja inicijalizuje webelemente PageFactory.initElements(driver,this);

  // WebElement se trazi tek kad zelimo da mu pristupimo (izbegavamo StaleElement Reference)
  // Ako je los xptah nece puci prilikom inicijalizacije strenice vec samo ako pokusamo interakciju sa WebElementom
  @FindBy (id="username")
  WebElement usernameTextField;

  @FindBy (id="firstName")
  WebElement firstNameTextField;

  @FindBy (id="lastName")
  WebElement lastNameTextField;

  @FindBy (id="email")
  WebElement emailTextField;

  @FindBy (id="about")
  WebElement aboutTextField;

  @FindBy (id="secretQuestion")
  WebElement secretQuestionTextField;

  @FindBy (id="secretAnswer")
  WebElement secretAnswerTextField;

  @FindBy (id="password")
  WebElement passwordTextField;

  @FindBy (id="repassword")
  WebElement confirmPasswordTextField;


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
    Assert.assertTrue(isFirstNameTextFieldDisplayed(), "'First Name' Text Field is NOT displayed on Register page!");
    clearAndTypeTextToWebElement(firstNameTextField, sLastName);
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
    Assert.assertTrue(isAboutTextFieldDisplayed(), "'Secret Question' Text Field is NOT displayed on Register page!");
    clearAndTypeTextToWebElement(secretAnswerTextField, sSecretQuestion);
    return this;
  }

  public String getSecretQuestion() {
    log.debug("getSecretQuestion()");
    Assert.assertFalse(isAboutTextFieldDisplayed(), "'Secret Question' Text Field is NOT displayed on Register page!");
    return getValueFromWebElement(secretAnswerTextField);
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
    Assert.assertFalse(isAboutTextFieldDisplayed(), "'Secret Answer' Text Field is NOT displayed on Register page!");
    return getValueFromWebElement(secretAnswerTextField);
  }

  //--------------------------------------------------------------------------------------------------------------------------------------------

}
