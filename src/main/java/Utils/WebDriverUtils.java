package Utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import Data.Time;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

public class WebDriverUtils extends LoggerUtils {

  public static WebDriver setUpDriver() {
    WebDriver driver = null;

    String sBrowser = PropertiesUtils.getBrowser();
    boolean bRemote = PropertiesUtils.getRemote();
    boolean bHeadless = PropertiesUtils.getHeadless();
    String sHubUrl = PropertiesUtils.getHubUrl();
    String sDriversFolder = PropertiesUtils.getDriversFolder();

    String sPathDriverChrome = sDriversFolder + "chromedriver.exe";
    String sPathDriverFirefox = sDriversFolder + "geckodriver.exe";
    String sPathDriverEdge = sDriversFolder + "msedgedriver.exe";
    
    String sRemote = "";
    if (bRemote) {
      sRemote = "Remote";
    }

    log.debug("setUpDriver (Browser: "+ sBrowser + ", Headless: " + bHeadless+ ")" );

    try {
      switch (sBrowser) {
        case "chrome": {
          ChromeOptions options = new ChromeOptions();
          options.setHeadless(bHeadless);
          options.addArguments("--window-size=1600x900");//defaultna dimenzija prozora za headless dok pravi browser se na kraju jos i maximizuje
          //dodatna konfiguracija chrome drivera da iskljucis crome driver logove https://ivanderevianko.com/2020/04/disable-logging-in-selenium-chromedriver
          options.addArguments("--log-level=3"); //loguje samo FATAL greske za chrome driver
          options.addArguments("--disable-logging");
          if (bRemote) {
            RemoteWebDriver remoteDriver = new RemoteWebDriver(new URL(sHubUrl), options);
            remoteDriver.setFileDetector(new LocalFileDetector());
            driver = remoteDriver;
          }
          else {
            System.setProperty("webdriver.chrome.driver", sPathDriverChrome);
            driver = new ChromeDriver(options);
          }
          break;
        }
        case ("firefox") : {
          FirefoxOptions options = new FirefoxOptions();
          options.setHeadless(bHeadless);
          options.addArguments("--window-size=1600x900");
          if (bRemote) {
            RemoteWebDriver remoteDriver = new RemoteWebDriver(new URL(sHubUrl), options);
            remoteDriver.setFileDetector(new LocalFileDetector());
            driver = remoteDriver;
          }
          else {
            System.setProperty("webdriver.geckodriver.driver", sPathDriverFirefox);
            driver = new FirefoxDriver(options);
          }
          break;
        }
        case ("egde") : {
          EdgeOptions options = new EdgeOptions();
          options.setHeadless(bHeadless);
          options.addArguments("--window-size=1600x900");
          if (bRemote) {
            RemoteWebDriver remoteDriver = new RemoteWebDriver(new URL(sHubUrl), options);
            remoteDriver.setFileDetector(new LocalFileDetector());
            driver = remoteDriver;
          }
          else {
            System.setProperty("webdriver.geckodriver.driver", sPathDriverEdge);
            driver = new EdgeDriver(options);
          }
          break;
        }
        default:  {
          Assert.fail("Cannot create driver: path to browser'"+ sBrowser + "' driver is not correct");
        }
      }
    } catch (MalformedURLException e) {
      Assert.fail("Cannot create driver: path to browser'"+ sBrowser + "' driver is not correct");
    }

    //Default Timeouts
    //Implicit Wait - za instancu drivera dok je ziv ili promenimo vrednost-1sec
    //ako nema elementa - ponavalja potragu do timeouta, na svakih 500ms
    //proverava samo dal je prisutan ali ne i dali je clickable itd, za to moras explicit wait
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Time.IMPLICIT_TIMEOUT));

    //da se dom struktura stranice ucitala-3 sec
    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Time.PAGE_LOAD_TIMEOUT));

    //ovo vazi ako koristis javascript executor za izvrsavanje asinhrone javascripte
    //koliko ce driver da ceka na izvrsavanje asinhrone javascripte
    //ako stavis -1 cekace zauvek sto nije dobro. mi cemo 10 sec.
    driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(Time.ASYNC_SCRIPT_TIMEOUT));

    //Maximize browser-vazi samo za podignut browser jer headless nece da odradi maximizaciju
    driver.manage().window().maximize();

    return driver;
  }

  public static boolean hasDriverQuit(WebDriver driver){
    if (driver!=null) {
      return ((RemoteWebDriver)driver).getSessionId() == null;
    } else {
      return true;
    }
  }

  public static void quitDriver(WebDriver driver) {
    if (!hasDriverQuit(driver)) {
      driver.quit();
    }
  }
}
