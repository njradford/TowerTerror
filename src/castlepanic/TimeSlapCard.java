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
public class TimeSlapCard extends EffectCard {
    TimeSlapCard() {
        super("time slap"); 
    }
    
    public int takeEffect(BoardEffectInterface e) {
        return e.timeSlap();    
    }
}
