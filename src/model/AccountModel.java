/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import assignment2.Constants;
import java.awt.Container;
import static java.awt.Frame.NORMAL;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import model.exceptions.WithdrawException;
import view.AgentActionEvent;
import view.EditView;
import view.UniqueActionEvent;

/**
 * Encapsulates application state, responds to state queries, exposes application
 * functionality, and notifies views of changes.
 * @author Ben 12/5/2017
 */
public class AccountModel extends AbstractModel {
    
    
    private String fileName; // name of the file to be read in.
    
    
    // list of accounts that were read in from a file
    private ArrayList<UserAccount> accounts; 
    // HashMap of accounts created and associated with EditViews
    private HashMap<Integer,EditAccount> openAccounts; 
    // HashMap of Agents created and associated with AgentRunningViews
    private HashMap<Integer,Agent> openAgents;
    
   // private int currentId;
    private String currentAccount; // current selected drop down menu account string
    private int currentId; // current id of selected drop down menu account
    private UserAccount currentUserAccount; // current selected drop down menu account
    // Withdraw threads are synchronized on this lock. Will fall asleep if
    // can't withdraw and deposit locks will use this lock to notifyAll.
    private final Object withdrawLock;
    // Keeps track of any changes made since last save. So it can write to file
    // if exit is pressed.
    private boolean changesPresent;
    
    
    /**
     * First constructs an empty model, then reads in from a given file
     * and saves data to private member fields.
     * @param fileName - file to be read in
     */
    public AccountModel(String fileName){
        
        accounts = new ArrayList<>(10);
        openAccounts = new HashMap<>(10);
        openAgents = new HashMap<>(10);
        
        withdrawLock = new Object();
        
        
        this.fileName = fileName;
        readInAccountData(fileName);
        
        if(!accounts.isEmpty()){
            currentAccount = getIdWithName()[0];
            currentUserAccount = accounts.get(0);
            currentId = currentUserAccount.getAccountId();
        }
        
        
        
    }
    

    /**
     * Concatenates id's and names.
     * @return A string array containing the concatenated id's and names. 
     */
    public String[] getIdWithName(){
        String[] accountList = new String[accounts.size()];
        for(int i = 0; i<accounts.size(); i++){
            accountList[i] = accounts.get(i).getAccountName().concat(" ")
                    .concat(accounts.get(i).getAccountId() + "");
         }
        return accountList;
    }
    
    /**
     * Reads in account data from a file
     * @param fileName - the name of the file to read data in from.
     */
    private void readInAccountData(String fileName){
        
        // Temporary storage before creaing userObj
        String userName; int id; double balance;
        String line; // Reads into line, then splits and parses data.
        String[] accountInfo; // Temporary storage before creaing userObj
        
        try {
            
            Scanner inputStream = new Scanner(new FileInputStream("src/"+fileName));
            
            while(inputStream.hasNext()){
                line = inputStream.nextLine();
                accountInfo = line.split(" ");
                userName = accountInfo[0];
                id = Integer.parseInt(accountInfo[1]);
                balance = Double.parseDouble(accountInfo[2]);
                // Add a user account with the parsed data entry
                accounts.add(new UserAccount(id,userName,balance));
            }
            
        } catch (FileNotFoundException ex) {
            System.out.println("File not found reading in account data: " 
                    +fileName+" - " + ex);
        }
    }
    
    /**
     * Exits the program, closes all windows. Writes changes to a file if model
     * of accounts has changed since last save.
     * @param e - Button event
     */
    public void exit(ActionEvent e){
        if(changesPresent)
            save();
        //Grab the window in question
        Window window = ((Window)((JButton)e.getSource()).getTopLevelAncestor());
        window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
        
        // Notify all open windows to close
         notifyChanged(new ModelEvent(0,Constants.EXIT,0));
         
         System.exit(0); // end the program
    }
    
    /**
     * Dismisses any window that occurred from a JButton click
     * @param e - The action event that was intended to dismiss a window
     */
    public void dismiss(ActionEvent e){
        WindowEvent winEvent = new WindowEvent((Window)((JButton)e.getSource()).getTopLevelAncestor(),
                        WindowEvent.WINDOW_CLOSING);
        ((JButton)e.getSource()).getTopLevelAncestor().dispatchEvent(winEvent);
    }

