package com.hackerrank.burger.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

@Component
public class OrderAiClient {

    private final ChatClient chatClient;

    // Requirement 1:
    // TODO:
    // - Initialize the ChatClient using the provided ChatClient.Builder.
    public OrderAiClient(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    // Requirement 2:
    // TODOs:
    // - Set the provided basicPrompt as the system prompt.
    // - Add the provided orderContext to the user prompt.
    // - Invoke the AI model and return the generated response content.
    public String generate(String basicPrompt, String orderContext) {
        return chatClient.prompt()
            .system(basicPrompt)
            .user(user -> user.text("{orderContext}")
                    .param("orderContext", orderContext))
            .call()
            .content();
    }
}
