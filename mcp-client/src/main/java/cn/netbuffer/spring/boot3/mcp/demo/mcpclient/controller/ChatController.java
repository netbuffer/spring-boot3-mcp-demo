package cn.netbuffer.spring.boot3.mcp.demo.mcpclient.controller;

import cn.netbuffer.spring.boot3.mcp.demo.mcpclient.service.DeepseekChatClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private DeepseekChatClient deepseekChatClient;

    @GetMapping("/q")
    public String q(@RequestParam String question) {
        return deepseekChatClient.q(question);
    }
}