package Objects;

import java.util.List;

import io.netty.handler.codec.string.LineSeparator;

public class Users {

  //Aributi Users klase su sve sto postoji u jsonu kad izvrsis getUsers
  private String username;
  private String password;
  private String email;
  private String firstname;
  private String lasttname;
  private String about;
  private String secretQuestion;
  private String secretAnswer;
  private String createdAt;
  private String heroCount;
  private List<Hero> heroes;

  private Users (String username, String password, String email, String firstname, String lasttname, String about, String secretQuestion, String secretAnswer, String createdAt, String heroCount, List heroes) {
    this.username=username;
    this.password=password;
    this.email=email;
    this.firstname=firstname;
    this.lasttname=lasttname;
    this.about=about;
    this.secretQuestion=secretQuestion;
    this.secretAnswer=secretAnswer;
    this.createdAt=createdAt;
    this.heroCount=heroCount;
    this.heroes=heroes;
  }
}
