/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import assignment2.Constants;
import controller.AccountController;
import controller.Controller;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import model.AccountModel;
import model.Agent;
import model.Model;
import model.ModelAgentEvent;
import model.ModelEvent;

/**
 * Renders the models, requests updates from the models, send user gestures to
 * controller, and allows controller to select view. 
 * In particular this view represents the newly created agent and displays 
 * the state of its operations with options to stop and dismiss agent.
 * @author Ben
 */
public class AgentRunningView extends JFrameView {

    private Integer windowId;
    private boolean deposit;
    private int accountId;
    private int agentId;
    private double amountInUSD;
    private int operationsPerSec;
    
    private JLabel agentStateVal;
    private JLabel amountTransferredVal;
    private JLabel opsCompletedVal;
    private JButton dismissAgent;
    
  
    /**
     * The constructor will construct and display the JFrame. This
     * frame is made using a GridBagLayout.
     * @param model - model of the application. (AccountModel)
     * @param controller - controller of the application. (AccountController)
     * @param windowId - unique id of the window (randomly generated).
     * @param deposit - whether or not the agent is doing deposits/withdrawals
     * @param accountId - the accountId is the agent operating on.
     * @param agentId - unique agentId associated with the window.
     * @param amountInUSD - amount to withdraw/deposit for the agent.
     * @param operationsPerSec - the amount of operations the agent should 
     * perform per second.
     */
    public AgentRunningView(Model model, Controller controller, int windowId,
            boolean deposit, int accountId, int agentId, double amountInUSD,
            int operationsPerSec) {
        super(model, controller);
        
        this.operationsPerSec = operationsPerSec;
        this.agentId = agentId;
        this.accountId = accountId;
        this.deposit = deposit;
        this.windowId = windowId;
        this.amountInUSD = amountInUSD;
        
        setAgentTitle(deposit);
        
        // For delegating view events (button clicks)
        Handler handler = new Handler();
        
        ((AccountModel)getModel()).addModelListener(this);
       
        GridBagConstraints c = new GridBagConstraints(); // Constraints for GBL
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(475,300));
        // Create border for the panel
        Border dashedBorder = BorderFactory.createDashedBorder(Color.DARK_GRAY);
        String agentBorder = Constants.AGENT_WINDOW.concat("[" + windowId + "]");
        panel.setBorder(BorderFactory.createTitledBorder (dashedBorder, 
                agentBorder));
        
        
        JLabel amountText = new JLabel("Amount in $: ");
        
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_END;
        
        
        panel.add(amountText,c);
        
        JLabel amountVal = new JLabel(""+amountInUSD);
        
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.gridy = 0;c.anchor = GridBagConstraints.LINE_START;

        

        panel.add(amountVal,c);
        
        
        JLabel operationsTitle = new JLabel("Operations per second: ");
        
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.LINE_END;
        
        panel.add(operationsTitle,c);
        
        JLabel operationsVal = new JLabel(""+operationsPerSec);
        
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        c.anchor = GridBagConstraints.LINE_START;

        panel.add(operationsVal,c);
        
       
        JLabel agentStateTitle = new JLabel("State: ");
        
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 2;
        c.anchor = GridBagConstraints.LINE_END;
        
        panel.add(agentStateTitle,c);
        
        agentStateVal = new JLabel("Running");
        
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 2;
        c.anchor = GridBagConstraints.LINE_START;

        panel.add(agentStateVal,c);
        
        
        
        JLabel amountTransferredTitle = new JLabel("Amount transferred in $: ");
        
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 3;
        c.anchor = GridBagConstraints.LINE_END;
        
        panel.add(amountTransferredTitle,c);
        
        amountTransferredVal = new JLabel("0");
        
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 3;
        c.anchor = GridBagConstraints.LINE_START;

        panel.add(amountTransferredVal,c);
        
        
        
        JLabel opsCompletedTitle = new JLabel("Operations Completed: ");
        
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 4;
        c.anchor = GridBagConstraints.LINE_END;
        
        panel.add(opsCompletedTitle,c);
        
        opsCompletedVal = new JLabel("0");
        
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 4;
        c.anchor = GridBagConstraints.LINE_START;


        panel.add(opsCompletedVal,c);
        
        
        JButton stopAgent = new JButton(Constants.STOP_AGENT);
        stopAgent.addActionListener(handler);
        
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 5;
        c.insets = new Insets(10,0,0,0);
        
        panel.add(stopAgent,c);
        
        dismissAgent = new JButton(Constants.DISMISS_AGENT);
        dismissAgent.addActionListener(handler);
        dismissAgent.setEnabled(false);
        
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 5;
        c.insets = new Insets(10,0,0,0);
        
        panel.add(dismissAgent,c);
        
        
        
        
        
        
        
        
        this.getContentPane().setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.insets = new Insets(15,15,15,15);
        this.getContentPane().add(panel,c);
        this.setResizable(false); // Can't resize window
        
        
        
        pack();
        
    }

    /**
     * Receives updates from model when the model changes state.
     * Updates agent running state, operations completed, and total amount
     * transferred.
     * @param event - the model changed event
     **/
    @Override
    public void modelChanged(ModelEvent event) {
        
       if(event instanceof ModelAgentEvent){
           
            ModelAgentEvent agentModelEvent = ((ModelAgentEvent)event);
            
            //If it's not the right agent, it's not interested in the update
            if(agentModelEvent.getAgentId() != agentId)
                return;
            
            
            if(agentModelEvent.getAgentId() == agentId){
                agentStateVal.setText(""+agentModelEvent.getState());
                amountTransferredVal.setText(""+agentModelEvent.getAmountTransferred());
                opsCompletedVal.setText(""+agentModelEvent.getOpsCompleted());

            }
       }
       
    }
    
    /**
     * Sets the title of the window depending on if it's deposit or withdraw.
     * @param deposit - flag for deposits/withdraws
     */
    public void setAgentTitle(boolean deposit){
        
        if(deposit){
            setTitle("Deposit agent " + agentId + " for account " + accountId);
        }
        else
            setTitle("Withdraw agent " + agentId + " for account " + accountId);
    }
    
    
    /**
     * Delegates actions from the views to the controller. Sends the action
     * event to controller.
     */
    class Handler implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent e) {
            
            
            // Stop agent
            AgentActionEvent event = new AgentActionEvent(e.getSource(),e.getID()
                ,e.getActionCommand(),false,agentId,0,0,accountId);
            event.setAgentSource(dismissAgent);
           ((AccountController)getController()).operation(event);
           
        } 
    }
    
    
}
