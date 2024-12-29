package abioduncode.spring_e_shop.features.notAuthenticated.register;

import abioduncode.spring_e_shop.models.Role;
import lombok.Data;

@Data
public class RegisterDto {
  
  private String firstName;

  private String lastName;

  private String email;

  private String password;

  private Role role;
}
