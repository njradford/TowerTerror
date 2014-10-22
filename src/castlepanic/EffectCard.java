/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package castlepanic;

import java.io.Serializable;

/**
 *
 * @author bmcdanie
 */
public abstract class EffectCard  extends Card implements Serializable {
    
    public EffectCard(String name) {
        super(name);
    }
    
    public abstract int takeEffect(BoardEffectInterface i);
    
}
