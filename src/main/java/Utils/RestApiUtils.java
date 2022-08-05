package Utils;

import Data.ApiCalls;
import Objects.User;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;

// Klasa koja hendluje celu REST API logiku preko RestAssured-a
public class RestApiUtils extends LoggerUtils{

  // http://18.219.75.209:8080/api - ovde su svi API pozivi

  public static final String BASE_URL = PropertiesUtils.getBaseUrl(); //zajednicko za sve API pozive
  public static final String sAdminUser = PropertiesUtils.getAdminUsername(); //ako ocemo poziv da uradimo sa adminom
  public static final String sAdminPass = PropertiesUtils.getAdminPassword();

  //---------------------------------------------------------------------------------
  // Metode za samo jedan API poziv: GET /api/users/exists/{username} - Check if user with specified username exists

  // Opsta privatna metodu koja izvrsi GET /api/users/exists/{username} i vrati CEO Response (moze biti successful ili error)
  private static Response checkIfUserExistsApiCall(String sUsername, String sAuthUser, String sAuthPass) {
    String sApiCall = BASE_URL + ApiCalls.createCheckIfUserExistsApiCall(sUsername); //kad se izvrsi daje http://18.219.75.209:8080/api/users/exists/sUsername
    log.info("API CALL: " + sApiCall);
    Response response = null;
    try {
      //pakujemo APi poziv: authentifikacija + header + body
      //Given-prerrequisites
      //When-steps
      //Then-expected result (pass/fail) criteria, ali mi to necemo vec cemo da vratimo ceo Response
      response = RestAssured.given().auth().basic(sAuthUser, sAuthPass) //samsara koristi basic auth
          .headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
          .when().get(sApiCall);
      }
    catch (Exception e) {
      Assert.fail("Exception in checkIfUserExistsApiCall(" +  sUsername+ ") Api call: " + e.getMessage());
    }
    return response;
  }

  // Public metoda koja ocekuje uspesan odgovor: True ili False, izvrsavamo poziv sa bilo kojim userom
  public static boolean checkIfUserExists(String sUsername, String sAuthUser, String sAuthPass) {
    log.trace("checkIfUserExistsApiCall(" + sUsername + ")");
    Response response = checkIfUserExistsApiCall(sUsername, sAuthUser, sAuthPass);
    int status = response.getStatusCode();
    String sResponseBody = response.getBody().asString(); //moze i asPreetyString da vrati lepi json.

    Assert.assertEquals(status, 200, "Wrong status code in checkIfUserExists(" + sUsername + ") Api. Response Body:" + sResponseBody);
    //posto moze da se desi da je status 200 ali da body ne bude T/F, onda cemo dadati i ovu proveru pre nego vratimo parsirano Boolean vrednost Body-a
    //ovo treba izvuci u zasebnu help metodu jer ce nam jos trebati
    String result = sResponseBody.toLowerCase();
    if (!(result.equals("true") || result.equals("false"))) {
      Assert.fail("Cannot convert response " + sResponseBody + " to boolean value!");
    }
    return Boolean.parseBoolean(sResponseBody); //Response Body isparsiramo kao Boolean
  }

  // Overload metode gore - izvrsavamo poziv sa admin userom
  public static boolean checkIfUserExists(String sUsername) {
    log.trace("checkIfUserExistsApiCall(" + sUsername+ ")");
    return checkIfUserExists(sUsername, sAdminUser, sAdminPass);
  }

  //---------------------------------------------------------------------------------

  // Metode za API poziv: GET /api/users/findByUsername/{username} - Get user with specified username
  // Opsta private metoda koja izvrsi GET poziv i vrati ceo odgovor
  private static Response getUserApiCall(String sUsername, String sAuthUser, String sAuthPass) {
    String sApiCall = BASE_URL + ApiCalls.createGetUserApiCall(sUsername);
    Response response = null;
    try {
      response = RestAssured.given().auth().basic(sAuthUser, sAuthPass)
          .headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
          .when().get(sApiCall);
    }
    catch (Exception e) {
      Assert.fail("Exception in getUserApiCall(" +  sUsername+ ") Api call: " + e.getMessage());
    }
    return response;
  }

