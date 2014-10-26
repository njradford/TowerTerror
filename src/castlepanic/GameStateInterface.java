package castlepanic;

/**
 * The Game State Interface contains all of the methods necessary for the User
 * Interface to Castle Panic
 *
 * @author Nick
 */
public interface GameStateInterface {

    /**
     * Game State
     */
    /**
     * Returns the number of trades allowed in this game given the number of
     * players
     *
     * @return
     */
    public int getNumTradesAllowed();

    /**
     * Returns the number of trades remaining in this turn
     *
     * @return
     */
    public int getNumTradesRemaining();

    /**
     * Returns the number of discards remaining in this game given the number of
     * players
     *
     * @return
     */
    public int getNumDiscardsAllowed();

    /**
     * Returns the number of discards remaining in this turn
     *
     * @return
     */
    public int getNumDiscardsRemaining();

    /**
     * Returns the max hand size given the number of players
     *
     * @return
     */
    public int getMaxHandSize();

    /**
     * Returns the index of the current active player
     *
     * @return
     */
    public int getCurrentPlayer();

    /**
     * Returns the current phase number
     *
     * @return
     */
    public int getCurrentPhase();

    /**
     * Returns a boolean of whether the players have lost
     *
     * @return
     */
    public boolean getDeadYet();

    /**
     * Players
     */
    /**
     * Returns the amount of players active in the game
     *
     * @return
     */
    public int getPlayers();

    /**
     * Returns number of cards in players hand
     *
     * @param playerIndex Desired position in the player array to query
     * @return
     */
    public int getNumCards(int playerIndex);

    /**
     * Return name of card for a given player and card index
     *
     * @param playerIndex Desired position in the player array to query
     * @param cardIndex Desired position in the player's hand array to query
     * @return
     */
    public String getCardNameFromPlayerHand(int playerIndex, int cardIndex);

    /**
     * Return damage value of a given player and card index
     *
     * @param playerIndex Desired position in the player array to query
     * @param cardIndex Desired position in the player's hand array to query
     * @return
     */
    public int getCardDamageFromPlayerHand(int playerIndex, int cardIndex);

    /**
     * Get card color
     *
     * @param playerIndex Desired position in the player array to query
     * @param cardIndex Desired position in the player's hand array to query
     * @return
     */
    public int getCardColorFromPlayerHand(int playerIndex, int cardIndex);

    /**
     * Return the index of a players top hand card given a player index
     *
     * @param playerIndex Desired position in the player array to query
     * @return
     */
    public int getTopOfHand(int playerIndex);

    /**
     * Returns score of a player at a given index
     *
     * @param playerIndex Desired position in the player array to query
     * @return
     */
    public int getScore(int playerIndex);

    /**
     * Returns a string of the players hand at a given index
     *
     * @param playerIndex Desired position in the player array to query
     * @return
     */
    public String[] getHand(int playerIndex);

    /**
     * Returns the hand size of a player at a given index
     *
     * @param playerIndex Desired position in the player array to query
     * @return
     */
    public int getHandSize(int playerIndex);

    /**
     * Returns the string of a players name at a given index
     *
     * @param playerIndex Desired position in the player array to query
     * @return
     */
    public String getPlayerName(int playerIndex);

    /**
     * Returns a boolean of whether a card at a given index in a players hand is
     * a hit card or not
     *
     * @param playerIndex Desired position in the player array to query
     * @param cardIndex Desired position in the player's hand array to query
     * @return
     */
    public boolean isHitCard(int playerIndex, int cardIndex);

    /**
     * Towers
     */
    /**
     * Returns int that represents the tower state
     *
     * @param pos position in the tower array [0-5] that you'd like to query.
     * @return
     */
    public int getTowerStatus(int pos);

    /**
     * Walls
     */
    /**
     * Return HP of a given wall position
     *
     * @param pos position of the wall array [0-5] that you'd like to query.
     * @return
     */
    public int getWallHP(int pos);

    /**
     * Monsters
     */
    /**
     * Return the number of monsters on the field
     *
     * @return
     */
    public int getNumMonstersInPlay();

    /**
     * Given a serial number, return the index of the monster with that serial
     * in the GameState's monstersInField array.
     *
     * @param serial Monster's serial identifier assigned on construction.
     * @return index in array
     */
    public int getMonsterIndex(int serial);
/**
 * Returns an integer array containing the serial number of every monster in play
 */
    public int[] getMonsterSerialsInPlay();
    /**
     * Return a monsters hit points
     *
     * @param index position of the monster array that you'd like to query.
     * @return      
    *
     */
    public int getMonsterHP(int index);

    /**
     * Return a monsters X value
     *
     * @param index position of the monster array that you'd like to query.
     * @return
     */
    public int getMonsterX(int index);

    /**
     * Return a monsters Y value
     *
     * @param index position of the monster array that you'd like to query.
     * @return
     */
    public int getMonsterY(int index);

    /**
     * Return a monsters next X value
     *
     * @param index position of the monster array that you'd like to query.
     * @return
     */
    public int getMonsterXnext(int index);

    /**
     * Return a monsters next Y value
     *
     * @param index position of the monster array that you'd like to query.
     * @return
     */
    public int getMonsterYnext(int index);

    /**
     * Return the number of Unplayed monsters
     *
     * @return
     */
    public int getUnplayedMonsters();

    /**
     * Return the number of monsters that have been killed
     *
     * @return
     */
    public int getMonstersKilled();

    /**
     * Deck
     */
    /**
     * Returns the index of the top deck pile
     *
     * @return
     */

    public int getDeckSize();

    /**
     * Returns the size of the discard pile
     *
     * @return
     */
    public int getDiscardSize();

    /**
     * Returns the number of unplayed cards in the deck
     *
     * @return
     */
    public int getNumOfUnplayedCards();

    /**
     * Returns a string of a cards name at a given position in the deck
     *
     * @param index position of the deck array that you'd like to query.
     * @return
     */
    public String getCardNameFromDeck(int index);

    /**
     * Returns a string of the color of a card from a given position in the deck
     *
     * @param index position of the deck array that you'd like to query.
     * @return
     */
    public int getCardColorFromDeck(int index);

    /**
     * Returns a string of a cards name at a given position in the discard pile
     *
     * @param index position of the discard array that you'd like to query.
     * @return
     */
    public String getCardNameFromDiscard(int index);

    /**
     * Returns a string of the color of a card from the given position in the
     * discard pile
     *
     * @param index position of the discard array that you'd like to query.
     * @return
     */
    public int getCardColorFromDiscard(int index);

}
