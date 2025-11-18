package cn.netbuffer.spring.boot3.mcp.demo.mcpclient.controller;

import cn.netbuffer.spring.boot3.mcp.demo.mcpclient.client.SearXNGClient;
import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/SearXNG")
public class SearXNGController {

    @Resource
    private SearXNGClient searXNGClient;

    @GetMapping("search")
    public JSONObject search(String q) {
        return searXNGClient.search(q, "json");
    }

}
