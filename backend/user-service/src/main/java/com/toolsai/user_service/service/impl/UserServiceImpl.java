package com.toolsai.user_service.service.impl;

import com.toolsai.user_service.dto.RequestUserDTO;
import com.toolsai.user_service.dto.ResponseUserDTO;
import com.toolsai.user_service.modal.Role;
import com.toolsai.user_service.modal.User;
import com.toolsai.user_service.repository.UserRepository;
import com.toolsai.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public ResponseUserDTO createUser(RequestUserDTO requestUserDTO) {
        try {
            User user = new User();
            user.setUsername(requestUserDTO.getUsername());
            user.setFirstName(requestUserDTO.getFirstName());
            user.setLastName(requestUserDTO.getLastName());
            user.setEmail(requestUserDTO.getEmail());
            user.setPassword(requestUserDTO.getPassword());
            user.setBanned(false);
            user.setRole(Role.USER);


            user = userRepository.save(user);

            log.info("User created successfully with (username: " + user.getUsername() + ", id: " + user.getId() + ")");

            return userToResponseUserDTO(user);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseUserDTO getUserById(String userId) {
        try{
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            if (user.isBanned()) {
                throw new RuntimeException("User is banned");
            }
            return userToResponseUserDTO(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public ResponseUserDTO updateUser(String userId, RequestUserDTO requestUserDTO) {
        try{
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            user.setFirstName(requestUserDTO.getFirstName());
            user.setLastName(requestUserDTO.getLastName());
            user.setEmail(requestUserDTO.getEmail());
            user.setPassword(requestUserDTO.getPassword());
            user.setRole(requestUserDTO.getRole());
            userRepository.save(user);

            log.info("User updated successfully with (username: " + user.getUsername() + ", id: " + user.getId() + ")");
            return userToResponseUserDTO(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<ResponseUserDTO> getAllUsers() {
        try {
            return userRepository.findAll().stream().map(this::userToResponseUserDTO).toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public ResponseUserDTO banUser(String adminId, String userId) {
        try{
            if (!isAdmin(adminId)) {
                throw new RuntimeException("Admin not found");
            }
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            user.setBanned(true);
            userRepository.save(user);
            return userToResponseUserDTO(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public ResponseUserDTO unBanUser(String adminId, String userId) {

        if (!isAdmin(adminId)) {
            throw new RuntimeException("Admin not found");
        }
        try {
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            user.setBanned(false);
            userRepository.save(user);
            return userToResponseUserDTO(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public ResponseUserDTO createAdminModerator(String adminId, RequestUserDTO requestUserDTO) {
        if (!isAdmin(adminId)) {
            throw new RuntimeException("Admin not found");
        }
        try {
            User user = new User();
            user.setUsername(requestUserDTO.getUsername());
            user.setFirstName(requestUserDTO.getFirstName());
            user.setLastName(requestUserDTO.getLastName());
            user.setEmail(requestUserDTO.getEmail());
            user.setPassword(requestUserDTO.getPassword());
            user.setRole(Role.MODERATOR);

            user = userRepository.save(user);

            log.info("Moderator created successfully with (username: " + user.getUsername() + ", id: " + user.getId() + ")");

            return userToResponseUserDTO(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public Boolean isAdmin(String adminId) {
        try{
            User user = userRepository.findById(adminId).orElseThrow(() -> new RuntimeException("User not found"));
            return user.getRole() == Role.ADMIN;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Boolean isModerator(String userId) {
        try{
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            return user.getRole() == Role.MODERATOR;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    private ResponseUserDTO userToResponseUserDTO(User user) {
        try{
            ResponseUserDTO responseUserDTO = new ResponseUserDTO();
            responseUserDTO.setId(user.getId());
            responseUserDTO.setUsername(user.getUsername());
            responseUserDTO.setFirstName(user.getFirstName());
            responseUserDTO.setLastName(user.getLastName());
            responseUserDTO.setEmail(user.getEmail());
            responseUserDTO.setRole(user.getRole());
            responseUserDTO.setBanned(user.isBanned());
            responseUserDTO.setCreatedAt(user.getCreatedAt());
            responseUserDTO.setUpdatedAt(user.getUpdatedAt());
            return responseUserDTO;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
