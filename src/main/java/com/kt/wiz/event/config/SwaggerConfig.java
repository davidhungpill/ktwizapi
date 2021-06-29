package com.kt.wiz.event.config;

import java.util.Collections;

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

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("kt wiz")
				.apiInfo(this.apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.kt.wiz.event"))
				.paths(PathSelectors.ant("/api/**"))
				.build();
				//.apiInfo(getApiInfo());
	}

	 private ApiInfo apiInfo() {

	        return new ApiInfoBuilder()
	                .title("KT Wiz AI를 이겨라 이벤트 프로젝트")
	                .description("API Documents")
	                .build();
	}
	 
	private ApiInfo getApiInfo() {
		// TODO Auto-generated method stub
		return new ApiInfo("KT Wiz AI를 이겨라 이벤트 프로젝트",
				"API Documents",
				"1.0",
				null,	
				null,
				null,
				null,
				Collections.emptyList()
		);
	}
	
}
