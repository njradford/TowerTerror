package castlepanic;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author gloftis
 */
public class GameState implements GameStateInterface, BoardEffectInterface, Serializable {

    //variables for keeping track of card effects
    private final static int DELAY = 0, TIME_STOP = 1;
    private boolean[] tempBoardEffectFlags = new boolean[2];
    private int[] turretAmmo = new int[7];

    private Castle gameCastle;
    private TokenPile gameTokenPile;
    private Deck gameDeck;
    private Player[] players;
    private int numberOfTrades;
    private int numberOfDiscards;
    private int handSize;
    private int selectedCardIndex = -1;
    private int selectedWallIndex = -1;
    private int selectedTowerIndex = 1;
    private int otherPlayerIndex = -1;
    private int otherPlayerCardIndex = -1;
    private ArrayList<Monster> monstersInField;
    private int selectedMonsterIndex;
    private int currentPhase; // Current Phase is 1 through 6
    private int currentPlayerNumber; //Player numbers cycles from 0 to numberOfPlayers - 1

    //phase 2 additions
    private int discardThisTurn;
    private boolean clearToDiscard;

    //phase 3 additions
    private int tradedThisTurn;
    private boolean clearToTrade;

    //phase 5 additions
    private final int collisionDamage;
    //end additions

    private int[] monsterSerials; //an array made to contain all monster serial numbers in play.

    //Section of methods overriding BoardEffectInterface
    @Override
    public int noNewMonsters() {
        tempBoardEffectFlags[DELAY] = true;
        return 0;
    }

    @Override
    public int turret() {
        if (gameCastle.getTowerState(selectedTowerIndex) == Castle.TOWER_STANDING) {
            turretAmmo[selectedTowerIndex] = 3;
        }
        return 0;
    }

    @Override
    public int rewind() {
        for (Monster m : monstersInField) {
            if (m.getVerticalLocation() == 0) {
                m.setVerticalLocation(1);
            }
        }
        return 0;
    }

    @Override
    public int timeSlap() {
        Monster m = monstersInField.get(selectedMonsterIndex);
        m.setVerticalLocation(4);
        return 0;
    }

    @Override
    public int reinforceWall() {
        gameCastle.reinforceWall(selectedWallIndex);
        return 0;
    }

    @Override
    public int barbarian() {
        if (monstersInField.size() - 1 < selectedMonsterIndex || selectedMonsterIndex < 0) {
            System.err.println("Attempting to play barbarian card on an invalid monster index.");
            return -1;
        } else {
            Monster m = monstersInField.get(selectedMonsterIndex);
            m.takeHit(m.getHP()); //hit selected monster so to set its hp to 0   

            monstersInField.get(selectedMonsterIndex).deathEffects(this);
            monstersInField.remove(selectedMonsterIndex);
            //check to see if game is won?
            players[currentPlayerNumber].awardPoints(monstersInField.get(selectedMonsterIndex).getPointValue()); //give player points based on monster value     
            return 0;
        }
    }

    @Override
    public int timeStop() {
        tempBoardEffectFlags[TIME_STOP] = true;
        return 0;
    }

