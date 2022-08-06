package com.crio.starter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
  private String basePackage = "com.crio.starter.controller";
  private String title = "Memes APIs";
  private String version = "0.0.1-SNAPSHOT";
  private String description = "Memes APIs documentation";
  private String contactName = "Vikas Choudhary";
  private String contactEmail = "Vikas4.Choudhary@ril.com";

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2).select()
      .apis(RequestHandlerSelectors.basePackage(basePackage)).paths(PathSelectors.any()).build()
      .apiInfo(apiInfo());
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
      .title(title)
      .description(description)
      .version(version)
      .contact(new Contact(contactName, null, contactEmail))
      .build();
  }
}
