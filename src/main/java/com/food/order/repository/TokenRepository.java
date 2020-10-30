package com.food.order.repository;

import com.food.order.storage.User;
import com.food.order.storage.VerificationToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TokenRepository extends MongoRepository<VerificationToken, String> {
    VerificationToken findByToken(String token);
    Optional<VerificationToken> findByUser(User user);
}
