/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package castlepanic;

import java.io.Serializable;

/**
 *
 * @author James Morrow <jmorrow1@unca.edu>
 */
public class BarbarianCard extends EffectCard implements Serializable {
    BarbarianCard(String name) {
        super(name); 
    }
    
    @Override 
    public int takeEffect(BoardEffectInterface i) {
        return i.barbarian(); 
    }
}
