package Data;


//Poput PageUtilsPaths gde smestamo sve URL pathove, tako cemo vode da stavimo sve API endpointe, jer necemo da ih hardkodujemo u RestApiUtils
public final class ApiCalls {

  //---------------------------------------------------------------------------------
  //GET /api/users/exists/{username} - Check if user with specified username exists.
  public static final String CHECK_IF_USER_EXISTS = "/api/users/exists/";

  public static String createCheckIfUserExistsApiCall(String sUsername) {
    return CHECK_IF_USER_EXISTS + sUsername;
  }
  //---------------------------------------------------------------------------------


}
