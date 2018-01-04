/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import controller.AccountController;

/**
 * Driver class, instantiates the controller which creates the MVC.
 * @author Ben
 */
public class main {

    /**
     * Driver
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        if(true){
             AccountController accountController = 
                new AccountController("Accounts.txt");
        }
        else
            System.out.println("Need file name of account as ONLY argument on "
                    + "the command line");
        
        
        
        
    }
    
}
