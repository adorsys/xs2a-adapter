package de.adorsys.xs2a.adapter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    @Primary
    public SwaggerResourcesProvider swaggerResourcesProvider() {
        return () -> {
            SwaggerResource psd2Api = new SwaggerResource();
            psd2Api.setLocation("/psd2api.json");
            psd2Api.setName("PSD2 API");

            SwaggerResource oauthApi = new SwaggerResource();
            oauthApi.setLocation("/oauthapi.json");
            oauthApi.setName("OAuth API");

            SwaggerResource aspspApi = new SwaggerResource();
            aspspApi.setLocation("/aspspapi.json");
            aspspApi.setName("ASPSP Registry API");

            SwaggerResource xs2aApi = new SwaggerResource();
            xs2aApi.setLocation("/xs2aapi.json");
            xs2aApi.setName("XS2A API");

            return Arrays.asList(psd2Api, oauthApi, aspspApi, xs2aApi);
        };
    }
}
