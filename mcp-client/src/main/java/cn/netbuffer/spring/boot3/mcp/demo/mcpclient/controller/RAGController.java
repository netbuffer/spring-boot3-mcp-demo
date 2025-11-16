package cn.netbuffer.spring.boot3.mcp.demo.mcpclient.controller;

import cn.netbuffer.spring.boot3.mcp.demo.mcpclient.service.impl.RAGService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/rag")
public class RAGController {

    @Resource
    private RAGService ragService;

    @PostMapping("knowledge/create")
    public List<Document> uploadKnowledge(@RequestParam("file") MultipartFile file) {
        return ragService.create(file.getResource(), file.getOriginalFilename());
    }

    @GetMapping("knowledge/search")
    public List<Document> searchKnowledge(String q) {
        return ragService.search(q);
    }

    @GetMapping("ask")
    public String askWithKnowledge(String question) {
        log.debug("RAG ask question: {}", question);
        return ragService.askWithKnowledge(question);
    }

}
