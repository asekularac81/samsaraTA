package Pages;

import java.time.Duration;
import java.util.function.Function;

import Data.Time;
import Utils.LoggerUtils;
import Utils.PropertiesUtils;
import Utils.WebDriverUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

// U BasePage klasi handlujemo sve osnovne Selenium metode, pa ako se depreactuju menjamo samo tu a ne diramo  Pages klase

//BasePage extenduje LoggerUtils da bi logovanje bilo centralizovano
public abstract class BasePage extends LoggerUtils {

  protected WebDriver driver;

  // KONSTRUKTOR
  // Ovde cemo da pozovemo staticku metodu za inicijalizaciju WebElemeneta koji su definisani sa @FindBy
  // I onda ne moremo da je pozivamo u ostalim Page klasama
  // Ne dohvataju se WebElementi sada vec samo pred interakciju sa pojedinacnim WebElementom
  public BasePage(WebDriver driver) {
    Assert.assertFalse(WebDriverUtils.hasDriverQuit(driver), "Driver instance has quit!");
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }

  //-----------------------------------------------------------------------------------------------
  // PAGE URL - getUrl, waitForUrlChange, openURL

  protected String getPageUrl(String sPath) {
    log.trace("getPageUrl(" + sPath + ")");
    return PropertiesUtils.getBaseUrl() + sPath;
  }

  protected String getCurrentUrl () {
    log.trace("getCurrentUrl()");
    return driver.getCurrentUrl();
  }

  // cekamo da se promeni URL - contains
  // vraca boolean T/PUCA ako nije - sa lepom greskom:
  // org.openqa.selenium.TimeoutException: Expected condition failed: waiting for url to contain "http://18.219.75.209:8080/home".
  // Current url: "http://18.219.75.209:8080/login" (tried for 5 second(s) with 500 milliseconds interval)
  // u ovom slucaju nam ne treba assert kad je pozivamo jer se zna da ce puci ako nije promenjen URL ali sa lepom greskom
  protected boolean waitForUrlChange(String url, int timeOut) {
    log.trace("waitForUrlChange(" + url + ". " + timeOut + ")");
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
    return wait.until(ExpectedConditions.urlContains(url));
  }

  // Ako ti bas treba da vrati false ako nije promenjena URL imas 2 opcije:
  // Prvo resenje - metoda sa FluentWaitom koja ignorise TimeoutException,  vraca T/F
  protected boolean waitForUrlChangeIgnoreException(String url, int timeOut, int pollingTime)   {
    log.trace("waitForUrlChangeIgnoreException(" + url + ", " + timeOut + "," + pollingTime + ")");
    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
        .withTimeout(Duration.ofSeconds(timeOut))
        .pollingEvery(Duration.ofSeconds(pollingTime))
        .ignoring(TimeoutException.class);
    return wait.until(ExpectedConditions.urlContains(url));
  }

  // Drugo resenje - sa try/catch blokom
  // vraca T/F pa posle koristis Assert.assertTrue, ali je test NG greska nedovoljno deskriptivna i moras u assertu dodat poruku
  // pa je zato bolje opcija da pukne sa waitForUrlChange()
  protected boolean waitForUrlChangeIgnoreException2(String url, int timeOut) {
    log.trace("waitForUrlChangeIgnoreException2(" + url + ". " + timeOut + ")");
    try {
      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
      return wait.until(ExpectedConditions.urlContains(url));
    }
    catch (TimeoutException e){
      return false;
    }
  }

  protected boolean waitForUrlChangeToExactUrl(String url, int timeOut) {
    log.trace("waitForUrlChangeToExactUrl(" + url + ". " + timeOut + ")");
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
    return wait.until(ExpectedConditions.urlToBe(url));
  }

  protected void openUrl(String url) {
    log.trace("openUrl(" + url + ")");
    driver.get(url);
  }

