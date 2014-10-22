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
    private boolean hostSessionActive = false;
    private final Scanner userInput = new Scanner(System.in);
    private String hostAddress;


    NetworkHandler(){

    }

    //OPENS A SPECTATE SESSION
    public void spectate(){
        System.out.println("NET:LOOP");
    }

    //OPENS A SERVER SOCKET ON THE DESIGNATED PORT
    public void openHosting(int openPort){
        port = openPort;
        try{
            System.out.println("NET: (HOST)PORT NUMBER?: ");
            port = userInput.nextInt();
            userInput.nextLine();
            localSocket = new ServerSocket(port);
            System.out.println("NET:(HOST)Waiting for connection on port "+port+" . . .");
            remoteSocket = localSocket.accept();
            System.out.println("NET:(HOST). . .CONNECTED.");
            output = new ObjectOutputStream(remoteSocket.getOutputStream());
            input = new ObjectInputStream(remoteSocket.getInputStream());
            hostSessionActive = true;

        } catch (IOException e){
            System.err.println("NET:(HOST)Error when opening new Server Socket on port "+port);
        }

    }

    //CONNECTS TO THE DESIGNATED HOST
    public void connectToHost(){
        try{
            System.out.println("NET:(CLIENT)HOST ADDRESS?: ");
            hostAddress = userInput.nextLine();
            System.out.println("NET:(CLIENT)PORT NUMBER?: ");
            port = userInput.nextInt();
            userInput.nextLine();
            remoteSocket = new Socket(hostAddress,port);
            System.out.println("NET:(CLIENT)Connection successful");
            output = new ObjectOutputStream(remoteSocket.getOutputStream());
            input = new ObjectInputStream(remoteSocket.getInputStream());
            try {
                System.out.println((String) input.readObject());
            }catch(Exception e){

            }

        }catch(IOException e){
            System.err.println("NET:(CLIENT)Error connecting to host on "+hostAddress+" @ port: "+port);
        }

    }

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
    }

    public boolean isSessionActive(){
        return hostSessionActive;
    }
}
