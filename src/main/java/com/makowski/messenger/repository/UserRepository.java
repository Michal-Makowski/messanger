package com.makowski.messenger.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;

import com.makowski.messenger.entity.User;

public interface UserRepository extends ListCrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<List<User>> findByUsernameContainingIgnoreCase(String username);
}