  // Cekamo da se ucita DOM struktura
  protected boolean waitUntilPageIsReady(int timeOut) {
    log.trace("waitUntilPageIsReady(" + timeOut + ")");
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
    return wait.until(driver1 -> ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete"));
  }

  //-----------------------------------------------------------------------------------------------

  // Osnovna stanja koja mozemo da proverimo preko Seleniuma:
  // Presence - da li postoji ili ne
  // Ako postoji imamo dalje:
    // Visible/Invisible
    // Clickable ili Enabled
    // Selected

  // Presence:
  // findElement              | implicit wait | vraca WebElement | ceka max implicit wait, pretrazuje na svakih 500ms
  // presenceOfElementLocated | explicit wait | vraca WebElement | definisemo kolko da ceka, ponavlja na 500 ms

  // Ako element postoji imamo dalje provere:
  // Visible/Displayed:
  // elementToBeVisible/Invisible | cekajuca         | explicit wait | vraca WebElement | definisemo kolko da ceka, ponavlja na 500 ms
  // Displayed                    | u datom trenutku | ne ceka       | vraca T/F        |

  // Clickable/Enabled:
  // elementToBeClickable        | cekajuca          | explicit wait | vraca WebElement | definisemo kolko da ceka, ponavlja na 500 ms
  // isEnabled                   | u datom trenutku  | ne ceka       | vraca T/F

  // Selected:
  // elementToBeSelected         | cekajuca          | explicit wait | vraca WebElement | definisemo kolko da ceka, ponavlja na 500 ms
  // isSelected                  | u datom trenutku  | ne ceka       | vraca T/F        |


 //-----------------------------------------------------------------------------------------------
  // DOHVATANJE WEB ELEMENATA preko BY lokatora
  // Proverava samo dal je PRESENCE ali NE i da li je visible/clickable/selected itd. Vraca WebElement

  // Opcija 1 - da koristimo implicitni wait (3 sec). Ako nema elementa - ponavalja potragu do timeouta, na svakih 500ms
  // Oslanja se na findElement () Selenium metodu
  protected WebElement getWebElement(By locator) {
    log.trace("getWebElement(" + locator + ")");
    return driver.findElement(locator);
  }

  // Opcija 2 - da koristimo eksplicitni (WebDriverWait) wait sa timeOut-om ako hocemo da cekako da se pojavi
  // Oslanja se na presenceOfElementLocated
  // Overload, sa timeOut-om
  protected WebElement getWebElement(By locator, int timeOut) {
    log.trace("getWebElement(" + locator + "," + timeOut +")");
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
    WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator)); // ona ne samo da ceka vec i VRACA WEBELEMENT! (ako koristimo presenceOfElementLocated)
    return element;
  }

  // Opcija 3 - da koristimo eksplicitni (Fluent Wait). Dodatno definisemo:
  // pooling time - razmak izmedju pokusaja
  // Exception koji  ignorisemo
  // Redje se koristi, ali ima smisla kad znamo da cemo duze cekati, recimo upload fajla traje 5 min i pooling time je 10 sec
  protected WebElement getWebElement(By locator, int timeOut, int pollingTime) {
    log.trace("getWebElement(" + locator + "," + timeOut + "," + pollingTime + ")");
    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
        .withTimeout(Duration.ofSeconds(timeOut))
        .pollingEvery(Duration.ofSeconds(pollingTime))
        .ignoring(NoSuchElementException.class);
    WebElement element = wait.until(new Function<WebDriver, WebElement>() {
      public WebElement apply (WebDriver driver){
        return driver.findElement(locator);}
      }
    );
    return element;
  }

  // Opcija 3.1 - da koristimo eksplicitni (Fluent Wait), napisana jednostavnije (kad uradimo replace with Lambda)
  protected WebElement getWebElementSimplified(By locator, int timeOut, int pollingTime) {
    log.trace("getWebElementSimplified(" + locator + "," + timeOut + "," + pollingTime + ")");
    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
        .withTimeout(Duration.ofSeconds(timeOut))
        .pollingEvery(Duration.ofSeconds(pollingTime))
        .ignoring(NoSuchElementException.class);
    WebElement element = wait.until(driver -> driver.findElement(locator)
    );
    return element;
  }

  //------------------------------------------------------------------------------------------------------------------

  // CLICKABLE/ENABLED
  // Metode predpostavljaju da element postoji i zato pre nego ih negde pozovemo treba da uradimo assert da element postoji

  // Proverava u DATOM TRENUTKU  da li je klikabilan. Vraca True/False
  protected boolean isWebElementEnabled (WebElement element) {
    log.trace("isWebElementEnabled(" + element + ")");
    return element.isEnabled();
  }

  // CEKA da element bude klikabilan i VRATI element!
  protected WebElement waitForElementToBeClickable(WebElement element, int timeOut) {
    log.trace("waitForElementToBeClickable(" + element + "," + timeOut + ")");
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
    return  wait.until(ExpectedConditions.elementToBeClickable(element));  // ima i varijanta da prosledis lokator ali mi cemo opciju sa prosledjenim WebElementom
  }

  // CEKA da element bude klikabilan i vrati True/False
  protected boolean isWebElementEnabled (WebElement element, int timeOut) {
    log.trace("isWebElementEnabled(" + element + "," + timeOut + ")");
    try {
      waitForElementToBeClickable(element, timeOut);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  //------------------------------------------------------------------------------------------------------------------

  // VISIBLE
  // Metode koje cekaju da nesto postane vidljivo/nevidljivo
  // WAIT for element to be VISIBLE (2) - preko locatora, preko WebElementa
  // timeout-vreme koje cekamo da od nevidljivosti postane vidljiv
  // dole imamo wrapere koji proveravaju jel vidljivo/nevidljivo-boolean

  // preko locatora, korisna bez PageFactory-a. vrati WebElement
  protected WebElement waitForElementToBeVisible(By locator, int timeOut) {
    log.trace("waitForElementToBeVisible(" + locator + "," + timeOut + ")");
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
    return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)); //da opipamo da li uopste postoji pomocu lokatora, sacekamo ga i ako postoji da vratimo WebElement
  }

  // preko WebElementa, korisna za PageFactory. WebElement smo vec dohvatili, samo da vidimo jel visible
  protected WebElement waitForElementToBeVisible(WebElement element, int timeOut) {
    log.trace("waitForElementToBeVisible(" + element + "," + timeOut + ")");
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
    return wait.until(ExpectedConditions.visibilityOf(element)); //vrati WebElement
  }

  // WAIT for element to be INVISIBLE (2) - preko locatora, preko WebElementa
  protected boolean waitForElementToBeInvisible(By locator, int timeOut) {
    log.trace("waitForElementToBeInvisible(" + locator + "," + timeOut + ")");
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
    return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));  //vrati boolean jer invisibilityOfElementLocated ne vraca WebElement
  }

  protected boolean waitForElementToBeInvisible(WebElement element, int timeOut) {
    log.trace("waitForElementToBeInvisible(" + element + "," + timeOut + ")");
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
    return wait.until(ExpectedConditions.invisibilityOf(element)); //vrati boolean jer invisibilityOf ne vraca WebElement
  }

  // Metode koje proveravaju da li je nesto vidljivo/nevidljivo
  // mora da se koristi explicit  wait jer implicit wait proverava samo dal je prisutan ali ne
  // dok da li je clickable, visible - za to moras explicit wait
  // Visible, preko lokatora
  protected boolean isWebElementVisible(By locator, int timeOut) {
    log.trace("isWebElementVisible(" + locator + "," + timeOut + ")");
    try {
      waitForElementToBeVisible(locator,timeOut);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  // Overload metode iznad samo sa predefinisanim wait-om
  protected boolean isWebElementVisible(By locator) {
    return isWebElementVisible(locator, Time.TIME_SHORTER);
  }

  // Visible, preko webElementa
  protected boolean isWebElementVisible(WebElement element, int timeOut) {
    log.trace("isWebElementVisible(" + element + "," + timeOut + ")");
    try {
      waitForElementToBeVisible(element,timeOut);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  // Overload metode iznad samo sa predefinisanim wait-om
  protected boolean isWebElementVisible(WebElement element) {
    return isWebElementVisible(element, Time.TIME_SHORTER);
  }

  // Invisible, preko lokatora
  protected boolean isWebElementInvisible(By locator, int timeOut) {
    log.trace("isWebElementInvisible(" + locator + "," + timeOut + ")");
    try {
      waitForElementToBeVisible(locator,timeOut);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  // Overload metode iznad samo sa predefinisanim wait-om
  protected boolean isWebElementInvisible(By locator) {
    return isWebElementVisible(locator, Time.TIME_SHORTER);
  }

  // Invisible, preko webelementa
  protected boolean isWebElementInvisible(WebElement element, int timeOut) {
    log.trace("isWebElementInvisible(" + element + "," + timeOut + ")");
    try {
      waitForElementToBeVisible(element,timeOut);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  // Overload metode iznad samo sa predefinisanim wait-om
  protected boolean isWebElementInvisible(WebElement element) {
    return isWebElementVisible(element, Time.TIME_SHORTER);
  }

  //------------------------------------------------------------------------------------------------------------------

  // DISPLAYED
  // Wrapovana opsta metoda koja  ako element ne postoji vrati false umesto da pukne
  // kako bi mogla da se koristi i za negativan scenario.
  protected boolean isWebElementDisplayed (By locator) {
    log.debug("isWebElementDisplayed()");
    try {
      WebElement webElement =getWebElement(locator);
      return webElement.isDisplayed();
    }
    catch (Exception e) {
      return false;
    }
  }

  // Overload metode isWebElementDisplayed(locator) koja prima WebElement (za Page Factory)
  protected boolean isWebElementDisplayed (WebElement element) {
    log.debug("isWebElementDisplayed()");
    try {
      return element.isDisplayed();
    }
    catch (Exception e) {
      return false;
    }
  }

  //------------------------------------------------------------------------------------------------------------------

  // KUCANJE TEXTA - type, clearAndType, getText
  protected void typeTextToWebElement(WebElement element, String text) {
    log.trace("typeTextToWebElement(" + element + "," + text +")");
    element.sendKeys(text);
  }

  protected void clearAndTypeTextToWebElement(WebElement element, String text) {
    log.trace("clearAndTypeTextToWebElement(" + element + "," + text +")");
    element.clear(); //cistimo predhodno jer sendKeys() samo dodaje, ne pise preko postejeceg
    element.sendKeys(text);
  }

  protected String getTextFromWebElement(WebElement element) {
    log.trace("getTextFromWebElement(" + element + ")");
    return element.getText();
  }

  // Sadrzaj text input fielda ne mozemo preko el.getText() vec moras el.getAtrribute("value")
  // Opsta metoda - da uzmemo sadrzaj bilo kog atributa
  protected String getAttributeFromWebElement(WebElement element, String attribute) {
    log.trace("getAttributeFromWebElement(" + element + "," + attribute +")");
    return element.getAttribute(attribute);
  }

  //Da uzmemo sadrzaj 'value' atributa (npr. kada se neki text upise u text field)
  protected String getValueFromWebElement(WebElement element) {
    log.trace("getValueFromWebElement(" + element +")");
    return getAttributeFromWebElement(element, "value");
  }

  // Nekad se desava da se value promeni na povrsini, i da nam vrati praznu vrednost ili staru vrednost
  // U tom slucaju moramo da koristimo Javascript executor
  // Napomena: ako nesto uspemo da uradimo pomocu Seleniuma, i obican korisnik ce sigurno moci to da uradi
  // Ako smo uspeli nesto da uradimo preko JS, to ne znaci da ce obican korisnik moci da uradi - npr drag and drop, treba probati i manuelno!
  protected String getValueFromWebElementJS(WebElement element) {
    log.trace("getValueFromWebElementJS(" + element +")");
    JavascriptExecutor js = (JavascriptExecutor) driver;
    return (String) js.executeScript("return arguments[0].value", element);
  }

  //------------------------------------------------------------------------------------------------------------------
  // KLIK NA ELEMENT

  // Najjednostavnija wrap metoda za klik na element, ne ceka da bude klikable
  protected void clickOnWebElement(WebElement element) {
    log.trace("clickOnWebElement(" + element +")");
    element.click(); // Seleniumov klik
  }

  // Overload metode od gore - ceka da bude klikable
  protected void clickOnWebElement(WebElement element, int timeOut) {
    log.trace("clickOnWebElement(" + element + "," + timeOut + ")");
    WebElement webElement = waitForElementToBeClickable(element, timeOut);
    webElement.click();
  }

  //Ako ne mozemo da kliknemo preko Selenium el.click(), mozemo da radimo preko JS:
  protected void clickOnWebElementJS(WebElement element) {
    log.trace("clickOnWebElementJS(" + element + ")");
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("arguments[0].click();", element);
  }

  //------------------------------------------------------------------------------------------------------------------

}
