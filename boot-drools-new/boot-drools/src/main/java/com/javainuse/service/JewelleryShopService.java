package com.javainuse.service;

import org.kie.api.io.ResourceType;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.StatefulKnowledgeSession;
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
		
		

		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("change-set.xml", getClass()), ResourceType.CHANGE_SET);
		
		if (kbuilder.hasErrors()) {
			System.err.println(kbuilder.getErrors().toString());
			}
		StatefulKnowledgeSession kieSession = kbuilder.newKnowledgeBase().newStatefulKnowledgeSession();
		
		 /* KnowledgeAgentConfiguration kaconf = KnowledgeAgentFactory.newKnowledgeAgentConfiguration();
		    kaconf.setProperty( "drools.agent.scanDirectories", "false" ); 
		
		KnowledgeAgent kagent = KnowledgeAgentFactory.newKnowledgeAgent( "MyAgent" ,kaconf);

		kagent.applyChangeSet( ResourceFactory.newFileResource("C:\\Users\\rajes\\Downloads\\boot-drools\\boot-drools\\src\\main\\resources\\change-set.xml"));

		KnowledgeBase kbase = kagent.getKnowledgeBase();
		StatefulKnowledgeSession kieSession = kbase.newStatefulKnowledgeSession();
		*/
		
		for (Product product : products.getProductList()) {
			kieSession.insert(product);
		}
		kieSession.fireAllRules();
		kieSession.dispose();
		return products;
	}
}