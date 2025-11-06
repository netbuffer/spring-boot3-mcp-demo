package cn.netbuffer.spring.boot3.mcp.demo.mcpclient.controller;

import cn.netbuffer.spring.boot3.mcp.demo.mcpclient.component.SSEServerComponent;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/chat/sse")
@CrossOrigin(origins = {"*"})
public class SSEChatController {

    @Resource
    private SSEServerComponent sseServerComponent;

    @GetMapping(path = "events", produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    public SseEmitter events(@RequestParam("userId") String userId) {
        return sseServerComponent.events(userId);
    }

    @PostMapping(path = "send")
    public boolean send(@RequestBody Map<String, String> data) {
        return sseServerComponent.send(data.get("userId"), data.get("message"));
    }

}