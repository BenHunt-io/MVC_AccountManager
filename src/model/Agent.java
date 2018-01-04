/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Agent object is constructed to store related agent information.
 * @author The Dough Boys
 */
public class Agent {
    
    public static final String AGENT_RUNNING = "Agent Running";
    public static final String AGENT_STOPPED = "Agent Stopped";
    public static final String AGENT_BLOCKED = "Agent Blocked";
    
    private int windowId;
    private int agentId;
    private String state;
    private double amountTransferred;
    private int operationsCompleted;
    private int accountId;
    private double transferAmt;
    
    
    /**
     * Agent object is constructed to store related agent information.
     * @param windowId - windowId of the where the agent is operating
     * @param agentId - id of the agent
     * @param state - state of the agent RUNNING/STOPPED/BLOCKED
     * @param accountId - accountId associated with the agent
     * @param transferAmt - amount the agent is suppose to transfer
     */
    public Agent(int windowId, int agentId, String state, int accountId, double transferAmt){
        
        this.windowId = windowId;
        this.agentId = agentId;
        this.state = state;
        this.accountId = accountId;
        this.transferAmt = transferAmt;
    }
    
    
    /**
     * Gets the  windowId of the where the agent is operating
     * @return -  windowId of the where the agent is operating
     */
    public int getWindowId(){
        return windowId;
    }
    
    /**
     * Gets the id of the agent
     * @return - id of the agent
     */
    public int getAgentId(){
        return agentId;
    }
    
    
    /**
     * Gest the state of the agent RUNNING/STOPPED/BLOCKED
     * @return - state of the agent RUNNING/STOPPED/BLOCKED
     */
    public String getState(){
        return state;
    }
    
    /**
     * Sets the amount the agent is suppose to transfer
     * @param amount - amount the agent is suppose to transfer
     */
    public void setAmountTransferred(double amount){
        this.amountTransferred = amount;
    }
    
    /**
     * Gets the amount the agent is suppose to transfer
     * @return - amount the agent is suppose to transfer
     */
    public double getAmountTransferred(){
        return amountTransferred;
    }
    
    /**
     * Sets the total operations completed
     * @param opsCompleted - gets the total operations completed 
     */
    public void setOpsComplete(int opsCompleted){
        this.operationsCompleted = opsCompleted;
    }
    
    /**
     * Increment the total operations by 1
     */
    public void incrementOpsCompleted(){
        operationsCompleted++;
    }
    
    /**
     * Gets the total operations completed
     * @return - the total operations completed
     */
    public int getOpsCompleted(){
        return operationsCompleted;
    }
    
    /**
     * Sets the state of the agent to RUNNING/STOPPED/BLOCKED
     * @param state the state of the agent = RUNNING/STOPPED/BLOCKED
     */
    public void setState(String state){
        this.state = state;
    }
    
    /**
     * Gets the accountId associated with the agent
     * @return - the accountId associated with the agent
     */
    public int getAccountId(){
        return accountId;
    }
    
    /**
     * Sets the accountId associated with the agent
     * @param accountId - the accountId associated with the agent
     */
    public void setAccountId(int accountId){
        this.accountId = accountId;
    }
    
    /**
     * Gets the amount the agent is suppose to transfer
     * @return - the amount the agent is suppose to transfer
     */
    public double getTransferAmt(){
        return transferAmt;
    }
    
   
    
}