    //Section of methods found in implemented interfaces
    public GameState(String[] listOfNames) {
        gameCastle = new Castle();
        gameTokenPile = new TokenPile();
        //dummy code
        String[] archetypes = new String[listOfNames.length];
        for (int i = 0; i < listOfNames.length; i++) {
            archetypes[i] = "Chronomancer";
        }

        players = new Player[listOfNames.length];
        gameDeck = new Deck(archetypes);
        collisionDamage = 1; //monsters take this amount of damage upon hitting wall/tower.
        //Rules for number of trades and discards in the game, 1 player can't trade, 2-5 players get 1 trade.
        //2 discards per phase for 1 player, 1 discard per phase for more.
        if (listOfNames.length == 1) {
            numberOfTrades = 0;
            numberOfDiscards = 2;
        } else if (listOfNames.length >= 2 && listOfNames.length <= 5) {
            numberOfTrades = 1;
            numberOfDiscards = 1;
        } else {
            numberOfTrades = 2;
            numberOfDiscards = 1;
        }
        //Player hand sizes determined -- 1 or 2 is 6, 3 to 5 is 5, more than that is 4.
        if (listOfNames.length == 1 || listOfNames.length == 2) {
            handSize = 6;
        } else if (listOfNames.length >= 3 && listOfNames.length <= 5) {
            handSize = 5;
        } else if (listOfNames.length > 5) {
            handSize = 4;
        } else {
            System.err.println("Invalid number of players: " + listOfNames.length);
        }
        //Create new players with the appropriate hand sizes, 
        for (int i = 0; i < listOfNames.length; i++) {
            players[i] = new Player(handSize, gameDeck, listOfNames[i], archetypes[i]);
        }

        monstersInField = new ArrayList(gameTokenPile.PILE_SIZE); //encap
        currentPhase = 1;
        currentPlayerNumber = 0;
        monsterSerials = new int[gameTokenPile.PILE_SIZE + gameTokenPile.MAX_SIZE]; //encap
        for (int serial : monsterSerials) { //initialize empty elements in monsterSerials to -1
            serial = -1;
        }
        BasicMonster troll = new BasicMonster("troll", 3, gameTokenPile.assignMonsterSerial()); //token pile method?
        BasicMonster orc1 = new BasicMonster("orc", 2, gameTokenPile.assignMonsterSerial());
        BasicMonster orc2 = new BasicMonster("orc", 2, gameTokenPile.assignMonsterSerial());
        BasicMonster goblin1 = new BasicMonster("goblin", 1, gameTokenPile.assignMonsterSerial());
        BasicMonster goblin2 = new BasicMonster("goblin", 1, gameTokenPile.assignMonsterSerial());
        BasicMonster goblin3 = new BasicMonster("goblin", 1, gameTokenPile.assignMonsterSerial());

        goblin1.beginningGamePlacement(1);
        orc1.beginningGamePlacement(2);
        goblin2.beginningGamePlacement(3);
        orc2.beginningGamePlacement(4);
        goblin3.beginningGamePlacement(5);
        troll.beginningGamePlacement(6);

        monstersInField.add(goblin1);
        monstersInField.add(orc1);
        monstersInField.add(goblin2);
        monstersInField.add(orc2);
        monstersInField.add(goblin3);
        monstersInField.add(troll);
    }

    /**
     * Phase 1 -- draw up. Should only be invoked on phase one, if invoked on a
     * different phase, fail. Increments the current phase upon a successful
     * invocation.
     *
     * @return true if successfully invoked, false if fail.
     */
    public boolean fillCurrentPlayerHand() {
        if (currentPhase != 1) {
            System.err.println("Incorrect Phase - discardOption invoked out of phase");
            return false;
        } else {
            players[currentPlayerNumber].drawToFull(gameDeck);
            currentPhase++;
            //initialize phase 2 specific data fields
            clearToDiscard = false;
            discardThisTurn = 0;
            return true;
        }
    }
//    public GameState(GameState gameInProgress) {
//        //STUB -- Do this later. I'm hungry.

    /**
     * Phase 2 -- Discard 1 Card and Draw (optional). Two methods are provided
     * for moving to phase 3.
     */
    /**
     * discardOption method: Should be invoked first and given a boolean
     * argument - true if the player chooses to discard card, and false if the
     * player chooses not to discard. If given false, discardOption will advance
     * the player to phase 3. If given true, a boolean will be set to true to
     * enable discardCurrentPlayersCard, and after its use tradeOption can be
     * called to advance to phase 3 or trade again.
     *
     * @param discardChoice: true if the player chooses to discard, false if
     * not.
     * @return -1 if invoked incorrectly, 0 if functioning correctly but not yet
     * advancing the phase, and 1 if used correctly and advancing the phase.
     */
    public int discardOption(boolean discardChoice) {
        if (currentPhase != 2) {
            System.err.println("Incorrect Phase - discardOption invoked out of phase");
            return -1;
        } else {
            if (discardChoice) {
                if (discardThisTurn < numberOfDiscards) {
                    clearToDiscard = true;
                    return 0;
                } else {
                    System.err.println("Max Discards reached!");
                    return -1;
                }
            } else {
                currentPhase++;
                //initialization of phase 3 data fields
                tradedThisTurn = 0;
                clearToTrade = false;
                return 1;
            }
        }
    }

    /**
     * discardCurrentPlayersCard method (graphical): Discards the selected card
     * from the index provided. This method will make sure the selected card is
     * discarded and the discard counter will keep track of maximum amount of
     * discards. After this step the the draw step will come into play and will
     * draw the hand to full.
     *
     * @return: -1 if used out of phase or without discardOption or drawUp being
     * called first or 0 if invoked correctly with successful discard and draw
     * session.
     */
    //Discard and Draw 1 Card(Optional) Phase 2
    public int discardCurrentPlayersCard() {
        if (currentPhase != 2) {
            System.err.println("Incorrect Phase - discardCurrentPlayersCard invoked out of phase");
            return -1;
        } else {
            if (clearToDiscard) {

                //Remove card i from the current player and put it in the discard pile
                //of the gameDeck.
                if (selectedCardIndex >= 0) {
                    gameDeck.toDiscard(players[currentPlayerNumber].removeCardAt(selectedCardIndex));
                    discardThisTurn++;
                    clearToDiscard = false;
                    //Draw a new card into the player's hand to replace the one discarded.
                    players[currentPlayerNumber].receiveCard(gameDeck.dealCard());
                }
                return 0;

            } else {
                System.err.println("An error has occurred, please try discardOption action again.");
                return -1;
            }
        }
    }

