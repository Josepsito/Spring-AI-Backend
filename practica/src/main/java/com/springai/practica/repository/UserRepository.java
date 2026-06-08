package com.springai.practica.repository;


import com.springai.practica.model.Conversation;
import com.springai.practica.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findFirstByOrderByCreatedAtDesc();
    Optional<User> findByUsername(String username);

}
