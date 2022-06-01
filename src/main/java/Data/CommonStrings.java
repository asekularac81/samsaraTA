package Data;

import java.util.Properties;

import Utils.PropertiesUtils;
import org.testng.Assert;

public final class CommonStrings {

  // Dinamicka putanja do locale_en.loc/locale_yu.loc fila
  public static final String sLocaleFile = "locale_" + PropertiesUtils.getLocale() + ".loc";
  public static final String sLocalePath = "\\locale\\" + sLocaleFile;

  // Ovde pravimo varijablu za svaki string koja se mapira sa key u locale_en.loc property fajlu
  // ako pogresimo dobicemo gresku: String LOGIN_ERROR doesn't exist in file locale_en.loc! expected object to not be null

  //APP STRINGS
  public static final String LOGIN_ERROR_MESSAGE = "LOGIN_ERROR_MESSAGE";
  public static final String LOGOUT_SUCCESS_MESSAGE = "LOGOUT_SUCCESS_MESSAGE";

  //PAGE TITLES
  public static final String LOGIN_PAGE_TITLE = "LOGIN_PAGE_TITLE";
  public static final String WELCOME_PAGE_TITLE = "WELCOME_PAGE_TITLE";
  public static final String HOME_PAGE_TITLE = "HOME_PAGE_TITLE";
  public static final String USERS_PAGE_TITLE = "USERS_PAGE_TITLE";
  public static final String HEROES_PAGE_TITLE = "HEROES_PAGE_TITLE";
  public static final String GALLERY_PAGE_TITLE = "GALLERY_PAGE_TITLE";
  public static final String API_PAGE_TITLE = "API_PAGE_TITLE";
  public static final String PRACTICE_PAGE_TITLE = "PRACTICE_PAGE_TITLE";
  public static final String ADMIN_PAGE_TITLE = "ADMIN_PAGE_TITLE";

  //TAB TITLES
  public static final String HOME_TAB_TITLE = "HOME_TAB_TITLE";
  public static final String USERS_TAB_TITLE = "USERS_TAB_TITLE";
  public static final String HEROES_TAB_TITLE = "HEROES_TAB_TITLE";
  public static final String GALLERY_TAB_TITLE = "GALLERY_TAB_TITLE";
  public static final String API_TAB_TITLE = "API_TAB_TITLE";
  public static final String PRACTICE_TAB_TITLE = "PRACTICE_TAB_TITLE";
  public static final String ADMIN_TAB_TITLE = "ADMIN_TAB_TITLE";

  //BUTTON TITLES
  public static final String LOGIN_BUTTON_TITLE = "LOGIN_BUTTON_TITLE";

  //LINK TITLES
  public static final String LOGOUT_LINK_TITLE = "LOGOUT_LINK_TITLE";

  // Kad se inicijalizuje CommonStrings klasa odmah ce da se iz locale_en.loc fila u Properties mapu upucaju svi parovi kljuc:vrednost
  public static final Properties locale = PropertiesUtils.loadPropertiesFile(sLocalePath);

  // OPSTE METODA KOJA DOHVATA BILO KOJU VREDNOST IZ PROPERTY-a. Bila bi private ako pravimo posebnu metodu za svaki String
  public static String getLocaleString (String title) {
    String text = locale.getProperty(title);
    Assert.assertNotNull(text,"String " + title + " doesn't exist in file " + sLocaleFile + "!");
    return text;
  }

  // Sad mozemo da pravimo pojedinacne metode za svaki String. o
  // Ali za aplikacije kao Sign bilo bi ih previse pa mozemo i da koristimo opstu metodu getLocaleString() u samom testu
  // To ima manu da posle ne mozemo da pravimo tokenizovane stringove sa .replace() vec bi to morali da radimo u testu sto nije dobro ako bude posle izmena u app

  // Login page Strings
  public static String getLoginErrorMessage() {
    return getLocaleString("LOGIN_ERROR_MESSAGE");
  }

  public static String getLogoutSuccessMessage() {
    return getLocaleString("LOGOUT_SUCCESS_MESSAGE");
  }

  //Ovo je kako smo poceli dok nismo imali podrsku za lokalizaciju
  //  public static final String LOGOUT_SUCCESS_MESSAGE = "You have been logged out.";
  // public static final String LOGIN_ERROR_MESSAGE = "Invalid username and password.";

  // PAGE TITLES
  // public static final String LOGIN_PAGE_TITLE = "Samsara";
  //  public static final String WELCOME_PAGE_TITLE = "Hello, and welcome to our gamers page!";


  // TAB TITLES
  //  public static final String HOME_TAB_TITLE = "Home";
  //  public static final String USERS_TAB_TITLE = "Users";
  //  public static final String HEROES_TAB_TITLE = "Heroes";

  // BUTTON TITLES
  // public static final String LOGIN_BUTTON_TITLE = "Log In";

  // LINK TITLES
  // public static final String LOGOUT_LINK_TITLE = "Log Out";

}
