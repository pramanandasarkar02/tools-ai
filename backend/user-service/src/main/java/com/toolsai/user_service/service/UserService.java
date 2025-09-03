package com.toolsai.user_service.service;

import com.toolsai.user_service.dto.RequestUserDTO;
import com.toolsai.user_service.dto.ResponseUserDTO;

import java.util.List;

public interface UserService {


    ResponseUserDTO createUser(RequestUserDTO requestUserDTO);

    ResponseUserDTO getUserById(String userId);

    ResponseUserDTO updateUser(String userId, RequestUserDTO requestUserDTO);

    List<ResponseUserDTO> getAllUsers();

    ResponseUserDTO banUser(String adminId, String userId);

    ResponseUserDTO unBanUser(String adminId, String userId);

    ResponseUserDTO createAdminModerator(String adminId, RequestUserDTO requestUserDTO);

    Boolean isAdmin(String adminId);

    Boolean isModerator(String userId);

    ResponseUserDTO getUserByUsername(String username);
}
