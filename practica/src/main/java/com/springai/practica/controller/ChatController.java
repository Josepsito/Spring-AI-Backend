package com.springai.practica.controller;

import com.springai.practica.dto.ChatMessage;
import com.springai.practica.service.ChatService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(
        value = "/api/v1/chat",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.TEXT_EVENT_STREAM_VALUE
)
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public Flux<String> chat(@RequestBody ChatMessage mensaje){
        return chatService.chat(mensaje.mensaje());
    }
}
