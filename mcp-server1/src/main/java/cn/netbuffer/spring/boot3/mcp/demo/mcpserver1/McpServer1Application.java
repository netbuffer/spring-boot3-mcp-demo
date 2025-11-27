package cn.netbuffer.spring.boot3.mcp.demo.mcpserver1;

import cn.netbuffer.spring.boot3.mcp.demo.mcpcommon.utils.DotEnvUtils;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class McpServer1Application {

    public static Dotenv DOTENV;

    // Initialize environment variables from .env file
    public static void main(String[] args) {
        // Launch the Spring Boot application
        DOTENV = DotEnvUtils.initDotEnv2SystemProperty("mcp-server1.env");
        SpringApplication.run(McpServer1Application.class, args);
    }

}
