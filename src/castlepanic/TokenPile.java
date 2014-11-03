package castlepanic;

import java.io.Serializable;
import java.util.Random;
import java.util.ArrayList;

/**
 * The TokenPile class that contains all the tokens to be used in this game.
 * Initializes the pile of tokens and stores them in an array of tokens of size
 * PILE_SIZE. Used by GameState to keep track of tokens dealt and monsters left.
 *
 * @author Adam Whitley
 * @author Gregory Loftis
 * @author Dipesh Dave
 * @author John Fenwick
 */
public class TokenPile implements Serializable {

    private Token[] pileOfTokens;
    private int topOfPile;
    public static final int PILE_SIZE = 42;
    public static final int MAX_SIZE = PILE_SIZE + 6; //pile size plus six beginning monsters
    private int monstersDealt;
    private int monsterCount = 6;
    private int beginningMonsterCount = 0;

    /**
     * Constructs a new pile of tokens for use in the game. For the first
     * iteration, there will be 48 tokens, divided evenly into 16 goblins, 16
     * trolls, and 16 orcs. Shuffles after construction.
     */
    public TokenPile() {
        monstersDealt = 0;
        pileOfTokens = new Token[PILE_SIZE];
        for (int i = 0; i < PILE_SIZE; i++) {
            if (i < 13) {
                pileOfTokens[i] = (new BasicMonster("goblin", 1, monsterCount++));
            } else if (i >= 13 && i < 27) {
                pileOfTokens[i] = (new BasicMonster("orc", 2, monsterCount++));
            } else if (i >= 27 && i < PILE_SIZE) {
                pileOfTokens[i] = (new BasicMonster("troll", 3, monsterCount++));
            }
        }
        topOfPile = pileOfTokens.length - 1;
        this.shuffle();
    }

    /**
     * Shuffles the pile. Takes the pile of tokens and sets them into a random
     * order.
     */
    public void shuffle() {

        Token[] tempArray = new Token[PILE_SIZE];
        Random r = new Random();
        for (int i = topOfPile; i >= 0; i--) {
            int index = r.nextInt(i + 1); // the index of a random token in the pile
            tempArray[i] = pileOfTokens[index];
            // swap pileOfTokens[i] with pileOfTokens[index]
            Token tempToken = pileOfTokens[i];
            pileOfTokens[i] = pileOfTokens[index];
            pileOfTokens[index] = tempToken;
        }
        // at this point, tempArray has all the tokens selected in random order
        pileOfTokens = tempArray;
    }

    /**
     * Deals a token from the top of the pile and updates the topOfPile pointer
     * and monstersDealt counter.
     *
     * @return The token at the top of the pile.
     */
    public Token dealAToken() {
        Token temp = pileOfTokens[topOfPile];
        topOfPile--;
        monstersDealt++;
        return temp;
    }

    /**
     * Gets a String array of every token in the pile as it appears in memory.
     * The lowermost token is in element 0, and the topmost token is in
     * PILE_SIZE -1.
     *
     * @return A string array detailing the tokens in the pile. Element 0
     * contains the token at the bottom of the pile and element PILE_SIZE-1
     * contains the top most token.
     */
    public String[] getTokenList() {
        String[] listOfTokens = new String[PILE_SIZE];
        for (int i = 0; i < PILE_SIZE; i++) {
            if (i == topOfPile) {
                listOfTokens[i] = ("Position: " + i + " Name: " + pileOfTokens[i].getTokenName() + " <-- Top Token");
            } else {
                listOfTokens[i] = ("Position: " + i + " Name: " + pileOfTokens[i].getTokenName());
            }
        }
        return listOfTokens;
    }

    /**
     * Gets the amount of unplayed tokens.
     *
     * @return The number of unplayed tokens still in the pile.
     */
    public int getUnPlayedTokens() {
        return topOfPile + 1;
    }

    /**
     * Gets the amount of tokens dealt this game.
     *
     * @return the amount of tokens dealt this game.
     */
    public int getNumTokensPlayed() {
        return monstersDealt;
    }

    /**
     * Gets a string detailing the token at a given location in the pile. Goes
     * from the bottom up, that is, the bottom token of the pile is in
     * pileOfTokens[0] and the top token is in pileOfTokens[PILE_SIZE-1].
     *
     * @param i The index location of the desired token.
     * @return A string detailing information about the token at the given index
     * location.
     */
    public String getTokenAt(int i) {
        return pileOfTokens[i].getTokenName();
    }

    /**
     * Gets the token at the top of the pile.
     *
     * @return the token at the top of the pile.
     */
    public Token getTopToken() {
        return pileOfTokens[topOfPile];
    }
/**
 * Used when creating monsters outside of the TokenPile class, e.g., for initial
 * monster construction.  Consider implementing a initialMonsters method to make
 * all monster construction occur inside TokenPile.
 * @return The next sequential serial and increment monsterCount.
 */
    public int assignMonsterSerial() {
        return beginningMonsterCount++;
} /**
 * Getter that returns the total number of monsters that will be played;
 * the number that must be killed to achieve victory.
 * @return 
 */
    public int getMaxMonsterTokens() {
        return MAX_SIZE;
    }
}