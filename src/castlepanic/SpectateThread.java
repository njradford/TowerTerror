package castlepanic;

/**
 * Created by Nick Radford on 10/25/14.
 */
public class SpectateThread implements Runnable {

    GameState state;


    SpectateThread(GameState state){

        this.state = state;
    }

    public void run(){
        while(state.getDeadYet()){
            System.out.println("CURRENT PLAYER IS: "+state.getCurrentPlayer());
        }
    }
}
