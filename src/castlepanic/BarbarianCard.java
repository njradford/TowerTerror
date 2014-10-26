/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package castlepanic;

/**
 *
 * @author James Morrow <jmorrow1@unca.edu>
 */
public class BarbarianCard extends EffectCard {
    BarbarianCard() {
        super("barbarian"); 
    }
    
    @Override 
    public int takeEffect(BoardEffectInterface i) {
        return i.barbarian(); 
    }
}
