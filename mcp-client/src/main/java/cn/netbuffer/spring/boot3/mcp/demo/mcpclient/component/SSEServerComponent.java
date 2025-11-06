package cn.netbuffer.spring.boot3.mcp.demo.mcpclient.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class SSEServerComponent {

    private static final Map<String, SseEmitter> sseClients = new ConcurrentHashMap<>();

    public SseEmitter connect(String userId) {
        SseEmitter sseEmitter = new SseEmitter(0L);
        sseEmitter.onTimeout(() -> {
            log.warn("SSE连接超时，用户ID为：{}", userId);
        });
        sseEmitter.onCompletion(() -> {
            log.info("SSE连接关闭，用户ID为：{}", userId);
        });
        sseEmitter.onError((throwable) -> {
            log.error("SSE连接出错，用户ID为：" + userId, throwable);
        });
        sseClients.put(userId, sseEmitter);
        log.info("SSE连接创建成功，连接的用户ID为：{}", userId);
        return sseEmitter;
    }

    public boolean send(String userId, String message) {
        boolean result = true;
        SseEmitter sseEmitter = sseClients.get(userId);
        try {
            sseEmitter.send(message);
        } catch (IOException e) {
            result = false;
            log.error("发送消息失败，用户ID为：" + userId, e);
        } finally {
            return result;
        }
    }

}
