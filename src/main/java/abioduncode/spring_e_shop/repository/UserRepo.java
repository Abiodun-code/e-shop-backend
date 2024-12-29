package abioduncode.spring_e_shop.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import abioduncode.spring_e_shop.models.User;

public interface UserRepo extends MongoRepository<User, String> {
  Optional<User>findByEmail(String email);
}
