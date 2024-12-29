package abioduncode.spring_e_shop.models;

import java.time.LocalDateTime;
import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection="users")
public class User {
  
  @Id
  private String id;

  private String firstName;

  private String lastName;

  private String email;

  private String password;

  private Integer otp;

  private LocalDateTime otpExpire;

  private boolean emailVerify = false;

  private Role role = Role.USER;

  // No args constructor
  public User() {}

  // All args constructor
  public User(String id, String firstName, String lastName, String email, String password) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
  }

  // Getter and Setter
  public String getUsername() {
    return email;
  }

  public void setUsername(String email) {
    this.email = email;
  }

  // toString
  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", email='" + email + '\'' +
        ", password='" + password + '\'' +
        ", emailVerified=" + emailVerify +
        ", otp='" + otp + '\'' +
        ", otpExpiry=" + otpExpire +
        ", role=" + role +
        '}';
  }
}