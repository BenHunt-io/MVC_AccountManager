/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Used to notify interested objects of changes in the state of a model.
 * These model events occur from agents.
 * @author Ben Hunt
 */
public class ModelAgentEvent extends ModelEvent{
    
    // agent specific
    private String state; // Running/Blocked/Stopped
    private double amountTransferred; // in USD
    private int operationsCompleted; // How many withdrawals/deposits made
    private int agentId;
    
    /**
     * Constructs basic AgentModelEvent with information regarding to the state change.
     * @param balance - balance after the change
     * @param actionCommand - string identifier of the action
     * @param agentId - agentId associated with the change.
     * @param accountId - accountId associated with the change.
     * @param state - state of the agent associated with the change.
     * @param amountTransferred - the total amount that has been transferred
     * @param operationsCompleted - the total amount of operations that have been
     * completed.
     */
    public ModelAgentEvent(double balance, String actionCommand, int agentId, int accountId, 
            String state, double amountTransferred, int operationsCompleted) {
        super(balance, actionCommand, accountId);
        
        this.agentId =agentId;
        this.state = state;
        this.amountTransferred = amountTransferred;
        this.operationsCompleted = operationsCompleted;
    }
    
    
    /**
     * Returns the State of the agent - Running/Stopped/Blocked
     * @return - the State of the agent - Running/Stopped/Blocked
     */
    public String getState(){
        return state;
    }
    
    /**
     * Gets the total amount transferred thus far.
     * @return - the total amount transferred thus far. (USD)
     */
    public double getAmountTransferred(){
        return amountTransferred;
    }
    
    /**
     * Gets the total amount transferred thus far.
     * @return - the total amount transferred thus far.
     */
    public int getOpsCompleted(){
        return operationsCompleted;
    }
    
    /**
     * Gets the agentId associated with the event.
     * @return - the agentId associated with the event.
     */
    public int getAgentId(){
        return agentId;
    }
}
