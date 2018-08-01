/*
 * Copyright 2010 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.examples.helloworld;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a sample file to launch a rule package from a rule source file.
 */
public class HelloWorldExample {

    public static final void main(final String[] args) throws FileNotFoundException {        
       /* KieServices ks = KieServices.Factory.get();       
        KieContainer kc = ks.getKieClasspathContainer();
        execute(kc);
*/    
    
    	String dir = "C:\\Users\\rajes";
    	String drl = "HelloWorld.drl";
    	KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kfs = kieServices.newKieFileSystem();
        FileInputStream fis =  new FileInputStream( dir + "/" + drl );

        kfs.write( "src/main/resources/" + drl,  kieServices.getResources().newInputStreamResource( fis ) );

        KieBuilder kieBuilder = kieServices.newKieBuilder( kfs ).buildAll();

        Results results = kieBuilder.getResults();
        if( results.hasMessages( Message.Level.ERROR ) ){
            System.out.println( results.getMessages() );
            throw new IllegalStateException( "### errors ###" );
        }

        KieContainer kieContainer =  kieServices.newKieContainer( kieServices.getRepository().getDefaultReleaseId() );

        execute(kieContainer);
       
    }

    public static void execute(KieContainer kieContainer ) { 
        //KieSession ksession = kc.newKieSession("HelloWorldKS");        
    	KieBase kieBase = kieContainer.getKieBase();
    	StatelessKieSession kieSession = kieContainer.newStatelessKieSession();
        kieSession.setGlobal( "list", new ArrayList<Object>());
     
      /*  kieSession.addEventListener( new DebugAgendaEventListener() );
        kieSession.addEventListener( new DebugRuleRuntimeEventListener() );

        */
        
        for(int i=0;i<2;i++) {
        final Employee employee = new Employee();
        employee.setName("Sophia");
        employee.setDept("IT");
        employee.setAge(30);
        employee.setSalary(100000);
        
        
        
        
        
        kieSession.execute( employee);

        
        
        
        //kieSession.dispose();
        }
    }

    public static class Employee {
        public String name;
        public String dept;
        public int age;
        public int salary;
        
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDept() {
			return dept;
		}
		public void setDept(String dept) {
			this.dept = dept;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		public int getSalary() {
			return salary;
		}
		public void setSalary(int salary) {
			this.salary = salary;
		}
        
    }

}
