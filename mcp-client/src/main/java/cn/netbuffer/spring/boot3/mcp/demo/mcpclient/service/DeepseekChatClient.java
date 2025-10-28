package cn.netbuffer.spring.boot3.mcp.demo.mcpclient.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

@Component
public class DeepseekChatClient {
    private ChatClient chatClient;

    public DeepseekChatClient(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
                .defaultSystem("你是一个全能的人工智能助手，可以回答任何问题。")
                .build();
    }

    public String q(String prompt) {
        return chatClient.prompt(prompt).call().content();
    }

}
