package cn.netbuffer.spring.boot3.mcp.demo.mcpclient.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Slf4j
@Component
public class OpenAIChatClient implements LLMChatClient {
    private ChatClient openAiChatClient;

    public OpenAIChatClient(OpenAiChatModel openAiChatModel) {
        this.openAiChatClient = ChatClient.builder(openAiChatModel)
                .defaultSystem("你是一个全能的人工智能助手，可以回答任何问题。")
                .build();
        log.info("init OpenAIChatClient");
    }

    @Override
    public String q(String prompt) {
        return openAiChatClient.prompt(prompt).call().content();
    }

    @Override
    public Flux<String> streamq(String prompt) {
        return openAiChatClient.prompt(prompt).stream().content();
    }

}
