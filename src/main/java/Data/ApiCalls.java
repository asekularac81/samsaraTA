package Data;


// Klasa u kojoj smestamo putanje za svaki API Endpoint (nesto pooput PageUtilsPaths)
// a zatim cemo dalje da ih koristimo u RestApiUtils klasi
public final class ApiCalls {

  //---------------------------------------------------------------------------------
  // GET /api/users/exists/{username} - Check if user with specified username exists
  public static final String CHECK_IF_USER_EXISTS = "/api/users/exists/"; //putanja

  // metoda koja pravi putanju ako imamo i parametar
  public static String createCheckIfUserExistsApiCall(String sUsername) {
    return CHECK_IF_USER_EXISTS + sUsername;
  }
  //---------------------------------------------------------------------------------
  // GET /api/users/findByUsername/{username} - Get user with specified username
  public static final String GET_USER = "/api/users/findByUsername/";

  public static String createGetUserApiCall(String sUsername) {
    return GET_USER + sUsername;
  }
  //---------------------------------------------------------------------------------
  // DELETE /api/users/deleteByUsername/{username} - Delete user with specified username
  public static final String DELETE_USER = "/api/users/deleteByUsername/";

  public static String createDeleteUserApiCall(String sUsername) {
    return DELETE_USER + sUsername;
  }


}
