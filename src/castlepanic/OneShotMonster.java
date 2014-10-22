/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package castlepanic;

import java.io.Serializable;

/**
 *
 * @author jfenwic1
 */
public class OneShotMonster extends Monster implements Serializable {
    private int oneShotDistance; //this is the vertical location from any damage from a hit card
    private int immuneDistance; // this is the spot in the ring that is immune depending on the special monster that is being hit
    
      /**
     * A two-argument constructor for a basic monster. Creates a basic monster
     * with the specified name and HP.
     *
     * @param name The name of the monster (goblin, orc, or troll).
     * @param defaultHP The starting HP of the monster.
     * @param serial The unique numerical identifier for the monster
     */
    public OneShotMonster(String name, int defaultHP, int serial, int oneShotDistance, int immuneDistance) {
        super(name, defaultHP, serial);
        this.oneShotDistance = oneShotDistance;
        this.immuneDistance = immuneDistance;
    }

    /**
     * A no-argument constructor for a basic monster. Creates a null token.
     *
     */
    public OneShotMonster() {
        super();
    }

    /**
     * The movement method for a basic monster. Basic monsters start in the
     * forest ring (vertical location 4) and move one toward the castle every
     * turn. Upon reaching the castle ring, they will damage walls and move in a
     * clockwise pattern every subsequent round.
     *
     */
    @Override
    public void movement(BoardEffectInterface e) {
        if (verticalLocation == 0) {
            //Move clockwise one wedge, wraps around so that it goes back to 1 instead of 7
            horizontalLocation = horizontalLocation % 6 + 1;
        } else {
            verticalLocation--;
        }
    }
    /**
     * The takeHit method for a basic monster.  When a basic monster is dealt
     * damage, the amount is subtracted from its hitPoints, to a minimum of 0
     * hitPoints, at which time the monster is dead.
     * @param damage subtracted from hitPoints, minimum of 0 remaining.
     */
    @Override
    public void takeHit(int damage) {
        if(verticalLocation == immuneDistance){
            return;
        }
        else if(verticalLocation == oneShotDistance){
            hitPoints = 0;
        }
        else { //We are not on the immune ring or the one shit ring, so the monster still takes 1 hitpoint of damage.
            hitPoints = Math.max(0, hitPoints-damage);
        }    
    }
    

    /**
     * The horizontal location that the monster will move to on the next turn.
     * Will only change if the monster is in the castle ring (vertical location
     * 0).
     *
     * @return The next horizontal location of the monster.
     */
    @Override
    public int getNextHorizontalLocation() {
        if (verticalLocation == 0) {
            //Move clockwise one wedge, wraps around so that it goes back to 1 instead of 7
            return horizontalLocation % 6 + 1;
        } else {
            return horizontalLocation;
        }
    }

    /**
     * The vertical location that the monster will move to on the next turn.
     * Basic monsters move one ring toward the castle every turn.
     *
     * @return The next vertical location of the monster.
     */
    @Override
    public int getNextVerticalLocation() {
        if (verticalLocation == 0) {
            return 0;
        } else {
            return (verticalLocation - 1);
        }
    }

    /**
     * The effects that happen when this monster enters the field. Basic
     * monsters do not have any effects upon entering the field.
     */
    @Override
    public void birthEffects(BoardEffectInterface e) {
        //Basic monsters have no birth effects (Leave Blank)  
    }

    /**
     * The effects that happen when this monster dies. Basic monsters do not
     * have any effects upon death.
     *
     */
    @Override
    public void deathEffects(BoardEffectInterface e) {
        //Basic monsters have no death effects (Leave Blank)
    }
    
}
