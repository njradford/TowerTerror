package castlepanic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author James Morrow <jmorrow1@unca.edu>
 */
public class Deck implements Serializable {
    private ArrayList<Card> deckPile; 
    private ArrayList<Card> discardPile; 
    private int initialDeckSize; 
    
    /**
     * Constructs a new deck with an array of cards to be used in the game. For
     * the first iteration, there will be only basic 48 basic hit cards. Three
     * cards of each distance and color combination (Archer, knight, swordsman,
     * hero. 0 = red, 1 = green, 2 = blue, 3 = any). Does not shuffle itself. 
     */
    Deck(String[] archetypes) {
        deckPile = new ArrayList<Card>(initialDeckSize); 
        discardPile = new ArrayList<Card>(); 
              
        //add hit cards
        for (int u = 0; u < 3; u++) { 
            for (int i = 0; i < 4; i++) { //Looping through the colors
                deckPile.add(new HitCard("swordsman", i));
                deckPile.add(new HitCard("archer", i));
                deckPile.add(new HitCard("knight", i));
                deckPile.add(new HitCard("hero", i));
            }
        }
        initialDeckSize += 48; 
        //add effect cards based on the player archetypes are in play
        ArrayList<String>archetypesCopy = new ArrayList<String>(); 
        int numPlayers = archetypes.length; 
        int numDuplicatesToAdd = 0; 
        
        //try to balance the deck a little bit
        //if there are more players, add less effect cards for each of them
        //if there are less players, add more effect cards for each of them
        if (numPlayers <= 3) {
            numDuplicatesToAdd = 2; 
        }
        else {
            numDuplicatesToAdd = 1; 
        }
        
        for (int i=0; i<archetypes.length; i++) {
            archetypesCopy.add(archetypes[i]); 
        }
        while (archetypesCopy.contains("Warleader")) {
            for (int i=0; i<numDuplicatesToAdd; i++) {
                deckPile.add(new BarbarianCard());
            }
            archetypesCopy.remove("Warleader"); 
            initialDeckSize += 1*numDuplicatesToAdd; 
        }
        while (archetypesCopy.contains("Engineer")) {
            for (int i=0; i<numDuplicatesToAdd; i++) {   
                deckPile.add(new ReinforceWallCard()); 
                deckPile.add(new TurretCard()); 
            }
            archetypesCopy.remove("Engineer");
            initialDeckSize += 2*numDuplicatesToAdd; 
        }
        while (archetypesCopy.contains("Chronomancer")) {
            for (int i=0; i<numDuplicatesToAdd; i++) {   
                deckPile.add(new MissingCard()); 
                deckPile.add(new TimeStopCard()); 
                deckPile.add(new TimeSlapCard()); 
                deckPile.add(new RewindCard()); 
            }
            archetypesCopy.remove("Chronomancer");
            initialDeckSize += 4*numDuplicatesToAdd; 
        }
   
        deckPile = shuffle(deckPile); 
        
        System.out.println("DECK SIZE IS " + initialDeckSize); 
    }
    
    /**
     * Takes the ArrayList given as an argument and depletes it of all its cards in the process of 
     * putting those cards in a new random order in a new ArrayList, which it returns. 
     * @param cardsToShuffle : The cards that this method takes and shuffles into a new ArrayList. 
     *                          cardsToShuffle will be removed of its cards in the process of shuffling
     * @return The given cards in a new ArrayList in a new order
     */
    private ArrayList<Card> shuffle(ArrayList<Card> cardsToShuffle) {
        ArrayList<Card> shuffledCards = new ArrayList<Card>(initialDeckSize); 
        Random r = new Random(); 
        while (cardsToShuffle.size() > 0) {
            int randomIndex = r.nextInt(cardsToShuffle.size()); 
            Card randomCard = cardsToShuffle.get(randomIndex); 
            shuffledCards.add(randomCard);
            cardsToShuffle.remove(randomIndex);                
        }
        return shuffledCards; 
    }
    
