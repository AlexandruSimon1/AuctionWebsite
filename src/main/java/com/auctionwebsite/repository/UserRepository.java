package com.auctionwebsite.repository;

import com.auctionwebsite.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//JpaRepository in one of the repository that is providing the CRUD methods for our application
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByEmail(String email);
    Optional<User> findUserByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
