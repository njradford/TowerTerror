package castlepanic;

/**
 * The Player class for handling each player's cards and points. Keeps track of
 * the cards in a player's hands with an array of cards and counts the player's
 * points. Also contains the logic for card trades and discards.
 *
 * @author Adam Whitley
 * @author Gregory Loftis
 * @author Dipesh Dave
 * @author John Fenwick
 */
public class Player {
    private String archetype; 
    private String name;
    private Card[] cardsInHand;
    private int handSize;
    private int points;
    private int topOfHand; //Top of hand is an index number into cards in hand. Cards in hand starts at element 0, goes to handSize-1

    /**
     * Constructs a new player and fills their hand with cards from the deck.
     *
     * @param sizeOfHand The size of the player's hand for this game.
     * @param gameDeck The deck used for this game.
     */
    public Player(int sizeOfHand, Deck gameDeck, String name, String archetype) {
        cardsInHand = new Card[sizeOfHand];
        handSize = sizeOfHand;
        topOfHand = sizeOfHand - 1;
        this.name = name;
        this.archetype = archetype; 
        for (int i = 0; i < sizeOfHand; i++) {
            cardsInHand[i] = gameDeck.dealCard();
        }
        points = 0;
    }

    /**
     * Adds a point to the player's total.
     *
     */
    public void awardPoints(int points) {
        this.points+= points;
    }
    
    /**
     * 
     * @return The name of the player's archetype
     */
    public String getArchetype() {
        return archetype; 
    }
    
    /**
     * 
     * @param archetype The new name for the player's archetype
     */
    public void setArchetype(String archetype) {
        this.archetype = archetype; 
    }

    /**
     * Gets the name of this player.
     *
     * @return The name of this player.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this player.
     *
     * @param name The desired name for the player.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the player's current points.
     *
     * @return The player's current points.
     */
    public int getScore() {
        return points;
    }

    /**
     * Returns the player's hand size.
     *
     * @return The player's hand size.
     */
    public int getHandSize() {
        return handSize;
    }

    /**
     * Gets the integer representing the card at the top of the player's hand.
     * Use as upper bound for the cardsInHand array.
     *
     * @return The integer representing the card at the top of the player's
     * hand.
     */
    public int getTopOfHand() {
        return topOfHand;
    }

    /**
     * Gets the card at the specified location in the player's hand.
     *
     * @param i The index of the card to retrieve.
     * @return The desired card in the player's hand.
     */
    public Card getCardAt(int i) {
        return cardsInHand[i];
    }

    /**
     * Gets a String detailing the card at the desired index.
     *
     * @param i The index of the player's hand to check.
     * @return A String containing information about the card at the argued
     * index.
     */
    public String printCardAt(int i) {
        return cardsInHand[i].getName();
    }

    /**
     * Gets a String array of all the cards in the player's hand.
     *
     * @return A String array of all the cards in the player's hand.
     */
    public String[] printHand() {
        String[] listOfCards = new String[handSize];
        for (int i = 0; i < handSize; i++) {
            listOfCards[i] = this.printCardAt(i);
        }
        return listOfCards;
    }

    /**
     * Removes the card at the desired location of the player's hand. Used for
     * playing a card and discarding a card.
     *
     * @param i The index of the card to remove.
     * @return The card to be removed.
     */
    public Card removeCardAt(int i) {
        //Swap element i with element topOfHand
        Card temp = cardsInHand[i];
        cardsInHand[i] = cardsInHand[topOfHand];
        cardsInHand[topOfHand] = temp;
        //Get rid of element at topOfHand
        temp = cardsInHand[topOfHand];
        topOfHand--;
        return temp;
    }

    /**
     * Swaps a card with another player.
     *
     * @param targetPlayer The target player to swap cards with.
     * @param thisIndex The index of the card in this player's hand to be
     * traded.
     * @param targetIndex The index of the card in the target player's hand to
     * be traded.
     */
    public void swapCard(Player targetPlayer, int thisIndex, int targetIndex) {
        Card temp = this.cardsInHand[thisIndex];
        this.cardsInHand[thisIndex] = targetPlayer.cardsInHand[targetIndex];
        targetPlayer.cardsInHand[targetIndex] = temp;
    }

    /**
     * Draws the player's hand back to full.
     *
     * @param gameDeck The deck to draw from.
     */
    public void drawToFull(Deck gameDeck) {
        for (int i = topOfHand + 1; i < handSize; i++) {
            cardsInHand[i] = gameDeck.dealCard();
        }
        topOfHand = handSize - 1;
    }
    /**
     * receiveCard method: Adds a card to the player's hand.
     * 
     * @param c: The card to be added to player's hand.
     * @return  An integer, -1 if invoked incorrectly (player's hand is already full),
     * or 0 if invoked correctly.
     */
    public int receiveCard (Card c) {
        if (topOfHand == handSize -1) { //if the hand is already full, print error.
            System.err.println("Attempting to put card into full hand");
            return -1;
        }
        cardsInHand[topOfHand+1] = c;
        topOfHand++;
        return 0;
    }
}
