# spring-boot3-mcp-demo

![jdk 17](https://img.shields.io/static/v1?label=jdk&message=17&color=blue)
![spring-boot](https://img.shields.io/static/v1?label=spring-boot&message=3.5.7&color=green)
![spring-ai](https://img.shields.io/static/v1?label=spring-ai&message=1.0.3&color=green)
![dotenv-java](https://img.shields.io/static/v1?label=dotenv-java&message=3.2.0&color=blue)
![postman](https://img.shields.io/static/v1?label=postman&message=v11.70.5&color=FF6C37)

## help
* http://127.0.0.1:8081

### Project Structure

```
spring-boot3-mcp-demo/
├── mcp-client/          # 主应用模块（Web 控制器、服务、配置）
│   └── src/main/resources/application.yaml
├── mcp-common/          # 公共组件
├── help/
│   └── http-requests.http  # IDEA HTTP Client 测试集合
├── docker-compose.yaml  # Redis Stack（向量存储）
└── pom.xml
```

### Prerequisites

- JDK 17+
- Maven 3.9+
- Docker（用于 Redis Stack）
- 环境变量（可选）：
  - `OPENAI_API_KEY`、`DEEPSEEK_API_KEY`

### Build & Run

```bash
# 构建
mvn clean install

# 启动 Redis Stack（向量存储）
docker-compose up -d
# Redis UI: http://localhost:8380  密码: your-pwd

# 运行应用
mvn spring-boot:run -pl mcp-client

# 可选 JVM 参数（Windows 控制台中文友好）
-Dfile.encoding=UTF-8
```

### Configuration

主配置文件：`mcp-client/src/main/resources/application.yaml`

关键项：
- Server: port 8081，强制 UTF-8
- Spring AI Chat: OpenAI / DeepSeek（max-tokens、temperature、model）
- VectorStore Redis: `index-name=sb3md-v`, `prefix=vtr:`
- Embedding cache: `.env/transformer-cache`
- Redis: host 127.0.0.1, port 8379, password `your-pwd`

### API Endpoints

- **Chat**
  - GET `/api/chat/q?question=...` → String
  - GET `/api/chat/streamq?question=...` → Server streaming text (Flux)
- **SSE**
  - GET `/api/chat/sse/events?userId=xxx` → SseEmitter
  - POST `/api/chat/sse/send?userId=xxx` body `{ "userId": "...", "message": "..." }`
- **RAG 知识上传**
  - POST `/rag/knowledge/create`（multipart/form-data）
  - 字段 `file`：文档（推荐 .docx）

测试示例见 `help/http-requests.http`，可直接在 IDEA 中运行。

### RAG 行为说明（概要）

- 文档解析：Apache Tika 解析 .docx，全链路 UTF-8
- 重复上传幂等覆盖：基于文件名的稳定向量 ID + Redis manifest，同名文件仅保留最新一版向量
- 中文文件名上传建议使用 RFC5987 `filename*`（见 http-requests.http 示例）

### FAQ

- **启动报错：VectorStore bean not found**
  - 确认 Redis 可用（8379 端口），`spring.data.redis.*` 配置正确
  - 确认存在可用的 Embedding 能力（如 transformer/openai；必要时开启 `spring.ai.embedding.transformer.enabled=true`）
  - Maven Reimport + `mvn clean package`

- **中文文件名/内容乱码**
  - JVM 参数：`-Dfile.encoding=UTF-8`
  - 日志配置：`logging.charset.console/file=UTF-8`
  - 上传请求使用 `filename*`（见 http-requests.http 示例）

### proxy config
```shell
-Dhttp.proxyHost=xxx
-Dhttp.proxyPort=xxx
-Dhttps.proxyHost=xxx
-Dhttps.proxyPort=xxx
```

### Project Modules

This project contains the following modules:

1. **mcp-client** - Main application module with Spring Boot web controllers and services
2. **mcp-common** - Shared utilities and common components

### Features

- RESTful API endpoints
- Environment variable management using dotenv-java
- Modular architecture

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.5.7/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.5.7/maven-plugin/build-image.html)
* [Spring Web](https://docs.spring.io/spring-boot/3.5.7/reference/web/servlet.html)

### Guides

The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the
parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.


