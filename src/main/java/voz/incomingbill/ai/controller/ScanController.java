package voz.incomingbill.ai.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import voz.incomingbill.ai.service.AiAgentService;

@RestController
@Slf4j
@RequestMapping("api/scans")
public class ScanController {
    private final AiAgentService aiAgentService;

    public ScanController(AiAgentService aiAgentService) {
        this.aiAgentService = aiAgentService;
    }

    @GetMapping("process")
    public String getAdvice(){
        return aiAgentService.processAllImages();
    }

    @GetMapping("test")
    public String getTest(){
        return aiAgentService.testConnection();
    }
}
