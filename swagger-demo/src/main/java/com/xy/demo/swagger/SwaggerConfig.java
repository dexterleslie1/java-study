package com.xy.demo.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /**
     *
     * @return
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(createApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com"))//扫描com路径下的api文档
                .paths(PathSelectors.any())//路径判断
                .build();
    }

    /**
     *
     * @return
     */
    private ApiInfo createApiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger开发规范")//标题
                .description("Saggger开发描述")
                .version("1.0.0")//版本号
                .build();
    }
}
