package com.springai.practica.repository;

import com.springai.practica.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation,String> {

    Optional<Conversation> findFirstByOrderByCreatedAtDesc();

}
