package com.ldb.core;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class CoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	// @Bean
	// public JavaMailSender getJavaMailSender() {
	// 	JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	// 	mailSender.setHost("smtp.gmail.com");
	// 	mailSender.setPort(587);

	// 	mailSender.setUsername("my.gmail@gmail.com");
	// 	mailSender.setPassword("password");

	// 	Properties props = mailSender.getJavaMailProperties();
	// 	props.put("mail.transport.protocol", "smtp");
	// 	props.put("mail.smtp.auth", "true");
	// 	props.put("mail.smtp.starttls.enable", "true");
	// 	props.put("mail.debug", "true");

	// 	return mailSender;
	// }

}

// PASSWORD.RESET,RESET/I/PROCESS,DMUSER.13/123456,RESET.API3,USER.RESET:1=BLTLYYOR.1,USER.TYPE:1=INT,USER.PASSWORD:1=97205701