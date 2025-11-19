package cn.netbuffer.spring.boot3.mcp.demo.mcpclient.service.impl;

import cn.netbuffer.spring.boot3.mcp.demo.mcpclient.service.LLMChatClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Slf4j
@Component
public class DeepseekChatClient implements LLMChatClient {
    private ChatClient deepseekChatClient;

    public DeepseekChatClient(DeepSeekChatModel deepSeekChatModel, ToolCallbackProvider toolCallbackProvider) {
        this.deepseekChatClient = ChatClient.builder(deepSeekChatModel)
                .defaultToolCallbacks(toolCallbackProvider)
                .defaultSystem("你是一个全能的人工智能助手，可以回答任何问题。")
                .build();
        log.info("init DeepseekChatClient");
    }

    @Override
    public String q(String prompt) {
        return deepseekChatClient.prompt(prompt).call().content();
    }

    @Override
    public Flux<String> streamq(String prompt) {
        Flux<String> content = deepseekChatClient.prompt(prompt).stream().content();
        // 使用doOnNext来记录流输出，而不是消费整个流
        return content.doOnNext(s -> log.info("stream output...:{}", s));
    }

}
