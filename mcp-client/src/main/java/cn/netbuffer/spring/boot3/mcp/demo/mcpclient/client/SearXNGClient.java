package cn.netbuffer.spring.boot3.mcp.demo.mcpclient.client;

import com.alibaba.fastjson2.JSONObject;
import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.Query;

@BaseRequest(
        baseURL = "{searxng.url}"
)
public interface SearXNGClient {

    @Get("search")
    JSONObject search(@Query("q") String query, @Query("format") String format);

}
