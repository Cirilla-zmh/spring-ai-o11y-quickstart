package com.alibaba.cloud.ai.example.observability;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class SelfCallingScheduleTask {

    @Value("${server.port}")
    private int port;

    @Autowired
    private RestTemplate restTemplate;

    @Scheduled(fixedRate = 2000)
    public void sendRequests() {
        String response = restTemplate.getForObject("http://localhost:" + port + "/joke", String.class);
        log.info("Response is " + response);
    }
}
