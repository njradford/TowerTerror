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
public interface BoardEffectInterface {
    /**
     * Needs javadoc
     * @return 
     */
    public int noNewMonsters();
    public int reinforceWall(); 
    public int barbarian(); 
    public int timeStop(); 
    public int timeSlap(); 
    public int rewind(); 
    public int turret(); 
    
}
