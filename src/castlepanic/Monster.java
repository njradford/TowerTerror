package castlepanic;

/**
 * The common monster superclass (abstract). Contains the data for a monster's
 * HP and location. Methods for determining movement, birth effects, and death
 * effects are overridden by the monster subclasses.
 *
 * @author Adam Whitley
 * @author Gregory Loftis
 * @author Dipesh Dave
 * @author John Fenwick
 */
public abstract class Monster extends Token {

    protected int hitPoints;
    protected int pointValue; //this is how many points awarded to the player who kills this monster.  Subclass constructors can modify this value.
    protected int verticalLocation; //0 - Castle ring, 1 - Swordsman, 2 - Knight, 3 - Archer, 4 - Forest
    protected int horizontalLocation; // 1 through 6 going clockwise. 1-2 = red, 3-4 = green, 5-6 = blue.
    protected int altitudeLocation; // 0 - ground, 1 - sky
    protected int serial;

    /**
     * A no-argument constructor for a monster. Creates a null token.
     *
     */
    public Monster() {
        super();
        pointValue = 1; //default point value
        serial = -1;
    }

    /**
     * A two-argument constructor for a monster. Creates a monster with the
     * given name and default HP. Subclasses of monster use the super invocation
     * to get this information. When a monster spawns, it is placed in a random
     * horizontal section in the forest ring.
     *
     * @param name The name of the monster.
     * @param defaultHP The starting HP of the monster.
     * @serial the order in which the monster enters play, its unique identifier
     */
    public Monster(String name, int defaultHP, int serial) {
        super(name);
        hitPoints = defaultHP;
        pointValue = 1; //default point value
        this.serial = serial;
    }

    /**
     * Any effects that happen when the monster comes into play. Overridden by
     * the subclass of monster.
     *
     */
    public abstract void birthEffects(BoardEffectInterface e);

    /**
     * Any effects that happen when the monster dies. Overridden by the subclass
     * of monster.
     *
     */
    public abstract void deathEffects(BoardEffectInterface e);

    /**
     * The movement of a monster on its turn. Overridden by the subclass of
     * monster.
     */
    public abstract void movement(BoardEffectInterface e);

    /**
     * A monster being hit by a hit card. Overridden by the subclass of Monster.
     *
     * @param damage The amount of damage being dealt to this monster.
     */
    public abstract void takeHit(int damage);

    /**
     * Gets the current HP of this monster.
     *
     * @return The current HP of this monster.
     */
    public int getHP() {
        return hitPoints;
    }

    /**
     * Gets the name of this monster.
     *
     * @return The name of this monster.
     */
    public String getName() {
        return super.getTokenName();
    }

    /**
     * Gets the current vertical location of this monster. 4 = forest ring, 3 =
     * archer ring, 2 = knight ring, 1 = swordsman ring, 0 = castle ring.
     *
     * @return The current vertical location of this monster.
     */
    public int getVerticalLocation() {
        return verticalLocation;
    }

    /**
     * Gets the current horizontal location of this monster. 1-2 = red wedges,
     * 3-4 = green wedges, 5-6 = blue wedges.
     *
     * @return The current horizontal wedge the monster is in.
     */
    public int getHorizontalLocation() {
        return horizontalLocation;
    }
    
     /**
     * Gets the current altitude location of this monster. 0 is ground, 1 is sky.
     *
     * @return The current altitude the monster is in.
     */
    public int getAltitudeLocation() {
        return altitudeLocation;
    }
    

    /**
     * Gets the name, HP, and location of this monster.
     *
     * @return a string containing the name, HP, and horizontal/vertical/altitude
     * location of this monster.
     */
    public String getMonsterStatus() {
        return ("Monster Name: " + super.getTokenName() + ", HP: " + this.getHP() + ", Horizontal Location: " + 
                this.getHorizontalLocation() + ", Vertical Location: " + this.getVerticalLocation()
                + ", Altitudnal Location: " + this.getAltitudeLocation());
    }

    /**
     * Gets the next horizontal location this monster will move to. Overridden
     * by the monster subclass.
     *
     * @return The horizontal location of this monster after its next movement.
     */
    public abstract int getNextHorizontalLocation();

    /**
     * Gets the next vertical location this monster will move to. Overridden by
     * the monster subclass.
     *
     * @return The vertical location of this monster after its next movement.
     */
    public abstract int getNextVerticalLocation();

        /**
     * Gets the next altitudinal location this monster will move to. Overridden by
     * the monster subclass.
     *
     * @return The altitude location of this monster after its next movement.
     */
    public abstract int getNextAltitudeLocation();
    
    /**
     * Gets the monster's unique identifier. The first monster to enter play
     * receives serial 0, and the last receives TokenPile.PIZE_SIZE
     *
     * @return The monster's serial, an int.
     */
    public int getSerial() {
        return serial;
    }
    
    /**
     * Places the monster on the field at a random horizontal wedge between 1
     * and 6, vertical location 4.
     *
     * @return The horizontal wedge to place the monster in, used by the UI for
     * animation.
     */
    
    public int placeMonster() {
        int initialHorizontalPosition = ((int) ((Math.random() * 6) + 1));
        horizontalLocation = initialHorizontalPosition;
        verticalLocation = 4;
        return initialHorizontalPosition;
    }

    /**
     * Only used during the construction of a new gameState. Should be using
     * PlaceMonster for regular placement.
     *
     * @param wedge The initial wedge to place this monster in.
     */
    public void beginningGamePlacement(int wedge) {
        horizontalLocation = wedge;
        verticalLocation = 4;
    }

    //document
    public int getPointValue() {
        return pointValue;
    }
    /**
     * add doc
     * @param newValue 
     */
    public void setVerticalLocation(int newValue) {
        this.verticalLocation = newValue; 
    }
}
