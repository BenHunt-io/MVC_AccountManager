/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import assignment2.Constants;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import model.AccountModel;
import model.exceptions.WithdrawException;
import view.AgentActionEvent;
import view.AgentRunningView;
import view.AgentView;
import view.EditView;
import view.HomeView;
import view.View;
import static java.lang.System.out;
import model.Agent;

/**
 * Defines application behavior, maps user actions to model updates, selects
 * views for response, one for each functionality.
 * @author Ben
 */
public class AccountController extends AbstractController {
    
    /**
     * The account controller essentially creates the MVC. In main it is passed
     * a fileName to read the accounts in from.
     * @param fileName -  to read the accounts in from.
     */
    public AccountController(String fileName){
        
        HashMap<Integer,View> openEditWindows = getEditViews();
        openEditWindows = new HashMap<>(10);
        
        setModel(new AccountModel(fileName));
        setView((View) new HomeView(getModel(),this));
        
        ((JFrame)getView()).setVisible(true);
    }
    
    /**
     * User gestures from views get mapped here where the controller
     * will decide what functions in the model to call based on the action event
     * that is passed in.
     * @param e - the action event (user gesture)
     */
    public void operation(ActionEvent e){
        
        switch(e.getActionCommand()){
            case Constants.EDIT_IN_USD: 
            case Constants.EDIT_IN_YEN:
            case Constants.EDIT_IN_EURO:
                openEdit(e.getActionCommand());
                break;
            case Constants.SAVE:
                ((AccountModel)getModel()).save();
                break;
            case Constants.EXIT: ((AccountModel)getModel()).exit(e);
                break;
            case Constants.CREATE_DEPOSIT_AGENT:
                openAgent(e);
                break;
            case Constants.CREATE_WITHDRAW_AGENT:
                openAgent(e);
                break;
            case Constants.DROP_DOWN:
                ((AccountModel)getModel()).setCurrentId(e);
                break;
                // Withdraw in the edit account window
            case Constants.WITHDRAW:
        {
            try {
                ((AccountModel)getModel()).withdraw(e);
            } catch (WithdrawException ex) {
                int windowId = ((AccountModel)getModel()).getRandomId();
                ((EditView)(getEditViews().get(ex.getWindowId()))).openErrorDialog();
            }
        }
                break;
            case Constants.DEPOSIT:
                ((AccountModel)getModel()).deposit(e);
                break;
            case Constants.DISMISS:
                ((AccountModel)getModel()).dismiss(e);
                break;
            case Constants.START_AGENT:
                openRunningAgent(e);
                break;
            case Constants.DISMISS_AGENT:
                ((AccountModel)getModel()).dismiss(e);
                ((AccountModel)getModel()).removeAgent(((AgentActionEvent)e).getAgentId());
                break;
            case Constants.STOP_AGENT:
                ((AccountModel)getModel()).stopAgent(e);
                break;
            
                
        }
    }
    
    
    /**
     * Opens a new EditView window with the given currency.
     * @param currency - currency to open the EditView with.
     */
    public void openEdit(String currency){
        // They key of the next edit window that gets made.
        int windowId = ((AccountModel)getModel()).getRandomId();
        
        // Create an account assocciated with the new editWindow
        ((AccountModel)getModel()).createOpenAccount(currency,windowId);
        HashMap<Integer,View> openEditWindows = getEditViews();

        // Put the new editWindow that was opened in the hashMap
        openEditWindows.put(windowId,(new EditView(getModel(),this,windowId)));
        ((EditView)openEditWindows.get(windowId)).setVisible(true);
  
    }
    
    /**
     * Opens an a new AgentView for creating agents
     * @param e - the action event will determine if it's a deposit/withdraw
     * agent
     */
    public void openAgent(ActionEvent e){
        // They key of the next edit window that gets made.
        int windowId = ((AccountModel)getModel()).getRandomId();
        int acctId = ((AccountModel)getModel()).getCurrentId();
        
        switch(e.getActionCommand()){
            case Constants.CREATE_DEPOSIT_AGENT:
                // Put the new editWindow that was opened in the hashMap
                new AgentView(getModel(),this,windowId,true,acctId).setVisible(true);
                break;
            case Constants.CREATE_WITHDRAW_AGENT:
                new AgentView(getModel(),this,windowId,false,acctId).setVisible(true);
                break;
        }
    }
    
    
    /**
     * Opens a window for a newly created agent. The agent should be running.
     * @param e - The action event that created the agent. (Button click)
     */
    public void openRunningAgent(ActionEvent e){
        // if the agent is a duplicate, return
        if(!((AccountModel)getModel()).verifyAgentId(e, ((AgentActionEvent)e).getAgentId())){
            return;
        }
        
        AgentActionEvent event = (AgentActionEvent)e;
        // They key of the next edit window that gets made.
        int windowId = ((AccountModel)getModel()).getRandomId();
        int acctId = ((AccountModel)getModel()).getCurrentId();
        
        // Create an agent object associated with the open running agent window
        ((AccountModel)getModel()).createOpenAgent(windowId, event.getAgentId(),
                                    Agent.AGENT_RUNNING, acctId, event.getAmt());
        
        if(((AgentActionEvent)e).isDeposit()){
             event.setAgentSource(new AgentRunningView(getModel(),this,windowId,true,acctId,
                     event.getAgentId(),event.getAmt(),event.getOperations()));
             ((JFrame)event.getAgentSource()).setVisible(true);
             
        }
             
        else{
            event.setAgentSource(new AgentRunningView(getModel(),this,windowId,false,acctId,
                     event.getAgentId(),event.getAmt(),event.getOperations()));
            ((JFrame)event.getAgentSource()).setVisible(true);
        }
        
        // Dismiss the old window
        ((AccountModel)getModel()).dismiss(e);
        
            
        ((AccountModel)getModel()).runAgent(event);
            
        }
    }
    

