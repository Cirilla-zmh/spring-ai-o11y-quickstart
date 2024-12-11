package com.alibaba.cloud.ai.example.observability;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@SpringBootApplication
@EnableScheduling
public class ObservabilityApplication {

    public static void main(String[] args) {
        SpringApplication.run(ObservabilityApplication.class, args);
    }

    @Bean
    ChatClient chatClient(ChatClient.Builder builder) {
        return builder.build();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public OpenTelemetry getOpenTelemetry() {
        return GlobalOpenTelemetry.get();
    }
}

@Controller
@ResponseBody
class JokeController {

    private final ChatClient chatClient;

    JokeController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/joke")
    Map<String, String> joke() {
        var reply = chatClient
                .prompt()
                .user("""
                        tell me a joke. be concise. don't send anything except the joke.
                        """)
                .call()
                .content();
        return Map.of("joke", reply, "traceId", Span.current().getSpanContext().getTraceId());
    }
}
