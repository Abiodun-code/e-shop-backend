package abioduncode.spring_e_shop.features.notAuthenticated;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import abioduncode.spring_e_shop.exceptions.CustomException;
import abioduncode.spring_e_shop.models.User;
import abioduncode.spring_e_shop.models.UserPrincipal;
import abioduncode.spring_e_shop.repository.UserRepo;


@Service
public class UserPrincipalService implements UserDetailsService {

  private UserRepo userRepo;

  public UserPrincipalService(UserRepo userRepo) {
    this.userRepo = userRepo;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws CustomException {
    User user = userRepo.findByEmail(email)
    .orElseThrow(()-> new CustomException("Email not found"));

    return new UserPrincipal(user);
  }
  
}
