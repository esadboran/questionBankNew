package com.example.questionbanknew.repository;

import com.example.questionbanknew.model.Blacklist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface BlacklistRepository extends JpaRepository<Blacklist, Long> {

    Optional<Blacklist> findByToken(String token);
    List<Blacklist> findAll();

}