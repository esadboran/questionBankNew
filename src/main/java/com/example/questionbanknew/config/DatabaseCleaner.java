package com.example.questionbanknew.config;

import com.example.questionbanknew.repository.BlacklistRepository;
import com.example.questionbanknew.repository.TokenRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;


@Component
public class DatabaseCleaner implements CommandLineRunner {

    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private BlacklistRepository blacklistRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        tokenRepository.deleteAllInBatch();
        blacklistRepository.deleteAllInBatch();
    }
}