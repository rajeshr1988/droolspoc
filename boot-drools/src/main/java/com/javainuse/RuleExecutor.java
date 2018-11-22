package com.javainuse;

import java.io.File;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuleExecutor {


	public static final String RULE_FILENAME_EXTENSION = ".drl";

	private Map<String, KieContainer> ruleSetContainers;
	private int totalRulesLoaded = 0;

	private Logger logger = LoggerFactory.getLogger(getClass());
	private boolean failedToInitialize;
	private final KieServices kieServices;

	public RuleExecutor() {
		ruleSetContainers = new HashMap<String, KieContainer>();
		kieServices = KieServices.Factory.get();
	}

	public RuleExecutor(String directoryOfRuleSets) {
		this();

		final File rulesDir = new File(directoryOfRuleSets);
		if (!rulesDir.isDirectory()) {
			failedToInitialize = true;
			logger.error("Rules directory does not exist: {}", rulesDir.getAbsolutePath());
		} else {
			for (File file : rulesDir.listFiles()) {
				if (file.isDirectory() && !file.isHidden()) {
					final String ruleSetName = file.getName();
					logger.info("Loading Drools rule set {}", ruleSetName);
					addRuleSet(file.getName(), file);
				}
			}
		}
	}

	public void addRuleSet(String ruleSetName, File ruleSetDirectory) throws RuleExecutorException {
		// Create the in-memory File System and add the resources files  to it
		KieFileSystem kieFileSystem = kieServices.newKieFileSystem();

		try {
			final RuleLoader ruleLoader = new RuleLoader(kieFileSystem);
			Files.walkFileTree(ruleSetDirectory.toPath(), ruleLoader);
			int rulesLoaded = ruleLoader.getRulesLoaded();
			if (rulesLoaded == 0) {
				logger.warn("No rules loaded. Rules directory: {}", ruleSetDirectory.getAbsolutePath());
			} else {
				logger.info("{} rules loaded.", ruleLoader.getRulesLoaded());
				totalRulesLoaded += rulesLoaded;
			}
		} catch (IOException e) {
			throw new RuleExecutorException("Failed to load rule set " + ruleSetName, e);
		}

		// Create the builder for the resources of the File System
		KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);

		// Build the KieBases
		kieBuilder.buildAll();

		// Check for errors
		if (kieBuilder.getResults().hasMessages(Message.Level.ERROR)) {
			throw new RuleExecutorException(kieBuilder.getResults().toString());
		}

		// Get the Release ID (mvn style: groupId, artifactId,version)
		ReleaseId relId = kieBuilder.getKieModule().getReleaseId();

		// Create the Container, wrapping the KieModule with the given ReleaseId
		ruleSetContainers.put(ruleSetName, kieServices.newKieContainer(relId));
	}

	

	private static final class RuleLoader extends SimpleFileVisitor<Path> {

		private final KieFileSystem kieFileSystem;
		private int rulesLoaded;

		public RuleLoader(KieFileSystem kieFileSystem) {
			this.kieFileSystem = kieFileSystem;
		}

		@Override
		public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
			File file = path.toFile();
			if (file.isFile() && file.getName().endsWith(RULE_FILENAME_EXTENSION)) {
				kieFileSystem.write(ResourceFactory.newFileResource(file));
				rulesLoaded++;
			}

			return FileVisitResult.CONTINUE;
		}

		public int getRulesLoaded() {
			return rulesLoaded;
		}

	}

	public int getTotalRulesLoaded() {
		return totalRulesLoaded;
	}
	
	public static void main(String args[]) {
		new RuleExecutor("/home/rajesh/rules");
	}
	
	
	
}
