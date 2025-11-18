package cn.netbuffer.spring.boot3.mcp.demo.mcpclient.service.impl;

import cn.netbuffer.spring.boot3.mcp.demo.mcpclient.client.SearXNGClient;
import cn.netbuffer.spring.boot3.mcp.demo.mcpclient.model.SearXNGRequest;
import cn.netbuffer.spring.boot3.mcp.demo.mcpclient.model.SearXNGResult;
import cn.netbuffer.spring.boot3.mcp.demo.mcpclient.service.LLMChatClient;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * SearXNG搜索服务实现，集成SearXNG API和LLM功能
 */
@Slf4j
@Service
public class SearXNGService {

    @Resource
    private SearXNGClient searXNGClient;
    
    @Resource(name = "openAIChatClient")
    private LLMChatClient openAIChatClient;
    
    @Resource(name = "deepseekChatClient")
    private LLMChatClient deepseekChatClient;

    /**
     * 执行搜索并根据需要进行LLM增强
     */
    public SearXNGResult search(SearXNGRequest request) {
        SearXNGResult result = new SearXNGResult();
        result.setQuery(request.getQuery());
        
        try {
            // 执行SearXNG搜索
            long searchStart = System.currentTimeMillis();
            JSONObject searchResponse = searXNGClient.search(request.getQuery(), request.getFormat());
            long searchEnd = System.currentTimeMillis();
            result.setSearchTime(searchEnd - searchStart);
            
            // 解析搜索结果
            List<SearXNGResult.SearchResult> searchResults = parseSearchResults(searchResponse);
            result.setSearchResults(searchResults);
            
            // 如果需要LLM增强且有搜索结果
            if (request.isLlmEnhanced() && !searchResults.isEmpty()) {
                long llmStart = System.currentTimeMillis();
                String enhancedAnswer = enhanceWithLLM(request.getQuery(), searchResults, request.getLlmModel());
                long llmEnd = System.currentTimeMillis();
                result.setLlmEnhancedAnswer(enhancedAnswer);
                result.setLlmProcessTime(llmEnd - llmStart);
                result.setLlmEnhanced(true);
            }
            
            log.info("Search completed for query: {}, results: {}, llmEnhanced: {}", 
                     request.getQuery(), searchResults.size(), result.isLlmEnhanced());
            
        } catch (Exception e) {
            log.error("Error during search: {}", e.getMessage(), e);
            // 即使出错也返回已有的结果
        }
        
        return result;
    }
    
    /**
     * 解析SearXNG返回的JSON结果
     */
    private List<SearXNGResult.SearchResult> parseSearchResults(JSONObject response) {
        List<SearXNGResult.SearchResult> results = new ArrayList<>();
        
        try {
            if (response != null && response.containsKey("results")) {
                JSONArray resultArray = response.getJSONArray("results");
                for (int i = 0; i < resultArray.size(); i++) {
                    JSONObject item = resultArray.getJSONObject(i);
                    SearXNGResult.SearchResult searchResult = new SearXNGResult.SearchResult();
                    searchResult.setTitle(item.getString("title"));
                    searchResult.setUrl(item.getString("url"));
                    searchResult.setContent(item.getString("content"));
                    searchResult.setEngine(item.getString("engine"));
                    searchResult.setCategory(item.getString("category"));
                    results.add(searchResult);
                }
            }
        } catch (Exception e) {
            log.error("Error parsing search results: {}", e.getMessage(), e);
        }
        
        return results;
    }
    
    /**
     * 使用LLM增强搜索结果
     */
    private String enhanceWithLLM(String query, List<SearXNGResult.SearchResult> searchResults, String llmModel) {
        // 构建提示词
        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append("请基于以下搜索结果，为用户的问题提供一个全面、准确的回答。\n\n");
        promptBuilder.append("用户问题：").append(query).append("\n\n");
        promptBuilder.append("搜索结果：\n");
        
        // 添加前5个最相关的搜索结果（如果有）
        int resultCount = Math.min(5, searchResults.size());
        for (int i = 0; i < resultCount; i++) {
            SearXNGResult.SearchResult result = searchResults.get(i);
            promptBuilder.append("[结果").append(i + 1).append("]\n");
            promptBuilder.append("标题：").append(result.getTitle()).append("\n");
            promptBuilder.append("内容：").append(result.getContent()).append("\n");
            promptBuilder.append("来源：").append(result.getUrl()).append("\n\n");
        }
        
        promptBuilder.append("请基于以上搜索结果，简洁明了的回答用户问题，注意不要增加主观推测。");
        
        String prompt = promptBuilder.toString();
        log.debug("LLM enhancement prompt: {}", prompt);
        
        // 选择合适的LLM客户端
        LLMChatClient llmChatClient = selectLLMClient(llmModel);
        
        // 调用LLM获取增强回答
        try {
            return llmChatClient.q(prompt);
        } catch (Exception e) {
            log.error("Error enhancing with LLM: {}", e.getMessage(), e);
            return "无法使用LLM增强搜索结果，请稍后重试。";
        }
    }
    
    /**
     * 根据模型名称选择合适的LLM客户端
     */
    private LLMChatClient selectLLMClient(String llmModel) {
        return Optional.ofNullable(llmModel)
                .map(model -> {
                    if (model.equalsIgnoreCase("deepseek")) {
                        return deepseekChatClient;
                    }
                    return openAIChatClient;
                })
                .orElse(openAIChatClient); // 默认使用OpenAI
    }
}
