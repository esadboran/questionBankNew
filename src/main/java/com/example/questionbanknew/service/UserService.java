package com.example.questionbanknew.service;

import com.example.questionbanknew.dto.PasswordChangeDTO;
import com.example.questionbanknew.dto.UserDTO;
import com.example.questionbanknew.model.User;
import com.example.questionbanknew.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    private static final String USER_NOT_FOUND_MSG = "User not found with email: ";
    private static final String ALL_FIELDS_REQUIRED_MSG = "All fields are required.";
    private static final String INCORRECT_OLD_PASSWORD_MSG = "Incorrect old password.";
    private static final String PASSWORD_MISMATCH_MSG = "New password and confirm password do not match.";
    private static final String PASSWORD_SAME_AS_OLD_MSG = "New password cannot be the same as the old password.";


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("User not found for username: " + username);
        }
        return user.get();
    }



    public UserDTO getProfileByUserName(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + userName));

        return mapToDTO(user);
    }

    public void changePassword(PasswordChangeDTO passwordChangeDTO) {
        // Check if any field is missing
        if (passwordChangeDTO.getUsername() == null || passwordChangeDTO.getOldPassword() == null ||
                passwordChangeDTO.getNewPassword() == null || passwordChangeDTO.getConfirmNewPassword() == null ||
                passwordChangeDTO.getUsername().isEmpty() || passwordChangeDTO.getOldPassword().isEmpty() ||
                passwordChangeDTO.getNewPassword().isEmpty() || passwordChangeDTO.getConfirmNewPassword().isEmpty()) {
            throw new IllegalArgumentException(ALL_FIELDS_REQUIRED_MSG);
        }

        // Retrieve user by username
        User user = userRepository.findByUsername(passwordChangeDTO.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_MSG + passwordChangeDTO.getUsername()));

        // Validate old password
        if (!encoder.matches(passwordChangeDTO.getOldPassword(), user.getPassword())) {
            throw new IllegalArgumentException(INCORRECT_OLD_PASSWORD_MSG);
        }

        // Check if new password is the same as old password
        if (encoder.matches(passwordChangeDTO.getNewPassword(), user.getPassword())) {
            throw new IllegalArgumentException(PASSWORD_SAME_AS_OLD_MSG);
        }

        // Confirm new password matches the confirmation new password
        if (!passwordChangeDTO.getNewPassword().equals(passwordChangeDTO.getConfirmNewPassword())) {
            throw new IllegalArgumentException(PASSWORD_MISMATCH_MSG);
        }

        // Encode new password and update user
        user.setPassword(encoder.encode(passwordChangeDTO.getNewPassword()));
        userRepository.save(user);
    }

    // Entity'yi DTO'ya dönüştüren metot
    private UserDTO mapToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setRole(user.getRole());
        userDTO.setEmail(user.getEmail());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setUpdatedAt(user.getUpdatedAt());
        return userDTO;
    }
}
