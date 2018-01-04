/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.event.ActionEvent;

/**
 * Action event that happens from a unique windowId. This was created because
 * currency's on different windows are different and there needs to be some 
 * identifying aspects of the action event as to implement conversions.
 * @author The Dough Boys
 */
public class UniqueActionEvent extends ActionEvent
{
    private int windowId;
    private double amount;
    
    /**
     * Creates unique action event if amount is also important. (deposit/withdrawal)
     * @param source - source that this event came from
     * @param id - id of the action event
     * @param command - command of the action event, to differentiate.
     * @param windowId - id of the window that event came from
     * @param amount  - amount to deposit/withdrawal
     */
    public UniqueActionEvent(Object source, int id, String command,
                             int windowId, double amount) {
        super(source, id, command);
       
        this.windowId = windowId;
        this.amount = amount;
    }
    
    /**
     * Creates unique action event if amount is also important. (deposit/withdrawal)
     * @param source - source that this event came from
     * @param id - id of the action event
     * @param command - command of the action event, to differentiate.
     * @param windowId - id of the window that event came from
     */
    public UniqueActionEvent(Object source, int id, String command,
                             int windowId) {
        super(source, id, command);
        this.windowId = windowId;
    }
    
    /**
     * Creates unique action event if amount is also important. (deposit/withdrawal)
     * @param source - source that this event came from
     * @param id - id of the action event
     * @param command - command of the action event, to differentiate.
     * @param amount  - amount to deposit/withdrawal
     * @param accountId - accountId associated with the event.
     *
     */
    public UniqueActionEvent(Object source, int id, String command, int amount,
            int accountId){
        super(source, id, command);
        
    }
    
    /**
     * Gets the windowId associated with the event.
     * @return - the windowId associated with the event.
     */
    public int getWindowId(){
        return windowId;
    }
    
    /**
     * Gets the amount to deposit/withdrawal associated with the event.
     * @return - the amount to deposit/withdrawal associated with the event.
     */
    public double getAmount(){
        return amount;
    }
}
