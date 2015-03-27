/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csci499;
/**
 *
 * @author James Morrow <jmorrow1@unca.edu>
 */
public class ReinforceWallCard extends EffectCard {
    ReinforceWallCard() {
        super("reinforce wall"); 
    }
    
    public int takeEffect(BoardEffectInterface i) {
        i.reinforceWall();
        return 0; 
    }
}