    /**
     * Gets the currentID of the drop down menu
     * @return - current selected accountId of the drop down menu
     */
    public int getCurrentId(){
        return currentId;
    }
    
    
    /**
     * Gets the current account balance for a given id.
     * @param accountId - id of the account you want the balance for.
     * @return  - the balance of the account
     */
    public double getAccountBalance(int accountId){
        UserAccount account = getAccount(accountId);
        return account.getAccountBalance();
    }
    
    
    /**
     * Gets the currentUserAccount
     * @return - the currentUserAccount associated with the accounts drop down menu.
     */
    public UserAccount getCurrentAccount(){
        return currentUserAccount;
    }
    
    /**
     * Sets current id (int), currentAccount (string) which is name of the
     * account and the id, and currentUserAccount which is determined by
     * the index of the drop down menu.
     * @param e - the event of selecting an index in the drop down menu.
     */
    public void setCurrentId(ActionEvent e){
        
        String accountLine = ((JComboBox)e.getSource()).getSelectedItem().toString();
        this.currentId = Integer.parseInt(accountLine.split(" ")[1]);
        this.currentAccount = ((JComboBox)e.getSource()).
                getSelectedItem().toString();
        
        this.currentUserAccount = 
                accounts.get(((JComboBox)e.getSource()).getSelectedIndex());
        
    }
    
