/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;


import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 *
 * @author The Dough Boys
 */
public class TestRunner {
    
    public static void main(String[] args){
        
      Result result = JUnitCore.runClasses(JUnitTests.class);
       
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
         
      }
		
      System.out.println(" Result was successful: " + result.wasSuccessful());
    }
}
