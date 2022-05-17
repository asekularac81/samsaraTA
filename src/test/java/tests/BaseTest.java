package tests;

import Utils.LoggerUtils;
import Utils.WebDriverUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;

//BasePage extenduje LoggerUtils da bi logovanje bilo centralizovano.
public class BaseTest extends LoggerUtils {

  protected WebDriver setupDriver() {
    log.debug("setupDriver");
    return WebDriverUtils.setUpDriver();
  }

  protected void quitDriver(WebDriver driver) {
    log.debug("quitDriver");
    WebDriverUtils.quitDriver(driver);

  }

  protected void tearDown(WebDriver driver, ITestResult testResult) {
    //ovde ide brisanje usera iz baze, ubijanje drivera, quit drivera
    //koristimo catch da se nebi desilo da test padne u tearDownu a da se prikazuje kao da je test pao
    String sTestName = testResult.getTestClass().getName();
    log.debug("tearDown[" + sTestName + ")");
    try {
      if (testResult.getStatus() == ITestResult.FAILURE) {
        //take screenshot
        log.warn("Test " + sTestName + " has failed");
      }
      Assert.fail("padanje");
    } catch (AssertionError | Exception e) {
      log.error("Exception occurred in tearDown(" + sTestName + "). Message:" + e.getMessage());
    }
    finally {
      quitDriver(driver);
    }
  }
}
