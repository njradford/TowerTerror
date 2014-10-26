/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package castlepanic;

/**
 *
 * @author bmcdanie
 */
public abstract class EffectCard extends Card {
    
    public EffectCard(String name) {
        super(name);
    }
    
    public abstract int takeEffect(BoardEffectInterface i);
    
}
