package com.springai.practica.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ChatService {

    private final ChatClient chatClient;

    public ChatService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public Flux<String> chat(String mensaje){
        return chatClient.prompt()
                .user(mensaje)
                .advisors(
                        advisorSpec -> advisorSpec.param(
                                ChatMemory.CONVERSATION_ID,
                                "consola"
                        )
                )
                .stream()
                .content();
    }
}
