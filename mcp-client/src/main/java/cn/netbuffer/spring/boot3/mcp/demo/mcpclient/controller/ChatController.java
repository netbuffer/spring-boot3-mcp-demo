package cn.netbuffer.spring.boot3.mcp.demo.mcpclient.controller;

import cn.netbuffer.spring.boot3.mcp.demo.mcpclient.service.LLMChatClient;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequestMapping("/api/chat")
public class ChatController {

//    @Resource(name = "googleAIChatClient")
//    @Resource(name = "openAIChatClient")
    @Resource(name = "deepseekChatClient")
    private LLMChatClient llmChatClient;

    @GetMapping("/q")
    public String q(@RequestParam String question) {
        log.debug("user asked question: {}", question);
        return llmChatClient.q(question);
    }

    @GetMapping("/streamq")
    public Flux<String> streamq(@RequestParam String question) {
        log.debug("user asked question: {}", question);
        return llmChatClient.streamq(question);
    }

}