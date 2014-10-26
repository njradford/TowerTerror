/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castlepanic;

/**
 *
 * @author James Morrow <jmorrow1@unca.edu>
 */
public class TurretCard extends EffectCard {
    TurretCard() {
        super("turret"); 
    }
    
    public int takeEffect(BoardEffectInterface i) {
        return i.turret(); 
    }
}
