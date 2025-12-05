package cn.netbuffer.spring.boot3.mcp.demo.mcpclient.chat.memory;

import cn.netbuffer.spring.boot3.mcp.demo.mcpclient.message.ChatMessage;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import java.util.List;
import java.util.stream.Collectors;

public class RedisChatMemory implements ChatMemory {

    private final RedisTemplate<String, ChatMessage> redisTemplate;
    private final ListOperations listOperations;

    public RedisChatMemory(RedisTemplate<String, ChatMessage> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.listOperations = redisTemplate.opsForList();
    }

    @Override
    public void add(String conversationId, Message message) {
        ChatMessage chatMessage = new ChatMessage(message);
        listOperations.leftPush(conversationId, chatMessage);
    }

    @Override
    public void add(String conversationId, List<Message> messages) {
        List<ChatMessage> chatMessages = messages.stream()
                .map(ChatMessage::new)
                .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(chatMessages)) {
            listOperations.leftPushAll(conversationId, chatMessages);
        }
    }

    @Override
    public List<Message> get(String conversationId) {
        List<ChatMessage> chatMessages = listOperations.range(conversationId, 0, -1);
        if (CollectionUtils.isEmpty(chatMessages)) {
            return List.of();
        }
        return chatMessages.stream()
                .map(ChatMessage::toMessage)
                .collect(Collectors.toList());
    }

    @Override
    public void clear(String conversationId) {
        redisTemplate.delete(conversationId);
    }

}