    /**
     * discardCurrentPlayersCard method (text): Discards the selected card from
     * the index provided. This method will make sure the selected card is
     * discarded and the discard counter will keep track of maximum amount of
     * discards. After this step the the draw step will come into play and will
     * draw the hand to full.
     *
     * @param i: The index of the card to be discarded from current players
     * hand.
     * @return: -1 if used out of phase or without discardOption or drawUp being
     * called first or 0 if invoked correctly with successful discard and draw
     * session.
     */
    //Discard and Draw 1 Card(Optional) Phase 2
    public int discardCurrentPlayersCard(int i) {
        if (currentPhase != 2) {
            System.err.println("Incorrect Phase - discardCurrentPlayersCard invoked out of phase");
            return -1;
        } else {
            if (clearToDiscard) {

                //Remove card i from the current player and put it in the discard pile
                //of the gameDeck.
                gameDeck.toDiscard(players[currentPlayerNumber].removeCardAt(i));
                discardThisTurn++;
                clearToDiscard = false;
                //Draw a new card into the player's hand to replace the one discarded.
                players[currentPlayerNumber].receiveCard(gameDeck.dealCard());
                return 0;
            } else {
                System.err.println("An error has occurred, please try discardOption action again.");
                return -1;
            }
        }
    }

    /**
     * Phase 3 -- trade (optional). Two methods are provided for moving to phase
     * 4.
     */
    /**
     * tradeOption method: Should be invoked first and given a boolean argument
     * - true if the player chooses to trade cards, and false if the player
     * chooses not to trade. If given false, tradeOption will advance the player
     * to phase 4. If given true, a boolean will be set to true to enable
     * tradeCurrentPlayerCards, and after its use tradeOption can be called to
     * advance to phase 3 or trade again.
     *
     * @param tradeChoice: true if the player chooses to trade, false if not.
     * @return -1 if invoked incorrectly, 0 if functioning correctly but not yet
     * advancing the phase, and 1 if used correctly and advancing the phase.
     */
    public int tradeOption(boolean tradeChoice) {
        if (currentPhase != 3) {
            System.err.println("Incorrect Phase - tradeOption invoked out of phase");
            return -1;
        } else {
            if (tradeChoice) {
                if (tradedThisTurn < numberOfTrades) {
                    clearToTrade = true;
                    return 0;
                } else {
                    System.err.println("Max trades reached!");
                    return -1;
                }
            } else {
                currentPhase++;
                //initialize phase 4 data fields
                //set all rubble walls to destroyed walls
                gameCastle.clearRubble();
                //set all tower rubble to destroyed towers
                gameCastle.clearTowerRubble();
                return 1;
            }
        }
    }

    /**
     * tradeCurrentPlayerCards method (graphical): Trades a selected card from
     * the current player to a target Player, toPlayer, at selected hand
     * indices. Will only allow trades if tradeOption has been called first to
     * ensure max trades haven't been reached, and resets after use to require a
     * call to tradeOption for each successive trade. Increments tradedThisTurn
     * to track how many trades have been made.
     *
     * @return: -1 if used out of phase or without tradeOption being called
     * first or 0 if invoked correctly with successful trade.
     */
    public int tradeCurrentPlayerCards() {

        if (currentPhase != 3) {
            System.err.println("Incorrect Phase - tradeCurrentPlayerCards invoked out of phase");
            return -1;
        } else {
            if (clearToTrade) {

                //Swap the indicated cards between current player and other player.
                players[currentPlayerNumber].swapCard(players[otherPlayerIndex], selectedCardIndex, otherPlayerCardIndex);
                tradedThisTurn++;
                clearToTrade = false;
                return 0;
            } else {
                System.err.println("Call tradeOption(boolean) before invoking this method");
                return -1;
            }
        }
    }

