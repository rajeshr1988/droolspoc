package com.javainuse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;

public class RuleExecutor {
	public static Map<String, KieContainer> kieContainerMap = new HashMap<String, KieContainer>();
	public static void main ( String args[]) {
    
	System.out.println("about to create container.");
	KieServices ks = KieServices.Factory.get();
  
    KieFileSystem kfs = ks.newKieFileSystem();
   
    	
    	final File rulesDir = new File("C:\\rules\\");
		if (!rulesDir.isDirectory()) {
			
			System.out.println("Rules directory does not exist: {}" +rulesDir.getAbsolutePath());
		} else {
			for (File file : rulesDir.listFiles()) {
				if (!file.isDirectory() && !file.isHidden()) {
					final String ruleSetName = file.getName();
					System.out.println("Loading Drools rule set {}" + ruleSetName);
					createContainer(file.getName(), file, ks, kfs);
					for (Map.Entry<String, KieContainer> entry : kieContainerMap.entrySet()) {
					    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
				}
				}
			}
		}
		
  
	}

 private static void createContainer(String ruleFileName, File ruleFile, KieServices ks, KieFileSystem kfs) {
	 String[] fileName= ruleFile.getName().split("\\.");
		try {
			
			kfs.write( "src/main/resources/" +fileName[0]+ "/" + ruleFile.getName(), ks.getResources().newInputStreamResource( new FileInputStream( ruleFile ) ) );
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ReleaseId releaseId = ks.newReleaseId( "com.sample.drools", fileName[0] , "1.0.0");
		kfs.generateAndWritePomXML( releaseId );
		
		// Now resources are built and stored into an internal repository
		ks.newKieBuilder( kfs ).buildAll();
		
		// You can get a KieContainer with the ReleaseId
		kieContainerMap.put(ruleFile.getName(), ks.newKieContainer(releaseId));
		}
	
}
