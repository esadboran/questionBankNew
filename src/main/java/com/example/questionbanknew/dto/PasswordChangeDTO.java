package com.example.questionbanknew.dto;

import lombok.Data;

@Data
public class PasswordChangeDTO {
    private String username;
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;
}