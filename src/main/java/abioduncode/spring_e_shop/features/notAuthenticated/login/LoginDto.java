package abioduncode.spring_e_shop.features.notAuthenticated.login;

import lombok.Data;

@Data
public class LoginDto {
  private String email;
  private String password;
}
