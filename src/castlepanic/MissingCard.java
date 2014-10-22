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
public class MissingCard extends EffectCard implements Serializable {
    
     MissingCard(String name) {
         super(name); 
     }
    
     @Override
     public int takeEffect(BoardEffectInterface i) {
         i.noNewMonsters();
         return 0; 
     }
}
