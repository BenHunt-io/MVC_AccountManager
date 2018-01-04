
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.exceptions;

/**
 * Exception that occurs when a withdraw would send the account negative.
 * @author The Dough Boys
 */
public class WithdrawException extends Exception {
    
    private double balance;
    private double withdrawAmt;
    private int windowId;
    
    /**
     * Constructs exception with info of the withdraw error.
     * @param balance - current account balance
     * @param withdrawAmt - the amount specified to be withdrawn.
     * @param windowId  - the windowId associated with the withdrawal.
     */
    public WithdrawException(double balance, double withdrawAmt, int windowId){
        this.balance = balance;
        this.withdrawAmt = withdrawAmt;
        this.windowId = windowId;
    }
    
    /**
     * Gets the current account balance
     * @return - current account balance
     */
    public double getBalance(){
        return balance;
    }
    
    /**
     * Gets the amount specified to be withdrawn.
     * @return - the amount specified to be withdrawn.
     */
    public double getWithdrawAmt(){
        return withdrawAmt;
    }
    
    /**
     * Gets the windowId associated with the withdrawal.
     * @return - the windowId associated with the withdrawal.
     */
    public int getWindowId(){
        return windowId;
    }
    
    
}
