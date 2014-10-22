package castlepanic;

import java.io.Serializable;
import java.util.Random;

/**
 * The Deck class that contains an array of all the cards that can be dealt to a
 * player's hand. It initializes an array of size DECK_SIZE and loads the proper
 * amount of cards into elements 0 through DECK_SIZE-1.
 *
 * @author Adam Whitley
 * @author Gregory Loftis
 * @author Dipesh Dave
 * @author John Fenwick
 */
public class Deck implements Serializable {

    private Card[] deckOfCards;
    private Card[] discardedCards;
    private int topOfDeck;
    private int topOfDiscard;
    public static final int DECK_SIZE = 52;

    /**
     * Constructs a new deck with an array of cards to be used in the game. For
     * the first iteration, there will be only basic 48 basic hit cards. Three
     * cards of each distance and color combination (Archer, knight, swordsman,
     * hero. 0 = red, 1 = green, 2 = blue, 3 = any). Shuffles after
     * construction.
     *
     */
    public Deck() {
        int currentIndex = 0;
        deckOfCards = new Card[DECK_SIZE];
        discardedCards = new Card[DECK_SIZE];
        topOfDeck = DECK_SIZE - 1;
        topOfDiscard = -1;

        //This doubly-nested loop creates 3 cards of each type and color combination
        //u = looping through the card types
        //i = looping through the possible colors
        for (int u = 0; u < 3; u++) {
            for (int i = 0; i < 4; i++) { //Looping through the colors

                deckOfCards[currentIndex++] = new HitCard("swordsman", i);
                deckOfCards[currentIndex++] = new HitCard("archer", i);
                deckOfCards[currentIndex++] = new HitCard("knight", i);
                deckOfCards[currentIndex++] = new HitCard("hero", i);

            }
        }
        //add two missing cards to the deck
        for (int i=0; i<2; i++) {   
            deckOfCards[currentIndex++] = new MissingCard("missing"); 
        }
        //add two barbarian cards to the deck
        for (int i=0; i<2; i++) {
            deckOfCards[currentIndex++] = new BarbarianCard("barbarian"); 
        }
        this.shuffle();
    }

    /**
     * Shuffles the deck. Takes the deckOfCards array and shuffles them into a
     * random order.
     *
     */
    private void shuffle() {

        Card[] tempArray = new Card[DECK_SIZE];
        Random r = new Random();

        //if this is a deck made from discard pile
        if (topOfDiscard != - 1) {
            for (int i = topOfDiscard; i >= 0; i--) {
                int index = r.nextInt(i + 1); // the index of a random card in the deck
                tempArray[i] = discardedCards[index];
                // swap cards[i] with cards[index]
                Card tempCard = discardedCards[i];
                discardedCards[i] = discardedCards[index];
                discardedCards[index] = tempCard;
            }
            // at this point, tempArray has all the cards selected in random order
            deckOfCards = tempArray;
            topOfDeck = topOfDiscard;
            discardedCards = new Card[DECK_SIZE];
            topOfDiscard = -1;

            //if this is a new deck
        } else if (topOfDiscard == -1) {
            for (int i = topOfDeck; i >= 0; i--) {
                int index = r.nextInt(i + 1); // the index of a random card in the deck
                tempArray[i] = deckOfCards[index];
                // swap cards[i] with cards[index]
                Card tempCard = deckOfCards[i];
                deckOfCards[i] = deckOfCards[index];
                deckOfCards[index] = tempCard;
            }
            // at this point, tempArray has all the cards selected in random order
            deckOfCards = tempArray;

            //if cards have been discarded, but the draw pile is still populated
            //   } else {
            //       System.out.println("Error Shuffling: Cards remain in deck");
        }

    }

    /**
     * Deals a card from the top of the deck and decrements the topOfDeck
     * variable to point to the next card in the deck. If there are no cards
     * left to be drawn in the deck, then the discard pile is reshuffled into
     * the draw pile before drawing.
     *
     * @return The card at the top of the deck.
     */
    public Card dealCard() {
        if (topOfDeck == -1) {
            shuffle();
        }
        Card temp = deckOfCards[topOfDeck];
        topOfDeck--;
        return temp;

    }

    /**
     * Places a card at the top of the discard pile, incrementing topOfDiscard
     * by 1. Should be called along with Player.removeCardAt() and a future
     * playCard() method.
     *
     * @param discarded The card you wish to discharge.
     */
    public void toDiscard(Card discarded) {
        discardedCards[topOfDiscard + 1] = discarded;
        topOfDiscard++;

    }

    /**
     * Returns the number of cards in the discard pile.
     *
     * @return An integer representing the number of cards in the discard pile.
     */
    public int getNumDiscardedCards() {
        return topOfDiscard + 1;
    }

    /**
     * Gets a string array of every card in the deck. The string contains the
     * card's name, and if it's a hit card, gives its color. Cards appear from
     * the bottom up, that is, index 0 contains the card at the bottom of the
     * deck.
     *
     * @return A string array detailing the cards in the deck. Element 0
     * contains the card at the bottom of the deck and element DECK_SIZE-1
     * contains the top most card.
     */
    public String[] getCardList() {
        String[] listOfCards = new String[topOfDeck + 1];
        for (int i = 0; i < topOfDeck + 1; i++) {
            listOfCards[i] = deckOfCards[i].getName();
        }
        return listOfCards;
    }

    /**
     * Gets the overall size of the deck.
     *
     * @return The number of cards in the deck.
     */
    public int getDeckSize() {
        return deckOfCards.length;
    }

    /**
     * Gets the number of unplayed cards left in the deck.
     *
     * @return The number of cards left in the deck.
     */
    public int getNumOfUnplayedCards() {
        return topOfDeck + 1;
    }

    /**
     * Gets a string detailing the card at a given location in the deck. Goes
     * from the bottom up, that is, the bottom card of the deck is in
     * deckOfCards[0] and the top card is in deckOfCards[DECK_SIZE-1].
     *
     * @param i The index location of the desired card.
     * @return A string detailing information about the card at the given index
     * location.
     */
    public String getUnplayedCardNameAt(int i) {
        return deckOfCards[i].getName();
    }

    //document
    public String getDiscardedCardNameAt(int i) {
        return discardedCards[i].getName();
    }
    //document
    //return Card at specific unplayed index
    public Card getUnplayedCard(int i) {
        return deckOfCards[i];
    }
    //document
    //return Card at specific discarded index
    public Card getDiscardedCard(int i) {
        return discardedCards[i];
    }

}
