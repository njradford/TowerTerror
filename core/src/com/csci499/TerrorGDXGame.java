package com.csci499;

import com.badlogic.gdx.Game;
import com.util.GameType;

public class TerrorGDXGame extends Game {

    public static final String TITLE = "Tower Terror";
    public static final int WIDTH = 1024, HEIGHT = 768; // used later to set window size

    protected GameType options;

    private StartScreen startScreen;
    private LocalScreen localScreen;
    private MultiScreen hostScreen;
    private MultiScreen clientScreen;
    private GameScreen gameScreen;
    private EndScreen winScreen;
    private EndScreen loseScreen;
    private AbstractScreen[] screens;

    private GameState gameState; //TODO: Make variable of type GameStateInterface after adding required methods to interface
    private NetworkHandler netHandler; //TODO: Networking

    private final int maxPlayers;

    //TODO: One sprite batch for all stages for performance

    public TerrorGDXGame() {
        options = GameType.UNKNOWN;
        maxPlayers = GameState.getMaxPlayers();
        netHandler = new NetworkHandler();
    }

    @Override
    public void create() {
        screens = new AbstractScreen[]{
        startScreen = new StartScreen(),
        localScreen = new LocalScreen(maxPlayers),
        hostScreen = new MultiScreen(true),
        clientScreen = new MultiScreen(false),
        gameScreen = new GameScreen(),
        winScreen = new EndScreen(true),
        loseScreen = new EndScreen(false)
        };
        setScreen(startScreen); //start first screen
    }

    @Override
    public void render() {

       AbstractScreen currentScreen = (AbstractScreen) this.getScreen();

        if (currentScreen.getChange()) { //current screen has signaled to change screens
            switch(currentScreen.getChangeScreen()) { //switch on which screen current screen signals to change to
                case UNKNOWN:
                    System.err.println("ERROR -- TERRORGDXGAME -- RENDER -- CALLED CHANGE SCREEN WITHOUT SETTING CHANGE FLAG");
                    throw new IllegalArgumentException("ERROR -- TERRORGDXGAME -- RENDER -- CALLED CHANGE SCREEN WITHOUT SETTING CHANGE FLAG");
                case LOCAL:
                    setScreen(localScreen);
                    options = GameType.LOCAL;
                    break;
                case HOSTS:
                    setScreen(hostScreen);
                    options = GameType.HOST;
                    break;
                case CLIENTS:
                    setScreen(clientScreen);
                    options = GameType.CLIENT;
                    break;

                case GAME:
                    switch (options) {
                        case LOCAL:
                            gameState = new GameState(localScreen.getPlayers());
                            break;
                        case HOST:
                            //case for HOST
                            break;
                        case CLIENT:
                            //case for CLIENT
                            break;
                        default:
                            System.err.println("ERROR -- TERRORGDXGAME -- RENDER -- UKNOWN MULTIPLAYER OPTION");
                            throw new IllegalArgumentException("ERROR -- TERRORGDXGAME -- UNKNOWN MULTIPLAYER OPTION");
                    }
                    setScreen(gameScreen);
                    break;
                case WIN:
                    setScreen(winScreen);
                    break;
                case LOSE:
                    setScreen(loseScreen);
                    break;
                case START:
                    options = GameType.UNKNOWN;
                    setScreen(startScreen);
                    break;
                default:
                    System.err.println("ERROR -- TERRORGDXGAME -- RENDER -- UKNOWN CHANGE SCREEN");
                    throw new IllegalArgumentException("ERROR -- TERRORGDXGAME -- UNKNOWN CHANGE SCREEN");

            }

        }
        super.render();
    }

    @Override
    public void dispose() {
        for (AbstractScreen screen:screens) {
        screen.dispose();
        }
    }

}

