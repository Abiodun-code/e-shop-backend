package abioduncode.spring_e_shop.features.notAuthenticated.register.resendOtp;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import abioduncode.spring_e_shop.exceptions.CustomException;
import abioduncode.spring_e_shop.models.User;
import abioduncode.spring_e_shop.repository.UserRepo;
import abioduncode.spring_e_shop.utils.OTPGenerator;

@Service
public class ResendOtpService {
  
  private final UserRepo userRepo;

  // private final EmailService emailService;

  public ResendOtpService(UserRepo userRepo){
    this.userRepo = userRepo;
    // this.emailService = emailService;
  }

  public User resendOtp(String email){
    User user = userRepo.findByEmail(email)
    .orElseThrow(()-> new CustomException("Email not found"));

    if (user.isEmailVerify()) throw new CustomException("Email already verify");

    Integer otp = OTPGenerator.generateOTP();

    user.setOtp(otp);
    user.setOtpExpire(LocalDateTime.now().plusMinutes(10));
    
    return userRepo.save(user);
  }
}
