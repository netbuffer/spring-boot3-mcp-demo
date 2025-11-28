package cn.netbuffer.spring.boot3.mcp.demo.mcpserver2.tools;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

@Slf4j
@Component
public class FileTool {

    @Tool(name = "create_file", description = "创建文件内容到用户本地磁盘")
    public static boolean createFile(String filePath, String content) {
        log.debug("createFile invoked with path: {}", filePath);

        if (filePath == null || filePath.trim().isEmpty()) {
            log.error("文件路径不能为空");
            return false;
        }

        if (content == null) {
            log.warn("文件内容为 null, 忽略写入操作");
            return false;
        }

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            IOUtils.write(content, fos, Charset.forName("UTF-8"));
            log.info("成功创建文件: {}", filePath);
            return true;
        } catch (FileNotFoundException e) {
            log.error("文件未找到: {}", filePath, e);
            return false;
        } catch (IOException e) {
            log.error("写入文件失败: {}", filePath, e);
            return false;
        }

    }

}