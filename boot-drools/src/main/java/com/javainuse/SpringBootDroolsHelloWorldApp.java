package com.javainuse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootDroolsHelloWorldApp {
	
	public static void main(String[] args) {
		
		SpringApplication.run(SpringBootDroolsHelloWorldApp.class, args);

	}

	@Bean
	public KieContainer kieContainer() {
		System.out.println("about to create container.");
		KieServices ks = KieServices.Factory.get();
        File file = new File("C:\\rules\\gold.drl");
        File diamondfile = new File("C:\\rules\\diamond.drl");
        // KieFileSystem is responsible for gathering resources
        KieFileSystem kfs = ks.newKieFileSystem();
        try {
			kfs.write( "src/main/resources/gold/" + file.getName(), ks.getResources().newInputStreamResource( new FileInputStream( file ) ) );
			kfs.write( "src/main/resources/diamond" + file.getName(), ks.getResources().newInputStreamResource( new FileInputStream( diamondfile ) ) );
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        ReleaseId releaseId = ks.newReleaseId( "com.sample.drools", "sample", "1.0.0");
        kfs.generateAndWritePomXML( releaseId );

        // Now resources are built and stored into an internal repository
        ks.newKieBuilder( kfs ).buildAll();

        // You can get a KieContainer with the ReleaseId
        return ks.newKieContainer( releaseId );
		
	}

}
