package com.springai.practica.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Value("${app.contexto}")
    public String contexto;

    @Value("${app.apiUrl}")
    public String apiUrl;

    @Value("${app.modelo}")
    public String modelo;

    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder()
                .maxMessages(20)
                .build();
    }

    @Bean()
    public ChatClient chatClient(ChatMemory chatMemory){

        OllamaApi ollamaApi = OllamaApi.builder()
                .baseUrl(apiUrl)
                .build();

        OllamaChatOptions options = OllamaChatOptions.builder()
                .model(modelo)
                .temperature(1.0)
                .build();

        ChatModel ollamaChatModel = OllamaChatModel.builder()
                .ollamaApi(ollamaApi)
                .defaultOptions(options)
                .build();

        return ChatClient.builder(ollamaChatModel)
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory)
                                .build()
                )
                .defaultSystem(contexto)
                .build();
    }

}
