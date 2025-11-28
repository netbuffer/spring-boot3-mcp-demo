package cn.netbuffer.spring.boot3.mcp.demo.mcpserver2.config;

import cn.netbuffer.spring.boot3.mcp.demo.mcpserver2.tools.FileTool;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class McpToolConfig {

    @Bean
    public ToolCallbackProvider buildToolCallbackProvider(FileTool dateTimeTool) {
        return MethodToolCallbackProvider.builder().toolObjects(dateTimeTool).build();
    }

}
