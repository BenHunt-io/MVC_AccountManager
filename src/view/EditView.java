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
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import model.AccountModel;
import model.EditAccount;
import model.Model;
import model.ModelEvent;
import view.listeners.NumberListener;
/**
 * Renders the models, requests updates from the models, send user gestures to
 * controller, and allows controller to select view. 
 * In particular this view corresponds to an edit window for the account in
 * a chosen currency.
 * @author Ben
 */
public class EditView extends JFrameView {

    
    private Integer windowId;
    private JLabel acctBalanceLbl;
    private JLabel amountFieldTitle;
    private JTextField amountField;
    
    /**
     * The constructor will construct and display the JFrame. This
     * frame is made using a GridBagLayout.
     * @param model - model of the application. (AccountModel)
     * @param controller - controller of the application. (AccountController)
     * @param windowId - unique id of the window (randomly generated).
     */
    public EditView(Model model, Controller controller, int windowId) {
        super(model, controller);
        
        this.windowId = windowId;
        
        setFrameTitle();
        
        // For delegating view events (button clicks)
        Handler handler = new Handler();
        
        ((AccountModel)getModel()).addModelListener(this);
       
        GridBagConstraints c = new GridBagConstraints(); // Constraints for GBL
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(540,190));
        // Create border for the panel
        Border dashedBorder = BorderFactory.createDashedBorder(Color.DARK_GRAY);
        panel.setBorder(BorderFactory.createTitledBorder (dashedBorder, 
                Constants.EDIT_ACCOUNT.concat((" ID["+ windowId +"]"))));
        
        acctBalanceLbl = new JLabel("Available Funds: " + 
                    ((AccountModel)getModel()).getOpenAccountBalance(windowId));
        
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        
        panel.add(acctBalanceLbl,c);
        
        String title = "Enter amount in " + 
                ((AccountModel)getModel()).getOpenAccountCurrency(windowId)+":";
        
        amountFieldTitle = new JLabel(title);
        
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(0,10,0,0);
        
        panel.add(amountFieldTitle,c);
        
        
        amountField = new JTextField();
        amountField.addKeyListener(new NumberListener(amountField));
        amountField.setText("0.0");
        amountField.setColumns(15);
        
        c = new GridBagConstraints();
        c.gridx = 2;
        c.gridy = 0;
        
        panel.add(amountField,c);
        
        
        JButton withdraw = new JButton(Constants.WITHDRAW);
        withdraw.addActionListener(handler);
        
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(15,0,0,0);
        
        panel.add(withdraw,c);
        
        JButton deposit = new JButton(Constants.DEPOSIT);
        deposit.addActionListener(handler);
        
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        c.insets = new Insets(15,0,0,0);
        
        panel.add(deposit,c);
        
        JButton dismiss = new JButton(Constants.DISMISS);
        dismiss.addActionListener(handler);
       
        c = new GridBagConstraints();
        c.gridx = 2;
        c.gridy = 1;
        c.insets = new Insets(15,0,0,0);
        
        panel.add(dismiss,c);
        

        
        this.getContentPane().setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.insets = new Insets(15,15,15,15);
        this.getContentPane().add(panel,c);
        this.setResizable(false); // Can't resize window
        
        
        
        pack();
        
    }

    /**
     * Receives updates from model when the model changes state.
     * Updates balance and resets text field if necessary.
     * @param event - the model changed event, used to update what is needed.
     */
    @Override
    public void modelChanged(ModelEvent event) {
        
        // windowId is from the respective EditView
        int acctId = ((AccountModel)getModel()).getOpenAccount(windowId).getAccountId();
        
        if(acctId != event.getAccountId() && 
                !event.getActionCommand().equals(Constants.EXIT)){
            return; // not the account in question
        }
        
        switch(event.getActionCommand()){
            case Constants.BALANCE_UPDATE:
                acctBalanceLbl.setText("Available Funds: "+
                        ((AccountModel)getModel()).getConvertedBalance(event.getNewBalance(),windowId));
                amountField.setText("0.0");
                break;
            case Constants.AGENT_BALANCE_UPDATE:
                acctBalanceLbl.setText("Available Funds: "+
                        ((AccountModel)getModel()).getConvertedBalance(event.getNewBalance(),windowId));
                break;
            case Constants.EXIT:
                System.out.println("test");
                this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                break;
           
                
        }
    }
    
    /**
     * Sets the title of the frame to represent what the window is doing.
     */
    public void setFrameTitle(){
        
        String currencySign = (((AccountModel)getModel()).getOpenAccountCurrency
         (windowId));
        
        String title = (((AccountModel)getModel()).getCurrentId() + "; " + 
                "Operations in " + currencySign);
        setTitle(title);
        
    }
    
    /**
     * Delegates actions from the views to the controller. Sends the action
     * event to controller.
     */
    class Handler implements ActionListener{
        
        
        
        @Override
        public void actionPerformed(ActionEvent e) {
            
            double amt = Double.parseDouble(amountField.getText());
            System.out.println(windowId);
            UniqueActionEvent editWindowEvent = new UniqueActionEvent
                    (e.getSource(),e.getID(),e.getActionCommand(),
                            windowId, amt);
            
           ((AccountController)getController()).operation(editWindowEvent);
           
        } 
    }
    
    /**
     * Used for different events, like dismissing windows.
     */
    class WindowHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            UniqueActionEvent uae = new UniqueActionEvent(this,0,Constants.DISMISS
                                ,windowId);
            ((AccountController)getController()).operation(uae);
        }
        
    }
    
    /**
     * Opens an error dialog if the withdraw amount is too great.
     */
    public void openErrorDialog(){
        Object[] objects = new Object[1];
        objects[0] = (Object)"Dismiss";
        double balance = ((AccountModel)getModel()).getOpenAccountBalance(windowId);
        double withdrawAmt = Double.parseDouble(amountField.getText());
        String errorMsg = "Insufficient funds: Amount to withdraw is " + withdrawAmt
                    + " ,with available funds " + balance;
        JOptionPane.showOptionDialog(this, errorMsg, "Insuffcient funds for Withdraw", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, null, objects, NORMAL);
    }
}
