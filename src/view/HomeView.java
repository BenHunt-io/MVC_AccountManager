/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import assignment2.Constants;
import controller.AccountController;
import controller.Controller;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import model.AccountModel;
import model.Model;
import model.ModelEvent;
import model.UserAccount;

/**
 * Renders the models, requests updates from the models, send user gestures to
 * controller, and allows controller to select view. 
 * In particular this is the home page view. All actions derive from this view.
 * Contains options, to manually edit the account or have create agents to edit
 * the account for you. This acts as a sort of control panel.
 * @author Ben
 */
public class HomeView extends JFrameView{
    
    

    /**
     * The constructor will construct and display the JFrame. This
     * frame is made using a GridBagLayout.
     * @param model - model of the application. (AccountModel)
     * @param controller - controller of the application. (AccountController)
     */
    public HomeView(Model model, Controller controller) {
        super(model, controller);
        
        ((AccountModel)getModel()).addModelListener(this);
        
        GridBagConstraints c = new GridBagConstraints(); // Constraints for GBL 
        
        Handler handler = new Handler(); // delegating clicks to Controller
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(395,235));
        // Create border for the panel
        Border dashedBorder = BorderFactory.createDashedBorder(Color.DARK_GRAY);
        panel.setBorder(BorderFactory.createTitledBorder
            (dashedBorder, Constants.APP_NAME));
        
        
        
        
        JLabel dropDownTitle = new JLabel(Constants.DROP_DOWN_TITLE);
        
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(0,0,0,10);
        
        panel.add(dropDownTitle, c);
        
        
        //Sets the accounts drop down menu with the accounts stored in model.
        JComboBox accountsDropDown = new JComboBox(((AccountModel)model)
                .getIdWithName());
        
        accountsDropDown.addActionListener(handler);
        accountsDropDown.setActionCommand(Constants.DROP_DOWN);
        
        
        
        
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.LINE_START;
        
        panel.add(accountsDropDown, c);
        
        JPanel editPanel = new JPanel(new GridBagLayout());
        
        JButton editInUSD = new JButton(Constants.EDIT_IN_USD);
        editInUSD.addActionListener(handler);
        
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
    
        
        editPanel.add(editInUSD, c);
        
        JButton editInYEN = new JButton(Constants.EDIT_IN_YEN);
        editInYEN.addActionListener(handler);
        
        
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
     
        
        editPanel.add(editInYEN, c);
        
        JButton editInEURO = new JButton(Constants.EDIT_IN_EURO);
        editInEURO.addActionListener(handler);
        
        c = new GridBagConstraints();
        c.gridx = 2;
        c.gridy = 0;
        c.gridwidth = 1;
       
        
        editPanel.add(editInEURO, c);
        
        // For the edit panel
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
       
        
        panel.add(editPanel, c);
        
        JButton createDepositAgent =new JButton(Constants.CREATE_DEPOSIT_AGENT);
        createDepositAgent.addActionListener(handler);
        
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;

        
        
        panel.add(createDepositAgent, c);
        
        JButton createWithdrawalAgent =
                new JButton(Constants.CREATE_WITHDRAW_AGENT);
        createWithdrawalAgent.addActionListener(handler);
       
        
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 1;
       
        
        panel.add(createWithdrawalAgent, c);
        
        JButton saveButton = new JButton(Constants.SAVE);
        saveButton.addActionListener(handler);
        
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(15,0,0,0);
        
        panel.add(saveButton,c);
        
        
        JButton exitButton = new JButton(Constants.EXIT);
        exitButton.addActionListener(handler);
        
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(15,0,0,0);
        
        panel.add(exitButton,c);
        
        this.getContentPane().setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.insets = new Insets(15,15,15,15);
        this.getContentPane().add(panel,c);
        this.setResizable(false); // Can't resize window
        
       
        
        
        
        // Sizes the frame so that all content is at or above their preferred
        // sizes
        pack(); 
        
    }

    /**
     * Receives updates from model when the model changes state.
     * For the Home View, no updates are necessary.
     * @param event - the model changed event
     */
    @Override
    public void modelChanged(ModelEvent event) {

    }
    
    /**
     * Delegates user gestures to the controller for processing.
     */
    class Handler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            ((AccountController)getController()).operation(e);
        }
        
    }
    
    
    
    
    
}
