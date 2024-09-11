package com.example.questionbanknew.service;

import com.example.questionbanknew.model.Blacklist;
import com.example.questionbanknew.repository.BlacklistRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableScheduling
public class BlacklistService {

    private final BlacklistRepository blacklistRepository;
    private final JWTService jwtService;

    @Autowired
    public BlacklistService(BlacklistRepository blacklistRepository, JWTService jwtService) {
        this.blacklistRepository = blacklistRepository;
        this.jwtService = jwtService;
    }

    public void addBlacklistToken(String token) {
        Blacklist blacklist = new Blacklist(token);
        blacklistRepository.save(blacklist);
    }

    @Scheduled(fixedRate = 1000 * 60 * 10)
    public void removeExpiredTokens() {
        System.out.println("Remove Expired Tokens Running");
        blacklistRepository.deleteAll();
    }
}