/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Objects interested in receiving updates from the Model should implement
 * this interface.
 * @author Ben
 */
public interface ModelListener {
    /**
     * Used to receive model updates
     * @param event - the model event (State change).
     */
    public void modelChanged(ModelEvent event);
}
