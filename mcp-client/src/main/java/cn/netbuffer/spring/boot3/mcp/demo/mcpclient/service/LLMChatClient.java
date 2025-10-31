package cn.netbuffer.spring.boot3.mcp.demo.mcpclient.service;

import reactor.core.publisher.Flux;

public interface LLMChatClient {
    String q(String prompt);
    Flux<String> streamq(String prompt);
}
