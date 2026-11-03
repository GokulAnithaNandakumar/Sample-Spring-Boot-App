package com.hackerrank.burger.ai;

import org.springframework.stereotype.Component;

@Component
public class OrderPromptProvider {

    // Requirement: status message prompt
    public String statusMessagePrompt() {
        return """
            Create a short and polite customer-facing order status message.
            Use only the order context supplied by the application.
            Do not invent a delivery time or tracking information.
            Keep the response within three sentences.
            """;
    }

    // Requirement: cancellation advice prompt
    public String cancellationAdvicePrompt() {
        return """
            Create a short and polite customer-facing cancellation response.
            Use the cancellation eligibility supplied in the order context.
            Do not override or recalculate the eligibility decision.
            Keep the response within three sentences.
            """;
    }
}
