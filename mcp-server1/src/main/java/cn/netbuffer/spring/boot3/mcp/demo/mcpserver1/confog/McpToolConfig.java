package cn.netbuffer.spring.boot3.mcp.demo.mcpserver1.confog;

import cn.netbuffer.spring.boot3.mcp.demo.mcpserver1.tools.DateTimeTool;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class McpToolConfig {

    @Bean
    public ToolCallbackProvider buildToolCallbackProvider(DateTimeTool dateTimeTool) {
        return MethodToolCallbackProvider.builder().toolObjects(dateTimeTool).build();
    }

}
