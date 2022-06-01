package Utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenShotUtils extends LoggerUtils {


  //Definisemo putanju gde ce da smesta screenshotove
  private static final String sScreenShotPath = System.getProperty("user.dir") + PropertiesUtils.getScreenshotsFolder();


  private static String createScreenShotPath (String sScreenShotName) {
    return sScreenShotPath + sScreenShotName + ".png";
  }

  public static void takeScreenShot (WebDriver driver, String sTestName) {
    log.trace("takeScreenShot(" + sTestName+")");
    String sPathToFile = createScreenShotPath(sTestName);

    //treba nam ziv driver
    if (WebDriverUtils.hasDriverQuit(driver)) {
      log.warn("ScreenShot for test '" + sTestName + "' could not be taken! Driver instance has quit!");
    }

    TakesScreenshot screenShot = ((TakesScreenshot) driver);
    File srcFile  = screenShot.getScreenshotAs(OutputType.FILE);
    File destFile = new File(sPathToFile);
    //sad treba da sa ovaj virt.fajl iskporiramo na pravu lokaciju
    //defaultna Java biblitoeka je dosta komplikovana pa cemo koristiti
    //Appache Commons IO biblioteku koju moramo dodati u dependancy-pom.file: Appache Commons-io 2.11.0

    try  {
      FileUtils.copyFile(srcFile,destFile);
      log.info("ScreeShot for test '" + sTestName + "' is successfully saved in  " + sPathToFile + ".");
    } catch (IOException e) {
      log.warn("ScreeShot for test '" + sTestName + "' could not be based in file " + sPathToFile + ". Message: "+ e.getMessage());
    }
  }
}
