package com.liangshou.springaitutorial.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author X-L-S
 */
@Configuration
@EnableSwagger2
@Profile({"dev", "prod"})
public class Knife4jConfig {

    @Bean(value = "defaultApi2")
    public Docket defaultApi2 () {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.liangshou.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 自定义接口文档信息
     */
    private ApiInfo apiInfo () {
        return new ApiInfoBuilder()
                .title("Spring AI Tutorial")
                .description("Spring AI Tutorial")
                .contact(new Contact("Liangshou", "github.com/LiangshouX", "xiaoliangshou.bit@gmail.com"))
                .version("1.0")
                .build();
    }
}
