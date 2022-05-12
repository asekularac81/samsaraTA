package Utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties; //ispod haube ova klasa je mapa - kljuc i vrednost

import org.testng.Assert;

public class PropertiesUtils extends LoggerUtils {

  private static final String sPropertiesPath = "common.properties";
  private static final Properties properties = loadPropertiesFile();

  public static Properties loadPropertiesFile(String sFilePath) {
    InputStream inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream(sFilePath);
    Properties properties = new Properties();
    try {
      properties.load(inputStream);
    }
    catch (IOException e) {
      Assert.fail("Cannot load " + sFilePath + " file!. Message: " + e.getMessage());
    }
    return properties;
  }

  private static Properties loadPropertiesFile() {
    return loadPropertiesFile(sPropertiesPath);
  }

  private static String getProperty(String sProperty) {
    String sResult = properties.getProperty(sProperty);
    Assert.assertNotNull(sResult, "Cannot find property " + sProperty + "' in " + sPropertiesPath + "file!");
    return sResult;
  }

  public static String getEnvironment() {
    return getProperty("environment");
  }

  public static String getLocalBaseUrl() {
    return getProperty("localBaseUrl");
  }

  public static String getTestBaseUrl() {
    return getProperty("testBaseUrl");
  }

  public static String getProdBaseUrl() {
    return getProperty("prodBaseUrl");
  }

  public static String getBaseUrl() {
    String sEnvironment =getEnvironment().toLowerCase();
    String sBaseUrl = null;
    switch(sEnvironment) {
      case ("local") : {
        sBaseUrl = getLocalBaseUrl();
        break;
      }
      case ("test") : {
        sBaseUrl = getTestBaseUrl();
        break;
      }
      case ("prod") : {
        sBaseUrl = getProdBaseUrl();
        break;
      }

      default: {
        Assert.fail("Cannot get BaseUrl! Environment '" + sEnvironment + "' is not recognized!");
      }
    }
    return sBaseUrl;
  }


  public static String getBrowser () {
    return getProperty("browser");
  }

  public static boolean getRemote () {
    return Boolean.parseBoolean(getProperty("remote"));
  }

  public static boolean getHeadless() {
    return Boolean.parseBoolean(getProperty("headless"));
  }

  public static String getHubUrl() {
    return getProperty("hubUrl");
  }

  public static String getAdminUsername () {
    return getProperty("adminUsername");
  }

  public static String getAdminPassword () {
    return getProperty("adminPassword");
  }

  public static String getRootUsername () {
    return getProperty("rootUsername");
  }

  public static String getRootPassword () {
    return getProperty("rootPassword");
  }

  public static String getDriversFolder () {
    return getProperty("driversFolders");
  }
}