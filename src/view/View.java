/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import model.Model;

/**
 * Core functions as described in the MVC architecture.
 * @author Ben
 */
public interface View {
    
    public Controller getController();
    public Model getModel();
    public void setModel(Model model);
    public void setController(Controller controller);
    
    
}
