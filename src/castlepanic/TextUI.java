/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package castlepanic;

import java.util.Scanner;

/**
 *
 * @author Nick Radford
 *
 * The TextUI is called as an alternate form of interfacing with the Castle Panic GameState.
 * Once instantiated it will bootstrap it's own processes, including creating a GameState instance
 * and simulate a game of CastlePanic until it's conclusion, at which point the session will self-terminate.
 */
public class TextUI {
    Scanner input = new Scanner(System.in);
    Scanner textInput = new Scanner(System.in);
    String[] players;
    GameState state;
    int pCount;

    TextUI(){

        System.out.println("--------------------------------");
        System.out.println("         Castle Panic           ");
        System.out.println("--------------------------------");
        System.out.println("---------Written By:------------");
        System.out.println("                                ");
        System.out.println("         Dipesh Dave            ");
        System.out.println("         Adam Whitley           ");
        System.out.println("         Tyler McDaniel         ");
        System.out.println("         John Fenwick           ");
        System.out.println("--------------------------------");
        System.out.println("--------------------------------");


        System.out.println("Please Enter The Amount of Players:");
        pCount = input.nextInt();
        players = new String[pCount];

        for(int i=0;i<pCount;i++){
            System.out.println("Player Number "+(i+1)+" Please enter your name");
            players[i]=textInput.nextLine();
        }

        gameLoop();
    }//Constructor

    /**
     * Method that starts the gameLoop process, contains a loop that will terminate when
     * the created gameState no longer returns false when asked if all players are dead.
     *
     */
    public void gameLoop(){
        state = new GameState(players);

        while(!state.getDeadYet()){

            System.out.println(state.getPlayerName(state.getCurrentPlayer())+"'s Turn!:");

            printMonsters(state);
            //PHASE 1(Draw Up)
            state.fillCurrentPlayerHand();


            //PHASE 2 (Discard and Draw)
            printHand(state.getCurrentPlayer());
            System.out.println("PHASE 2: ");
            System.out.println("Would you like to discard a card? (y/n): ");
            if(getUserConfirm()){
                state.discardOption(true);
                System.out.println("Which card would you like to discard? (INT): ");
                int userInput = textInput.nextInt();
                state.discardCurrentPlayersCard(userInput);
                state.discardOption(false);

            } else {

                System.out.println("User has elected not to discard");
                state.discardOption(false);

            }

            printHand(state.getCurrentPlayer());

            //PHASE 3 (Trade Cards)
            System.out.println("NOW ENTERING PHASE: "+state.getCurrentPhase());

            System.out.println("PHASE 3: ");
            //PHASE 3 CODE
            System.out.println("Would you like to trade a card? (y/n): ");

            if(getUserConfirm()){
                state.tradeOption(true);
                for(int i =0; i<state.getPlayers();i++){
                    printHand(i);
                }

                //SET TARGETS
                while(state.getClearToTrade()){
                    System.out.println("Who would you like to trade with?");
                    int targetPlayer = textInput.nextInt();
                    System.out.println("What card would you like to trade?:");
                    int cardToTrade = textInput.nextInt();
                    System.out.println("What card would you like from the other player?:");
                    int targetCard = textInput.nextInt();
                    //Trade
                    state.tradeCurrentPlayerCards(targetPlayer, cardToTrade, targetCard);

                    state.tradeOption(false);
                }
            } else{
                state.tradeOption(false);
            }

            printHand(state.getCurrentPlayer());


            //PHASE 4 (PLAY CARDS)
            System.out.println("PHASE 4: ");

            printMonsters(state);
            System.out.println("Would you like to play a card? (y/n): ");

            System.out.println("Phase number: "+state.getCurrentPhase());
            if(getUserConfirm()){
                boolean playingCards = true;

                while(playingCards){
                    //GET TARGET INFO
                    System.out.println("Which monster would you like to hit? (int): ");
                    int targetMonster = textInput.nextInt();

                    printHand(state.getCurrentPlayer());
                    System.out.println("Which card would you like to play? (int):");
                    int targetCard = textInput.nextInt();


                    state.playHitCard(targetMonster, targetCard);
                    printMonsters(state);

                    printHand(state.getCurrentPlayer());

                    System.out.println("Play another card? (y/n): ");
                    if(!getUserConfirm()){
                        playingCards=false;
                    }
                }//PLAYING LOOP
            }

            state.playAdvance();
            //PHASE 5 (MOVE MONSTERS)
            System.out.println("PHASE 5: ");

            //PHASE 6 (ADD MONSTERS)
            System.out.println("PHASE 6: ");
            System.out.println("----------------------------------------------------");
        }

        System.exit(0);
    }//GameLoop

    /**
     * Helper function for internal use only. Accepts a gameState object and prints a list of
     * monsters in play.
     *
     * @param gState The gameState that is to be queried and have it's monsters printed.
     *
     */
    private void printMonsters(GameState gState){
        //Fetches the number of monsters in the play
        int numMonsters = gState.getNumMonstersInPlay();
        Monster[] monsters = new Monster[numMonsters];
        for(int i=0; i<numMonsters;i++){
            monsters[i] = new BasicMonster();
            monsters[i].hitPoints = gState.getMonsterHP(i);
            monsters[i].horizontalLocation = gState.getMonsterX(i);
            monsters[i].verticalLocation = gState.getMonsterY(i);
            monsters[i].tokenName = state.getMonsterName(i);
            monsters[i].serial = state.getMonsterSerialsInPlay()[i];
        }

        for(int i =0; i<numMonsters; i++){
            int hL =monsters[i].horizontalLocation;
            int vL = monsters[i].verticalLocation;
            String name =monsters[i].tokenName;
            int hp = monsters[i].hitPoints;
            int serial = monsters[i].serial;

            System.out.println(i +": "+ name+ " @ X:"+hL+" Y:"+vL+" HP: "+hp+", Serial:"+serial);

        }
    };

    /**
     * Helper function for internal use only that prints out the hand of the player at the index provided
     *
     * @param player The index of the player in the gameState that is to be printed
     */
    private void printHand(int player){
        String[] hand =state.getHand(player);

        System.out.println(state.getPlayerName(player)+"'s hand!:");
        for(int i = 0; i<hand.length;i++){
            System.out.println((i)+". . .  "+hand[i]);
        }
    }


    /**
     * Helper function for internal use only that maintains a loop in the console
     * until the player enters a proper response to a true or false question.
     *
     * @return Returns a boolean of the players reponse based on text input
     */
    private Boolean getUserConfirm(){
        String response = textInput.next();
        Boolean properResponse =false;
        Boolean userResponse = false;
        boolean errorCounter = false;
        while(!properResponse){
            if(response.matches("y")){
                properResponse = true;
                userResponse = true;
            } else if (response.matches("n")) {
                properResponse = true;
                userResponse = false;
            } else {
                if (!errorCounter) {
                    System.out.println("Please enter a valid response!");
                    errorCounter=true;
                }
            }

        }
        return userResponse;
    }


    /**
     *
     *
     * @param monsterPosition The position in the list of monsters spat out in the text UI that will be attacked.
     * @param cardInHand The card position inside of the card list for player's hand.
     */
    private void playTextCard(int monsterPosition, int cardInHand){
        state.setSelectedCard(cardInHand);
        state.setSelectedMonster(state.getMonsterSerialsInPlay()[monsterPosition]);
        state.playCard();
    }
}