    /**
     * Saves the account data to a file given some fileName
     */
    public void save(){
        try {
        
            PrintWriter outputStream = new PrintWriter(new FileOutputStream("src/"+fileName));
            for(UserAccount userAccount: accounts){
                outputStream.println(userAccount.getEntry());
                System.out.println(userAccount.getEntry());
            }
            outputStream.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
        
        changesPresent = false;
    }
    
   
    /**
     * Generates a random id for a window that doesn't already exist.
     * Checks to see if it exists in the hash, if it does already exist it will
     * generate another.
     * @return - random id for a window
     */
    public int getRandomId(){
        Random rand = new Random(System.currentTimeMillis());
        // Zero out any negative values
        Integer id = (rand.nextInt() & Integer.MAX_VALUE) % 1000000;
        int timeOut = 0;
        while(openAccounts.containsKey(id) && timeOut < 1000000){
            id = rand.nextInt() % 1000000;
        }
        
        return id;
    }
    
    /**
     * Gets the account associated with the unique window key. Each window that
     * is open gets a unique key. The same account can be associated with 
     * different keys, except that the currency would be different. For ex, one
     * window has account1 open in USD for withdraws and then another window
     * has account1 also open but for deposits in Yen.
     * @param key
     * @return 
     */
    public EditAccount getOpenAccount(Integer key){
        
        if(openAccounts.containsKey(key)){
            return openAccounts.get(key);
        }
        else
            return null;
    }
    
    
    /**
     * Gets the open account
     * @param windowId
     * @return 
     */
    public int getOpenAccountId(int windowId){
        EditAccount openAccount = getOpenAccount(windowId);
        return openAccount.getAccountId();
    }
    
    /**
     * Creates a reference to an account that is open. It's associated with a
     * corresponding EditView.
     * @param windowId - unique id of the newly created window
     * @param currency  - currency of the account being operated on.
     */
    public void createOpenAccount(String currency, int windowId){

        EditAccount newEditAccount = new EditAccount(windowId,
            currentUserAccount.getAccountId(), currentUserAccount.getAccountName()
            ,currentUserAccount.getAccountBalance(), currency);
        
        openAccounts.put(windowId,newEditAccount);
    }
    
    
    /**
     * Given the id of the window that is open, returns the account balance
     * associated with the account of that window.
     * @param windowId
     * @return 
     */
    public double getOpenAccountBalance(int windowId){
        
        NumberFormat formatter = new DecimalFormat("#0.00");   
        
        EditAccount openAccount = openAccounts.get(windowId);
        double rate = 1;
        switch(openAccount.getCurrency()){
            case Constants.EDIT_IN_USD: 
                break;
            case Constants.EDIT_IN_YEN: rate = Constants.YEN;
                break;
            case Constants.EDIT_IN_EURO: rate = Constants.EURO;
                break;
        }
        
        double balance = getAccountBalance(openAccount.getAccountId());
        return Double.parseDouble(
                formatter.format(balance * rate));
    }
    
    /**
     * Get balance of a given edit window. Balance is not converted to USD.
     * @param windowId - id of the window in question
     * @return the unconverted balance of the given window.
     */
    public double getUnconvertedBalance(int windowId){
        
        return getAccountBalance(getOpenAccountId(windowId));
    }
    
    /**
     * Gets the currency sign of the window in question.
     * @param windowId - id of the window that the currency is being requested for.
     * @return returns the currency sign.
     */
    public String getOpenAccountCurrency(int windowId){
        
        EditAccount openAccount = openAccounts.get(windowId);
        String currency = openAccount.getCurrency();
        String currencySign = "N/A";
        switch(currency){
            case Constants.EDIT_IN_USD: currencySign = "$";
                break;
            case Constants.EDIT_IN_YEN: currencySign = "Yen";
                break;
            case Constants.EDIT_IN_EURO: currencySign = "Euro";
                break;
                
        }
        
        return currencySign;
    }
    
    /**
     * returns the currency rate for the open account.
     * @return 
     */
    public double getOpenAccRate(int windowId){
        return openAccounts.get(windowId).getConversionRate();
    }
    
    /**
     * Manual Withdraw.
     * @param e - button event intended to make a withdraw, contains amount
     * to withdraw
     * @throws WithdrawException - Can't make a withdraw that makes balance go negative
     */
    public void withdraw(ActionEvent e) throws WithdrawException{
        
        double amountToWithdraw = ((UniqueActionEvent)e).getAmount();
        
        // Has to be positive number > 1
        if(!verifyInput(e,amountToWithdraw)){
            return;
        }
        
        int windowId = ((UniqueActionEvent)e).getWindowId();
        EditAccount openAccount = getOpenAccount(windowId);
        UserAccount account = getAccount(openAccount.getAccountId());
        double currentBalance = account.getAccountBalance();
        double windowBalance = getOpenAccountBalance(windowId);
        
        amountToWithdraw = convertToUSD(amountToWithdraw,windowId);
        // Can't have a negative balance
        if(currentBalance - amountToWithdraw < 0){
            throw new WithdrawException(windowBalance,amountToWithdraw,windowId);
        }
        
        
        

        // Update balance
        currentBalance -= amountToWithdraw;
        account.setBalance(currentBalance);
        changesPresent = true;
        
        int accountId = account.getAccountId();
                
        notifyChanged(new ModelEvent
                    (currentBalance,Constants.BALANCE_UPDATE,accountId));
        
    }
    
    /**
     * Withdraw occurring from an agent. 
     * @param e - an agent event containing necessary data to make a 
     * withdrawal/deposit
     * @throws WithdrawException - Agents can't withdrawal if balance would be 
     * negative
     */
    public void withdraw(AgentActionEvent e) throws WithdrawException{
        
        double amountToWithdraw = ((AgentActionEvent)e).getAmt();
     
        UserAccount account = getAccount(e.getAccountId());
        Agent agent = openAgents.get(e.getAgentId());
        double currentBalance = account.getAccountBalance();
       
        
        // Can't have a negative balance
        if(currentBalance - amountToWithdraw < 0){
            throw new WithdrawException(currentBalance,amountToWithdraw,agent.getWindowId());
        }

        // Update balance
        currentBalance -= amountToWithdraw;
        account.setBalance(currentBalance);
        changesPresent = true;
        
        
        // update agent
        agent.setAmountTransferred(agent.getAmountTransferred()+amountToWithdraw);
        agent.incrementOpsCompleted();
        
        // notify interested views of updated balance
        notifyChanged(new ModelAgentEvent
                    (currentBalance,Constants.AGENT_BALANCE_UPDATE,e.getAgentId(),
                            e.getAccountId(),agent.getState(),agent.getAmountTransferred(),
                            agent.getOpsCompleted()));
        
    }
    
    
    
    /**
     * Retrieve the account corresponding with the accountId given.
     * @param accountId
     * @return 
     */
    public UserAccount getAccount(int accountId){
        for(UserAccount userAccount: accounts){
            if(userAccount.getAccountId() == accountId){
                return userAccount;
            }
        }
        return null;
        
    }
    
    /**
     * Given a windowID and a modelEvent, a converted balance should be returned.
     * With a given window the currency is known and with a model event the 
     * new balance in USD is known.
     * @param e
     * @param windowId
     * @return 
     */
    public double getConvertedBalance(ModelEvent e, int windowId){
        EditAccount openAccount = openAccounts.get(windowId);
        NumberFormat formatter = new DecimalFormat("#0.00");
        double correctBalance = e.getNewBalance()*openAccount.getConversionRate();
        
        return Double.parseDouble(formatter.format(correctBalance));
    }
    
    
    /**
     * Gets the converted balance (converts to USD).
     * @param bal - current balance in whichever currency is used in the EditWindows.
     * @param windowId - window id for the open window.
     * @return converted balance in USD.
     */
    public double getConvertedBalance(double bal, int windowId){
        EditAccount openAccount = openAccounts.get(windowId);
        NumberFormat formatter = new DecimalFormat("#0.00");
        double correctBalance = bal *openAccount.getConversionRate();
        
        return Double.parseDouble(formatter.format(correctBalance));
    }
    
    
    /**
     * Converts a given balance to USD. Needs windowId to know currency.
     * @param balance - balance in either YEN/USD/EURO.
     * @param windowId - id of the open window.
     * @return - converted balance in USD.
     */
    public double convertToUSD(double balance, int windowId){
        
        switch(getOpenAccount(windowId).getCurrency()){
            case Constants.EDIT_IN_EURO:
                return balance/Constants.EURO;
            case Constants.EDIT_IN_YEN:
                return balance/Constants.YEN;
                
        }
        return balance;
    }
    
    
    
    /**
     * Manual Deposits happen here. Deposits an amount according to a textfield.
     * @param e - the event that should make a manual deposit (Button click).
     */
    public void deposit(ActionEvent e){
        
        double amountToDeposit = ((UniqueActionEvent)e).getAmount();
        
        // Has to be an amount greater than 1 to deposit
        if(!verifyInput(e,amountToDeposit)){
            return;
        }
      
        int windowId = ((UniqueActionEvent)e).getWindowId();
        
        amountToDeposit = convertToUSD(amountToDeposit,windowId);
        

        synchronized(withdrawLock){
            EditAccount openAccount = getOpenAccount(windowId);
            UserAccount account = getAccount(openAccount.getAccountId());
            int accountId = account.getAccountId();

            account.setBalance(account.getAccountBalance() + amountToDeposit);
            changesPresent = true;
        
        
 
        
        notifyChanged(new ModelEvent
            (account.getAccountBalance(),Constants.BALANCE_UPDATE,accountId));
        
            withdrawLock.notifyAll();
        }
        
    }
    
    /**
     * Agent deposits occur here. Deposits the amount specified by the Agent.
     * @param e - the agent event, has data needed to make deposit.
     */
    public void deposit(AgentActionEvent e){
        
        double amountToDeposit = ((AgentActionEvent)e).getAmt();
        Agent agent = openAgents.get(e.getAgentId());
        
        
      
        int accountId = ((AgentActionEvent)e).getAccountId();
        UserAccount account = getAccount(accountId);
        account.setBalance(account.getAccountBalance() + amountToDeposit);
        changesPresent = true;
        
        agent.setAmountTransferred(agent.getAmountTransferred()+e.getAmt());
        agent.incrementOpsCompleted();
 
        
        notifyChanged(new ModelAgentEvent
            (account.getAccountBalance(),Constants.AGENT_BALANCE_UPDATE,
                    e.getAgentId(),accountId,agent.getState(),agent.getAmountTransferred(),
                    agent.getOpsCompleted()));
        

    }
    
    
    
    
    
    
    /**
     * Creates an openAgent which is an Agent account that corresponds to an
     * open agent window.
     * @param windowId - id of the newly created agent window.
     * @param agentId - id of the newly created agent.
     * @param state - state of the newly created agent.
     * @param accountId - accountId associated with the newly created agent.
     * @param transferAmt - amount the agent will transfer every operation.
     */
    public void createOpenAgent(int windowId, int agentId, String state, int accountId, double transferAmt){
        if(!openAgents.containsKey(agentId));
            openAgents.put(agentId,new Agent(windowId, agentId, state, accountId, transferAmt));
            
    }
    
    
    /**
     * Creates an openAgent which is an Agent account that corresponds to an
     * open agent window. (Duplicate of createOpenAgent(...))
     * @param windowId
     * @param agentId
     * @param state
     * @param accountId
     * @param transferAmt 
     */
    public void startAgent(int windowId, int agentId, String state, int accountId, double transferAmt){
        if(!openAgents.containsKey(agentId));
            openAgents.put(agentId,new Agent(windowId, agentId, state, accountId, transferAmt));
                 
    }
    
    /**
     * Creates a deposit/withdraw thread that an agent operates on. The withdraws
     * will wait till a valid withdrawal can be made. The deposit thread makes a
     * deposit and then will wake up sleeping withdrawal threads from a notifyAll
     * and will check to see if a withdraw can occur. (balance - amountToWithdraw > 0).
     * Withdrawal threads never makes a withdraw that causes the balance to go negative.
     * State of threads also depend on the following flags, Agent.AGENT_RUNNING,
     * AGENT.AGENT_BLOCKED, AGENT.AGENT_STOPPED.
     * @param e - The button event that should start an agent.
     */
    public void runAgent(AgentActionEvent e){
        
        int agentId = e.getAgentId();
        Agent agent = openAgents.get(agentId);
        int accountId = e.getAccountId();
        UserAccount userAccount = getAccount(accountId);
        final int operationsPerSec = e.getOperations();
        final double amount = e.getAmt();
        
        if(e.isDeposit()){
            Thread agentDepositThread = new Thread(new AgentRunnable(agent) {

                    @Override
                    public void run() {
                       while(getAgentState().equals(Agent.AGENT_RUNNING)){
                           
                            synchronized(withdrawLock){
                                //System.out.println("before deposit" + e.getAmt());
                                deposit(e);
                                withdrawLock.notifyAll();
                            }
                            
                             try {
                                 Thread.sleep(1000/operationsPerSec);
                             } catch (InterruptedException ex) {
                                 Logger.getLogger(AccountModel.class.getName()).log(Level.SEVERE, null, ex);
                             }

                        }
                    }
                });


            agentDepositThread.start();
        }
        else
        {
            Thread agentWithdrawThread = new Thread(new AgentRunnable(agent) {

                UserAccount account;
        
                    @Override
                    public void run() {
                       // get account associated with agent
                       account = getAccount(agent.getAccountId());
                       
                       while(!getAgentState().equals(Agent.AGENT_STOPPED)){
                           
                           if(getAgentState().equals(Agent.AGENT_BLOCKED)){
                               synchronized(withdrawLock){
                                   while(account.getAccountBalance() < agent.getTransferAmt()){
                                        try {
                                            withdrawLock.wait();
                                        } catch (InterruptedException ex) {
                                            Logger.getLogger(AccountModel.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                   }
                                }
                            }
                                   
                                   setAgentState(agent.getAgentId(),Agent.AGENT_RUNNING);
                                    
                                    
                                    try {
                                        withdraw(e);
                                     } catch (WithdrawException ex) {
                                        //openErrorDialog(ex,e.getAgentSource());
                                        setAgentState(agent.getAgentId(),Agent.AGENT_BLOCKED);


                                     }
                                      try {
                                          Thread.sleep(1000/operationsPerSec);
                                      } catch (InterruptedException ex) {
                                          Logger.getLogger(AccountModel.class.getName()).log(Level.SEVERE, null, ex);
                                      }
                        }
                            

                    }
                       
                       
                       
                    
                });


            agentWithdrawThread.start();
        }
        
    }
    
    
    /**
     * Opens an error dialog when an withdraw exception occurs.
     * @param ex - withdraw exception occurring when withdraw would cause balance
     * to be negative.
     * @param source - JFrame, place to put the error dialog in.
     */
    public void openErrorDialog(WithdrawException ex, Object source){
        Object[] objects = new Object[1];
        objects[0] = (Object)"Dismiss";
        double withdrawAmt = ex.getWithdrawAmt();
        String errorMsg = "Insufficient funds: Amount to withdraw is " + withdrawAmt
                    + " ,with available funds " + ex.getBalance();
       
        JOptionPane optionPane = new JOptionPane(errorMsg, JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, null, objects, NORMAL);
        JDialog dialog = optionPane.createDialog((JFrame)source,"Insuffcient funds for Withdraw");
        dialog.setModal(false);
        dialog.setVisible(true);
      
    }
   
    /**
     * Sets the agent State to the following flags, Agent.AGENT_RUNNING,
     * AGENT.AGENT_BLOCKED, AGENT.AGENT_STOPPED. Updates AgentViews
     * @param agentId - id of agent to change the state of
     * @param state - the following flags: Agent.AGENT_RUNNING,
     * AGENT.AGENT_BLOCKED, AGENT.AGENT_STOPPED.
     */
    public void setAgentState(int agentId, String state){
        Agent agent = openAgents.get(agentId);
        agent.setState(state);
        notifyChanged(new ModelAgentEvent
            (0,"",agentId,0,state,agent.getAmountTransferred(),agent.getOpsCompleted()));
    }
    
    
    /**
     * Stops an agent, thread ends. No more operations occur.
     * @param e - the button event intended to stop an agent.
     */
    public void stopAgent(ActionEvent e){
        
       
        Agent agent = openAgents.get(((AgentActionEvent)e).getAgentId());
        agent.setState(Agent.AGENT_STOPPED);
       
        notifyChanged(new ModelAgentEvent
            (0,"",agent.getAgentId(),0,Agent.AGENT_STOPPED,agent.getAmountTransferred(),agent.getOpsCompleted()));
        
        ((JButton)((AgentActionEvent)e).getAgentSource()).setEnabled(true);
    }
    
    
    /**
     * Verify that the requested agentId isn't in use.
     * @param e - button event intended to create Agent.
     * @param agentId - id of agent to be created
     * @return 
     */
    public boolean verifyAgentId(ActionEvent e, int agentId){
        
        JFrame jframe = ((JFrame)((JButton)(e.getSource())).getTopLevelAncestor());
        
        Object[] objects = new Object[1];
        objects[0] = (Object)"Dismiss";
        String errorMsg = "Agent id: " + agentId + " is already in use. Please Try again.";
        
        if(openAgents.containsKey(agentId)){
            JOptionPane optionPane = new JOptionPane(errorMsg, JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, null, objects, NORMAL);
            JDialog dialog = optionPane.createDialog(jframe,"Agent Validation");
            dialog.setModal(false);
            dialog.setVisible(true);
            return false;
            
        }
        else
            return true;
        
    }
    
    
    /**
     * Verifies that the input for manual withdrawal/deposit is >1
     * @param e - button event that is intended to deposit/withdrawal.
     * @param amt - amount to deposit/withdrawal
     * @return 
     */
    public boolean verifyInput(ActionEvent e, double amt){
        
        JFrame jframe = ((JFrame)((JButton)(e.getSource())).getTopLevelAncestor());
        
        Object[] objects = new Object[1];
        objects[0] = (Object)"Dismiss";
        String errorMsg = "Deposit/Withdrawal Amount: " + amt + " is too small. Amount needs to be > 1";
        
        if(amt <= 1){
            JOptionPane optionPane = new JOptionPane(errorMsg, JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, null, objects, NORMAL);
            JDialog dialog = optionPane.createDialog(jframe,"Input Validation");
            dialog.setModal(false);
            dialog.setVisible(true);
            return false;
            
        }
        else
            return true;
        
    }
    
    /**
     * Removes and Agent from the HashMap
     * @param agentId - id of agent to be removed
     */
    public void removeAgent(int agentId){
        openAgents.remove(agentId);
    }
    
    
    
    

    
}

