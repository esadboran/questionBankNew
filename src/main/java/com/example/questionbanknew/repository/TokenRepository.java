package com.example.questionbanknew.repository;

import com.example.questionbanknew.model.Token;
import com.example.questionbanknew.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Token findByUser(User user);
    Token findByToken(String token);
}