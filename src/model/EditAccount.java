/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import assignment2.Constants;

/**
 * An EditAccount account is constructed to store values corresponding to an EditView
 * with the given WindowId.
 * @author The Dough Boys
 */
public class EditAccount extends UserAccount{
    
    private Integer windowId;
    private String currency;
    private double conversionRate;

    
    /**
     * This account is constructed to store values corresponding to an EditView
     * with the given WindowId.
     * @param windowId - windowId of the open EditView account
     * @param accountId - accountId of the associated with the EditView account
     * @param accountName - accountId of the associated with the EditView account
     * @param balance - balance of the account associated  with the open EditView window
     * @param currency - currency of the open EditView window associated with
     * the given windowId.
     */
    public EditAccount(Integer windowId, int accountId, String accountName, 
                       double balance,String currency) {
        
        super(accountId, accountName, balance);
        this.windowId = windowId;
        this.currency = currency;
        
        switch(currency){
            case Constants.EDIT_IN_USD:
                conversionRate = 1;
                break;
            case Constants.EDIT_IN_EURO:
                conversionRate = Constants.EURO;
                break;
            case Constants.EDIT_IN_YEN:
                conversionRate = Constants.YEN;
                break;
            
        }
    }
    
    /**
     * Gets the windowId of the open EditView window
     * @return 
     */
    public int windowId(){
        return windowId;
    }
    
    /**
     * Gets the currency which is just the string name of it used in the Constants
     * file.
     * @return - the currency which is just the string name of it used in the Constants
     * file.
     */
    public String getCurrency(){
        return currency;
    }
    
    /** 
     * Gets the conversionRate associated with the open EditView.
     * @return - the conversionRate associated with the open EditView.
     */
    public double getConversionRate(){
        return conversionRate;
    }
}
