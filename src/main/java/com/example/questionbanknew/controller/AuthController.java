package com.example.questionbanknew.controller;


import com.example.questionbanknew.dto.UserCreateDTO;
import com.example.questionbanknew.dto.UserDTO;
import com.example.questionbanknew.dto.UserLoginDTO;
import com.example.questionbanknew.service.AuthService;
import com.example.questionbanknew.dto.GenericResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class AuthController {

    private final AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<GenericResponse<UserDTO>> registerUser(@RequestBody UserCreateDTO userCreateDTO) {
        UserDTO createdUserDTO = authService.register(userCreateDTO);
        return ResponseEntity.ok(new GenericResponse<>("User registered successfully", createdUserDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<GenericResponse<String>> loginUser(@RequestBody UserLoginDTO userLoginDTO) {
        String result = authService.verify(userLoginDTO);
        return ResponseEntity.ok(new GenericResponse<>("Login successful", result));
    }



    // Exception Handler
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<GenericResponse<String>> handleInvalidRoleException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new GenericResponse<>(ex.getMessage(), null));
    }
}
