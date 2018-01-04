/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Object to store the accounts being read in from the file, in.
 * @author Ben
 */
public class UserAccount {
    
    private int accountId;
    private String accountName;
    private double balance;
    
    /**
     * Takes in information that makes up an account
     * @param accountId - accountId associated with the account
     * @param accountName - name of the account
     * @param balance - balance of the account
     */
    public UserAccount(int accountId, String accountName, double balance){
        this.accountId = accountId;
        this.accountName = accountName;
        this.balance = balance;
    }
    
    /**
     * Gets the id associated with the account.
     * @return the id of the account.
     */
    public int getAccountId(){
        return accountId;
    }
    
    
    /**
     * Gets the name associated with the account.
     * @return the name of the account owner.
     */
    public String getAccountName(){
        return accountName;
    }
    
    /**
     * Gets the balance associated with the account.
     * @return the balance of the account.
     */
    public double getAccountBalance(){
        return balance;
    }
    
    /**
     * Sets the balance of the account
     * @param balance - the new balance of the account
     */
    public void setBalance(double balance){
        this.balance = balance;
    }
    
    /**
     * Gets the whole string entry of the account
     * @return - the whole string entry of the account
     */
    public String getEntry(){
        return accountName + " " + accountId + " " + balance;
    }
}
