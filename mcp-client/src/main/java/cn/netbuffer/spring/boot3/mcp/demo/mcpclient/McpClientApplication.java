package cn.netbuffer.spring.boot3.mcp.demo.mcpclient;

import cn.netbuffer.spring.boot3.mcp.demo.mcpcommon.utils.DotEnvUtils;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class McpClientApplication {

    public static Dotenv DOTENV;

    /**
     * Main entry point for the MCP Client application.
     * This method initializes the environment and starts the Spring Boot application.
     *
     * @param args Command line arguments passed to the application
     */

    // Initialize environment variables from .env file
    public static void main(String[] args) {
        // Launch the Spring Boot application
        DOTENV = DotEnvUtils.initDotEnv2SystemProperty("mcp-client.env");
        SpringApplication.run(McpClientApplication.class, args);
    }

}
