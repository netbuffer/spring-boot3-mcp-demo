package cn.netbuffer.spring.boot3.mcp.demo.mcpcommon.utils;

import io.github.cdimascio.dotenv.Dotenv;

public class DotEnvUtils {

    public static Dotenv initDotEnv2SystemProperty(String filename) {
        Dotenv dotenv = Dotenv.configure()
                .filename(filename)
                .ignoreIfMissing()
                .load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
        return dotenv;
    }

}
