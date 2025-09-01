package com.toolsai.user_service.repository;

import com.toolsai.user_service.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, String> {
}
