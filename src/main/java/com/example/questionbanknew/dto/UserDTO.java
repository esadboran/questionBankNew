package com.example.questionbanknew.dto;

import com.example.questionbanknew.model.Role;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private String username;
    private Role role;
    private String email;
    private LocalDateTime createdAt;  // Oluşturulma zamanı
    private LocalDateTime updatedAt;  // Güncellenme zamanı
}