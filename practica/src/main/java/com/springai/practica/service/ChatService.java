package com.springai.practica.service;

import com.springai.practica.model.Conversation;
import com.springai.practica.model.User;
import com.springai.practica.repository.ConversationRepository;
import com.springai.practica.repository.UserRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class ChatService {

    private final ChatClient chatClient;
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;

    public ChatService(ChatClient chatClient, ConversationRepository conversationRepository, UserRepository userRepository) {
        this.chatClient = chatClient;
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
    }

    private Flux<String> chat(String sessionID,String mensaje){

        System.out.println(sessionID);

        return chatClient.prompt()
                .user(mensaje)
                .advisors(
                        advisorSpec -> advisorSpec.param(
                                ChatMemory.CONVERSATION_ID,
                                sessionID
                        )
                )
                .stream()
                .content();
    }

    public Flux<String> chat_sesion(String mensaje){

        String conversationID = conversationRepository.findFirstByOrderByCreatedAtDesc()
                .orElseGet(()->{

                    Conversation conversation = new Conversation();
                    User user = userRepository.findFirstByOrderByCreatedAtDesc()
                            .orElseThrow(()->new RuntimeException("no se encontro el usuario"));

                    conversation.setId(UUID.randomUUID().toString());
                    conversation.setMessages(new ArrayList<>());
                    conversation.setUser(user);

                    conversation = conversationRepository.save(conversation);

                    return conversation;
                }).getId();

        return chat(conversationID, mensaje);
    }

    public Flux<String> chat_anon(String sessionID, String mensaje){
        return chat(sessionID, mensaje);
    }

}
