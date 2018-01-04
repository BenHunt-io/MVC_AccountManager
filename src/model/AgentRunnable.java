/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Used for storing the associated agent with a running agent thread.
 * @author Ben
 */
public abstract class AgentRunnable implements Runnable{

    private Agent agent;
    
    /**
     * Takes in the agent that is associated with the new agent thread.
     * @param agent -  the agent that is associated with the new agent thread.
     */
    public AgentRunnable(Agent agent){
        this.agent = agent;
    }
    
    /**
     * Gets the agent that is associated with the new agent thread.
     * @return  the agent that is associated with the new agent thread.
     */
    public Agent getAgent(){
        return agent;
    }
    
    /**
     * Sets the agent that is associated with the new agent thread.
     * @param agent - agent to set the current agent to,
     */
    public void setAgent(Agent agent){
        this.agent = agent;
    }
    
    /**
     * Gets the state of the agent that is associated with the new agent thread.
     * @return - the state of the agent that is associated with the new agent thread.
     */
    public String getAgentState(){
        return agent.getState();
    }
    
}
