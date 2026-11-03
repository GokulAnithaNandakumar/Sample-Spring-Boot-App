package com.hackerrank.burger.ai;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class BurgerAiController {

    private final OrderAiService service;

    public BurgerAiController(OrderAiService service) {
        this.service = service;
    }

    @PostMapping("/recommend")
    public RecommendationResponse recommend(@RequestBody RecommendationRequest request) {
        if (request == null || request.message() == null || request.message().isBlank()) {
            return new RecommendationResponse("Please enter a message.");
        }
        // placeholder recommendation (Spring AI call can be added here)
        return new RecommendationResponse("Try our Veg-CrunchBurger for a crunchy vegetarian option.");
    }

    @GetMapping("/order/{id}/status")
    public AiResponse status(@PathVariable Integer id) {
        return service.generateStatusMessage(id);
    }

    @GetMapping("/order/{id}/cancel")
    public AiResponse cancel(@PathVariable Integer id) {
        return service.generateCancellationAdvice(id);
    }

    public record RecommendationRequest(String message) {
    }

    public record RecommendationResponse(String recommendation) {
    }
}
