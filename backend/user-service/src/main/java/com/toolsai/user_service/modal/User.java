package com.toolsai.user_service.modal;


import jakarta.persistence.Entity;
import lombok.Data;

//@Entity
//@Data
public class User {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;




}
