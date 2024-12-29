package abioduncode.spring_e_shop.features.notAuthenticated.register.verifyOtp;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import abioduncode.spring_e_shop.exceptions.CustomException;
import abioduncode.spring_e_shop.models.User;
import abioduncode.spring_e_shop.repository.UserRepo;

@Service
public class VerifyOtpService {

  private final UserRepo userRepo;

  // private final EmailService emailService;

  public VerifyOtpService(UserRepo userRepo){
    this.userRepo = userRepo;
    // this.emailService = emailService;
  }
  
  public String verifyOtp(VerifyOtpDto request, String email){

    User user = userRepo.findByEmail(email)
    .orElseThrow(()-> new CustomException("Email not found"));

    // Check if OTP has expired
    if (user.getOtpExpire().isBefore(LocalDateTime.now())) {
      throw new CustomException("OTP has expired.");
    }

    if (user.getOtp().equals(request.getOtp())) {
      user.setEmailVerify(true);
      user.setOtp(null);
      user.setOtpExpire(null);

      userRepo.save(user);
      return "User verified successfully";
    }else{
      throw new CustomException("Invalid otp");
    }
  }
}
