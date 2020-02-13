package com.documentflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication//(scanBasePackages = {"com.documentflow"})
@PropertySource("classpath:database.properties")
public class DocumentflowApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(DocumentflowApplication.class, args);


	}

}
