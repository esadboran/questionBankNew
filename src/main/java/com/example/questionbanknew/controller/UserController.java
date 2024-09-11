package com.example.questionbanknew.controller;


import com.example.questionbanknew.dto.GenericResponse;
import com.example.questionbanknew.dto.PasswordChangeDTO;
import com.example.questionbanknew.dto.UserDTO;
import com.example.questionbanknew.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<GenericResponse<UserDTO>> getProfile(@RequestParam String username) {
        UserDTO userDTO = userService.getProfileByUserName(username);
        return ResponseEntity.ok(new GenericResponse<>("Profile fetched successfully", userDTO));
    }


    @PutMapping("/change-password")
    public ResponseEntity<GenericResponse<Void>> changePassword(@RequestBody PasswordChangeDTO passwordChangeDTO) {
        userService.changePassword(passwordChangeDTO);
        return ResponseEntity.ok(new GenericResponse<>("Password changed successfully", null));
    }

    // Exception Handler
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<GenericResponse<String>> handleInvalidRoleException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new GenericResponse<>(ex.getMessage(), null));
    }

}
