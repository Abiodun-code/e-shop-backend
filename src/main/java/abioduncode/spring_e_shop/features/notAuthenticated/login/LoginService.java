package abioduncode.spring_e_shop.features.notAuthenticated.login;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import abioduncode.spring_e_shop.exceptions.CustomException;
import abioduncode.spring_e_shop.features.jwt.JwtService;
import abioduncode.spring_e_shop.models.User;
import abioduncode.spring_e_shop.repository.UserRepo;

@Service
public class LoginService {

  private final JwtService jwtService;

  private final UserRepo userRepo;

  private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


  private final AuthenticationManager authManager;

  public LoginService(JwtService jwtService, UserRepo userRepo, AuthenticationManager authManager) {
    this.jwtService = jwtService;
    this.userRepo = userRepo;
    this.authManager = authManager;
  }
  
  public Map<String, Object> loginUser(LoginDto request){

    User user = userRepo.findByEmail(request.getEmail())
    .orElseThrow(()->new CustomException("Email not found"));

    if(!encoder.matches(request.getPassword(), user.getPassword())){
      throw new CustomException("Incorrect password");
    }

    Authentication auth = authManager.authenticate(
      new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
    );

    if(auth.isAuthenticated()){
      String accessToken = jwtService.generateToken(request.getEmail());
      String refreshToken = jwtService.generateRefreshToken(request.getEmail());

      Map<String, Object> response = new HashMap<>();
      response.put("accessToken", accessToken);
      response.put("refreshToken", refreshToken);

      return response;
    }else{
      throw new CustomException("Authentication failed");
    }
  }
}
