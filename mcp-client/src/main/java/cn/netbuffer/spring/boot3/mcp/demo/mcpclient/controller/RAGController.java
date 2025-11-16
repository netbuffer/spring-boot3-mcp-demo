package cn.netbuffer.spring.boot3.mcp.demo.mcpclient.controller;

import cn.netbuffer.spring.boot3.mcp.demo.mcpclient.service.impl.RAGService;
import jakarta.annotation.Resource;
import org.springframework.ai.document.Document;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/rag")
public class RAGController {

    @Resource
    private RAGService ragService;

    @PostMapping("knowledge/create")
    public List<Document> uploadKnowledge(@RequestParam("file") MultipartFile file) {
        return ragService.create(file.getResource(), file.getOriginalFilename());
    }

}
