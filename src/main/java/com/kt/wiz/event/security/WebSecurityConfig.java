package com.kt.wiz.event.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
		.antMatchers("/event/**").permitAll()
		.and()
		.cors()
		.and()
		.csrf().disable()
		;
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		
		String webUrl = "http://ec2-3-36-248-102.ap-northeast-2.compute.amazonaws.com";
		String webDomain = "http://xservice.co.kr";
		String localUrl = " http://localhost:8080";
		
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedHeaders(Arrays.asList("*"));
		config.setAllowedOrigins(Arrays.asList(webUrl, webDomain, localUrl));
		config.setAllowedMethods(Arrays.asList("GET", "POST","OPTIONS"));;
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
}
