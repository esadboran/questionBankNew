package com.example.questionbanknew.dto;

import lombok.Data;
import com.example.questionbanknew.model.Role;


@Data
public class UserCreateDTO {
    private String username;
    private String password;
    private String email;
    private Role role;
}