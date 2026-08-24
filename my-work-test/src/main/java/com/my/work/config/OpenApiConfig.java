package com.my.work.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger/OpenAPI 接口文档配置（DOC-P2-3）.
 *
 * <p>引入 {@code springdoc-openapi-starter-webmvc-ui} 后，springdoc 会在运行时
 * 自动扫描全部 {@code @RestController}，基于方法注解与 Javadoc 生成 OpenAPI 契约：</p>
 * <ul>
 *   <li>Swagger UI 页面：{@code http://localhost:8080/swagger-ui.html}</li>
 *   <li>OpenAPI JSON：{@code http://localhost:8080/v3/api-docs}</li>
 * </ul>
 *
 * <p>本类仅补充文档元数据（标题/版本/描述）与 API 分组；Spring Boot 4 需在
 * {@code application.yml} 显式开启 {@code springdoc.api-docs.enabled} 与
 * {@code springdoc.swagger-ui.enabled}。</p>
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "test-service API",
                version = "1.1-SNAPSHOT",
                description = "测试服务 REST 接口文档：统一响应体 CommonResult(code/msg/data)，"
                        + "异常由 GlobalExceptionHandler 统一兜底（400 参数错误 / 500 服务器内部错误）。"
        )
)
public class OpenApiConfig {

    /**
     * 公开 API 分组（默认分组，覆盖全部 /test 接口）.
     *
     * <p>springdoc 支持按路径拆分多个分组，便于按业务域展示；本项目接口集中在
     * {@code /test/**}，故仅定义一个 {@code public-api} 分组。</p>
     *
     * @return 匹配 {@code /test/**} 的 API 分组
     */
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public-api")
                .pathsToMatch("/test/**")
                .build();
    }
}