    /**
     * tradeCurrentPlayerCards method (text): Trades a selected card from the
     * current player to a target Player, toPlayer, at selected hand indices.
     * Will only allow trades if tradeOption has been called first to ensure max
     * trades haven't been reached, and resets after use to require a call to
     * tradeOption for each successive trade. Increments tradedThisTurn to track
     * how many trades have been made.
     *
     * @param otherPlayerIndex: The player card is being traded to.
     * @param currentPlayerCardIndex: The index of the card to be traded in the
     * current player's hand.
     * @param otherPlayerCardIndex: The index of the card to be received from
     * the target player's hand.
     * @return: -1 if used out of phase or without tradeOption being called
     * first or 0 if invoked correctly with successful trade.
     */
    public int tradeCurrentPlayerCards(int otherPlayerIndex, int currentPlayerCardIndex, int otherPlayerCardIndex) {
        if (currentPhase != 3) {
            System.err.println("Incorrect Phase - tradeCurrentPlayerCards invoked out of phase");
            return -1;
        } else {
            if (clearToTrade) {

                //Swap the indicated cards between current player and other player.
                players[currentPlayerNumber].swapCard(players[otherPlayerIndex], currentPlayerCardIndex, otherPlayerCardIndex);
                tradedThisTurn++;
                clearToTrade = false;
                return 0;
            } else {
                System.err.println("Call tradeOption(boolean) before invoking this method");
                return -1;
            }
        }
    }

    /*
     * @return result from the appropriate method (playHitCard() or playEffectCard()), if possible
     * otherwise, return -1
     */
    public int playCard() {
        if (players[currentPlayerNumber].getCardAt(selectedCardIndex) instanceof HitCard) {
            return playHitCard();
        } else if (players[currentPlayerNumber].getCardAt(selectedCardIndex) instanceof EffectCard) {
            return playEffectCard();
        }
        return -1;
    }

    /**
     * Phase 4 -- play cards (optional). Two methods are provided for moving to
     * phase 5. Current methods only support HitCards.
     */
    /**
     * playHitCard method (graphical): Invoked to attack monsters with HitCards.
     * After being given the index of a monster in the monstersInField ArrayList
     * and the index of a HitCard in the current player's hand, checks to ensure
     * that monster is in range, calls its takeHit method, and removes it from
     * the monstersInField ArrayList if its HP reaches 0.
     *
     * @return -1 if invoked incorrectly (with another type of card, out of
     * range, out of phase), 0 if functioning correctly.
     */
    private int playHitCard() {
        if (currentPhase != 4) {
            System.err.println("Incorrect Phase - playHitCard invoked out of phase");
            return -1;
        } else if (players[currentPlayerNumber].getHandSize() - 1 < selectedCardIndex) {
            System.err.println("Attempting to play hit card at invalid hand index.");
            return -1;
        } else if (monstersInField.size() - 1 < selectedMonsterIndex || selectedMonsterIndex < 0) {
            System.err.println("Attempting to play hit card on a monster whose index is invalid.");
            return -1;
        } else if (!(players[currentPlayerNumber].getCardAt(selectedCardIndex) instanceof HitCard)) {
            System.err.println("playHitCard invoked with a non-hit card selected!");
            return -1;
        } else {
            HitCard f = (HitCard) players[currentPlayerNumber].getCardAt(selectedCardIndex); //f is the current HitCard being played.
            boolean[][][] range = f.getHitLocations();

            //add helper function - input: monster location output - boolean
            //if the current HitCard can hit the location the current monster resides in
            if (range[monstersInField.get(selectedMonsterIndex).getHorizontalLocation()][monstersInField.get(selectedMonsterIndex).getVerticalLocation()][monstersInField.get(selectedMonsterIndex).getAltitudeLocation()]) {
                monstersInField.get(selectedMonsterIndex).takeHit(f.getDamage()); //HitCard deals its damage to monster.
                if (monstersInField.get(selectedMonsterIndex).getHP() <= 0) {
                    monstersInField.get(selectedMonsterIndex).deathEffects(this);
                    monstersInField.remove(selectedMonsterIndex);
                    //check to see if game is won?
                    players[currentPlayerNumber].awardPoints(monstersInField.get(selectedMonsterIndex).getPointValue()); //give player points based on monster value
                }
                gameDeck.toDiscard(players[currentPlayerNumber].removeCardAt(selectedCardIndex)); //removes HitCard from hand and places in discard pile.
                return 0;
            } else {
                System.err.println("Monster not in range of selected card!");
                return -2;
            }
        }
    }

