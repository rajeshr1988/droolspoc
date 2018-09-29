package com.javainuse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;

import org.springframework.boot.autoconfigure.MessageSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;



@SpringBootApplication(exclude = MessageSourceAutoConfiguration.class)
public class SpringBootDroolsHelloWorldApp extends SpringBootServletInitializer  {
	 private static final Logger LOGGER = LoggerFactory.getLogger(SpringBootDroolsHelloWorldApp.class);
	/*public static void main(String[] args) {
		SpringApplication.run(SpringBootDroolsHelloWorldApp.class, args);

	}*/
	
	 @Override
	   protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		 LOGGER.debug("Spring boot initiated.");
	      return application.sources(SpringBootDroolsHelloWorldApp.class);
	   }

	@Bean
	public KieContainer kieContainer() {
		LOGGER.debug("Kie is loading");
		return KieServices.Factory.get().getKieClasspathContainer();
	}

}
