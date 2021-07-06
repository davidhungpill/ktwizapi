package com.kt.wiz.event;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@EnableCaching
@EnableEncryptableProperties
@SpringBootApplication
public class KtwizapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(KtwizapiApplication.class, args);
	}


	@Bean
	public RestTemplate getRestTemplate() {
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setConnectTimeout(3 * 1000);
		factory.setReadTimeout(30 * 1000);
		
		return new RestTemplate(factory);
	}
}