    /**
     * playHitCard method (text): Invoked to attack monsters with HitCards.
     * After being given the index of a monster in the monstersInField ArrayList
     * and the index of a HitCard in the current player's hand, checks to ensure
     * that monster is in range, calls its takeHit method, and removes it from
     * the monstersInField ArrayList if its HP reaches 0.
     *
     * @param targetMonsterIndex: Index of monster to attack in the
     * monstersInField ArrayList.
     * @param hitCardIndex: Index of a HitCard in the current player's hand.
     * @return -1 if invoked incorrectly (with another type of card, out of
     * range, out of phase), 0 if functioning correctly.
     */
    /*
     public int playHitCard(int targetMonsterIndex, int hitCardIndex) {
     if (currentPhase != 4) {
     System.err.println("Incorrect Phase - playHitCard invoked out of phase");
     return -1;
     } else if (players[currentPlayerNumber].getHandSize() - 1 < hitCardIndex) {
     System.err.println("Attempting to play hit card at invalid hand index.");
     return -1;
     } else if (monstersInField.size() - 1 < targetMonsterIndex) {
     System.err.println("Attempting to play hit card on a monster whose index is invalid.");
     return -1;
     } else if (!(players[currentPlayerNumber].getCardAt(hitCardIndex) instanceof HitCard)) {
     System.err.println("playHitCard invoked with a non-hit card selected!");
     return -1;
     } else {
     HitCard f = (HitCard) players[currentPlayerNumber].getCardAt(hitCardIndex); //f is the current HitCard being played.
     boolean[][] range = f.getHitLocations();

     //add helper function - input: monster location output - boolean
     //if the current HitCard can hit the location the current monster resides in
     if (range[monstersInField.get(targetMonsterIndex).getHorizontalLocation()][monstersInField.get(targetMonsterIndex).getVerticalLocation()]) {
     monstersInField.get(targetMonsterIndex).takeHit(f.getDamage()); //HitCard deals its damage to monster.
     if (monstersInField.get(targetMonsterIndex).getHP() <= 0) {
     monstersInField.get(targetMonsterIndex).deathEffects(this);
     monstersInField.remove(targetMonsterIndex);
     //check to see if game is won?
     players[currentPlayerNumber].awardPoints(monstersInField.get(targetMonsterIndex).getPointValue()); //give player points based on monster value
     }
     gameDeck.toDiscard(players[currentPlayerNumber].removeCardAt(hitCardIndex)); //removes HitCard from hand and places in discard pile.
     return 0;
     } else {
     System.err.println("Monster not in range of selected card!");
     return -1;
     }
     }
     }
     */
    /**
     * needs javadoc 9/10
     */
    private int playEffectCard() {
        if (currentPhase != 4) {
            System.err.println("Incorrect Phase - playEffectCard invoked out of phase");
            return -1;
        } else if (players[currentPlayerNumber].getHandSize() - 1 < selectedCardIndex) {
            System.err.println("Attempting to play effect card at invalid hand index.");
            return -1;
        } else if (!(players[currentPlayerNumber].getCardAt(selectedCardIndex) instanceof EffectCard)) {
            System.err.println("playEffectCard invoked with a hit card selected!");
            return -1;
        }
        EffectCard eCard = (EffectCard) players[currentPlayerNumber].getCardAt(selectedCardIndex);
        eCard.takeEffect(this);
        gameDeck.toDiscard(players[currentPlayerNumber].removeCardAt(selectedCardIndex)); //removes EffectCard from hand and places in discard pile.
        return 0;
    }

    /**
     * playAdvance method: Should be invoked after the player has played all
     * desired cards in the current turn. No argument method completes turn and
     * moves to next player.
     *
     * @return: -1 if invoked out of phase, 1 if invoked correctly and moving to
     * turn end, 3 if game is won.
     */
    public int playAdvance() {
        if (currentPhase != 4) {
            System.err.println("Incorrect Phase - playAdvance invoked out of phase");
            return -1;
        } else {
            if (monstersInField.isEmpty() && gameTokenPile.getUnPlayedTokens() == 0) { //WE WON - ALL YOUR BASE ARE BELONG TO US
                return 3;
            }
            if (!tempBoardEffectFlags[TIME_STOP]) {
                monstersMove();
            }
            //as long as the missing flag is not set to true, draw tokens
            if (!tempBoardEffectFlags[DELAY]) {
                drawTokens();
            }

            currentPhase = 1;
            currentPlayerNumber = (currentPlayerNumber + 1) % players.length; //cycle to next player.

            //reset all the temporary flags at the end of turn
            for (int i = 0; i < tempBoardEffectFlags.length; i++) {
                tempBoardEffectFlags[i] = false;
            }
            //update turret effect logic
            for (int i = 1; i < turretAmmo.length; i++) {
                int towerIndex = i;
                //if the tower is gone, the turret goes with it
                if (!(gameCastle.getTowerState(towerIndex) == Castle.TOWER_STANDING)) {
                    turretAmmo[i] = 0;
                }
                //if the tower has a turret
                if (turretAmmo[i] != 0) {
                    final int anImpossiblyBigNumber = 100;
                    int closestToTower = anImpossiblyBigNumber;
                    Monster closestMonster = null;
                    for (Monster m : monstersInField) {
                        if (m.getHorizontalLocation() == towerIndex) {
                            if (m.getVerticalLocation() < closestToTower) {
                                closestToTower = m.getVerticalLocation();
                                closestMonster = m;
                            }
                        }
                    }
                    if (closestMonster != null) {
                        closestMonster.takeHit(1);
                        if (closestMonster.getHP() <= 0) {
                            closestMonster.deathEffects(this);
                            monstersInField.remove(closestMonster);
                        }
                        turretAmmo[i]--;
                    }

                }
            }

            return 1;
        }
    }

