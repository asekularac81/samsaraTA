package Utils;

public class DateTimeUtils extends LoggerUtils{

  // Da bi koristili Thread.sleep u testovima (samo kad se mora)
  // wrapovacemo sleep da ne moramo u testu da hendlujemo exception koji sleep baca
  // Generalno u testu necemo da bacamo exceptione, vec ako koristis metode koje forsiraju handlovanje exceptiona
  // uvek ih oivicis sa try/catch i zatim u catchu
  // ako je bitno  - oboris test sa Assert.fail(message)
  // ako nije bitno loggujes warn log.warn(message)
  public static void wait (int seconds) {
    try {
      Thread.sleep(1000 * seconds);
    }
    catch (InterruptedException e) {
      log.warn("InterruptedException in Thread.sleep(). Message: " + e.getMessage());
    }
  }
}
