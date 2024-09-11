package com.example.questionbanknew.service;

import com.example.questionbanknew.dto.UserCreateDTO;
import com.example.questionbanknew.dto.UserDTO;
import com.example.questionbanknew.dto.UserLoginDTO;
import com.example.questionbanknew.model.Role;
import com.example.questionbanknew.model.Token;
import com.example.questionbanknew.model.User;
import com.example.questionbanknew.repository.TokenRepository;
import com.example.questionbanknew.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    AuthenticationManager authManager;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final TokenRepository tokenRepository;
    @Autowired
    private BlacklistService blacklistService;


    public AuthService(UserRepository userRepository, JWTService jwtService, TokenRepository tokenRepository ) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;

    }

    public UserDTO register(UserCreateDTO userCreateDTO) {

        if (!isValidRole(userCreateDTO.getRole())) {
            throw new IllegalArgumentException("Invalid role: " + userCreateDTO.getRole());
        }

        // Email kontrolü (Email boş veya null olamaz)
        if (userCreateDTO.getEmail() == null || userCreateDTO.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty.");
        }

        // Username benzersiz olmalı
        if (userRepository.findByUsername(userCreateDTO.getUsername()).isPresent()) {
            throw new IllegalArgumentException("User Name already in use: " + userCreateDTO.getUsername());
        }

        // Şifre null veya boş olamaz
        if (userCreateDTO.getPassword() == null || userCreateDTO.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty.");
        }

        User user = new User();
        user.setUsername(userCreateDTO.getUsername());
        user.setPassword(encoder.encode(userCreateDTO.getPassword()));
        user.setEmail(userCreateDTO.getEmail());
        user.setRole(userCreateDTO.getRole());


        User createdUser = userRepository.save(user);

        return mapToUserCreateDTO(createdUser);
    }

    public String verify(UserLoginDTO userLoginDTO) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken
                        (userLoginDTO.getUsername(), userLoginDTO.getPassword()));

        if (authentication.isAuthenticated()) {
            Optional<User> user = userRepository.findByUsername(userLoginDTO.getUsername());
            if (user.isEmpty()) {
                throw new IllegalArgumentException("User not found");
            }

            Token existingToken = tokenRepository.findByUser(user.get());
            String newToken = jwtService.generateToken(userLoginDTO.getUsername());
            if (existingToken != null) {
                blacklistService.addBlacklistToken(existingToken.getToken());
                existingToken.setToken(newToken);
                tokenRepository.save(existingToken);
                return existingToken.getToken();
            }
            else {
                Token token = new Token();
                token.setToken(newToken);
                token.setUser(user.get());
                tokenRepository.save(token);
                return newToken;
            }
        } else {
            throw new IllegalArgumentException("Invalid email or password");
        }
    }

    // userCreateDTO'dan gelen string değeri herhangi bir Role ait mi ?
    private boolean isValidRole(Role role) {
        for (Role r : Role.values()) {
            if (r == role) {
                return true;
            }
        }
        return false;
    }

    private UserDTO mapToUserCreateDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setRole(user.getRole());
        userDTO.setEmail(user.getEmail());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setUpdatedAt(user.getUpdatedAt());
        return userDTO;
    }
}
