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
public interface BoardEffectInterface extends Serializable {
    /**
     * Needs javadoc
     * @return 
     */
    public int noNewMonsters();
    public int reinforceWall(); 
    public int barbarian(); 
    
}
