package castlepanic;

import java.util.Scanner;

/**
 * @author Nick
 *
 * The TextUI is called as an alternate form of interfacing with the Castle Panic GameState
 * Once instantiated it will bootstrap it's own processes, including creating a GameState instance
 * and simulate a game of CastlePanic until it's conclusion, at which point the session will self-terminate.
 *
 * Updated to provide more clarity and ease of use
 */
public class TextUIb {

    Scanner input = new Scanner(System.in);
    Scanner textInput = new Scanner(System.in);
    String[] players;
    GameState state;
    int pCount;

    TextUIb(){

        System.out.println("~~~Welcome to Tower Terror~~~\n");
        System.out.println("Please Enter The Amount of Players:");
        pCount = input.nextInt();
        players = new String[pCount];

        for(int i=0;i<pCount;i++){
            System.out.println("Player Number "+(i+1)+" Please enter your name");
            players[i]=textInput.nextLine();
        }

        gameLoop();

    }

    /**
     * Maintains the gameLoop until the gameState returns that the game is over.
     */
    public void gameLoop() {
        state = new GameState(players);
        while (!state.getDeadYet()) {
            //Print current user name
            System.out.println("It is currently Player "+state.getCurrentPlayer()+"'s turn. ("+state.getPlayerName(state.getCurrentPlayer()));

            //PHASE 1 (Draw up cards)
            state.fillCurrentPlayerHand();
            printHand(state.getCurrentPlayer());

            //PHASE 2 (Discard)
            System.out.println("Would you like to discard?");

            if(getUserConfirm()){
                state.discardOption(true);
                System.out.println("Which card would you like to discard? (INT): ");
                int userInput = textInput.nextInt();
                state.discardCurrentPlayersCard(userInput);
                state.discardOption(false);

            } else {

                System.out.println("User has elected not to discard");
                //does this need to be inside else block above??
                state.discardOption(false);

            }

            printHand(state.getCurrentPlayer());

            //PHASE 3 (Trade)
            System.out.println("Would you like to trade? (y/n)");
            if(getUserConfirm()){
                state.tradeOption(true);
                for(int i =0; i<state.getPlayers();i++){
                    printHand(i);
                }

                //Set Targets
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

            //PHASE (Play Cards)
            System.out.println("PHASE 4: ");

            printMonsters(state);
            System.out.println("Would you like to play a card? (y/n): ");

            System.out.println("Phase number: "+state.getCurrentPhase());
            if(getUserConfirm()){
                boolean playingCards = true;

                while(playingCards){
                    //Get target info
                    System.out.println("Which monster would you like to hit? (int): ");
                    int targetMonster = textInput.nextInt();

                    printHand(state.getCurrentPlayer());
                    System.out.println("Which card would you like to play? (int):");
                    int targetCard = textInput.nextInt();




                    state.setSelectedCard(targetCard);
                    state.setSelectedMonster(targetMonster);

                    state.playCard();

                    printMonsters(state);

                    printHand(state.getCurrentPlayer());

                    System.out.println("Play another card? (y/n): ");
                    if(!getUserConfirm()){
                        playingCards=false;
                    }
                }//Playing loop
            }

        }
    }


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
            int ser = monsters[i].serial;

            System.out.println(i +": "+ name+ " @ X:"+hL+" Y:"+vL+" HP: "+hp+" SERIAL: "+ser);
        }
    };



    /**
     * Helper function for internal use only that prints out the hand of the player at the index provided
     *
     * @param player The index of the player in the gameState that is to be printed
     */
    private void printHand(int player){
        String[] hand =state.getHand(player);

        System.out.println("\n"+state.getPlayerName(player).toLowerCase()+"'S HAND:");
        for(int i = 0; i<hand.length;i++){
            System.out.println((i)+". . .  "+hand[i]);
        }
        System.out.println("");
    }


    /**
     * Helper function for internal use only that maintains a loop in the console
     * until the player enters a proper response to a true or false question.
     *
     * @return Returns a boolean of the players response based on text input
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
}
