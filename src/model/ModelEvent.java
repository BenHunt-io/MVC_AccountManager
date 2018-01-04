/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.event.ActionEvent;

/**
 *
 * @author Ben
 * Used to notify interested objects of changes in the state of a model
 */
public class ModelEvent{
    
    private double newBalance;
    private String actionCommand;
    private int accountId;
    
    /**
     * Constructs basic ModelEvent with information regarding to the state change.
     * @param newBalance - new balance of account.
     * @param actionCommand - what kind of event it is.
     * @param accountId  - what id is associated with the event.
     */
    public ModelEvent(double newBalance, String actionCommand, int accountId){
        this.newBalance = newBalance;
        this.actionCommand = actionCommand;
        this.accountId = accountId;
    }
    
    /**
     * Constructs basic ModelEvent with information regarding to the state change.
     * @param actionCommand - what kind of event it is.
     * @param accountId - what id is associated with the event.
     */
    public ModelEvent(String actionCommand, int accountId){
        this.actionCommand = actionCommand;
        this.accountId = accountId;
    }
    
    
    /**
     * Gets the balance after the state change.
     * @return - the new balance
     */
    public double getNewBalance(){
        return newBalance;
    }
    
    /**
     * Gets what kind of event it is.
     * @return - the kind of event it is.
     */
    public String getActionCommand(){
        return actionCommand;
    }
    
    /**
     * Gets the accountId associated with the event.
     * @return 
     */
    public int getAccountId(){
        return accountId;
    }
    
}
