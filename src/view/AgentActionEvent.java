/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This event occurs when a deposit/withdraw is made from an Agent
 * Contains necessary information to update the views.
 * @author Ben
 */
public class AgentActionEvent extends ActionEvent {
    
    private boolean deposit;
    private int agentId;
    private double amountInUSD;
    private int operationsPerSec;
    private int accountId;
    private Object runningAgentSource;

    /**
     * Constructs an action event corresponding to an agent depositing/withdrawing.
     * Contains necessary information to update the views.
     * @param source - source of the event.
     * @param id - id of the event.
     * @param command - action command of the event, can be to differentiate.
     * @param deposit - deposit flag
     * @param agentId - agentId associated with the event.
     * @param amountInUSD - amount being withdrawn/deposited.
     * @param operationsPerSec - amount of operations occurring/sec for the agent
     * associated with the event.
     * @param accountId - accountId associated with the event.
     */
    public AgentActionEvent(Object source, int id, String command,boolean deposit,
                        int agentId, double amountInUSD, int operationsPerSec,
                        int accountId) {
        super(source, id, command);
        
        this.accountId = accountId;
        this.operationsPerSec = operationsPerSec;
        this.amountInUSD = amountInUSD;
        this.agentId = agentId;
        this.deposit = deposit;
    }
    
    /**
     * Is it a deposit/withdraw event
     * @return - true if deposit/ false if withdraw
     */
    public boolean isDeposit(){
        return deposit;
    }
    /**
     * Gets the agentId associated with the event.
     * @return the agentId associated with the event.
     */
    public int getAgentId(){
        return agentId;
    }
    
    /**
     * Get amount being withdrawn/deposited.
     * @return amount being withdrawn/deposited.
     */
    public double getAmt(){
        return amountInUSD;
    }
    
    /**
     * Get amount of operations occurring/sec for the agent
     * associated with the event.
     * @return - amount of operations occurring/sec for the agent
     * associated with the event.
     */
    public int getOperations(){
        return operationsPerSec;
    }
    
    /**
     * Gets the accountId associated with the event
     * @return 
     */
    public int getAccountId(){
        return accountId;
    }
    
    /**
     * Used for operation on some other object that the once that was initially
     * interacted with.
     * @param obj - different component that needs to be changed or accessed.
     */
    public void setAgentSource(Object obj){
        this.runningAgentSource = obj;
    }
    
    /**
     * Gets the different component that needs to be changed or accessed.
     * @return - the different component that needs to be changed or accessed.
     */
    public Object getAgentSource(){
        return runningAgentSource;
    }
    
}
