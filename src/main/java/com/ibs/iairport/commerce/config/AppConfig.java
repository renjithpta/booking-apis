package com.ibs.iairport.commerce.config;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import lombok.AllArgsConstructor;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.ibs.iairport.commerce" })
@ConditionalOnProperty(value = "springdoc.api-docs.enabled", havingValue = "true")
@AllArgsConstructor
public class AppConfig {


   
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    

    @Bean
    public OpenAPI customOpenAPI(
            @Value("${application.description}") String appDes,
            @Value("${application.version}") String appVersion) {

        return new OpenAPI()
                .info(new Info()
                .title("Hyperledger Fabric Contract Management Chain REST APIs")
                .version(appVersion)
                .description(appDes)
                .termsOfService("http://swagger.io/terms/")
                .license(new License().name("Apache 2.0")
                .url("")));
    }
    @Bean
    public GroupedOpenApi userRegistrationOpenApi() {
        String packagesToscan[] = {"com.ibs.iairport.commerce"};
        return GroupedOpenApi
                .builder()
                .group("iarpror_rest")
                .packagesToScan(packagesToscan)
                .build();
    }
    private Info getInfo() {
      return new Info()
          .title("iAirport API Services")
          .description("REST API for iAirport sellable and booking ");
    }

    private List<Server> getServers() {
      return List.of(new Server().url("http://localhost:8080"));
    }
}
