package com.kt.wiz.event.config;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Configuration
public class JasyptConfig {

	private static final String JASYPT_KEY = "ktwiz";
	private static final String ALGORITHM  = "PBEWithMD5AndDES";
	
	@Bean("jasyptStringEncryptor")
	public StandardPBEStringEncryptor StringEnc() {
		StandardPBEStringEncryptor enc = new StandardPBEStringEncryptor();
		EnvironmentPBEConfig conf = new EnvironmentPBEConfig();
		conf.setPassword(JASYPT_KEY);
		conf.setAlgorithm(ALGORITHM);
		enc.setConfig(conf);
		return enc;
	}
	
	public static void main(String[] args) {
		StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
		pbeEnc.setAlgorithm(ALGORITHM);
		pbeEnc.setPassword(JASYPT_KEY);
		
		String username = pbeEnc.encrypt("postgres");
		String password = pbeEnc.encrypt("Test8080!");
		String password2 = pbeEnc.encrypt("1q2w3e4r");
		
		log.debug("username: "+ username);
		log.debug("password: "+ password);
		log.debug("password2: "+ password2);
		
		log.debug(pbeEnc.decrypt(username));
		log.debug(pbeEnc.decrypt(password));
	}
			
}
