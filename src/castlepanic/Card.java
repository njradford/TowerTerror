package castlepanic;

/**
 * The card class. This is a common superclass designed to be used by subclasses
 * of cards. It contains only a card name.
 *
 * @author Adam Whitley
 * @author Gregory Loftis
 * @author Dipesh Dave
 * @author John Fenwick
 */
public class Card {

    protected String name;



    /**
     * A no-argument constructor for a card. Creates a null object.
     *
     */
    public Card() {
    }

    /**
     * A one-argument constructor for a card. Creates a card with the given
     * name. Will use super invocations from the subclasses of the card
     * superclass.
     *
     * @param s The name of this card.
     */
    public Card(String s) {
        name = s;

    }

    /**
     * Gets the name of this card.
     *
     * @return The name of this card in a string.
     */
    public String getName() {
        return "Card: " + name;
    }
 

}
