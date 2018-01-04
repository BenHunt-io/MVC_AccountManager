/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Model;
import view.View;

/**
 *
 * The Controller interface is the interface which must be implemented by
 * all classes which wish to take the role of a Controller.
 * All controllers must be able to reference a model and a view object.
 * <p>
 * The primary role of a Controller within the MVC is to determine what
 * should happen in response to user input.
 * @author Ben
 */
public interface Controller {
    
    /**
     * Gets the model
     * @return - the model
     */
    public Model getModel();
    
    /**
     * Gets the view
     * @return - the view
     */
    public View getView();
    
    /**
     * Sets the Model
     * @param model - the model to be set
     */
    public void setModel(Model model);
    
    /**
     * Sets the view
     * @param view - the view to be set
     */
    public void setView(View view);
    
}
