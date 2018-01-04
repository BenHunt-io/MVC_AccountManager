/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.listeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 * NumberListener created for views that need only decimals and digits.
 * It only will accept input of that type.
 * @author Ben
 */
public class NumberListener extends KeyAdapter
    {

        private JTextField field;
        
        /**
         * Constructs a basic number listener corresponding to a component
         * @param component - the component used for input
         */
        public NumberListener(JComponent component){
            this.field = (JTextField) component;
        }
        
        /**
         * Only allows input that is a decimal or digit.
         * @param e - the key pressed
         */
        @Override
        public void keyTyped(KeyEvent e) {
           if((e.getKeyChar() >= 48 && e.getKeyChar() <= 58) 
                   || (e.getKeyChar() == 46 && !field.getText().contains("."))){
               return;
           }
           else
               e.consume();
        }
        
    }