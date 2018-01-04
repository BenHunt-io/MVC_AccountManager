  /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Supplies the one function used for updating the views. All Models should 
 * implement this.
 * @author Ben
 */
public interface Model {
    
    /**
     * Notify all registered listeners of an update occurring in the model.
     * @param event 
     */
    public void notifyChanged(ModelEvent event);
}
