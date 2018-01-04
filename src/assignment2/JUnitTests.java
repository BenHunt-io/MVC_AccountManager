/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.util.logging.Level;
import java.util.logging.Logger;
import static junit.framework.Assert.*;
import model.AccountModel;
import model.Model;
import model.UserAccount;
import model.exceptions.WithdrawException;
import org.junit.Test;
import view.AgentActionEvent;






/**
 *
 * @author The Dough Boys
 */
public class JUnitTests {
    
    
    @Test
    public void testGetIdWithName() {
        AccountModel model = new AccountModel("Accounts.txt"); // MyClass is tested
        assertNotNull(model.getIdWithName());
    }
    
    @Test
    public void testReadInAcountData() {
        
        AccountModel model = new AccountModel("Accounts.txt"); // MyClass is tested
        assertNotNull(model.getAccount(12321));
        
    }
    
    
    @Test
    public void testGetAccount(){
        
        AccountModel model = new AccountModel("Accounts.txt"); // MyClass is tested
        assertNotNull(model.getAccount(12321));
        
    }
    
    
    
    
}
