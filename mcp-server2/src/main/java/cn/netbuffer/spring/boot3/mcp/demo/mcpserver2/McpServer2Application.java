package cn.netbuffer.spring.boot3.mcp.demo.mcpserver2;

import cn.netbuffer.spring.boot3.mcp.demo.mcpcommon.utils.DotEnvUtils;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class McpServer2Application {

    public static Dotenv DOTENV;

    public static void main(String[] args) {
        DOTENV = DotEnvUtils.initDotEnv2SystemProperty("mcp-server2.env");
        SpringApplication.run(McpServer2Application.class, args);
    }

}
