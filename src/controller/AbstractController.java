/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.HashMap;
import model.Model;
import view.View;

/**
 * The root of the Controller class hierarchy is the AbstractController class.
 * This class defines all the basic facilities required to implement a
 * controller. That is, it allows a view and model to be linked to the
 * controller. This is in addition to get and set methods for views and models.
 * @author Ben
 */
public abstract class AbstractController implements Controller {

    private Model model;
    private View view;
    private HashMap<Integer,View> editViews;
    
    /**
     * Gets the model
     * @return - the model
     */
    @Override
    public Model getModel() {
        return model;
    }

    /**
     * Gets the view
     * @return - the view
     */
    @Override
    public View getView() {
       return view;
    }

    /**
     * Sets the Model
     * @param model - the model to be set
     */
    @Override
    public void setModel(Model model) {
        this.model = model;
    }

    /**
     * Sets the view
     * @param view - the view to be set
     */
    @Override
    public void setView(View view) {
        this.view = view;
    }
    
    /**
     * Either instantiates an empty list of EditViews and returns a reference
     * to the that one or one that's in use.
     * @return returns a reference to the empty one or one that's in use.
     */
    HashMap<Integer,View> getEditViews(){
       if(editViews == null){
           editViews = new HashMap<>(10);
       }
       return editViews;
    }
    
}
