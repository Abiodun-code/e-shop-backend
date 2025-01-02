package abioduncode.spring_e_shop.features.notAuthenticated.login;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import abioduncode.spring_e_shop.features.notAuthenticated.login.refreshToken.RefreshTokenService;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
public class LoginController {
  
  private final LoginService loginService;

  private final RefreshTokenService refreshTokenService;

  public LoginController(LoginService loginService, RefreshTokenService refreshTokenService){
    this.loginService = loginService;
    this.refreshTokenService = refreshTokenService;
  }

  @PostMapping("/login")
  public ResponseEntity<?> signIn(@RequestBody LoginDto request) {
    
    Map<String, Object> tokens = loginService.loginUser(request); // Returns accessToken and refreshToken
    
    return new ResponseEntity<>(tokens, HttpStatus.OK);
  }

  @GetMapping("/refresh-token/{refreshToken}")
  public ResponseEntity<Map<String, String>> refreshToken(@PathVariable String refreshToken) {
    
    Map<String, String> tokens = refreshTokenService.refreshAccessToken(refreshToken);
    
    return ResponseEntity.ok(tokens);
  }
  
}