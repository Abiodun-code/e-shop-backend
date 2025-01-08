package abioduncode.spring_e_shop.features.authenticated;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import abioduncode.spring_e_shop.models.User;
import abioduncode.spring_e_shop.repository.UserRepo;

@RestController
@RequestMapping("/api")
public class GetCurrentUserController {

  private final UserRepo userRepo;

  public GetCurrentUserController(UserRepo userRepo) {
    this.userRepo = userRepo;
  }

  @GetMapping("/current-user")
  public ResponseEntity<User> getCurrentUser() {
    try {
      // Get the authentication object from the SecurityContext
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

      // Ensure the user is authenticated
      if (authentication == null || !authentication.isAuthenticated()) {
        return ResponseEntity.status(401).body(null); // User is not authenticated
      }

      // Extract the user's email (or username) from the authentication object
      String email = authentication.getName(); // This should be set during JWT token generation

      // Fetch the user from the database using the email
      User user = userRepo.findByEmail(email)
          .orElseThrow(() -> new RuntimeException("User not found"));

      // Return the user details
      return ResponseEntity.ok(user);

    } catch (Exception e) {
      return ResponseEntity.status(500).body(null); // Internal server error
    }
  }
}
