package castlepanic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Nick
 */
public class NetworkHandler {

    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Socket remoteSocket;
    private ServerSocket localSocket;
    private int port;
    private boolean localActive;
    private boolean sessionActive;
    private boolean hostSessionActive = false;
    private boolean clientSessionActive =false;
    private final Scanner userInput = new Scanner(System.in);
    private String hostAddress;


    NetworkHandler(){

    }

    //OPENS A SPECTATE SESSION
    public void spectate(){
        System.out.println("NET:LOOP");
    }

    //OPENS A SERVER SOCKET ON THE DESIGNATED PORT
    public void openHosting(String port){
        try{
            localSocket = new ServerSocket(Integer.parseInt(port));
            System.out.println("NET:(HOST)Waiting for connection on port "+port+" . . .");
            remoteSocket = localSocket.accept();
            System.out.println("NET:(HOST). . .CONNECTED.");
            output = new ObjectOutputStream(remoteSocket.getOutputStream());
            input = new ObjectInputStream(remoteSocket.getInputStream());
            hostSessionActive = true;
            sessionActive =true;

        } catch (IOException e){
            System.err.println("NET:(HOST)Error when opening new Server Socket on port ");
        }

    }

    //CONNECTS TO THE DESIGNATED HOST
    public void connectToHost(String address, String port){
        try{
            remoteSocket = new Socket(address,Integer.parseInt(port));
            System.out.println("NET:(CLIENT)Connection successful");
            output = new ObjectOutputStream(remoteSocket.getOutputStream());
            input = new ObjectInputStream(remoteSocket.getInputStream());
            clientSessionActive = true;
            sessionActive =true;

        }catch(IOException e){
            System.err.println("NET:(CLIENT)Error connecting to host on "+hostAddress+" @ port: "+port);
        }

    }

    public void transmitNames(String[] names){
        try{
            System.out.println("(NET) ATTEMPTING TO TRANSMIT NAMES . . .");
            output.writeObject(names);
        }catch(IOException e){
            System.out.println("(NET) TRANSMISSION OF NAME FAILED");
        }
    }

    public String[] listenForNames(){
        String[] clientNames = new String[]{""};
        try {
            clientNames = (String[]) input.readObject();

        }catch(IOException | ClassNotFoundException e){
            System.out.println("(NET) ERROR TRYING TO RECEIVE NAME FROM CLIENT");
        }
        return clientNames;
    };

    //SENDS A GAMESTATE INSTANCE TO THE CONNECTED CLIENT
    public void transmitGameState(GameState state){
        try{
            System.out.println("NET:(HOST)Transmitting GameState to client . . .");
            output.writeObject(state);
            System.out.println("NET:(HOST)GameState transmitted.");
            output.reset();

        }catch(IOException e){
            System.err.println("NET:(HOST)FAILED TO TRANSMIT GAMESTATE TO CLIENT");
        }

    }

    //LISTENS FOR A RECIEVED GAMESTATE FROM THE CONNECTION
    public GameState listenForState(){
        GameState returnState = new GameState(new String[]{"lorem","ipsum"});

        try {
            System.out.println("NET:(CLIENT)WAITING FOR gameState FROM HOST . . .");
            returnState = (GameState) input.readObject();
            System.out.println("NET:(CLIENT)REPLY RECEIVED");

        }catch(Exception e){
            System.err.println("NET:FAILED TO RECEIVE gameState FROM CLIENT");
        }


        return returnState;
    };

    //LISTENS FOR A CALLBACK WHICH UNLOCKS THE LOCAL PROCESS.
    public boolean listenForCallback(){
        boolean callback = false;

        try{
            System.out.println("(NET) LISTENING FOR CALLBACK.");
            callback = input.readBoolean();
            System.out.println("(NET) CALLBACK RECEIVED.");
        }catch(Exception e){
            System.out.println("(NET) FAILED TO RECEIVE CALLBACK.");
        }

        return  callback;
    };

    public void sendCallback(boolean callback){

        try{
            System.out.println("(NET) ATTEMPTING TO TRANSMIT CALLBACK.");
            output.writeBoolean(callback);
            System.out.println("(NET) CALLBACK TRANSMITTED.");
        }catch(Exception e){
            System.out.println("(NET) FAILED TO TRANSMIT CALLBACK.");
        }
    };

    public boolean isSessionActive(){
        return sessionActive;
    }

    public void setLocalActive(boolean active){
        this.localActive = active;

    }

    public boolean isLocalActive(){return localActive;}

    public boolean isHostSessionActive(){return hostSessionActive;}

    //UPDATES THE SESSION ACTIVE VARAIBLES ACCORDING TO CURRENT ACTIVE PLAYER.
    public void updateLocalActive(GameState state){
        int activePlayer = state.getCurrentPlayer();

        if(hostSessionActive){
            if(activePlayer == 0){
                setLocalActive(true);
            }else{
                setLocalActive(false);
            }
        } else if(clientSessionActive){
            if(activePlayer == 1){
                setLocalActive(true);
            } else{
                setLocalActive(false);
            }
        }

        System.out.println("IS THIS CURRENTLY ACTIVE?:" +isLocalActive());
    }


}