    /**
     * monstersMove method: Calls the movement method of all monsters in the
     * monstersInField ArrayList. Checks to see if walls and towers block
     * monster movement, deals damage to both parties accordingly, and removes
     * monsters killed by collision. No argument.
     *
     * @return -1 if invoked out of phase, 0 if invoked correctly, and 2 if
     * correctly invoked and game is lost.
     */
    private int monstersMove() {
        //variation from rules - not all monsters remain in swordsmen ring if 
        //wall is destroyed prior to their movement...also, monster killed should be 
        //players choice, not by index in ArrayList.
        if (currentPhase != 4) {
            System.err.println("Incorrect Phase");
            return -1;
        } else {
            for (Monster monster : monstersInField.toArray(new Monster[monstersInField.size()])) {
                if (monster.getNextVerticalLocation() > 0) { //if the monster's next location is not approaching castle.

                    monster.movement(this);
                } else if (monster.getVerticalLocation() == 0) { //if the monster is already inside castle ring.
                    if (gameCastle.getTowerState(monster.getNextHorizontalLocation()) != Castle.TOWER_DESTROYED) { //if monster is moving into standing tower.
                        gameCastle.hitTower(monster.getNextHorizontalLocation());
                        monster.takeHit(collisionDamage);
                        if (monster.getHP() <= 0) {
                            monster.deathEffects(this);
                            monstersInField.remove(monster);
                            //no points awarded - monster killed by tower.
                        }
                        if (gameCastle.areWeDeadYet()) { //GAME OVER.
                            return 2;
                        }
                    } else {
                        monster.movement(this);
                    }
                } else if (monster.getNextVerticalLocation() == 0) { //if monster is moving through wall area.
                    if (gameCastle.getWallHealth(monster.getHorizontalLocation()) <= 0 && gameCastle.getWallHealth(monster.getHorizontalLocation()) < 5) { //TO DO double check basilisk rules/calvalry
                        if (gameCastle.getTowerState(monster.getNextHorizontalLocation()) == Castle.TOWER_STANDING) { //if monster is moving into standing tower.
                            gameCastle.hitTower(monster.getNextHorizontalLocation());
                            monster.takeHit(collisionDamage);
                            if (monster.getHP() <= 0) {
                                monster.deathEffects(this);
                                monstersInField.remove(monster);
                            }
                        } else if (gameCastle.getTowerState(monster.getNextHorizontalLocation()) == Castle.TOWER_RUBBLE) {
                            //do nothing - don't go through the tower rubble
                        } else {
                            monster.movement(this);
                        }
                    } else if (gameCastle.getWallHealth(monster.getHorizontalLocation()) == 5) {
                        //don't move through rubble!
                    } else {
                        gameCastle.hitWall(monster.getHorizontalLocation()); //TO DO double check basilisk rules/calvalry
                        monster.takeHit(collisionDamage);
                        if (monster.getHP() <= 0) {
                            monster.deathEffects(this);
                            monstersInField.remove(monster);
                            //no points awarded - monster killed by wall, not by player action.
                        }
                    }
                } else {
                    System.err.println("Monster named " + monster.getName() + " at horizontal location " + monster.getHorizontalLocation()
                            + " and vertical location " + monster.getVerticalLocation() + " movement unexpected.");
                    return -1;
                }
            }
            return 0;
        }
    }

