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
    }

    @Override
    public ResponseUserDTO getUserById(String userId) {
//        don't allow banned users to get their details
        return userToResponseUserDTO(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found")));
    }

    @Override
    public ResponseUserDTO updateUser(String userId, RequestUserDTO requestUserDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setFirstName(requestUserDTO.getFirstName());
        user.setLastName(requestUserDTO.getLastName());
        user.setEmail(requestUserDTO.getEmail());
        user.setPassword(requestUserDTO.getPassword());
        user.setRole(requestUserDTO.getRole());
        userRepository.save(user);

        log.info("User updated successfully with (username: " + user.getUsername() + ", id: " + user.getId() + ")");
        return userToResponseUserDTO(user);
    }

    @Override
    public List<ResponseUserDTO> getAllUsers(String adminId) {
//        check if adminId is valid
        return userRepository.findAll().stream().map(this::userToResponseUserDTO).toList();
    }

    @Override
    public ResponseUserDTO banUser(String adminId, String userId) {
//        check if adminId is valid

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setBanned(true);
        userRepository.save(user);
        return userToResponseUserDTO(user);
    }

    @Override
    public ResponseUserDTO unBanUser(String adminId, String userId) {
//        check if adminId is valid
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setBanned(false);
        userRepository.save(user);
        return userToResponseUserDTO(user);
    }

    @Override
    public ResponseUserDTO createAdminModerator(String adminId, RequestUserDTO requestUserDTO) {
//        check if adminId is valid
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

    }




    private ResponseUserDTO userToResponseUserDTO(User user) {
        ResponseUserDTO responseUserDTO = new ResponseUserDTO();
        responseUserDTO.setId(user.getId());
        responseUserDTO.setUsername(user.getUsername());
        responseUserDTO.setFirstName(user.getFirstName());
        responseUserDTO.setLastName(user.getLastName());
        responseUserDTO.setEmail(user.getEmail());
        responseUserDTO.setRole(user.getRole());
        responseUserDTO.setBanned(user.isBanned());
        return responseUserDTO;
    }
}
