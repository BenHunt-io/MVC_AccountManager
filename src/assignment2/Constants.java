package assignment2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Constants that are used throughout the program. Many of which are used
 * for routing user gestures to the controller correctly.
 * Constants of the agent are segregated away from the other constants of
 * the application.
 * @author Ben
 */
public class Constants {
    
    public static final double EURO = .79; // $1 = .79 Euro
    public static final double YEN = 94.1; // $1 = 94.1 Yen
    
    public static final String EDIT_IN_USD = "Edit in USD";
    public static final String EDIT_IN_YEN = "Edit in Yen";
    public static final String EDIT_IN_EURO = "Edit in Euro";
    public static final String DROP_DOWN = "Drop Down";
    public static final String CREATE_DEPOSIT_AGENT = "Create deposit Agent";
    public static final String CREATE_WITHDRAW_AGENT = "Create withdraw Agent";
    
    
    public static final String APP_NAME = "Account Manager";
    public static final String EDIT_ACCOUNT = "Edit Account";
    public static final String AGENT_WINDOW = "Agent";
    
    public static final String SAVE = "Save";
    public static final String EXIT = "Exit";
    
    public static final String DROP_DOWN_TITLE = "Accounts:";
    
    public static final String WITHDRAW = "Withdraw";
    public static final String DEPOSIT = "Deposit";
    public static final String DISMISS = "Dismiss";
    
    
    //////////////// Model Update Action Commands ///////////////////
    public static final String BALANCE_UPDATE = "BALANCE_UPDATE";
    
    //////////////// Agent //////////////////////////////////////////
    public static final String STOP_AGENT = "Stop Agent";
    
    public static final String AGENT_BALANCE_UPDATE = "Agent balance update";
    public static final String AGENT_ID = "Agent ID";
    public static final String AGENT_AMOUNT = "Amount in $";
    public static final String AGENT_OPERATIONS = "Operations per second";
    
    public static final String START_AGENT = "Start agent";
    public static final String DISMISS_AGENT = "Dismiss agent";
    
    public static final String AGENT_RUNNING_WINDOW = "Agent running window";
    
}
