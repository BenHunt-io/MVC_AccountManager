/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import javax.swing.JFrame;
import model.Model;
import model.ModelEvent;
import model.ModelListener;

/**
 * All views extend this JFrameView. It serves as a base JFrame to extend from
 * and provides the MVC functionality, implementing ModelListener and View. This
 * way it can receive updates from the model.
 * @author Ben
 */
abstract public class JFrameView extends JFrame implements ModelListener, View{
    
    private Model model;
    private Controller controller;

    /**
     * Constructs basic JFrame View, this is called from the superclass.
     * @param model - model of the application. (AccountModel)
     * @param controller - controller of the application. (AccountController)
     */
    public JFrameView(Model model, Controller controller){
        setModel(model);
        setController(controller);
    }
    
    /**
     * Gets the controller.
     * @return - the controller
     */
    @Override
    public Controller getController() {
        return controller;
    }

    /**
     * Gets the model
     * @return - the model
     */
    @Override
    public Model getModel() {
        return model;
    }

    /**
     * Sets the model (in constructor).
     */
    @Override
    public void setModel(Model model) {
        this.model = model;
    }

    
    /**
     * Sets the controller (in constructor).
     */
    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }
    
}
