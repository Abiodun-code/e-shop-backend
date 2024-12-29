package abioduncode.spring_e_shop.features.notAuthenticated.register;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import abioduncode.spring_e_shop.features.notAuthenticated.register.resendOtp.ResendOtpService;
import abioduncode.spring_e_shop.features.notAuthenticated.register.verifyOtp.VerifyOtpDto;
import abioduncode.spring_e_shop.features.notAuthenticated.register.verifyOtp.VerifyOtpService;
import abioduncode.spring_e_shop.models.User;

@RestController
@RequestMapping("/auth")
public class RegisterController {

  private final RegisterService registerService;

  private final ResendOtpService resendOtpService;

  private final VerifyOtpService verifyOtpService;

  public RegisterController(RegisterService registerService, ResendOtpService resendOtpService, VerifyOtpService verifyOtpService){
    this.registerService = registerService;
    this.resendOtpService = resendOtpService;
    this.verifyOtpService = verifyOtpService;
  }
  
  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegisterDto request){
    User registerMsg = registerService.registerUser(request);
    return new ResponseEntity<>(registerMsg, HttpStatus.CREATED);
  }

  @PostMapping("/resend-otp/{email}")
  public ResponseEntity<?> resendOtp(@PathVariable String email) {
    User resendMsg = resendOtpService.resendOtp(email);
    return new ResponseEntity<>(resendMsg, HttpStatus.ACCEPTED);
  }

  @PostMapping("/verify-otp/{email}")
  public ResponseEntity<?> verifyOtp(@PathVariable String email, @RequestBody VerifyOtpDto verifyOtpDto) {
    String verifyMsg = verifyOtpService.verifyOtp(verifyOtpDto, email);
    return new ResponseEntity<>(verifyMsg, HttpStatus.ACCEPTED);
  }
  
}
