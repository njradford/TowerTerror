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
public class ReinforceWallCard extends EffectCard implements Serializable {
    ReinforceWallCard(String name) {
        super(name); 
    }
    
    public int takeEffect(BoardEffectInterface i) {
        i.reinforceWall();
        return 0; 
    }
}
