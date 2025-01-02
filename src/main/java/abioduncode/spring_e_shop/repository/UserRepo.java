package abioduncode.spring_e_shop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import abioduncode.spring_e_shop.models.User;

public interface UserRepo extends MongoRepository<User, String> {

  Optional<User>findByEmail(String email);

  // Fetch all users excluding admins
  @Query("{ 'role': 'ROLE_USER' }")
  List<User> findAllUsers();
}
