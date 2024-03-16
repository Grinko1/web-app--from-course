package com.example.demo.repository;

import com.example.demo.entity.SelmagUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SelmagUserRepository extends CrudRepository<SelmagUser, Integer> {
    Optional<SelmagUser> findByUsername(String username);
}
