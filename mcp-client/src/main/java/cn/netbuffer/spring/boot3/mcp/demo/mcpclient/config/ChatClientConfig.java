package cn.netbuffer.spring.boot3.mcp.demo.mcpclient.config;

import cn.netbuffer.spring.boot3.mcp.demo.mcpclient.chat.memory.RedisChatMemory;
import cn.netbuffer.spring.boot3.mcp.demo.mcpclient.message.ChatMessage;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Slf4j
@Configuration
public class ChatClientConfig {

    @Bean("springAiRedisTemplate")
    @ConditionalOnProperty(prefix = "spring.ai.chat.memory.redis", name = "enabled", havingValue = "true", matchIfMissing = true)
    public RedisTemplate<String, ChatMessage> buildSpringAiRedisTemplate(
            RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, ChatMessage> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.activateDefaultTyping(
                objectMapper.getPolymorphicTypeValidator(),
                ObjectMapper.DefaultTyping.EVERYTHING,
                JsonTypeInfo.As.PROPERTY
        );
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer(objectMapper);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public ChatMemory redisChatMemory(RedisTemplate<String, ChatMessage> springAiRedisTemplate) {
        return new RedisChatMemory(springAiRedisTemplate);
    }

    @Bean
    public ChatMemoryRepository buildChatMemoryRepository() {
        log.debug("buildChatMemoryRepository use InMemoryChatMemoryRepository");
        ChatMemoryRepository repository = new InMemoryChatMemoryRepository();
        return repository;
    }

}