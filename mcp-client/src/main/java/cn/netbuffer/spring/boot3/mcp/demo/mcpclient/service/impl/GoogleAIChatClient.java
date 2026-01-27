package cn.netbuffer.spring.boot3.mcp.demo.mcpclient.service.impl;

import cn.netbuffer.spring.boot3.mcp.demo.mcpclient.service.LLMChatClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Slf4j
@Component
public class GoogleAIChatClient implements LLMChatClient {

    private ChatClient googleAiChatClient;

    public GoogleAIChatClient(GoogleGenAiChatModel googleGenAiChatModel, ToolCallbackProvider toolCallbackProvider) {
        this.googleAiChatClient = ChatClient.builder(googleGenAiChatModel)
                .defaultToolCallbacks(toolCallbackProvider)
                .defaultSystem("你是一个全能的人工智能助手，可以回答任何问题。")
                .build();
        log.info("init GoogleAIChatClient");
    }

    @Override
    public String q(String prompt) {
        return googleAiChatClient.prompt(prompt).call().content();
    }

    @Override
    public Flux<String> streamq(String prompt) {
        return googleAiChatClient.prompt(prompt).stream().content();
    }

}
