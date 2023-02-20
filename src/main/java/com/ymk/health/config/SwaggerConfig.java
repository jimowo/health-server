package com.ymk.health.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfig {

    /**
     * 创建接口文档
     * @return
     */
    @Bean
    public Docket createApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(generateApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ymk.health.controller"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContextList());
    }

    /**
     * 设置文档信息
     * @return
     */
    private ApiInfo generateApiInfo() {
        return new ApiInfoBuilder()
                .title("个人健康平台")
                .version("1.0.0")
                .contact(new Contact("严明奎", "http://localhost:9090/doc.html", "13905142704@163.com"))
                .description("个人健康平台接口文档")
                .build();
    }

    /**
     * 设置请求信息
     * @return
     */
    private List<ApiKey> securitySchemes() {
        List<ApiKey> list = new ArrayList<>();
        ApiKey key = new ApiKey("Authorization", "Authorization", "Header");
        list.add(key);
        return list;
    }

    /**
     * 配置Security 对Swagger 测试的权限
     * @return
     */
    public List<SecurityContext> securityContextList() {
        List<SecurityContext> list = new ArrayList<>();
        list.add(getSecurityContext());
        return list;
    }

    /**
     * 获取授权路径
     * @return
     */
    private SecurityContext getSecurityContext() {
        return SecurityContext
                .builder()
                .securityReferences(securityReferences())
                .forPaths(PathSelectors.regex("hello/.*"))
                .build();
    }

    /**
     * 授权Swagger 可以进行接口测试
     * @return
     */
    private List<SecurityReference> securityReferences() {
        List<SecurityReference> list = new ArrayList<>();
        // 授权范围：全局
        AuthorizationScope scope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] scopes = new AuthorizationScope[1];
        scopes[0] = scope;
        list.add(new SecurityReference("Authorization", scopes));
        return list;
    }
}
