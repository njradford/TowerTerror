package com.csci499;

/**
 * The basic monster class. Basic monsters are goblins, orcs, and trolls. They
 * are spawned in a random horizontal location in the forest ring and move one
 * ring toward the castle on their turn.
 *
 * @author Adam Whitley
 * @author Gregory Loftis
 * @author Dipesh Dave
 * @author John Fenwick
 */
public class BasicMonster extends Monster {

    /**
     * A two-argument constructor for a basic monster. Creates a basic monster
     * with the specified name and HP.
     *
     * @param name The name of the monster (goblin, orc, or troll).
     * @param defaultHP The starting HP of the monster.
     * @param serial The unique numerical identifier for the monster
     */
    public BasicMonster(String name, int defaultHP, int serial) {
        super(name, defaultHP, serial);
    }

    /**
     * A no-argument constructor for a basic monster. Creates a null token.
     *
     */
    public BasicMonster() {
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
        hitPoints = Math.max(0, hitPoints-damage);
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
     * The altitude location that the monster will move to on the next turn.
     * Basic monsters stay on the ground
     *
     * @return The next altitude location of the monster.
     */
     @Override
    public int getNextAltitudeLocation() {
         return altitudeLocation; 
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
