package com.toolsai.user_service.controller;


import com.toolsai.user_service.dto.RequestUserDTO;
import com.toolsai.user_service.dto.ResponseUserDTO;
import com.toolsai.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<ResponseUserDTO> createUser(
            @Valid @RequestBody RequestUserDTO requestUserDTO
    ) {
        return ResponseEntity.ok(userService.createUser(requestUserDTO));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseUserDTO> getUserById(
            @PathVariable String userId
    ) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ResponseUserDTO> updateUser(
            @PathVariable String userId,
            @RequestBody RequestUserDTO requestUserDTO
    ) {
        return ResponseEntity.ok(userService.updateUser(userId, requestUserDTO));
    }


//    admin

    @PostMapping("/admin/{adminId}")
    public ResponseEntity<ResponseUserDTO> createAdmin(
            @PathVariable String adminId,
            @RequestBody RequestUserDTO requestUserDTO
    ) {
        return ResponseEntity.ok(userService.createAdminModerator(adminId, requestUserDTO));
    }

    @GetMapping()
    public ResponseEntity<List<ResponseUserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PatchMapping("/admin/{adminId}/ban/{userId}")
    public ResponseEntity<ResponseUserDTO> banUser(
            @PathVariable String adminId,
            @PathVariable String userId
    ) {
        return ResponseEntity.ok(userService.banUser(adminId, userId));
    }

    @PatchMapping("/admin/{adminId}/unban/{userId}")
    public ResponseEntity<ResponseUserDTO> unbanUser(
            @PathVariable String adminId,
            @PathVariable String userId
    ) {
        return ResponseEntity.ok(userService.unBanUser(adminId, userId));
    }

    @GetMapping("/admin/{adminId}")
    public ResponseEntity<Boolean> isAdmin(
            @PathVariable String adminId
    ) {
        try{
            return ResponseEntity.ok(userService.isAdmin(adminId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(false);
        }

    }

    @GetMapping("/moderator/{userId}")
    public ResponseEntity<Boolean> isModerator(
            @PathVariable String userId
    ) {
        try{
            return ResponseEntity.ok(userService.isModerator( userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(false);
        }
    }



}
