package castlepanic;

import java.io.Serializable;

/**
 * The hit card class that contains hit card types and logic. Basic hit cards
 * are archer, knight, swordsman, and hero. Each type of card can have one of
 * four colors. Colors are represented by an integer: 0 = red, 1 = green, 2 =
 * blue, 3 = any. Card types are checked by comparing strings: "archer",
 * "knight", "swordsman", "hero."
 *
 * @author Adam Whitley
 * @author Gregory Loftis
 * @author Dipesh Dave
 * @author John Fenwick
 */
public class HitCard extends Card implements Serializable {
    protected boolean[][] hitLocations; //First dimension is for horizontal location, second dimension is vertical location
    protected int color; //0 = red, 1 = green, 2 = blue, 3 = any
    protected int damage; //Amount of damage a card does

    /**
     * Constructs a new HitCard -- hit location logic is coded here. To create a
     * new type of HitCard, modify this if-else.
     *
     * @param name Name of the card (string) -- "archer", "knight", "swordsman",
     * "hero".
     * @param color Color of the card (0 = red, 1 = green, 2 = blue, 3 = any)
     */
    public HitCard(String name, int color) {
        super(name);
        this.color = color;
        hitLocations = new boolean[7][5];
        damage = 1;

        //2D Array: first dimension is the horizontal wedge. Element 1 and 2 = red, Element 3 and 4 = green, Element 5 and 6 = blue.
        //2D Array: second dimension is the vertical location. 4 = Forest ring, 3 = Archer ring, 2 = Knight ring, 1 = Swordsman ring, 0 = Castle ring.
        if (color == 0) { //red
            if (name == "archer") {
                hitLocations[1][3] = true;
                hitLocations[2][3] = true;
            } else if (name == "knight") {
                hitLocations[1][2] = true;
                hitLocations[2][2] = true;
            } else if (name == "swordsman") {
                hitLocations[1][1] = true;
                hitLocations[2][1] = true;
            } else if (name == "hero") {
                hitLocations[1][1] = true;
                hitLocations[1][2] = true;
                hitLocations[1][3] = true;
                hitLocations[2][1] = true;
                hitLocations[2][2] = true;
                hitLocations[2][3] = true;
            } else {
                System.err.println("Card + " + name + "does not exist");
            }
        } else if (color == 1) { //green
            if (name == "archer") {
                hitLocations[3][3] = true;
                hitLocations[4][3] = true;
            } else if (name == "knight") {
                hitLocations[3][2] = true;
                hitLocations[4][2] = true;
            } else if (name == "swordsman") {
                hitLocations[3][1] = true;
                hitLocations[4][1] = true;
            } else if (name == "hero") {
                hitLocations[3][1] = true;
                hitLocations[3][2] = true;
                hitLocations[3][3] = true;
                hitLocations[4][1] = true;
                hitLocations[4][2] = true;
                hitLocations[4][3] = true;
            } else {
                System.err.println("Card + " + name + "does not exist");
            }
        } else if (color == 2) { //blue
            if (name == "archer") {
                hitLocations[5][3] = true;
                hitLocations[6][3] = true;
            } else if (name == "knight") {
                hitLocations[5][2] = true;
                hitLocations[6][2] = true;
            } else if (name == "swordsman") {
                hitLocations[5][1] = true;
                hitLocations[6][1] = true;
            } else if (name == "hero") {
                hitLocations[5][1] = true;
                hitLocations[5][2] = true;
                hitLocations[5][3] = true;
                hitLocations[6][1] = true;
                hitLocations[6][2] = true;
                hitLocations[6][3] = true;
            } else {
                System.err.println("Card + " + name + "does not exist");
            }
        } else if (color == 3) { //any
            if (name == "archer") {
                for (int i = 1; i < 7; i++) {
                    hitLocations[i][3] = true;
                }
            } else if (name == "knight") {
                for (int i = 1; i < 7; i++) {
                    hitLocations[i][2] = true;
                }
            } else if (name == "swordsman") {
                for (int i = 1; i < 7; i++) {
                    hitLocations[i][1] = true;
                }
            } else if (name == "hero") {
                for (int j = 1; j < 4; j++) {
                    for (int i = 1; i < 7; i++) {
                        hitLocations[i][j] = true;
                    }
                }
            } else {
                System.err.println("Card + " + name + "does not exist");
            }
        } else {
            System.err.println("Invalid color value: " + color);
        }
    }

    /**
     * Gets the name and color of this hit card. Transforms the integer
     * representation of the color into a string.
     *
     * @return The name and color of this hit card.
     */
    @Override
    public String getName() {
        //Turns the color int into a word
        String colorWord = "";
        if (color == 0) {
            colorWord = "Red";
        } else if (color == 1) {
            colorWord = "Green";
        } else if (color == 2) {
            colorWord = "Blue";
        } else if (color == 3) {
            colorWord = "Any";
        }
        return ("Hit Card: " + name + " Color: " + colorWord);
    }
     /**
     * Gets the range of this card.
     *
     * @return boolean[][], true inside card's attack/effect range.
     */
    public boolean[][] getHitLocations() {
        return hitLocations;
    }
     /**
     * Gets the damage of this card.
     *
     * @return integer amounting to damage card causes when played.
     */
    public int getDamage() {
        return damage;
    }
        
    /**
     * Gets the color of this card
     * 
     * @return integer equivalent to the card color.
     */
    public int getColor(){
        return color;
    }

}
