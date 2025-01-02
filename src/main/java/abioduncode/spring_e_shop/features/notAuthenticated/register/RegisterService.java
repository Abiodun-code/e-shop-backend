package abioduncode.spring_e_shop.features.notAuthenticated.register;

import java.time.LocalDateTime;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import abioduncode.spring_e_shop.exceptions.CustomException;
import abioduncode.spring_e_shop.models.Role;
import abioduncode.spring_e_shop.models.User;
import abioduncode.spring_e_shop.repository.UserRepo;
import abioduncode.spring_e_shop.utils.OTPGenerator;

@Service
public class RegisterService {

  private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

  private UserRepo userRepo;

  public RegisterService(UserRepo userRepo){
    this.userRepo = userRepo;
  }
  
  public User registerUser(RegisterDto request){

    // Check if email exist in the database
    boolean existEmail = userRepo.findByEmail(request.getEmail()).isPresent();

    if(existEmail) throw new CustomException("Email already exist");

    User user = new User();

    Integer otp = OTPGenerator.generateOTP();

    user.setFirstName(request.getFirstName());
    user.setLastName(request.getLastName());
    user.setEmail(request.getEmail());
    user.setPassword(encoder.encode(request.getPassword()));
    user.setRole(request.getRole() == null ? Role.ROLE_USER : request.getRole());
    user.setEmailVerify(false);
    user.setOtp(otp);
    user.setOtpExpire(LocalDateTime.now().plusMinutes(10));
    return userRepo.save(user);

  }
}
