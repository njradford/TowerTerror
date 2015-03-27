/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csci499;

/**
 *
 * @author James Morrow <jmorrow1@unca.edu>
 */
public class MissingCard extends EffectCard {
    
     MissingCard() {
         super("missing"); 
     }
    
     @Override
     public int takeEffect(BoardEffectInterface i) {
         i.noNewMonsters();
         return 0; 
     }
}
