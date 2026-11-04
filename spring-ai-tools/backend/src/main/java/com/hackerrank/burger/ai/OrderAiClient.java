package com.hackerrank.burger.ai;

import com.hackerrank.burger.tools.OrderTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

@Component
public class OrderAiClient {

    private final ChatClient chatClient;

    // Requirement 1:
    // TODO:
    // - Initialize the ChatClient using the provided ChatClient.Builder.
    // - Register the available tools with the ChatClient.
    public OrderAiClient(ChatClient.Builder chatClientBuilder, OrderTools orderTools) {
        this.chatClient = chatClientBuilder.defaultTools(orderTools).build();
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