    /**
     * Deals a card from the top of the deck and decrements the topOfDeck
     * variable to point to the next card in the deck. If there are no cards
     * left to be drawn in the deck, then the discard pile is reshuffled into
     * the draw pile before drawing.
     *
     * @return The card at the top of the deck (the card that's last in the ArrayList)
     */
    public Card dealCard() {
        if (deckPile.isEmpty()) {
            deckPile = shuffle(discardPile);
        }
        Card cardToDeal = deckPile.get(deckPile.size()-1);
        deckPile.remove(deckPile.size()-1); 
        return cardToDeal;

    }

    /**
     * Places a card at the top of the discard pile, incrementing topOfDiscard
     * by 1. Should be called along with Player.removeCardAt() and a future
     * playCard() method.
     *
     * @param discarded The card you wish to discharge.
     */
    public void toDiscard(Card discarded) {
        discardPile.add(discarded); 
    }

    /**
     * Returns the number of cards in the discard pile.
     *
     * @return An integer representing the number of cards in the discard pile.
     */
    public int getNumDiscardedCards() {
        return discardPile.size(); 
    }

    /**
     * Gets a string array of every card in the deck. The string contains the
     * card's name, and if it's a hit card, gives its color. Cards appear from
     * the bottom up, that is, index 0 contains the card at the bottom of the
     * deck.
     *
     * @return A string array detailing the cards in the deck. 
     */
    public String[] getCardList() {   
        String[] listOfCards = new String[deckPile.size()];
        for (int i = 0; i < deckPile.size(); i++) {
            listOfCards[i] = deckPile.get(i).getName();
        }
        return listOfCards;
    }

    /**
     * @return The number of cards in the deck.
     */
    public int getDeckPileSize() {
        return deckPile.size(); 
    }
    
    /**
     * 
     * @return The number of cards in the discardPile
     */
    public int getDiscardPileSize() {
        return discardPile.size(); 
    }

    /**
     * Gets the size of the discardPile
     * @return The number of cards left in the deck.
     */
    public int getNumOfUnplayedCards() {
        return discardPile.size(); 
    }

    /**
     * 
     * @param deckPileIndex
     * @return name of card at given index to deck pile
     */
    public String getUnplayedCardNameAt(int deckPileIndex) {
        return deckPile.get(deckPileIndex).getName(); 
    }
    
    /**
     * 
     * @param discardPileIndex
     * @return name of card at given index to discard pile
     */
    public String getDiscardedCardNameAt(int discardPileIndex) {
        return discardPile.get(discardPileIndex).getName(); 
    }
    
    /**
     * 
     * @param deckPileIndex
     * @return card at given index to deck pile
     */
    public Card getUnplayedCard(int deckPileIndex) {
        return deckPile.get(deckPileIndex); 
    }
    
    /**
     * 
     * @param discardPileIndex
     * @return card at given index to discard pile
     */
    public Card getDiscardedCard(int discardPileIndex) {
        return discardPile.get(discardPileIndex); 
    }
    
    /**
     * 
     * @param card
     * @return true if the given card is in the deck pile
     */
    public boolean isCardInDeckPile(Card card) {
        return deckPile.contains(card);
    }
    
    /**
     * 
     * @param card
     * @return true if the given card is in the discard pile
     */
    public boolean isCardInDiscardPile(Card card) {
        return discardPile.contains(card); 
    }
    
    /**
     * @param card : the card whose index you want to find
     * @return The index of the given card in the deckPile. If the given card does not exist within the deckPile, the method returns -1. 
     */
    public int getLocationOfCardInDeckPile(Card card) {
        return deckPile.indexOf(card);
    }
    
    /**
     * @param card : the card whose index you want to find
     * @return The index of the given card in the discardPile. If the given card does not exist within the deckPile, the method returns -1. 
     */
    public int getLocationOfCardInDiscardPile(Card card) {
        return discardPile.indexOf(card); 
    }
}
