package Pages;

import java.time.Duration;
import Utils.LoggerUtils;
import Utils.PropertiesUtils;
import Utils.WebDriverUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public abstract class BasePage extends LoggerUtils {

  protected WebDriver driver;

  public BasePage(WebDriver driver) {
    Assert.assertFalse(WebDriverUtils.hasDriverQuit(driver), "Driver instance has quit!");
    this.driver = driver;
  }

  protected String getPageUrl(String sPath) {
    log.trace("getPageUrl(" + sPath + ")");
    return PropertiesUtils.getBaseUrl() + sPath;
  }

  protected boolean waitForUrlChange(String url, int timeOut) {
    log.trace("waitForUrlChange(" + url + ". " + timeOut + ")");
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
    return wait.until(ExpectedConditions.urlContains(url));
  }

  protected boolean waitForUrlChangeToExactUrl(String url, int timeOut) {
    log.trace("waitForUrlChangeToExactUrl(" + url + ". " + timeOut + ")");
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
    return wait.until(ExpectedConditions.urlToBe(url));
  }

  protected boolean waitUntilPageIsReady(int timeOut) {
    log.trace("waitUntilPageIsReady(" + timeOut + ")");
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
    return wait.until(driver1 -> ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete"));
  }

}
