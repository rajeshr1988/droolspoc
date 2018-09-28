package com.javainuse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SpringBootDroolsHelloWorldApp extends SpringBootServletInitializer {
	 //private static final Logger logger = LoggerFactory.getLogger(SpringBootDroolsHelloWorldApp.class);
	public static void main(String[] args) {
		
		SpringApplication.run(SpringBootDroolsHelloWorldApp.class, args);

	}
	 @Override
	    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	        return application.sources(SpringBootDroolsHelloWorldApp.class);
	    }

	/*@Bean
	public KieContainer kieContainer() {
		return KieServices.Factory.get().getKieClasspathContainer();
	}*/

}
