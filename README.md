# spring-boot3-mcp-demo

![jdk 17](https://img.shields.io/static/v1?label=jdk&message=17&color=blue)
![spring-boot](https://img.shields.io/static/v1?label=spring-boot&message=3.5.7&color=green)
![spring-ai](https://img.shields.io/static/v1?label=spring-ai&message=1.0.3&color=green)
![dotenv-java](https://img.shields.io/static/v1?label=dotenv-java&message=3.2.0&color=blue)
![postman](https://img.shields.io/static/v1?label=postman&message=v11.70.5&color=FF6C37)

## help
* http://127.0.0.1:8081

### Project Modules

This project contains the following modules:

1. **mcp-client** - Main application module with Spring Boot web controllers and services
2. **mcp-common** - Shared utilities and common components

### Quick Start

To run the application:

1. Clone the repository
2. Run `mvn clean install` to build all modules
3. Start the application with:
   ```bash
   mvn spring-boot:run -pl mcp-client
   ```

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


