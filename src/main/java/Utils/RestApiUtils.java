package Utils;

import Data.ApiCalls;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;

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

  //public metoda koja ocekuje uspesan odgovor: True ili False, izvrsavamo poziv sa bilo kojim userom
  public static boolean checkIfUserExists(String sUsername, String sAuthUser, String sAuthPass) {
    log.trace("checkIfUserExistsApiCall(" + sUsername+ ")");
    Response response = checkIfUserExistsApiCall(sUsername, sAuthUser, sAuthPass);
    int status = response.getStatusCode();
    String sResponseBody=response.getBody().asString(); //moze i asPreetyString da vrati lepi json.
    Assert.assertEquals(status, 200, "Wrong status code in checkIfUserExists(" + sUsername+") Api. Response Body:" + sResponseBody );
    return Boolean.parseBoolean(sResponseBody); //Response Body isparsiramo kao Boolean
  }

  //overload metode gore - izvrsavamo poziv sa admin userom
  public static boolean checkIfUserExists(String sUsername) {
    log.trace("checkIfUserExistsApiCall(" + sUsername+ ")");
    return checkIfUserExists(sUsername, sAdminUser, sAdminPass);
  }

  //---------------------------------------------------------------------------------
}