    /**
     * drawTokens method: Takes two tokens from the token pile and places them
     * on the board, unless an effect card prohibiting that action has been
     * played in the current turn, or less than 2 tokens remain in the token
     * pile. If one token remains, it will be placed. If no tokens remain, the
     * method does nothing. No arguments.
     *
     * @return An integer representing the number of tokens drawn.
     *
     */
    private int drawTokens() {
        if (currentPhase != 4) {
            System.err.println("Incorrect Phase");
            return -1;
        } else {
            if (gameTokenPile.getUnPlayedTokens() >= 2) {
                Token drawnToken1 = gameTokenPile.dealAToken();
                Token drawnToken2 = gameTokenPile.dealAToken();
                if (drawnToken1 instanceof Monster) {
                    ((Monster) drawnToken1).placeMonster();
                    ((Monster) drawnToken1).birthEffects(this);
                    monstersInField.add((Monster) drawnToken1);
                }
                //add if else for effects tokens when implemented: ((EffectToken) drawnToken1).applyEffect();
                if (drawnToken2 instanceof Monster) {
                    ((Monster) drawnToken2).placeMonster();
                    ((Monster) drawnToken2).birthEffects(this);
                    monstersInField.add((Monster) drawnToken2);
                }
                //add else for effects tokens
                return 2;
            } else if (gameTokenPile.getUnPlayedTokens() == 1) {
                Token drawnToken = gameTokenPile.dealAToken();
                if (drawnToken instanceof Monster) {
                    ((Monster) drawnToken).placeMonster();
                    ((Monster) drawnToken).birthEffects(this);
                    monstersInField.add(((Monster) drawnToken));
                }
                //add else for effects tokens
                return 1;
            } else {
                return 0;

            }
        }
    }

    //Section for implementing GameState interface
    /**
     * GETTER METHODS FOR THE UI CLASS
     */
    @Override
    public int getNumTradesAllowed() {
        return numberOfTrades;
    }

    @Override
    public int getNumTradesRemaining() {
        return numberOfTrades - tradedThisTurn;
    }

    @Override
    public int getNumDiscardsAllowed() {
        return numberOfDiscards;
    }

    @Override
    public int getNumDiscardsRemaining() {
        return numberOfDiscards - discardThisTurn;
    }
//add to interface

    public int getNumberDiscards() {
        return discardThisTurn;
    }

    @Override
    public int getMaxHandSize() {
        return handSize;
    }
//add to interface

    public boolean getClearToTrade() {
        return clearToTrade;
    }
//add to interface

    public boolean getClearToDiscard() {
        return clearToDiscard;
    }

    @Override
    public int getCurrentPlayer() {
        return currentPlayerNumber;
    }

    @Override
    public int getCurrentPhase() {
        return currentPhase;
    }

    @Override
    public boolean getDeadYet() {
        return gameCastle.areWeDeadYet();
    }

    @Override
    public int getPlayers() {
        return players.length;
    }

    @Override
    public int getNumCards(int playerIndex) {
        return players[playerIndex].getHandSize();
    }

    public void setSelectedWall(int horizontalLocation) {
        if (horizontalLocation < 1 || horizontalLocation > 6) {
            System.err.println("Tried to reinforce wall number " + horizontalLocation + "but it's invalid");
        } else {
            selectedWallIndex = horizontalLocation;
        }
    }

    //additional method
    public void setSelectedCard(int playerCardIndex) {

        selectedCardIndex = playerCardIndex;
    }

    //additional method
    public void setOtherCard(int otherPlayerIndex, int otherPlayerCardIndex) {
        this.otherPlayerIndex = otherPlayerIndex;
        this.otherPlayerCardIndex = otherPlayerCardIndex;
    }

    @Override
    public String getCardNameFromPlayerHand(int playerIndex, int cardIndex) {
        return players[playerIndex].printCardAt(cardIndex);
    }

    @Override
    //Talk to gang about best implementations
    public int getCardDamageFromPlayerHand(int playerIndex, int cardIndex) {
        int answer = -1;
        if (isHitCard(playerIndex, cardIndex)) {

            Card card = players[playerIndex].getCardAt(cardIndex);
            answer = ((HitCard) card).getDamage();
        } else {
            System.err.println("Card at Player: " + playerIndex + " Card: " + cardIndex + " Does "
                    + "not have property: Color");

        }

        return answer;
    }