  // Public metoda koja u slucaju uspesnog odgovora vrati ceo json
  public static String getUserJsonFormat(String sUsername, String sAuthUser, String sAuthPass) {
    log.trace("getUserJsonFormat(" + sUsername + ")");
    Assert.assertTrue(checkIfUserExists(sUsername, sAuthUser, sAuthPass), "User " + sUsername + " doesn't exist!");
    Response response = getUserApiCall(sUsername, sAuthUser, sAuthPass);
    int status = response.getStatusCode();
    String sResponseBody = response.getBody().asPrettyString(); //ovde cemo PreetyString jer je odgovor u JSon Formatu.
    Assert.assertEquals(status, 200, "Wrong status code in getUserJsonFormat(" + sUsername + ") Api. Response Body:" + sResponseBody);
    return sResponseBody;
  }

  // Overload metode gore da je izvrsi sa adminom
  public static String getUserJsonFormat(String sUsername) {
    return getUserJsonFormat(sUsername, sAdminUser, sAdminPass);
  }

  // DESERIJALIZACIJA:
  // Gson pogleda klasu User i vidi koji su njeni atributi i uparuje ih po imenu i tipu sa onim u jsonu koji smo mu da
  // tip - dozvoljeno je implicitno castovanje Integer-int...
  // ono sto nije prepoznato, nece da se deserijalizuje
  // napravice se objekat User koji ce da popuni atribute samo sa onim sto ima

  public static User getUser(String sUsername, String sAuthUser, String sAuthPass) {
    log.debug("getUser(" + sUsername + ")");
    String json = getUserJsonFormat(sUsername, sAuthUser, sAuthPass);
    Gson gson = new Gson();
    return gson.fromJson(json, User.class);
  }

  // Overload metode gore, izvrsavamo poziv sa admin userom
  public static User getUser(String sUsername) {
    return getUser (sUsername, sAdminUser, sAdminPass);
  }

  //---------------------------------------------------------------------------------

  // Metode za API poziv: DELETE /api/users/deleteByUsername/{username} - Delete user with specified username - PRIVATE
  private static Response deleteUserApiCall(String sUsername, String sAuthUser, String sAuthPass) {
    String sApiCall = BASE_URL + ApiCalls.createDeleteUserApiCall(sUsername);
    Response response = null;
    try {
      response = RestAssured.given().auth().basic(sAuthUser, sAuthPass)
          .headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
          .when().delete(sApiCall);
    }
    catch (Exception e) {
      Assert.fail("Exception in deleteUserApiCall(" +  sUsername+ ") Api call: " + e.getMessage());
    }
    return response;
  }

  // Uspesan odgovor na delete user je code 200, nema Json Body i ne treba nam dohvatanje u JSON Formatu vec samo
  public static void  deleteUser(String sUsername, String sAuthUser, String sAuthPass) {
    log.debug("deleteUser(" + sUsername + ")");
    Assert.assertTrue(checkIfUserExists(sUsername, sAuthUser, sAuthPass), "User " + sUsername + " doesn't exist!");
    Response response = deleteUserApiCall(sUsername,sAuthUser,sAuthPass);
    int status = response.getStatusCode();
    String sResponseBody = response.getBody().asString();
    Assert.assertEquals(status, 200, "Wrong status code in deleteUser(" + sUsername + ") Api call. Response Body:" + sResponseBody);
    log.debug("User Deleted: " + checkIfUserExists(sUsername, sAuthUser,sAuthPass));
  }

  // Overload metode, izvrsavamo poziv sa admin userom
  public static void  deleteUser(String sUsername) {
    log.debug("deleteUser(" + sUsername + ")");
    deleteUser(sUsername, sAdminUser, sAdminPass);
  }
}
