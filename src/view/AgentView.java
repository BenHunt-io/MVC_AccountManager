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
import static java.awt.Frame.NORMAL;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import model.AccountModel;
import model.Model;
import model.ModelEvent;
import view.listeners.NumberListener;

/**
 *Renders the models, requests updates from the models, send user gestures to
 * controller, and allows controller to select view. 
 * In Particular this window corresponds to the creation of an agent window.
 * This window spawns an agent and disappears.
 * @author Ben Hunt 12/5/2017
 */
public class AgentView extends JFrameView {
    
    private Integer windowId;
    private boolean deposit;
    private int accountId;
    
    private JTextField amountField;
    private JTextField agentId;
    private JTextField operationsField;
  
    
    /**
     * The constructor will construct and display the JFrame. This
     * frame is made using a GridBagLayout.
     * @param model - model of the application. (AccountModel)
     * @param controller - controller of the application. (AccountController)
     * @param windowId - unique id of the window (randomly generated).
     * @param deposit - whether or not the agent is doing deposits/withdrawals
     * @param accountId - the accountId is the agent operating on.
     */
    public AgentView(Model model, Controller controller, int windowId,
            boolean deposit, int accountId) {
        super(model, controller);
        
        this.accountId = accountId;
        this.deposit = deposit;
        this.windowId = windowId;
        
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
        
        
        JLabel agentIdTitle = new JLabel(Constants.AGENT_ID);
        
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(0,0,0,10);
        
        panel.add(agentIdTitle,c);
        
        agentId = new JTextField();
        agentId.setColumns(10);
        
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(5,0,0,0);
        
        panel.add(agentId,c);
        
        
        JLabel amountFieldTitle = new JLabel(Constants.AGENT_AMOUNT);
        
        
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(0,0,0,10);
        
        panel.add(amountFieldTitle,c);
        
        amountField = new JTextField();
        amountField.addKeyListener(new NumberListener(amountField));
        amountField.setText("0.0");
        amountField.setColumns(10);
        
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        c.insets = new Insets(5,0,0,0);
        
        panel.add(amountField,c);
        
        
        JLabel operationsTitle = new JLabel(Constants.AGENT_OPERATIONS);
        
        
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(0,0,0,10);

        
        
        panel.add(operationsTitle,c);
        
        
        operationsField = new JTextField();
        operationsField.setColumns(10);
        
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 2;
        c.insets = new Insets(5,0,0,0);
        
        panel.add(operationsField,c);
        
        
        JButton startAgent = new JButton(Constants.START_AGENT);
        startAgent.addActionListener(handler);
        
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 3;
        c.insets = new Insets(10,0,0,0);
        
        panel.add(startAgent,c);
        
        JButton dismissAgent = new JButton(Constants.DISMISS_AGENT);
        dismissAgent.addActionListener(handler);
        
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 3;
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
     * For the AgentView, no updates are necessary.
     * @param event - the model changed event
     **/
     
    @Override
    public void modelChanged(ModelEvent event) {
       
    }
    
    /**
     * Delegates actions from the views to the controller. Sends the action
     * event to controller.
     */
    class Handler implements ActionListener{
        
        
        @Override
        public void actionPerformed(ActionEvent e) {
            
            // Grab the values needed
            double amount = Double.parseDouble(amountField.getText());
            
            // Validate agent Id
            JFrame jframe = ((JFrame)((JButton)(e.getSource())).getTopLevelAncestor());       
            Object[] objects = new Object[1];
            objects[0] = (Object)"Dismiss";
            String errorMsg = "Agent id: " + agentId.getText() + " should only be digits. Please Try again.";

    
            int idOfAgent;
            
            try{
                idOfAgent = Integer.parseInt(agentId.getText());
            }catch(NumberFormatException nfe){
                JOptionPane optionPane = new JOptionPane(errorMsg, JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, null, objects, NORMAL);
                JDialog dialog = optionPane.createDialog(jframe,"Agent Validation");
                dialog.setModal(false);
                dialog.setVisible(true);
                return;
            }
             
            
            // Validate Operations input
            errorMsg = "Operations Input: " + operationsField.getText() + " should only be digits. Please Try again.";
        
            int operationsPerSec;
            
            try{
                operationsPerSec = Integer.parseInt(operationsField.getText());
            }catch(NumberFormatException nfe){
                JOptionPane optionPane = new JOptionPane(errorMsg, JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, null, objects, NORMAL);
                JDialog dialog = optionPane.createDialog(jframe,"Operations Validation");
                dialog.setModal(false);
                dialog.setVisible(true);
                return;
            }
            
            // Delegate action event
            AgentActionEvent event = new AgentActionEvent(e.getSource(),0,e.getActionCommand(),
                    deposit,idOfAgent,amount,operationsPerSec,accountId);
                    
           ((AccountController)getController()).operation(event);
        } 
    }
    
    /**
     * Sets the title of the window depending on if it's deposit or withdraw.
     * @param deposit - flag for deposits/withdraws
     */
    public void setAgentTitle(boolean deposit){
        
        if(deposit){
            setTitle("Start deposit agent for account:" + accountId);
        }
        else
            setTitle("Start withdraw agent for account:" + accountId);
    }
}