    @Override
    public int getCardColorFromPlayerHand(int playerIndex, int cardIndex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getTopOfHand(int playerIndex) {
        return players[playerIndex].getTopOfHand();
    }

    @Override
    public int getScore(int playerIndex) {
        return players[playerIndex].getScore();
    }

    @Override
    public String[] getHand(int playerIndex) {
        return players[playerIndex].printHand();
    }

    @Override
    public int getHandSize(int playerIndex) {
        return players[playerIndex].getHandSize();
    }

    @Override
    public String getPlayerName(int playerIndex) {
        return players[playerIndex].getName();
    }

    @Override
    public boolean isHitCard(int playerIndex, int cardIndex) {
        Card s = players[playerIndex].getCardAt(cardIndex);
        boolean isHitCard = false;
        if (s instanceof HitCard) {
            isHitCard = true;
        }
        return isHitCard;
    }

    @Override
    public int getTowerStatus(int pos) {
        return gameCastle.getTowerState(pos);
    }

    @Override
    public int getWallHP(int pos) {
        return gameCastle.getWallHealth(pos);
    }

    @Override
    public int getNumMonstersInPlay() {
        return monstersInField.size();
    }
//different, probably unused in GameState TextUI

    @Override
    public int[] getMonsterSerialsInPlay() {
        for (int i = 0; i < monstersInField.size(); i++) {
            monsterSerials[i] = monstersInField.get(i).getSerial();
        }
        for (int j = monstersInField.size(); j < monsterSerials.length; j++) {
            monsterSerials[j] = -1;
        }
        return monsterSerials;
    }
//different, probably unused in GameState TextUI

    @Override
    public int getMonsterIndex(int serial) {

        for (int i = 0; i < monstersInField.size(); i++) {
            if (monstersInField.get(i).getSerial() == serial) {
                return i;
            }
        }
        System.err.println("getMonsterIndex cannot find that serial!");
        return -1; //didn't find that serial ??
    }
//additional method

    public void setSelectedMonster(int serial) {
        selectedMonsterIndex = getMonsterIndex(serial);

    }

    //add error checking for illegal arguments and add to interface.  doc.
    public int getMonstersInSquare(int xBoard, int yBoard) {
        int count = 0;
        for (Monster monster : monstersInField) {
            if (monster.getHorizontalLocation() == xBoard && monster.getVerticalLocation() == yBoard) {
                count++;
            }
        }
        System.out.println(count);
        return count;

    }

    @Override
    public int getMonsterHP(int index) {
        return monstersInField.get(index).getHP();
    }

    /**
     * public java.awt.Point getMonsterPoint(int serial) { int monsterIndex =
     * this.getMonsterIndex(serial); java.awt.Point location = new
     * java.awt.Point (this.getMonsterX(monsterIndex),
     * this.getMonsterY(monsterIndex)); return location; }
     *
     */
    @Override
    public int getMonsterX(int index) {
        return monstersInField.get(index).getHorizontalLocation();
    }

    @Override
    public int getMonsterY(int index) {
        return monstersInField.get(index).getVerticalLocation();
    }

    @Override
    public int getMonsterXnext(int index) {
        return monstersInField.get(index).getNextHorizontalLocation();
    }

    @Override
    public int getMonsterYnext(int index) {
        return monstersInField.get(index).getNextVerticalLocation();
    }

    public String getMonsterName(int index) {
        return monstersInField.get(index).getName();
    }

    @Override
    public int getUnplayedMonsters() {
        return gameTokenPile.getUnPlayedTokens();
    }

    @Override
    public int getMonstersKilled() {
        return gameTokenPile.getNumTokensPlayed() - monstersInField.size();
    }

    @Override
    public int getDeckSize() {
        return gameDeck.getDeckPileSize();
    }

    @Override
    public int getDiscardSize() {
        return gameDeck.getNumDiscardedCards();
    }

    @Override
    public int getNumOfUnplayedCards() {
        return gameDeck.getNumOfUnplayedCards();
    }

    @Override
    public String getCardNameFromDeck(int index) {
        return gameDeck.getUnplayedCardNameAt(index);
    }

    @Override
    public int getCardColorFromDeck(int index) {
        Card card = gameDeck.getUnplayedCard(index);
        if (card instanceof HitCard) {
            return ((HitCard) card).getColor();
        } else {
            return -1;
        }
    }

    @Override
    public String getCardNameFromDiscard(int index) {
        return gameDeck.getDiscardedCardNameAt(index);
    }

    @Override
    public int getCardColorFromDiscard(int index) {
        Card card = gameDeck.getDiscardedCard(index);
        if (card instanceof HitCard) {
            return ((HitCard) card).getColor();
        } else {
            return -1;
        }
    }

    public String getPlayerArchetype(int playerIndex) {
        return players[playerIndex].getArchetype();
    }
}
