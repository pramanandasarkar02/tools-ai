package com.toolsai.user_service.repository;

import com.toolsai.user_service.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
}
