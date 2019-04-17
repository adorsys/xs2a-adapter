package de.adorsys.xs2a.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${info.app.version}")
    private String version;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                       .apiInfo(apiInfo())
                       .select()
                       .apis(RequestHandlerSelectors.basePackage("de.adorsys.xs2a.gateway.controller"))
                       .paths(PathSelectors.any())
                       .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                       .title("xs2a Gateway API")
                       .description("")
                       .version(version)
                       .build();
    }
}
