package com.javainuse.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.ReleaseId;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

import com.javainuse.model.Product;
import com.javainuse.model.Products;

@Service
public class JewelleryShopService {

	/*
	 * private final KieContainer kieContainer;
	 * 
	 * @Autowired public JewelleryShopService(KieContainer kieContainer) {
	 * this.kieContainer = kieContainer; }
	 */

	public Products getProductDiscount(Products products) {
		
		
/*
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("change-set.xml", getClass()), ResourceType.CHANGE_SET);
		
		if (kbuilder.hasErrors()) {
			System.err.println(kbuilder.getErrors().toString());
			}
		StatefulKnowledgeSession kieSession = kbuilder.newKnowledgeBase().newStatefulKnowledgeSession();
		
		  KnowledgeAgentConfiguration kaconf = KnowledgeAgentFactory.newKnowledgeAgentConfiguration();
		    kaconf.setProperty( "drools.agent.scanDirectories", "false" ); 
		
		KnowledgeAgent kagent = KnowledgeAgentFactory.newKnowledgeAgent( "MyAgent" ,kaconf);

		kagent.applyChangeSet( ResourceFactory.newFileResource("C:\\Users\\rajes\\Downloads\\boot-drools\\boot-drools\\src\\main\\resources\\change-set.xml"));

		KnowledgeBase kbase = kagent.getKnowledgeBase();
		StatefulKnowledgeSession kieSession = kbase.newStatefulKnowledgeSession();
		*/
		
		/*KieServices kieServices = KieServices.Factory.get();
	    // Create File System services
        KieFileSystem kFileSystem = kieServices.newKieFileSystem();

        File file = new File("C:\\rules\\gold.drl");
        Resource resource = kieServices.getResources().newFileSystemResource(file).setResourceType(ResourceType.DRL);
        kFileSystem.write( resource );       

        KieBuilder kbuilder = kieServices.newKieBuilder( kFileSystem );
        // kieModule is automatically deployed to KieRepository if successfully built.
        kbuilder.buildAll();

        if (kbuilder.getResults().hasMessages(org.kie.api.builder.Message.Level.ERROR)) {
            throw new RuntimeException("Build time Errors: " + kbuilder.getResults().toString());
        }
        KieContainer kContainer = kieServices.newKieClasspathContainer();
        
        KieSession kieSession = kContainer.newKieSession("ksession1");*/
		
		
	    // KieServices is the entry point of APIs
        KieServices ks = KieServices.Factory.get();
        File file = new File("C:\\rules\\gold.drl");
        // KieFileSystem is responsible for gathering resources
        KieFileSystem kfs = ks.newKieFileSystem();
        try {
			kfs.write( "src/main/resources/" + file.getName(), ks.getResources().newInputStreamResource( new FileInputStream( file ) ) );
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        /*File[] files = directory.listFiles();
        for ( File file : files ) {
            String filename = file.getName();
            // for simplicity, only pick .drl
            if ( filename.endsWith( ".drl" ) ) {
                // You can add your DRL files as InputStream here
               
            }
        }*/

        // You need to specify a unique ReleaseId per KieContainer (= the unit which you store a set of DRL files)
        // ReleaseId consists of "GroupId" + "ArtifactId" + "Version". The same idea of Maven artifact.
        ReleaseId releaseId = ks.newReleaseId( "com.sample", "sample", "1.0.");
        kfs.generateAndWritePomXML( releaseId );

        // Now resources are built and stored into an internal repository
        ks.newKieBuilder( kfs ).buildAll();

        // You can get a KieContainer with the ReleaseId
        KieContainer kcontainer = ks.newKieContainer( releaseId );
		
        KieBase kbase = kcontainer.getKieBase();
		
		
        KieSession kieSession = kbase.newKieSession();
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		for (Product product : products.getProductList()) {
			kieSession.insert(product);
		}
		kieSession.fireAllRules();
		kieSession.dispose();
		return products;
	}
}