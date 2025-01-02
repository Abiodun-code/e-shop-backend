package abioduncode.spring_e_shop.features.authenticated;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import abioduncode.spring_e_shop.models.User;
import abioduncode.spring_e_shop.repository.UserRepo;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api")
public class GetAllUserController {

  private final UserRepo userRepo;

  public GetAllUserController(UserRepo userRepo) {
    this.userRepo = userRepo;
 
  }

  @GetMapping("/admin/users")
  @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepo.findAllUsers();
        return ResponseEntity.ok(users);
    }
  
}
