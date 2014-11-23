package castlepanic;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class CastleFrame extends javax.swing.JFrame {

    /**
     * Creates new form CastleFrame
     */
    public CastleFrame() {

        initComponents();
        try {
            blueKnight = new javax.swing.ImageIcon(getClass().getResource("/blueKnightMock.png"));
            redKnight = new javax.swing.ImageIcon(getClass().getResource("/redKnightMock.png"));
            greenKnight = new javax.swing.ImageIcon(getClass().getResource("/greenKnightMock.png"));
            blueArcher = new javax.swing.ImageIcon(getClass().getResource("/blueArcherMock.png"));
            redArcher = new javax.swing.ImageIcon(getClass().getResource("/redArcherMock.png"));
            greenArcher = new javax.swing.ImageIcon(getClass().getResource("/greenArcherMock.png"));
            blueHero = new javax.swing.ImageIcon(getClass().getResource("/blueHeroMock.png"));
            redHero = new javax.swing.ImageIcon(getClass().getResource("/redHeroMock.png"));
            greenHero = new javax.swing.ImageIcon(getClass().getResource("/greenHeroMock.png"));
            blueSwords = new javax.swing.ImageIcon(getClass().getResource("/blueSwordsMock.png"));
            redSwords = new javax.swing.ImageIcon(getClass().getResource("/redSwordsMock.png"));
            greenSwords = new javax.swing.ImageIcon(getClass().getResource("/greenSwordsMock.png"));
            anyArcher = new javax.swing.ImageIcon(getClass().getResource("/anyArcherMock.png"));
            anyKnight = new javax.swing.ImageIcon(getClass().getResource("/anyKnightMock.png"));
            anySwords = new javax.swing.ImageIcon(getClass().getResource("/anySwordsMock.png"));
            anyHero = new javax.swing.ImageIcon(getClass().getResource("/anyHeroMock.png"));
            blankCard = new javax.swing.ImageIcon(getClass().getResource("/blankCard.png"));
            turnInd = new javax.swing.ImageIcon(getClass().getResource("/turnSpot.png"));
            wall = new javax.swing.ImageIcon(getClass().getResource("/wall17040.png"));
            deadWall = new javax.swing.ImageIcon(getClass().getResource("/wallRubbleCleared.png"));
            rubbleWall = new javax.swing.ImageIcon(getClass().getResource("/deadWall.png"));
            keep = new javax.swing.ImageIcon(getClass().getResource("/keep17070.png"));
            deadKeep = new javax.swing.ImageIcon(getClass().getResource("/keepRubbleCleared.jpg"));
            rubbleKeep = new javax.swing.ImageIcon(getClass().getResource("/deadKeep.png"));
            missingCard = new javax.swing.ImageIcon(getClass().getResource("/missingCard.png"));
            barbarianCard = new javax.swing.ImageIcon(getClass().getResource("/barbarianCard.png"));
            timeStopCard = new javax.swing.ImageIcon(getClass().getResource("/timeStopCard.png"));
            timeSlapCard = new javax.swing.ImageIcon(getClass().getResource("/timeSlapCard.png"));
            rewindCard = new javax.swing.ImageIcon(getClass().getResource("/rewindCard.png"));
            turretCard = new javax.swing.ImageIcon(getClass().getResource("/turretCard.png"));
            liveBlip = new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"));
            deadBlip = new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_GRAY.png"));
            activeNow = new javax.swing.ImageIcon(getClass().getResource("/active.png"));
            spectator = new javax.swing.ImageIcon(getClass().getResource("/spectator.png"));
            p2pGame = new javax.swing.ImageIcon(getClass().getResource("/networkGame.png"));
            localGame = new javax.swing.ImageIcon(getClass().getResource("/localGame.png"));

        } catch (NullPointerException e) {
            System.err.println("GUI: You dolt - The card image files are missing!");
        }

        multiDialog = new MultiMenu(this, true);
        dialog = new GameMenu(this, true);
        dialog.setVisible(false);
        userDialog = new UserMenu(this, true);
        userDialog.setVisible(false);
        multiDialog.addWindowListener(new java.awt.event.WindowAdapter() {

            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            }

            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {

                switch (multiDialog.getDecision()) {
                    case ("Single"):

                        networkGame = false;

                        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                            @Override
                            public void windowClosing(java.awt.event.WindowEvent e) {
                                System.exit(0);
                            }

                            @Override
                            public void windowClosed(java.awt.event.WindowEvent e) {
                                if (dialog.getDecision().equals("GRAPHICAL")) {
                                    int playerCount = 0;
                                    int index = 0;
                                    for (String name : dialog.getPlayerNames()) {
                                        if (!name.isEmpty()) {
                                            playerCount++;
                                        }
                                        players = new String[playerCount];
                                    }
                                    for (String name : dialog.getPlayerNames()) {
                                        if (!name.isEmpty()) {
                                            players[index] = name;
                                            index++;
                                        }
                                    }
                                    System.out.println("GUI: players String [] set.");
                                    for (String name : players) {
                                        System.out.println("GUI: Player: - " + name);
                                    }

                                    gameState = new GameState(players);

                                    handLabels = new javax.swing.JLabel[]{handLabel0, handLabel1, handLabel2,
                                        handLabel3, handLabel4, handLabel5};
                                    handPanels = new javax.swing.JPanel[]{handPanel0, handPanel1, handPanel2,
                                        handPanel3, handPanel4, handPanel5};
                                    cardButtons = new javax.swing.JButton[gameState.getPlayers()][gameState.getMaxHandSize()];
                                    wallButtons = new javax.swing.JButton[]{wallButton0, wallButton1, wallButton2, wallButton3,
                                        wallButton4, wallButton5};
                                    phaseButtons = new javax.swing.JButton[]{phaseButton1, phaseButton2, phaseButton3, phaseButton4};
                                    skipButtons = new javax.swing.JButton[]{skipButton1, skipButton2, skipButton3};
                                    castleButtons = new javax.swing.JButton[]{castleButton0, castleButton1, castleButton2,
                                        castleButton3, castleButton4, castleButton5};
                                    currentPlayerLabels = new javax.swing.JLabel[]{handTurnLabel0, handTurnLabel1, handTurnLabel2,
                                        handTurnLabel3, handTurnLabel4, handTurnLabel5};
                                    scoreLabels = new javax.swing.JLabel[]{handPointsLabel0, handPointsLabel1, handPointsLabel2, handPointsLabel3,
                                        handPointsLabel4, handPointsLabel5};
                                    blipLabels = new javax.swing.JLabel[]{blipLabel0, blipLabel1, blipLabel2, blipLabel3, blipLabel4, blipLabel5, blipLabel6, blipLabel7,
                                        blipLabel8, blipLabel9, blipLabel10, blipLabel11, blipLabel12, blipLabel13, blipLabel14, blipLabel15, blipLabel16,
                                        blipLabel17, blipLabel18, blipLabel19, blipLabel20, blipLabel21, blipLabel22, blipLabel23, blipLabel24, blipLabel25, blipLabel26,
                                        blipLabel27, blipLabel28, blipLabel29, blipLabel30, blipLabel31, blipLabel32, blipLabel33, blipLabel34, blipLabel35, blipLabel36,
                                        blipLabel37, blipLabel38, blipLabel39, blipLabel40, blipLabel41, blipLabel42, blipLabel43, blipLabel44, blipLabel45, blipLabel46,
                                        blipLabel47};
                                    initializeGame();
                                    updateGame();
                                }
                            }
                        });
                        dialog.setVisible(true);
                        if (dialog.getDecision().equals("TEXT")) {
                            TextUI text = new TextUI();
                        }
                        break;
                    case ("Multi"):
                        networkGame = true;
                        userDialog.setHosting(multiDialog.getHosting());
                        userDialog.addWindowListener(new java.awt.event.WindowAdapter() {
                            //////////////////////////////////////////////////////////////////

                            @Override
                            public void windowClosing(java.awt.event.WindowEvent e) {
                                System.exit(0);
                            }

                            @Override
                            public void windowClosed(java.awt.event.WindowEvent e) {

                                int playerCount = 0;

                                String name = userDialog.getUsername();
                                String port = userDialog.getPort();
                                net = new NetworkHandler();

                                switch (multiDialog.getHosting()) {
                                    case "CLIENT":
                                        isHost = false;
                                        isActive = false;
                                        String address = userDialog.getAddress();
                                        net.setLocalActive(isActive);
                                        net.connectToHost(address, port);
                                        break;
                                    case "HOST":
                                        isHost = true;
                                        isActive = true;
                                        net.setLocalActive(isActive);
                                        net.openHosting(port);
                                        break;
                                }

                                playerCount++;
                                players = new String[playerCount];
                                players[0] = name;

                                if (isHost) {
                                    String[] clientNames = net.listenForNames();
                                    //LISTENING FOR CLIENT NAMES SO THAT THEY CAN BE COMBINED
                                    for (String current : clientNames) {
                                        System.out.println("Name Recieved");
                                        System.out.println(current);
                                    }
                                    //PUTTING THE NAMES TOGETHER
                                    String localName = players[0];
                                    players = new String[2];
                                    players[0] = localName;
                                    players[1] = clientNames[0];
                                    //SEND THE NEW NAME LISTBACK
                                    net.transmitNames(players);

                                    players[0] = players[0] + " (HOST)";
                                    gameState = new GameState(players);
                                    System.out.println("ATTEMPTING TO SYNCHRONIZE STATES. . . ");
                                    net.transmitGameState(gameState);

                                } else {
                                    //SEND LOCAL NAME
                                    net.transmitNames(players);
                                    //RECEIVE COMBINED NAME ARRAY
                                    players = net.listenForNames();
                                    //LISTEN FOR FINAL GAMESTATE
                                    players[1] = players[1] + " (CLIENT)";
                                    gameState = net.listenForState();
                                }

                                handLabels = new javax.swing.JLabel[]{handLabel0, handLabel1, handLabel2,
                                    handLabel3, handLabel4, handLabel5};
                                handPanels = new javax.swing.JPanel[]{handPanel0, handPanel1, handPanel2,
                                    handPanel3, handPanel4, handPanel5};
                                cardButtons = new javax.swing.JButton[gameState.getPlayers()][gameState.getMaxHandSize()];
                                wallButtons = new javax.swing.JButton[]{wallButton0, wallButton1, wallButton2, wallButton3,
                                    wallButton5, wallButton4};
                                phaseButtons = new javax.swing.JButton[]{phaseButton1, phaseButton2, phaseButton3, phaseButton4};
                                skipButtons = new javax.swing.JButton[]{skipButton1, skipButton2, skipButton3};
                                castleButtons = new javax.swing.JButton[]{castleButton0, castleButton1, castleButton2,
                                    castleButton3, castleButton4, castleButton5};
                                currentPlayerLabels = new javax.swing.JLabel[]{handTurnLabel0, handTurnLabel1, handTurnLabel2,
                                    handTurnLabel3, handTurnLabel4, handTurnLabel5};
                                scoreLabels = new javax.swing.JLabel[]{handPointsLabel0, handPointsLabel1, handPointsLabel2, handPointsLabel3,
                                    handPointsLabel4, handPointsLabel5};
                                blipLabels = new javax.swing.JLabel[]{blipLabel0, blipLabel1, blipLabel2, blipLabel3, blipLabel4, blipLabel5, blipLabel6, blipLabel7,
                                    blipLabel8, blipLabel9, blipLabel10, blipLabel11, blipLabel12, blipLabel13, blipLabel14, blipLabel15, blipLabel16,
                                    blipLabel17, blipLabel18, blipLabel19, blipLabel20, blipLabel21, blipLabel22, blipLabel23, blipLabel24, blipLabel25, blipLabel26,
                                    blipLabel27, blipLabel28, blipLabel29, blipLabel30, blipLabel31, blipLabel32, blipLabel33, blipLabel34, blipLabel35, blipLabel36,
                                    blipLabel37, blipLabel38, blipLabel39, blipLabel40, blipLabel41, blipLabel42, blipLabel43, blipLabel44, blipLabel45, blipLabel46,
                                    blipLabel47};
                                initializeGame();
                                updateGame();
                            }
                        });
                        userDialog.setVisible(true);
                        break;
                }
            }
        });
        multiDialog.setVisible(true);
    }

    public void initializeGame() {

        for (int i = 0; i < cardButtons.length; i++) {
            java.awt.Component[] comps = handPanels[i].getComponents();

            for (int j = 0; j < cardButtons[i].length; j++) {
                if (comps[j] instanceof javax.swing.JButton) {
                    cardButtons[i][j] = (javax.swing.JButton) comps[j];
                }
            }
        }
        for (int k = 0; k < cardButtons.length; k++) {
            for (int l = 0; l < cardButtons[k].length; l++) {
                System.out.println("Card Button:" + " " + cardButtons[k][l]);
            }
        }

        for (int m = 0; m < players.length; m++) {
            handLabels[m].setText(players[m].substring(0, Math.min(8, players[m].length())).trim());
        }
        //TODO: OLD PROGBAR - REMOVE?
        //monsterProgBar.setMaximum(gameState.getUnplayedMonsters());

        this.updateGame();

        if (networkGame) {
            phaseTitleLabel1.setIcon(p2pGame);
            if (!isHost) {
                netProcess();
            } else {
                phaseTitleLabel2.setIcon(activeNow);
            }
        } else {

        }

    }

    /*
     Builds and executes a new instance of a Swing worker thread. That will listen for new gameStates until the end of a turn.
     */
    public void buildSpectateThread() {
        /*WORKER THREAD DRAW ALTERNATIVE
         ///////////////////////////////////////////////////////////////////////*/
        System.out.println("(NET)[SPECTATOR THREAD SPUN]");
        SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                //deact buttons when not active
                for (JButton button : phaseButtons) {
                    button.setEnabled(net.isLocalActive());
                }
                for (JButton button : skipButtons) {
                    button.setEnabled(net.isLocalActive());
                }
                //print not active to network label
                phaseTitleLabel2.setIcon(spectator);
                System.out.println("(NET) BEGINNING SPECTATING . . .");
                while (!net.isLocalActive()) {
                    gameState = net.listenForState();
                    updateGame();
                    revalidate();
                    paintComponents(getGraphics());
                    net.updateLocalActive(gameState);
                }
                //act buttons when active
                for (JButton button : phaseButtons) {
                    button.setEnabled(net.isLocalActive());
                }
                for (JButton button : skipButtons) {
                    button.setEnabled(net.isLocalActive());
                }
                phaseTitleLabel2.setIcon(activeNow);
                System.out.println("(NET) ENDING SPECTATING . . .");
                return true;
            }
        };
        worker.execute();
    }

    public void updateGame() {
        this.setVisible(true);
        if (gameState.getDeadYet()) {
            System.out.println("GUI: End game");
            endScreen = new ItsCurtains(this, true);
            endScreen.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            endScreen.setVisible(true);
        }
        updateCards();
        updateBoard();
        updateMisc();

        if (networkGame) {
            if (net.isLocalActive()) {
                System.out.println("FIRING NET PROCESS");
                netProcess();
            }
        }
    }

    public void netProcess() {
        if (net.isLocalActive()) {
            revalidate();
            net.transmitGameState(gameState);
            net.updateLocalActive(gameState);
        }

        if (!net.isLocalActive()) {
            updateGame();
            revalidate();
            buildSpectateThread();
        }

    }

    public void updateCards() {
        for (int j = 0; j < gameState.getPlayers(); j++) {

            for (int k = 0; k < gameState.getNumCards(j); k++) {
                cardButtons[j][k].setBorderPainted(false);
                switch (gameState.getCardNameFromPlayerHand(j, k)) {
                    case ("Hit Card: knight Color: Blue"):
                        cardButtons[j][k].setIcon(blueKnight);
                        System.out.println("GUI: Assigned BK to " + j + k);
                        break;
                    case ("Hit Card: knight Color: Green"):
                        cardButtons[j][k].setIcon(greenKnight);
                        System.out.println("GUI: Assigned GK to " + j + k);
                        break;
                    case ("Hit Card: knight Color: Red"):
                        cardButtons[j][k].setIcon(redKnight);
                        System.out.println("GUI: Assigned RK to " + j + k);
                        break;
                    case ("Hit Card: archer Color: Blue"):
                        cardButtons[j][k].setIcon(blueArcher);
                        System.out.println("GUI: Assigned BA to " + j + k);
                        break;
                    case ("Hit Card: archer Color: Green"):
                        cardButtons[j][k].setIcon(greenArcher);
                        System.out.println("GUI: Assigned GA to " + j + k);
                        break;
                    case ("Hit Card: archer Color: Red"):
                        cardButtons[j][k].setIcon(redArcher);
                        System.out.println("GUI: Assigned RA to " + j + k);
                        break;
                    case ("Hit Card: swordsman Color: Blue"):
                        cardButtons[j][k].setIcon(blueSwords);
                        System.out.println("GUI: Assigned BS to " + j + k);
                        break;
                    case ("Hit Card: swordsman Color: Green"):
                        cardButtons[j][k].setIcon(greenSwords);
                        System.out.println("GUI: Assigned GS to " + j + k);
                        break;
                    case ("Hit Card: swordsman Color: Red"):
                        cardButtons[j][k].setIcon(redSwords);
                        System.out.println("GUI: Assigned RS to " + j + k);
                        break;
                    case ("Hit Card: hero Color: Blue"):
                        cardButtons[j][k].setIcon(blueHero);
                        System.out.println("GUI: Assigned BH to " + j + k);
                        break;
                    case ("Hit Card: hero Color: Green"):
                        cardButtons[j][k].setIcon(greenHero);
                        System.out.println("GUI: Assigned GH to " + j + k);
                        break;
                    case ("Hit Card: hero Color: Red"):
                        cardButtons[j][k].setIcon(redHero);
                        System.out.println("GUI: Assigned RK to " + j + k);
                        break;
                    case ("Hit Card: archer Color: Any"):
                        cardButtons[j][k].setIcon(anyArcher);
                        System.out.println("GUI: Assigned AA to " + j + k);
                        break;
                    case ("Hit Card: knight Color: Any"):
                        cardButtons[j][k].setIcon(anyKnight);
                        System.out.println("GUI: Assigned AK to " + j + k);
                        break;
                    case ("Hit Card: swordsman Color: Any"):
                        cardButtons[j][k].setIcon(anySwords);
                        System.out.println("GUI: Assigned AS to " + j + k);
                        break;
                    case ("Hit Card: hero Color: Any"):
                        cardButtons[j][k].setIcon(anyHero);
                        System.out.println("GUI: Assigned AH to " + j + k);
                        break;
                    case ("Card: missing"):
                        cardButtons[j][k].setIcon(missingCard);
                        break;
                    case ("Card: barbarian"):
                        cardButtons[j][k].setIcon(barbarianCard);
                        break;
                    case ("Card: time slap"):
                        cardButtons[j][k].setIcon(timeSlapCard);
                        break;
                    case ("Card: time stop"):
                        cardButtons[j][k].setIcon(timeStopCard);
                        break;
                    case ("Card: rewind"):
                        cardButtons[j][k].setIcon(rewindCard);
                        break;
                    case ("Card: turret"):
                        cardButtons[j][k].setIcon(turretCard);
                        break;
                    default:
                        System.err.println("GUI: Problem initalizing hands - card type: " + gameState.getCardNameFromPlayerHand(j, k) + " was not recognized");
                }
            }
            for (int l = gameState.getTopOfHand(j) + 1; l < gameState.getHandSize(j); l++) {
                cardButtons[j][l].setIcon(blankCard);
            }
        }
        try {
            selectedCard.setBorderPainted(true);
            otherCard.setBorderPainted(true);
        } catch (NullPointerException e) {
            System.out.println("GUI: Current player card or other player card not selected yet");
        }
        this.repaint();
    }

    public void updateBoard() {
        for (int i = 0; i < wallButtons.length; i++) {
            switch (gameState.getWallHP(i + 1)) {

                case 0:
                    wallButtons[i].setIcon(deadWall);
                    break;
                case 1:
                    wallButtons[i].setIcon(wall);
                    break;
                case 2:
                    //add reinforced wall code here
                    break;
                case 5:
                    wallButtons[i].setIcon(rubbleWall);
                    break;
            }
            switch (gameState.getTowerStatus(i + 1)) { //FIX
                case 1:
                    castleButtons[i].setIcon(keep);
                    break;
                case 0:
                    castleButtons[i].setIcon(deadKeep);
                    break;
                case -1:
                    castleButtons[i].setIcon(rubbleKeep);
                    break;
            }
        }
        boardShapeLayer.updateMonsters();
        this.repaint();
    }

    public void updateMisc() {

        //TODO OLD PROGRESS BAR - REMOVE?
        /*   monsterProgBar.setValue(gameState.getUnplayedMonsters());
         monsterCountLabel.setText(String.valueOf(gameState.getUnplayedMonsters()) + "/"
         + String.valueOf(monsterProgBar.getMaximum()));*/
        for (javax.swing.JLabel label : currentPlayerLabels) {
            label.setIcon(null);
        }
        currentPlayerLabels[gameState.getCurrentPlayer()].setIcon(turnInd);
        int totalScore = 0;
        for (int i = 0; i < gameState.getPlayers(); i++) {
            scoreLabels[i].setText(String.valueOf(gameState.getScore(i)));
            totalScore += gameState.getScore(i);
        }

        for (int i = gameState.getNumMonsterTokens() - 1; i > gameState.getNumMonsterTokens() - 1 - gameState.getMonstersKilled(); i--) {
            blipLabels[i].setIcon(deadBlip);
        }

        switch (gameState.getCurrentPhase()) {
            case 1:
                phaseButtons[0].setSelected(true);
                for (int i = 1; i < phaseButtons.length; i++) {
                    phaseButtons[i].setSelected(false);
                }
                for (int i = 1; i < skipButtons.length; i++) {
                    skipButtons[i].setSelected(false);
                }
                break;
            case 2:
                phaseButtons[0].setSelected(false);
                phaseButtons[1].setSelected(true);
                skipButtons[0].setSelected(true);
                break;
            case 3:
                phaseButtons[1].setSelected(false);
                phaseButtons[2].setSelected(true);
                skipButtons[0].setSelected(false);
                skipButtons[1].setSelected(true);
                break;
            case 4:
                skipButtons[1].setSelected(false);
                skipButtons[2].setSelected(true);
                phaseButtons[2].setSelected(false);
                phaseButtons[3].setSelected(true);

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        phasePanel = new javax.swing.JPanel();
        phaseTitleLabel = new javax.swing.JLabel();
        phaseButton1 = new javax.swing.JButton();
        phaseButton2 = new javax.swing.JButton();
        phaseButton3 = new javax.swing.JButton();
        phaseButton4 = new javax.swing.JButton();
        skipButton1 = new javax.swing.JButton();
        skipButton2 = new javax.swing.JButton();
        skipButton3 = new javax.swing.JButton();
        phaseTitleLabel1 = new javax.swing.JLabel();
        phaseTitleLabel2 = new javax.swing.JLabel();
        boardLayeredPanel = new javax.swing.JLayeredPane();
        boardPaletteLayer = new javax.swing.JPanel();
        boardButton04 = new javax.swing.JButton();
        boardButton03 = new javax.swing.JButton();
        boardButton02 = new javax.swing.JButton();
        boardButton01 = new javax.swing.JButton();
        boardButton24 = new javax.swing.JButton();
        boardButton23 = new javax.swing.JButton();
        boardButton32 = new javax.swing.JButton();
        boardButton31 = new javax.swing.JButton();
        boardButton34 = new javax.swing.JButton();
        boardButton33 = new javax.swing.JButton();
        boardButton22 = new javax.swing.JButton();
        boardButton21 = new javax.swing.JButton();
        boardButton14 = new javax.swing.JButton();
        boardButton13 = new javax.swing.JButton();
        boardButton12 = new javax.swing.JButton();
        boardButton11 = new javax.swing.JButton();
        boardButton44 = new javax.swing.JButton();
        boardButton43 = new javax.swing.JButton();
        boardButton42 = new javax.swing.JButton();
        boardButton41 = new javax.swing.JButton();
        boardButton51 = new javax.swing.JButton();
        boardButton52 = new javax.swing.JButton();
        boardButton53 = new javax.swing.JButton();
        boardButton54 = new javax.swing.JButton();
        wallButton0 = new javax.swing.JButton();
        castleButton0 = new javax.swing.JButton();
        wallButton1 = new javax.swing.JButton();
        castleButton1 = new javax.swing.JButton();
        wallButton2 = new javax.swing.JButton();
        castleButton2 = new javax.swing.JButton();
        wallButton3 = new javax.swing.JButton();
        castleButton3 = new javax.swing.JButton();
        wallButton4 = new javax.swing.JButton();
        wallButton5 = new javax.swing.JButton();
        castleButton5 = new javax.swing.JButton();
        castleButton4 = new javax.swing.JButton();
        boardShapeLayer = new ShapePanel();
        handPanel = new javax.swing.JPanel();
        handPanel5 = new javax.swing.JPanel();
        cardButton50 = new javax.swing.JButton();
        cardButton51 = new javax.swing.JButton();
        cardButton52 = new javax.swing.JButton();
        cardButton53 = new javax.swing.JButton();
        cardButton54 = new javax.swing.JButton();
        cardButton55 = new javax.swing.JButton();
        handTurnLabel5 = new javax.swing.JLabel();
        handLabel5 = new javax.swing.JLabel();
        handScoreLabel5 = new javax.swing.JLabel();
        handPointsLabel5 = new javax.swing.JLabel();
        handPanel1 = new javax.swing.JPanel();
        cardButton10 = new javax.swing.JButton();
        cardButton11 = new javax.swing.JButton();
        cardButton12 = new javax.swing.JButton();
        cardButton13 = new javax.swing.JButton();
        cardButton14 = new javax.swing.JButton();
        cardButton15 = new javax.swing.JButton();
        handTurnLabel1 = new javax.swing.JLabel();
        handLabel1 = new javax.swing.JLabel();
        handScoreLabel1 = new javax.swing.JLabel();
        handPointsLabel1 = new javax.swing.JLabel();
        handPanel2 = new javax.swing.JPanel();
        cardButton20 = new javax.swing.JButton();
        cardButton21 = new javax.swing.JButton();
        cardButton22 = new javax.swing.JButton();
        cardButton23 = new javax.swing.JButton();
        cardButton24 = new javax.swing.JButton();
        cardButton25 = new javax.swing.JButton();
        handLabel2 = new javax.swing.JLabel();
        handTurnLabel2 = new javax.swing.JLabel();
        handScoreLabel2 = new javax.swing.JLabel();
        handPointsLabel2 = new javax.swing.JLabel();
        handPanel4 = new javax.swing.JPanel();
        cardButton40 = new javax.swing.JButton();
        cardButton41 = new javax.swing.JButton();
        cardButton42 = new javax.swing.JButton();
        cardButton43 = new javax.swing.JButton();
        cardButton44 = new javax.swing.JButton();
        cardButton45 = new javax.swing.JButton();
        handTurnLabel4 = new javax.swing.JLabel();
        handLabel4 = new javax.swing.JLabel();
        handScoreLabel4 = new javax.swing.JLabel();
        handPointsLabel4 = new javax.swing.JLabel();
        handPanel0 = new javax.swing.JPanel();
        cardButton00 = new javax.swing.JButton();
        cardbutton01 = new javax.swing.JButton();
        cardButton02 = new javax.swing.JButton();
        cardbutton03 = new javax.swing.JButton();
        cardButton04 = new javax.swing.JButton();
        cardButton05 = new javax.swing.JButton();
        handLabel0 = new javax.swing.JLabel();
        handTurnLabel0 = new javax.swing.JLabel();
        handScoreLabel0 = new javax.swing.JLabel();
        handPointsLabel0 = new javax.swing.JLabel();
        handPanel3 = new javax.swing.JPanel();
        cardButton30 = new javax.swing.JButton();
        cardButton31 = new javax.swing.JButton();
        cardButton32 = new javax.swing.JButton();
        cardButton33 = new javax.swing.JButton();
        cardButton34 = new javax.swing.JButton();
        cardButton35 = new javax.swing.JButton();
        handLabel3 = new javax.swing.JLabel();
        handTurnLabel3 = new javax.swing.JLabel();
        handScoreLabel3 = new javax.swing.JLabel();
        handPointsLabel3 = new javax.swing.JLabel();
        controlPanel = new javax.swing.JPanel();
        monsterProgLabel = new javax.swing.JLabel();
        blipLabel0 = new javax.swing.JLabel();
        blipLabel1 = new javax.swing.JLabel();
        blipLabel2 = new javax.swing.JLabel();
        blipLabel3 = new javax.swing.JLabel();
        blipLabel4 = new javax.swing.JLabel();
        blipLabel5 = new javax.swing.JLabel();
        blipLabel6 = new javax.swing.JLabel();
        blipLabel7 = new javax.swing.JLabel();
        blipLabel8 = new javax.swing.JLabel();
        blipLabel9 = new javax.swing.JLabel();
        blipLabel10 = new javax.swing.JLabel();
        blipLabel11 = new javax.swing.JLabel();
        blipLabel12 = new javax.swing.JLabel();
        blipLabel13 = new javax.swing.JLabel();
        blipLabel14 = new javax.swing.JLabel();
        blipLabel15 = new javax.swing.JLabel();
        blipLabel16 = new javax.swing.JLabel();
        blipLabel17 = new javax.swing.JLabel();
        blipLabel18 = new javax.swing.JLabel();
        blipLabel19 = new javax.swing.JLabel();
        blipLabel20 = new javax.swing.JLabel();
        blipLabel21 = new javax.swing.JLabel();
        blipLabel22 = new javax.swing.JLabel();
        blipLabel23 = new javax.swing.JLabel();
        blipLabel24 = new javax.swing.JLabel();
        blipLabel25 = new javax.swing.JLabel();
        blipLabel26 = new javax.swing.JLabel();
        blipLabel27 = new javax.swing.JLabel();
        blipLabel28 = new javax.swing.JLabel();
        blipLabel29 = new javax.swing.JLabel();
        blipLabel30 = new javax.swing.JLabel();
        blipLabel31 = new javax.swing.JLabel();
        blipLabel32 = new javax.swing.JLabel();
        blipLabel33 = new javax.swing.JLabel();
        blipLabel34 = new javax.swing.JLabel();
        blipLabel35 = new javax.swing.JLabel();
        blipLabel36 = new javax.swing.JLabel();
        blipLabel37 = new javax.swing.JLabel();
        blipLabel38 = new javax.swing.JLabel();
        blipLabel39 = new javax.swing.JLabel();
        blipLabel40 = new javax.swing.JLabel();
        blipLabel41 = new javax.swing.JLabel();
        blipLabel42 = new javax.swing.JLabel();
        blipLabel43 = new javax.swing.JLabel();
        blipLabel44 = new javax.swing.JLabel();
        blipLabel45 = new javax.swing.JLabel();
        blipLabel46 = new javax.swing.JLabel();
        blipLabel47 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 255, 255));
        setMinimumSize(new java.awt.Dimension(1230, 860));
        setPreferredSize(new java.awt.Dimension(1230, 860));

        phasePanel.setFocusable(false);
        phasePanel.setOpaque(false);

        phaseTitleLabel.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 12)); // NOI18N
        phaseTitleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        phaseTitleLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/orderOfTurn.png"))); // NOI18N
        phaseTitleLabel.setAlignmentX(0.5F);
        phaseTitleLabel.setAutoscrolls(true);
        phaseTitleLabel.setMaximumSize(new java.awt.Dimension(283, 57));
        phaseTitleLabel.setMinimumSize(new java.awt.Dimension(283, 57));
        phaseTitleLabel.setPreferredSize(new java.awt.Dimension(283, 57));

        phaseButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawTurn.png"))); // NOI18N
        phaseButton1.setActionCommand("Draw");
        phaseButton1.setBorderPainted(false);
        phaseButton1.setMaximumSize(new java.awt.Dimension(283, 81));
        phaseButton1.setMinimumSize(new java.awt.Dimension(283, 81));
        phaseButton1.setPreferredSize(new java.awt.Dimension(283, 81));
        phaseButton1.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/drawTurn_rollOver.png"))); // NOI18N
        phaseButton1.setSelected(true);
        phaseButton1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/drawTurn_highlight.png"))); // NOI18N
        phaseButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dealClicked(evt);
            }
        });

        phaseButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/discardTurn.png"))); // NOI18N
        phaseButton2.setBorderPainted(false);
        phaseButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        phaseButton2.setMaximumSize(new java.awt.Dimension(253, 81));
        phaseButton2.setMinimumSize(new java.awt.Dimension(253, 81));
        phaseButton2.setPreferredSize(new java.awt.Dimension(253, 81));
        phaseButton2.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/discardTurn_rollOver.png"))); // NOI18N
        phaseButton2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/discardTurn_highlight.png"))); // NOI18N
        phaseButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tradeDealerClicked(evt);
            }
        });

        phaseButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeTurn.png"))); // NOI18N
        phaseButton3.setActionCommand("tradeTo");
        phaseButton3.setBorderPainted(false);
        phaseButton3.setMaximumSize(new java.awt.Dimension(253, 81));
        phaseButton3.setMinimumSize(new java.awt.Dimension(253, 81));
        phaseButton3.setPreferredSize(new java.awt.Dimension(253, 81));
        phaseButton3.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeTurn_rollOver.png"))); // NOI18N
        phaseButton3.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeTurn_highlight.png"))); // NOI18N
        phaseButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tradePlayerClicked(evt);
            }
        });

        phaseButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/attack_COMBO.png"))); // NOI18N
        phaseButton4.setActionCommand("attack");
        phaseButton4.setBorderPainted(false);
        phaseButton4.setMaximumSize(new java.awt.Dimension(211, 81));
        phaseButton4.setMinimumSize(new java.awt.Dimension(211, 81));
        phaseButton4.setPreferredSize(new java.awt.Dimension(211, 81));
        phaseButton4.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/attack_COMBO_rollOver.png"))); // NOI18N
        phaseButton4.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/attack_COMBO_highlight.png"))); // NOI18N
        phaseButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                attackClicked(evt);
            }
        });

        skipButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/orderOfTurn_SKIP.png"))); // NOI18N
        skipButton1.setAlignmentY(0.0F);
        skipButton1.setBorderPainted(false);
        skipButton1.setFocusPainted(false);
        skipButton1.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        skipButton1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        skipButton1.setMaximumSize(new java.awt.Dimension(30, 81));
        skipButton1.setMinimumSize(new java.awt.Dimension(30, 81));
        skipButton1.setPreferredSize(new java.awt.Dimension(30, 81));
        skipButton1.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/orderOfTurn_SKIP_rollOver.png"))); // NOI18N
        skipButton1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/orderOfTurn_SKIP_highlight.png"))); // NOI18N
        skipButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                skipButton1Clicked(evt);
            }
        });

        skipButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/orderOfTurn_SKIP.png"))); // NOI18N
        skipButton2.setAlignmentY(0.0F);
        skipButton2.setBorderPainted(false);
        skipButton2.setFocusPainted(false);
        skipButton2.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        skipButton2.setMaximumSize(new java.awt.Dimension(30, 81));
        skipButton2.setMinimumSize(new java.awt.Dimension(30, 81));
        skipButton2.setPreferredSize(new java.awt.Dimension(30, 81));
        skipButton2.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/orderOfTurn_SKIP_rollOver.png"))); // NOI18N
        skipButton2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/orderOfTurn_SKIP_highlight.png"))); // NOI18N
        skipButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                skipButton2Clicked(evt);
            }
        });

        skipButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/endTurn_COMBO.png"))); // NOI18N
        skipButton3.setAlignmentY(0.0F);
        skipButton3.setBorderPainted(false);
        skipButton3.setFocusPainted(false);
        skipButton3.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        skipButton3.setMaximumSize(new java.awt.Dimension(72, 81));
        skipButton3.setMinimumSize(new java.awt.Dimension(72, 81));
        skipButton3.setPreferredSize(new java.awt.Dimension(72, 81));
        skipButton3.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/endTurn_COMBO_rollOver.png"))); // NOI18N
        skipButton3.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/endTurn_COMBO_highlight.png"))); // NOI18N
        skipButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                skipButton3Clicked(evt);
            }
        });

        phaseTitleLabel1.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 12)); // NOI18N
        phaseTitleLabel1.setForeground(new java.awt.Color(255, 0, 0));
        phaseTitleLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        phaseTitleLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/localGame.png"))); // NOI18N
        phaseTitleLabel1.setAlignmentX(0.5F);
        phaseTitleLabel1.setAutoscrolls(true);
        phaseTitleLabel1.setFocusable(false);

        phaseTitleLabel2.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 12)); // NOI18N
        phaseTitleLabel2.setForeground(new java.awt.Color(255, 0, 0));
        phaseTitleLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        phaseTitleLabel2.setAlignmentX(0.5F);
        phaseTitleLabel2.setAutoscrolls(true);
        phaseTitleLabel2.setFocusable(false);
        phaseTitleLabel2.setMaximumSize(new java.awt.Dimension(161, 33));
        phaseTitleLabel2.setMinimumSize(new java.awt.Dimension(161, 33));
        phaseTitleLabel2.setPreferredSize(new java.awt.Dimension(161, 33));

        javax.swing.GroupLayout phasePanelLayout = new javax.swing.GroupLayout(phasePanel);
        phasePanel.setLayout(phasePanelLayout);
        phasePanelLayout.setHorizontalGroup(
            phasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(phasePanelLayout.createSequentialGroup()
                .addGroup(phasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(phaseButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(phasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(phaseButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(phaseTitleLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, phasePanelLayout.createSequentialGroup()
                            .addComponent(phaseButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, 0)
                            .addGroup(phasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(skipButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(skipButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, phasePanelLayout.createSequentialGroup()
                            .addGroup(phasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(phasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(phaseTitleLabel1)
                                    .addComponent(phaseTitleLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(phaseButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addComponent(skipButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        phasePanelLayout.setVerticalGroup(
            phasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(phasePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(phaseTitleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(phaseButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(phasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(phaseButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(skipButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(phasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(phaseButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(skipButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(phasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(phaseButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(skipButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(phaseTitleLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(phaseTitleLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        boardLayeredPanel.setPreferredSize(new java.awt.Dimension(906, 500));

        boardPaletteLayer.setMinimumSize(new java.awt.Dimension(906, 444));
        boardPaletteLayer.setOpaque(false);
        boardPaletteLayer.setPreferredSize(new java.awt.Dimension(906, 500));

        boardButton04.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttonForestRed.png"))); // NOI18N
        boardButton04.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.red, null));
        boardButton04.setBorderPainted(false);
        boardButton04.setContentAreaFilled(false);
        boardButton04.setFocusPainted(false);
        boardButton04.setMaximumSize(new java.awt.Dimension(149, 90));
        boardButton04.setMinimumSize(new java.awt.Dimension(149, 90));
        boardButton04.setPreferredSize(new java.awt.Dimension(149, 90));
        boardButton04.setRequestFocusEnabled(false);
        boardButton04.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton04boardButtonClicked(evt);
            }
        });

        boardButton03.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttonArcherRed.png"))); // NOI18N
        boardButton03.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.red, null));
        boardButton03.setBorderPainted(false);
        boardButton03.setContentAreaFilled(false);
        boardButton03.setFocusPainted(false);
        boardButton03.setMaximumSize(new java.awt.Dimension(149, 90));
        boardButton03.setMinimumSize(new java.awt.Dimension(149, 90));
        boardButton03.setPreferredSize(new java.awt.Dimension(149, 90));
        boardButton03.setRequestFocusEnabled(false);
        boardButton03.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton03boardButtonClicked(evt);
            }
        });

        boardButton02.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttonKnightRed.png"))); // NOI18N
        boardButton02.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.red, null));
        boardButton02.setBorderPainted(false);
        boardButton02.setContentAreaFilled(false);
        boardButton02.setFocusPainted(false);
        boardButton02.setMaximumSize(new java.awt.Dimension(149, 90));
        boardButton02.setMinimumSize(new java.awt.Dimension(149, 90));
        boardButton02.setPreferredSize(new java.awt.Dimension(149, 90));
        boardButton02.setRequestFocusEnabled(false);
        boardButton02.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton02boardButtonClicked(evt);
            }
        });

        boardButton01.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttonSwordsmanRed.png"))); // NOI18N
        boardButton01.setAutoscrolls(true);
        boardButton01.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.red, null));
        boardButton01.setBorderPainted(false);
        boardButton01.setContentAreaFilled(false);
        boardButton01.setFocusPainted(false);
        boardButton01.setMaximumSize(new java.awt.Dimension(149, 90));
        boardButton01.setMinimumSize(new java.awt.Dimension(149, 90));
        boardButton01.setPreferredSize(new java.awt.Dimension(149, 90));
        boardButton01.setRequestFocusEnabled(false);
        boardButton01.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton01boardButtonClicked(evt);
            }
        });

        boardButton24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttonForestGreen.png"))); // NOI18N
        boardButton24.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.green, null));
        boardButton24.setBorderPainted(false);
        boardButton24.setContentAreaFilled(false);
        boardButton24.setMaximumSize(new java.awt.Dimension(149, 90));
        boardButton24.setMinimumSize(new java.awt.Dimension(149, 90));
        boardButton24.setPreferredSize(new java.awt.Dimension(149, 90));
        boardButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton24boardButtonClicked(evt);
            }
        });

        boardButton23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttonArcherGreen.png"))); // NOI18N
        boardButton23.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.green, null));
        boardButton23.setBorderPainted(false);
        boardButton23.setContentAreaFilled(false);
        boardButton23.setMaximumSize(new java.awt.Dimension(149, 90));
        boardButton23.setMinimumSize(new java.awt.Dimension(149, 90));
        boardButton23.setPreferredSize(new java.awt.Dimension(149, 90));
        boardButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton23boardButtonClicked(evt);
            }
        });

        boardButton32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttonKnightGreen.png"))); // NOI18N
        boardButton32.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.green, null));
        boardButton32.setBorderPainted(false);
        boardButton32.setContentAreaFilled(false);
        boardButton32.setMaximumSize(new java.awt.Dimension(149, 90));
        boardButton32.setMinimumSize(new java.awt.Dimension(149, 90));
        boardButton32.setPreferredSize(new java.awt.Dimension(149, 90));
        boardButton32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton32boardButtonClicked(evt);
            }
        });

        boardButton31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttonSwordsmanGreen.png"))); // NOI18N
        boardButton31.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.green, null));
        boardButton31.setBorderPainted(false);
        boardButton31.setContentAreaFilled(false);
        boardButton31.setMaximumSize(new java.awt.Dimension(149, 90));
        boardButton31.setMinimumSize(new java.awt.Dimension(149, 90));
        boardButton31.setPreferredSize(new java.awt.Dimension(149, 90));
        boardButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton31boardButtonClicked(evt);
            }
        });

        boardButton34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttonForestGreen.png"))); // NOI18N
        boardButton34.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.green, null));
        boardButton34.setBorderPainted(false);
        boardButton34.setContentAreaFilled(false);
        boardButton34.setMaximumSize(new java.awt.Dimension(149, 90));
        boardButton34.setMinimumSize(new java.awt.Dimension(149, 90));
        boardButton34.setPreferredSize(new java.awt.Dimension(149, 90));
        boardButton34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton34boardButtonClicked(evt);
            }
        });

        boardButton33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttonArcherGreen.png"))); // NOI18N
        boardButton33.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.green, null));
        boardButton33.setBorderPainted(false);
        boardButton33.setContentAreaFilled(false);
        boardButton33.setMaximumSize(new java.awt.Dimension(149, 90));
        boardButton33.setMinimumSize(new java.awt.Dimension(149, 90));
        boardButton33.setPreferredSize(new java.awt.Dimension(149, 90));
        boardButton33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton33boardButtonClicked(evt);
            }
        });

        boardButton22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttonKnightGreen.png"))); // NOI18N
        boardButton22.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.green, null));
        boardButton22.setBorderPainted(false);
        boardButton22.setContentAreaFilled(false);
        boardButton22.setMaximumSize(new java.awt.Dimension(149, 90));
        boardButton22.setMinimumSize(new java.awt.Dimension(149, 90));
        boardButton22.setPreferredSize(new java.awt.Dimension(149, 90));
        boardButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton22boardButtonClicked(evt);
            }
        });

        boardButton21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttonSwordsmanGreen.png"))); // NOI18N
        boardButton21.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.green, null));
        boardButton21.setBorderPainted(false);
        boardButton21.setContentAreaFilled(false);
        boardButton21.setMaximumSize(new java.awt.Dimension(149, 90));
        boardButton21.setMinimumSize(new java.awt.Dimension(149, 90));
        boardButton21.setPreferredSize(new java.awt.Dimension(149, 90));
        boardButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton21boardButtonClicked(evt);
            }
        });

        boardButton14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttonForestRed.png"))); // NOI18N
        boardButton14.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.red, null));
        boardButton14.setBorderPainted(false);
        boardButton14.setContentAreaFilled(false);
        boardButton14.setFocusPainted(false);
        boardButton14.setMaximumSize(new java.awt.Dimension(149, 90));
        boardButton14.setMinimumSize(new java.awt.Dimension(149, 90));
        boardButton14.setPreferredSize(new java.awt.Dimension(149, 90));
        boardButton14.setRequestFocusEnabled(false);
        boardButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton14boardButtonClicked(evt);
            }
        });

        boardButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttonArcherRed.png"))); // NOI18N
        boardButton13.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.red, null));
        boardButton13.setBorderPainted(false);
        boardButton13.setContentAreaFilled(false);
        boardButton13.setFocusPainted(false);
        boardButton13.setMaximumSize(new java.awt.Dimension(149, 90));
        boardButton13.setMinimumSize(new java.awt.Dimension(149, 90));
        boardButton13.setPreferredSize(new java.awt.Dimension(149, 90));
        boardButton13.setRequestFocusEnabled(false);
        boardButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton13boardButtonClicked(evt);
            }
        });

        boardButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttonKnightRed.png"))); // NOI18N
        boardButton12.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.red, null));
        boardButton12.setBorderPainted(false);
        boardButton12.setContentAreaFilled(false);
        boardButton12.setFocusPainted(false);
        boardButton12.setMaximumSize(new java.awt.Dimension(149, 90));
        boardButton12.setMinimumSize(new java.awt.Dimension(149, 90));
        boardButton12.setPreferredSize(new java.awt.Dimension(149, 90));
        boardButton12.setRequestFocusEnabled(false);
        boardButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton12boardButtonClicked(evt);
            }
        });

        boardButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttonSwordsmanRed.png"))); // NOI18N
        boardButton11.setAutoscrolls(true);
        boardButton11.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.red, null));
        boardButton11.setBorderPainted(false);
        boardButton11.setContentAreaFilled(false);
        boardButton11.setFocusPainted(false);
        boardButton11.setMaximumSize(new java.awt.Dimension(149, 90));
        boardButton11.setMinimumSize(new java.awt.Dimension(149, 90));
        boardButton11.setPreferredSize(new java.awt.Dimension(149, 90));
        boardButton11.setRequestFocusEnabled(false);
        boardButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton11boardButtonClicked(evt);
            }
        });

        boardButton44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttonForestBlue.png"))); // NOI18N
        boardButton44.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.blue, null));
        boardButton44.setBorderPainted(false);
        boardButton44.setContentAreaFilled(false);
        boardButton44.setFocusPainted(false);
        boardButton44.setFocusable(false);
        boardButton44.setMaximumSize(new java.awt.Dimension(149, 90));
        boardButton44.setMinimumSize(new java.awt.Dimension(149, 90));
        boardButton44.setPreferredSize(new java.awt.Dimension(149, 90));
        boardButton44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton44boardButtonClicked(evt);
            }
        });

        boardButton43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttonArcherBlue.png"))); // NOI18N
        boardButton43.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.blue, null));
        boardButton43.setBorderPainted(false);
        boardButton43.setContentAreaFilled(false);
        boardButton43.setFocusPainted(false);
        boardButton43.setFocusable(false);
        boardButton43.setMaximumSize(new java.awt.Dimension(149, 90));
        boardButton43.setMinimumSize(new java.awt.Dimension(149, 90));
        boardButton43.setPreferredSize(new java.awt.Dimension(149, 90));
        boardButton43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton43boardButtonClicked(evt);
            }
        });

        boardButton42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttonKnightBlue.png"))); // NOI18N
        boardButton42.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.blue, null));
        boardButton42.setBorderPainted(false);
        boardButton42.setContentAreaFilled(false);
        boardButton42.setFocusPainted(false);
        boardButton42.setFocusable(false);
        boardButton42.setMaximumSize(new java.awt.Dimension(149, 90));
        boardButton42.setMinimumSize(new java.awt.Dimension(149, 90));
        boardButton42.setPreferredSize(new java.awt.Dimension(149, 90));
        boardButton42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton42boardButtonClicked(evt);
            }
        });

        boardButton41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttonSwordsmanBlue.png"))); // NOI18N
        boardButton41.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.blue, null));
        boardButton41.setBorderPainted(false);
        boardButton41.setContentAreaFilled(false);
        boardButton41.setFocusPainted(false);
        boardButton41.setFocusable(false);
        boardButton41.setMaximumSize(new java.awt.Dimension(149, 90));
        boardButton41.setMinimumSize(new java.awt.Dimension(149, 90));
        boardButton41.setPreferredSize(new java.awt.Dimension(149, 90));
        boardButton41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton41boardButtonClicked(evt);
            }
        });

        boardButton51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttonSwordsmanBlue.png"))); // NOI18N
        boardButton51.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.blue, null));
        boardButton51.setBorderPainted(false);
        boardButton51.setContentAreaFilled(false);
        boardButton51.setFocusPainted(false);
        boardButton51.setFocusable(false);
        boardButton51.setMaximumSize(new java.awt.Dimension(149, 90));
        boardButton51.setMinimumSize(new java.awt.Dimension(149, 90));
        boardButton51.setPreferredSize(new java.awt.Dimension(149, 90));
        boardButton51.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButtonClicked(evt);
            }
        });

        boardButton52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttonKnightBlue.png"))); // NOI18N
        boardButton52.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.blue, null));
        boardButton52.setBorderPainted(false);
        boardButton52.setContentAreaFilled(false);
        boardButton52.setFocusPainted(false);
        boardButton52.setFocusable(false);
        boardButton52.setMaximumSize(new java.awt.Dimension(149, 90));
        boardButton52.setMinimumSize(new java.awt.Dimension(149, 90));
        boardButton52.setPreferredSize(new java.awt.Dimension(149, 90));
        boardButton52.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton52boardButtonClicked(evt);
            }
        });

        boardButton53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttonArcherBlue.png"))); // NOI18N
        boardButton53.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.blue, null));
        boardButton53.setBorderPainted(false);
        boardButton53.setContentAreaFilled(false);
        boardButton53.setFocusPainted(false);
        boardButton53.setFocusable(false);
        boardButton53.setMaximumSize(new java.awt.Dimension(149, 90));
        boardButton53.setMinimumSize(new java.awt.Dimension(149, 90));
        boardButton53.setPreferredSize(new java.awt.Dimension(149, 90));
        boardButton53.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton53boardButtonClicked(evt);
            }
        });

        boardButton54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttonForestBlue.png"))); // NOI18N
        boardButton54.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.blue, null));
        boardButton54.setBorderPainted(false);
        boardButton54.setContentAreaFilled(false);
        boardButton54.setFocusPainted(false);
        boardButton54.setFocusable(false);
        boardButton54.setMaximumSize(new java.awt.Dimension(149, 90));
        boardButton54.setMinimumSize(new java.awt.Dimension(149, 90));
        boardButton54.setPreferredSize(new java.awt.Dimension(149, 90));
        boardButton54.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton54boardButtonClicked(evt);
            }
        });

        wallButton0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wall17040.png"))); // NOI18N
        wallButton0.setActionCommand("wall1");
        wallButton0.setBorderPainted(false);
        wallButton0.setContentAreaFilled(false);
        wallButton0.setMaximumSize(new java.awt.Dimension(149, 42));
        wallButton0.setMinimumSize(new java.awt.Dimension(149, 42));
        wallButton0.setOpaque(true);
        wallButton0.setPreferredSize(new java.awt.Dimension(149, 42));
        wallButton0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wallButton0wallButtonClicked(evt);
            }
        });

        castleButton0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/castleRed.png"))); // NOI18N
        castleButton0.setActionCommand("board00");
        castleButton0.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.black, null));
        castleButton0.setBorderPainted(false);
        castleButton0.setContentAreaFilled(false);
        castleButton0.setMaximumSize(new java.awt.Dimension(149, 80));
        castleButton0.setMinimumSize(new java.awt.Dimension(149, 80));
        castleButton0.setPreferredSize(new java.awt.Dimension(149, 80));
        castleButton0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                castleButtonClicked(evt);
            }
        });

        wallButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wall17040.png"))); // NOI18N
        wallButton1.setActionCommand("wall1");
        wallButton1.setBorderPainted(false);
        wallButton1.setContentAreaFilled(false);
        wallButton1.setMaximumSize(new java.awt.Dimension(149, 42));
        wallButton1.setMinimumSize(new java.awt.Dimension(149, 42));
        wallButton1.setOpaque(true);
        wallButton1.setPreferredSize(new java.awt.Dimension(149, 42));
        wallButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wallButton1wallButtonClicked(evt);
            }
        });

        castleButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/castleRed.png"))); // NOI18N
        castleButton1.setActionCommand("board00");
        castleButton1.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.black, null));
        castleButton1.setBorderPainted(false);
        castleButton1.setContentAreaFilled(false);
        castleButton1.setMaximumSize(new java.awt.Dimension(149, 80));
        castleButton1.setMinimumSize(new java.awt.Dimension(149, 80));
        castleButton1.setPreferredSize(new java.awt.Dimension(149, 80));
        castleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                castleButtonClicked(evt);
            }
        });

        wallButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wall17040.png"))); // NOI18N
        wallButton2.setActionCommand("wall1");
        wallButton2.setBorderPainted(false);
        wallButton2.setContentAreaFilled(false);
        wallButton2.setMaximumSize(new java.awt.Dimension(149, 42));
        wallButton2.setMinimumSize(new java.awt.Dimension(149, 42));
        wallButton2.setOpaque(true);
        wallButton2.setPreferredSize(new java.awt.Dimension(149, 42));
        wallButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wallButton2wallButtonClicked(evt);
            }
        });

        castleButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/castleGreen.png"))); // NOI18N
        castleButton2.setActionCommand("board00");
        castleButton2.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.black, null));
        castleButton2.setBorderPainted(false);
        castleButton2.setContentAreaFilled(false);
        castleButton2.setMaximumSize(new java.awt.Dimension(149, 80));
        castleButton2.setMinimumSize(new java.awt.Dimension(149, 80));
        castleButton2.setPreferredSize(new java.awt.Dimension(149, 80));
        castleButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                castleButtonClicked(evt);
            }
        });

        wallButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wall17040.png"))); // NOI18N
        wallButton3.setActionCommand("wall1");
        wallButton3.setBorderPainted(false);
        wallButton3.setContentAreaFilled(false);
        wallButton3.setMaximumSize(new java.awt.Dimension(149, 42));
        wallButton3.setMinimumSize(new java.awt.Dimension(149, 42));
        wallButton3.setOpaque(true);
        wallButton3.setPreferredSize(new java.awt.Dimension(149, 42));
        wallButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wallButton3wallButtonClicked(evt);
            }
        });

        castleButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/castleGreen.png"))); // NOI18N
        castleButton3.setActionCommand("board00");
        castleButton3.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.black, null));
        castleButton3.setBorderPainted(false);
        castleButton3.setContentAreaFilled(false);
        castleButton3.setMaximumSize(new java.awt.Dimension(149, 80));
        castleButton3.setMinimumSize(new java.awt.Dimension(149, 80));
        castleButton3.setPreferredSize(new java.awt.Dimension(149, 80));
        castleButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                castleButtonClicked(evt);
            }
        });

        wallButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wall17040.png"))); // NOI18N
        wallButton4.setActionCommand("wall1");
        wallButton4.setBorderPainted(false);
        wallButton4.setContentAreaFilled(false);
        wallButton4.setMaximumSize(new java.awt.Dimension(149, 42));
        wallButton4.setMinimumSize(new java.awt.Dimension(149, 42));
        wallButton4.setOpaque(true);
        wallButton4.setPreferredSize(new java.awt.Dimension(149, 42));
        wallButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wallButton4wallButtonClicked(evt);
            }
        });

        wallButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wall17040.png"))); // NOI18N
        wallButton5.setActionCommand("wall1");
        wallButton5.setBorderPainted(false);
        wallButton5.setContentAreaFilled(false);
        wallButton5.setMaximumSize(new java.awt.Dimension(149, 42));
        wallButton5.setMinimumSize(new java.awt.Dimension(149, 42));
        wallButton5.setOpaque(true);
        wallButton5.setPreferredSize(new java.awt.Dimension(149, 42));
        wallButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wallButton5wallButtonClicked(evt);
            }
        });

        castleButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/castleBlue.png"))); // NOI18N
        castleButton5.setActionCommand("board00");
        castleButton5.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.black, null));
        castleButton5.setBorderPainted(false);
        castleButton5.setContentAreaFilled(false);
        castleButton5.setMaximumSize(new java.awt.Dimension(149, 80));
        castleButton5.setMinimumSize(new java.awt.Dimension(149, 80));
        castleButton5.setPreferredSize(new java.awt.Dimension(149, 80));
        castleButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                castleButton5boardButtonClicked(evt);
            }
        });

        castleButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/castleBlue.png"))); // NOI18N
        castleButton4.setActionCommand("board00");
        castleButton4.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.black, null));
        castleButton4.setBorderPainted(false);
        castleButton4.setContentAreaFilled(false);
        castleButton4.setMaximumSize(new java.awt.Dimension(149, 80));
        castleButton4.setMinimumSize(new java.awt.Dimension(149, 80));
        castleButton4.setPreferredSize(new java.awt.Dimension(149, 80));
        castleButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                castleButton4boardButtonClicked(evt);
            }
        });

        javax.swing.GroupLayout boardPaletteLayerLayout = new javax.swing.GroupLayout(boardPaletteLayer);
        boardPaletteLayer.setLayout(boardPaletteLayerLayout);
        boardPaletteLayerLayout.setHorizontalGroup(
            boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(boardPaletteLayerLayout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(boardPaletteLayerLayout.createSequentialGroup()
                        .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(wallButton0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(castleButton0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(castleButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(wallButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(wallButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(castleButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(wallButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(castleButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(wallButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(castleButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(wallButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(castleButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(boardPaletteLayerLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(boardButton02, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(boardButton12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(boardButton22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(boardButton32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(boardButton42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(boardButton52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, boardPaletteLayerLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(boardButton01, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(boardButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(boardButton21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(boardButton31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(boardButton41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(boardButton51, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(boardPaletteLayerLayout.createSequentialGroup()
                        .addComponent(boardButton03, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(boardButton13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(boardButton23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(boardButton33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(boardButton43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(boardButton53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(boardPaletteLayerLayout.createSequentialGroup()
                        .addComponent(boardButton04, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(boardButton14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(boardButton24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(boardButton34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(boardButton44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(boardButton54, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        boardPaletteLayerLayout.setVerticalGroup(
            boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(boardPaletteLayerLayout.createSequentialGroup()
                .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(boardButton34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(boardButton24, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(boardButton54, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(boardButton44, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(boardButton04, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boardButton14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(boardButton33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(boardButton23, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(boardButton13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boardButton03, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(boardButton53, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(boardButton43, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(2, 2, 2)
                .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(boardButton22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boardButton32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boardButton12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boardButton02, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(boardButton42, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(boardButton52, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(2, 2, 2)
                .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(boardButton21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boardButton31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boardButton11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boardButton01, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(boardButton41, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(boardButton51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(2, 2, 2)
                .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(wallButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(wallButton0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(wallButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(wallButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(wallButton5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(wallButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(castleButton0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(castleButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(castleButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(castleButton5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(castleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(castleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        boardShapeLayer.setMinimumSize(new java.awt.Dimension(906, 494));
        boardShapeLayer.setOpaque(false);
        boardShapeLayer.setPreferredSize(new java.awt.Dimension(906, 500));

        javax.swing.GroupLayout boardShapeLayerLayout = new javax.swing.GroupLayout(boardShapeLayer);
        boardShapeLayer.setLayout(boardShapeLayerLayout);
        boardShapeLayerLayout.setHorizontalGroup(
            boardShapeLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 906, Short.MAX_VALUE)
        );
        boardShapeLayerLayout.setVerticalGroup(
            boardShapeLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 494, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout boardLayeredPanelLayout = new javax.swing.GroupLayout(boardLayeredPanel);
        boardLayeredPanel.setLayout(boardLayeredPanelLayout);
        boardLayeredPanelLayout.setHorizontalGroup(
            boardLayeredPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 906, Short.MAX_VALUE)
            .addGroup(boardLayeredPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(boardLayeredPanelLayout.createSequentialGroup()
                    .addComponent(boardPaletteLayer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(boardLayeredPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(boardShapeLayer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        boardLayeredPanelLayout.setVerticalGroup(
            boardLayeredPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 511, Short.MAX_VALUE)
            .addGroup(boardLayeredPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(boardLayeredPanelLayout.createSequentialGroup()
                    .addComponent(boardPaletteLayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
            .addGroup(boardLayeredPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, boardLayeredPanelLayout.createSequentialGroup()
                    .addComponent(boardShapeLayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        boardLayeredPanel.setLayer(boardPaletteLayer, 2);
        boardLayeredPanel.setLayer(boardShapeLayer, 3);

        boardShapeLayer.getAccessibleContext().setAccessibleParent(boardPaletteLayer);

        handPanel5.setMaximumSize(new java.awt.Dimension(465, 90));
        handPanel5.setMinimumSize(new java.awt.Dimension(465, 90));
        handPanel5.setName(""); // NOI18N

        cardButton50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cardBack6080.png"))); // NOI18N
        cardButton50.setActionCommand("card50");
        cardButton50.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(255, 51, 51)));
        cardButton50.setBorderPainted(false);
        cardButton50.setContentAreaFilled(false);
        cardButton50.setMaximumSize(new java.awt.Dimension(60, 60));
        cardButton50.setMinimumSize(new java.awt.Dimension(60, 60));
        cardButton50.setPreferredSize(new java.awt.Dimension(60, 80));
        cardButton50.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardButtonClicked(evt);
            }
        });

        cardButton51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cardBack6080.png"))); // NOI18N
        cardButton51.setActionCommand("card51");
        cardButton51.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(255, 51, 51)));
        cardButton51.setBorderPainted(false);
        cardButton51.setContentAreaFilled(false);
        cardButton51.setMaximumSize(new java.awt.Dimension(60, 100));
        cardButton51.setMinimumSize(new java.awt.Dimension(60, 100));
        cardButton51.setPreferredSize(new java.awt.Dimension(60, 100));
        cardButton51.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardButtonClicked(evt);
            }
        });

        cardButton52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cardBack6080.png"))); // NOI18N
        cardButton52.setActionCommand("card52");
        cardButton52.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(255, 51, 51)));
        cardButton52.setBorderPainted(false);
        cardButton52.setContentAreaFilled(false);
        cardButton52.setMaximumSize(new java.awt.Dimension(60, 60));
        cardButton52.setMinimumSize(new java.awt.Dimension(60, 60));
        cardButton52.setPreferredSize(new java.awt.Dimension(60, 80));
        cardButton52.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardButtonClicked(evt);
            }
        });

        cardButton53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cardBack6080.png"))); // NOI18N
        cardButton53.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(255, 51, 51)));
        cardButton53.setBorderPainted(false);
        cardButton53.setContentAreaFilled(false);
        cardButton53.setMaximumSize(new java.awt.Dimension(60, 100));
        cardButton53.setMinimumSize(new java.awt.Dimension(60, 100));
        cardButton53.setPreferredSize(new java.awt.Dimension(60, 100));
        cardButton53.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardButtonClicked(evt);
            }
        });

        cardButton54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cardBack6080.png"))); // NOI18N
        cardButton54.setActionCommand("card54");
        cardButton54.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(255, 51, 51)));
        cardButton54.setBorderPainted(false);
        cardButton54.setContentAreaFilled(false);
        cardButton54.setMaximumSize(new java.awt.Dimension(60, 60));
        cardButton54.setMinimumSize(new java.awt.Dimension(60, 60));
        cardButton54.setPreferredSize(new java.awt.Dimension(60, 80));
        cardButton54.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardButtonClicked(evt);
            }
        });

        cardButton55.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cardBack6080.png"))); // NOI18N
        cardButton55.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(255, 51, 51)));
        cardButton55.setBorderPainted(false);
        cardButton55.setContentAreaFilled(false);
        cardButton55.setMaximumSize(new java.awt.Dimension(60, 100));
        cardButton55.setMinimumSize(new java.awt.Dimension(60, 100));
        cardButton55.setPreferredSize(new java.awt.Dimension(60, 100));
        cardButton55.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardButtonClicked(evt);
            }
        });

        handTurnLabel5.setIconTextGap(0);
        handTurnLabel5.setMaximumSize(new java.awt.Dimension(60, 24));
        handTurnLabel5.setMinimumSize(new java.awt.Dimension(60, 24));
        handTurnLabel5.setName("turnLabel"); // NOI18N
        handTurnLabel5.setPreferredSize(new java.awt.Dimension(60, 24));

        handLabel5.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        handLabel5.setText("P6");
        handLabel5.setMaximumSize(new java.awt.Dimension(46, 20));
        handLabel5.setMinimumSize(new java.awt.Dimension(46, 20));
        handLabel5.setName("playerLabel"); // NOI18N
        handLabel5.setPreferredSize(new java.awt.Dimension(46, 20));

        handScoreLabel5.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        handScoreLabel5.setText("Score:");
        handScoreLabel5.setMaximumSize(new java.awt.Dimension(46, 20));
        handScoreLabel5.setMinimumSize(new java.awt.Dimension(46, 20));
        handScoreLabel5.setName("playerLabel"); // NOI18N
        handScoreLabel5.setPreferredSize(new java.awt.Dimension(46, 20));

        handPointsLabel5.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        handPointsLabel5.setForeground(new java.awt.Color(255, 0, 0));
        handPointsLabel5.setText("0");
        handPointsLabel5.setMaximumSize(new java.awt.Dimension(46, 20));
        handPointsLabel5.setMinimumSize(new java.awt.Dimension(46, 20));
        handPointsLabel5.setName("playerLabel"); // NOI18N
        handPointsLabel5.setPreferredSize(new java.awt.Dimension(46, 20));

        javax.swing.GroupLayout handPanel5Layout = new javax.swing.GroupLayout(handPanel5);
        handPanel5.setLayout(handPanel5Layout);
        handPanel5Layout.setHorizontalGroup(
            handPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(handPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cardButton50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cardButton51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cardButton52, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cardButton53, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cardButton54, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cardButton55, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(handPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(handLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(handPanel5Layout.createSequentialGroup()
                        .addGroup(handPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(handTurnLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(handPanel5Layout.createSequentialGroup()
                                .addComponent(handScoreLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(handPointsLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        handPanel5Layout.setVerticalGroup(
            handPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(handPanel5Layout.createSequentialGroup()
                .addGroup(handPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(handPanel5Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(handPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cardButton50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cardButton51, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cardButton52, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cardButton53, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cardButton54, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cardButton55, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(handPanel5Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(handLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(handTurnLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(handPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(handScoreLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(handPointsLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        handPanel1.setMaximumSize(new java.awt.Dimension(465, 90));
        handPanel1.setMinimumSize(new java.awt.Dimension(465, 90));
        handPanel1.setName(""); // NOI18N

        cardButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cardBack6080.png"))); // NOI18N
        cardButton10.setActionCommand("card10");
        cardButton10.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(255, 51, 51)));
        cardButton10.setBorderPainted(false);
        cardButton10.setContentAreaFilled(false);
        cardButton10.setMaximumSize(new java.awt.Dimension(60, 60));
        cardButton10.setMinimumSize(new java.awt.Dimension(60, 60));
        cardButton10.setPreferredSize(new java.awt.Dimension(60, 80));
        cardButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardButtonClicked(evt);
            }
        });

        cardButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cardBack6080.png"))); // NOI18N
        cardButton11.setActionCommand("card11");
        cardButton11.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(255, 51, 51)));
        cardButton11.setBorderPainted(false);
        cardButton11.setContentAreaFilled(false);
        cardButton11.setMaximumSize(new java.awt.Dimension(60, 100));
        cardButton11.setMinimumSize(new java.awt.Dimension(60, 100));
        cardButton11.setPreferredSize(new java.awt.Dimension(60, 100));
        cardButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardButtonClicked(evt);
            }
        });

        cardButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cardBack6080.png"))); // NOI18N
        cardButton12.setActionCommand("card12");
        cardButton12.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(255, 51, 51)));
        cardButton12.setBorderPainted(false);
        cardButton12.setContentAreaFilled(false);
        cardButton12.setMaximumSize(new java.awt.Dimension(60, 60));
        cardButton12.setMinimumSize(new java.awt.Dimension(60, 60));
        cardButton12.setPreferredSize(new java.awt.Dimension(60, 80));
        cardButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardButtonClicked(evt);
            }
        });

        cardButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cardBack6080.png"))); // NOI18N
        cardButton13.setActionCommand("card13");
        cardButton13.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(255, 51, 51)));
        cardButton13.setBorderPainted(false);
        cardButton13.setContentAreaFilled(false);
        cardButton13.setMaximumSize(new java.awt.Dimension(60, 100));
        cardButton13.setMinimumSize(new java.awt.Dimension(60, 100));
        cardButton13.setPreferredSize(new java.awt.Dimension(60, 100));
        cardButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardButtonClicked(evt);
            }
        });

        cardButton14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cardBack6080.png"))); // NOI18N
        cardButton14.setActionCommand("card14");
        cardButton14.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(255, 51, 51)));
        cardButton14.setBorderPainted(false);
        cardButton14.setContentAreaFilled(false);
        cardButton14.setMaximumSize(new java.awt.Dimension(60, 60));
        cardButton14.setMinimumSize(new java.awt.Dimension(60, 60));
        cardButton14.setPreferredSize(new java.awt.Dimension(60, 80));
        cardButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardButtonClicked(evt);
            }
        });

        cardButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cardBack6080.png"))); // NOI18N
        cardButton15.setActionCommand("card15");
        cardButton15.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(255, 51, 51)));
        cardButton15.setBorderPainted(false);
        cardButton15.setContentAreaFilled(false);
        cardButton15.setMaximumSize(new java.awt.Dimension(60, 100));
        cardButton15.setMinimumSize(new java.awt.Dimension(60, 100));
        cardButton15.setPreferredSize(new java.awt.Dimension(60, 100));
        cardButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardButtonClicked(evt);
            }
        });

        handTurnLabel1.setIconTextGap(0);
        handTurnLabel1.setMaximumSize(new java.awt.Dimension(60, 24));
        handTurnLabel1.setMinimumSize(new java.awt.Dimension(60, 24));
        handTurnLabel1.setName("turnLabel"); // NOI18N
        handTurnLabel1.setPreferredSize(new java.awt.Dimension(60, 24));

        handLabel1.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        handLabel1.setText("P2");
        handLabel1.setMaximumSize(new java.awt.Dimension(46, 20));
        handLabel1.setMinimumSize(new java.awt.Dimension(46, 20));
        handLabel1.setName("playerLabel"); // NOI18N
        handLabel1.setPreferredSize(new java.awt.Dimension(46, 20));

        handScoreLabel1.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        handScoreLabel1.setText("Score:");
        handScoreLabel1.setMaximumSize(new java.awt.Dimension(46, 20));
        handScoreLabel1.setMinimumSize(new java.awt.Dimension(46, 20));
        handScoreLabel1.setName("playerLabel"); // NOI18N
        handScoreLabel1.setPreferredSize(new java.awt.Dimension(46, 20));

        handPointsLabel1.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        handPointsLabel1.setForeground(new java.awt.Color(255, 0, 0));
        handPointsLabel1.setText("0");
        handPointsLabel1.setMaximumSize(new java.awt.Dimension(46, 20));
        handPointsLabel1.setMinimumSize(new java.awt.Dimension(46, 20));
        handPointsLabel1.setName("playerLabel"); // NOI18N
        handPointsLabel1.setPreferredSize(new java.awt.Dimension(46, 20));

        handPanel2.setMaximumSize(new java.awt.Dimension(465, 90));
        handPanel2.setMinimumSize(new java.awt.Dimension(465, 90));
        handPanel2.setName(""); // NOI18N

        cardButton20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cardBack6080.png"))); // NOI18N
        cardButton20.setActionCommand("card20");
        cardButton20.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(255, 51, 51)));
        cardButton20.setBorderPainted(false);
        cardButton20.setContentAreaFilled(false);
        cardButton20.setMaximumSize(new java.awt.Dimension(60, 60));
        cardButton20.setMinimumSize(new java.awt.Dimension(60, 60));
        cardButton20.setPreferredSize(new java.awt.Dimension(60, 80));
        cardButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardButtonClicked(evt);
            }
        });

        cardButton21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cardBack6080.png"))); // NOI18N
        cardButton21.setActionCommand("card21");
        cardButton21.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(255, 51, 51)));
        cardButton21.setBorderPainted(false);
        cardButton21.setContentAreaFilled(false);
        cardButton21.setMaximumSize(new java.awt.Dimension(60, 100));
        cardButton21.setMinimumSize(new java.awt.Dimension(60, 100));
        cardButton21.setPreferredSize(new java.awt.Dimension(60, 100));
        cardButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardButtonClicked(evt);
            }
        });

        cardButton22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cardBack6080.png"))); // NOI18N
        cardButton22.setActionCommand("card22");
        cardButton22.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(255, 51, 51)));
        cardButton22.setBorderPainted(false);
        cardButton22.setContentAreaFilled(false);
        cardButton22.setMaximumSize(new java.awt.Dimension(60, 60));
        cardButton22.setMinimumSize(new java.awt.Dimension(60, 60));
        cardButton22.setPreferredSize(new java.awt.Dimension(60, 80));
        cardButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardButtonClicked(evt);
            }
        });

        cardButton23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cardBack6080.png"))); // NOI18N
        cardButton23.setActionCommand("card23");
        cardButton23.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(255, 51, 51)));
        cardButton23.setBorderPainted(false);
        cardButton23.setContentAreaFilled(false);
        cardButton23.setMaximumSize(new java.awt.Dimension(60, 100));
        cardButton23.setMinimumSize(new java.awt.Dimension(60, 100));
        cardButton23.setPreferredSize(new java.awt.Dimension(60, 100));
        cardButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardButtonClicked(evt);
            }
        });

        cardButton24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cardBack6080.png"))); // NOI18N
        cardButton24.setActionCommand("card24");
        cardButton24.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(255, 51, 51)));
        cardButton24.setBorderPainted(false);
        cardButton24.setContentAreaFilled(false);
        cardButton24.setMaximumSize(new java.awt.Dimension(60, 60));
        cardButton24.setMinimumSize(new java.awt.Dimension(60, 60));
        cardButton24.setPreferredSize(new java.awt.Dimension(60, 80));
        cardButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardButtonClicked(evt);
            }
        });

        cardButton25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cardBack6080.png"))); // NOI18N
        cardButton25.setActionCommand("card25");
        cardButton25.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(255, 51, 51)));
        cardButton25.setBorderPainted(false);
        cardButton25.setContentAreaFilled(false);
        cardButton25.setMaximumSize(new java.awt.Dimension(60, 100));
        cardButton25.setMinimumSize(new java.awt.Dimension(60, 100));
        cardButton25.setPreferredSize(new java.awt.Dimension(60, 100));
        cardButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardButtonClicked(evt);
            }
        });

        handLabel2.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        handLabel2.setText("P3");
        handLabel2.setMaximumSize(new java.awt.Dimension(46, 20));
        handLabel2.setMinimumSize(new java.awt.Dimension(46, 20));
        handLabel2.setName("playerLabel"); // NOI18N
        handLabel2.setPreferredSize(new java.awt.Dimension(46, 20));

        handTurnLabel2.setIconTextGap(0);
        handTurnLabel2.setMaximumSize(new java.awt.Dimension(60, 24));
        handTurnLabel2.setMinimumSize(new java.awt.Dimension(60, 24));
        handTurnLabel2.setName("turnLabel"); // NOI18N
        handTurnLabel2.setPreferredSize(new java.awt.Dimension(60, 24));

        handScoreLabel2.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        handScoreLabel2.setText("Score:");
        handScoreLabel2.setMaximumSize(new java.awt.Dimension(46, 20));
        handScoreLabel2.setMinimumSize(new java.awt.Dimension(46, 20));
        handScoreLabel2.setName("playerLabel"); // NOI18N
        handScoreLabel2.setPreferredSize(new java.awt.Dimension(46, 20));

        handPointsLabel2.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        handPointsLabel2.setForeground(new java.awt.Color(255, 0, 0));
        handPointsLabel2.setText("0");
        handPointsLabel2.setMaximumSize(new java.awt.Dimension(46, 20));
        handPointsLabel2.setMinimumSize(new java.awt.Dimension(46, 20));
        handPointsLabel2.setName("playerLabel"); // NOI18N
        handPointsLabel2.setPreferredSize(new java.awt.Dimension(46, 20));

        javax.swing.GroupLayout handPanel2Layout = new javax.swing.GroupLayout(handPanel2);
        handPanel2.setLayout(handPanel2Layout);
        handPanel2Layout.setHorizontalGroup(
            handPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(handPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cardButton20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cardButton21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cardButton22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cardButton23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cardButton24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cardButton25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(handPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(handLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(handPanel2Layout.createSequentialGroup()
                        .addGroup(handPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(handTurnLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(handPanel2Layout.createSequentialGroup()
                                .addComponent(handScoreLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(handPointsLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        handPanel2Layout.setVerticalGroup(
            handPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(handPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(handPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(handPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cardButton20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cardButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cardButton22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cardButton23, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cardButton24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cardButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(handPanel2Layout.createSequentialGroup()
                        .addComponent(handLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(handTurnLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(handPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(handScoreLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(handPointsLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout handPanel1Layout = new javax.swing.GroupLayout(handPanel1);
        handPanel1.setLayout(handPanel1Layout);
        handPanel1Layout.setHorizontalGroup(
            handPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(handPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cardButton10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cardButton11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cardButton12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cardButton13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cardButton14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cardButton15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(handPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(handLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(handTurnLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(handPanel1Layout.createSequentialGroup()
                        .addComponent(handScoreLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(handPointsLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addGroup(handPanel1Layout.createSequentialGroup()
                .addComponent(handPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        handPanel1Layout.setVerticalGroup(
            handPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(handPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(handPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(handPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cardButton10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cardButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cardButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cardButton14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cardButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(handPanel1Layout.createSequentialGroup()
                        .addComponent(handLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(handTurnLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(handPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(handScoreLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(handPointsLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(cardButton12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(handPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        handPanel4.setMaximumSize(new java.awt.Dimension(465, 90));
        handPanel4.setMinimumSize(new java.awt.Dimension(465, 90));
        handPanel4.setName(""); // NOI18N

        cardButton40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cardBack6080.png"))); // NOI18N
        cardButton40.setActionCommand("card40");
        cardButton40.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(255, 51, 51)));
        cardButton40.setBorderPainted(false);
        cardButton40.setContentAreaFilled(false);
        cardButton40.setMaximumSize(new java.awt.Dimension(60, 60));
        cardButton40.setMinimumSize(new java.awt.Dimension(60, 60));
        cardButton40.setPreferredSize(new java.awt.Dimension(60, 80));
        cardButton40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardButtonClicked(evt);
            }
        });

        cardButton41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cardBack6080.png"))); // NOI18N
        cardButton41.setActionCommand("card41");
        cardButton41.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(255, 51, 51)));
        cardButton41.setBorderPainted(false);
        cardButton41.setContentAreaFilled(false);
        cardButton41.setMaximumSize(new java.awt.Dimension(60, 100));
        cardButton41.setMinimumSize(new java.awt.Dimension(60, 100));
        cardButton41.setPreferredSize(new java.awt.Dimension(60, 100));
        cardButton41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardButtonClicked(evt);
            }
        });

        cardButton42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cardBack6080.png"))); // NOI18N
        cardButton42.setActionCommand("card42");
        cardButton42.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(255, 51, 51)));
        cardButton42.setBorderPainted(false);
        cardButton42.setContentAreaFilled(false);
        cardButton42.setMaximumSize(new java.awt.Dimension(60, 60));
        cardButton42.setMinimumSize(new java.awt.Dimension(60, 60));
        cardButton42.setPreferredSize(new java.awt.Dimension(60, 80));
        cardButton42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardButtonClicked(evt);
            }
        });

        cardButton43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cardBack6080.png"))); // NOI18N
        cardButton43.setActionCommand("card43");
        cardButton43.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(255, 51, 51)));
        cardButton43.setBorderPainted(false);
        cardButton43.setContentAreaFilled(false);
        cardButton43.setMaximumSize(new java.awt.Dimension(60, 100));
        cardButton43.setMinimumSize(new java.awt.Dimension(60, 100));
        cardButton43.setPreferredSize(new java.awt.Dimension(60, 100));
        cardButton43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardButtonClicked(evt);
            }
        });

        cardButton44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cardBack6080.png"))); // NOI18N
        cardButton44.setActionCommand("card44");
        cardButton44.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(255, 51, 51)));
        cardButton44.setBorderPainted(false);
        cardButton44.setContentAreaFilled(false);
        cardButton44.setMaximumSize(new java.awt.Dimension(60, 60));
        cardButton44.setMinimumSize(new java.awt.Dimension(60, 60));
        cardButton44.setPreferredSize(new java.awt.Dimension(60, 80));
        cardButton44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardButtonClicked(evt);
            }
        });

        cardButton45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cardBack6080.png"))); // NOI18N
        cardButton45.setActionCommand("card45");
        cardButton45.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(255, 51, 51)));
        cardButton45.setBorderPainted(false);
        cardButton45.setContentAreaFilled(false);
        cardButton45.setMaximumSize(new java.awt.Dimension(60, 100));
        cardButton45.setMinimumSize(new java.awt.Dimension(60, 100));
        cardButton45.setPreferredSize(new java.awt.Dimension(60, 100));
        cardButton45.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardButtonClicked(evt);
            }
        });

        handTurnLabel4.setIconTextGap(0);
        handTurnLabel4.setMaximumSize(new java.awt.Dimension(60, 24));
        handTurnLabel4.setMinimumSize(new java.awt.Dimension(60, 24));
        handTurnLabel4.setName("turnLabel"); // NOI18N
        handTurnLabel4.setPreferredSize(new java.awt.Dimension(60, 24));

        handLabel4.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        handLabel4.setText("P5");
        handLabel4.setMaximumSize(new java.awt.Dimension(46, 20));
        handLabel4.setMinimumSize(new java.awt.Dimension(46, 20));
        handLabel4.setName("playerLabel"); // NOI18N
        handLabel4.setPreferredSize(new java.awt.Dimension(46, 20));

        handScoreLabel4.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        handScoreLabel4.setText("Score:");
        handScoreLabel4.setMaximumSize(new java.awt.Dimension(46, 20));
        handScoreLabel4.setMinimumSize(new java.awt.Dimension(46, 20));
        handScoreLabel4.setName("playerLabel"); // NOI18N
        handScoreLabel4.setPreferredSize(new java.awt.Dimension(46, 20));

        handPointsLabel4.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        handPointsLabel4.setForeground(new java.awt.Color(255, 0, 0));
        handPointsLabel4.setText("0");
        handPointsLabel4.setMaximumSize(new java.awt.Dimension(46, 20));
        handPointsLabel4.setMinimumSize(new java.awt.Dimension(46, 20));
        handPointsLabel4.setName("playerLabel"); // NOI18N
        handPointsLabel4.setPreferredSize(new java.awt.Dimension(46, 20));

        javax.swing.GroupLayout handPanel4Layout = new javax.swing.GroupLayout(handPanel4);
        handPanel4.setLayout(handPanel4Layout);
        handPanel4Layout.setHorizontalGroup(
            handPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(handPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cardButton40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cardButton41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cardButton42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cardButton43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cardButton44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cardButton45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(handPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(handLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(handPanel4Layout.createSequentialGroup()
                        .addGroup(handPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(handTurnLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(handPanel4Layout.createSequentialGroup()
                                .addComponent(handScoreLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(handPointsLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        handPanel4Layout.setVerticalGroup(
            handPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(handPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(handPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(handPanel4Layout.createSequentialGroup()
                        .addComponent(handLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(handTurnLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(handPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(handScoreLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(handPointsLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(handPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cardButton40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cardButton41, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cardButton42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cardButton43, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cardButton44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cardButton45, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        handPanel0.setMaximumSize(new java.awt.Dimension(465, 90));
        handPanel0.setMinimumSize(new java.awt.Dimension(465, 90));
        handPanel0.setName(""); // NOI18N

        cardButton00.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cardBack6080.png"))); // NOI18N
        cardButton00.setActionCommand("card00");
        cardButton00.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(255, 51, 51)));
        cardButton00.setBorderPainted(false);
        cardButton00.setContentAreaFilled(false);
        cardButton00.setMaximumSize(new java.awt.Dimension(60, 60));
        cardButton00.setMinimumSize(new java.awt.Dimension(60, 60));
        cardButton00.setPreferredSize(new java.awt.Dimension(60, 80));
        cardButton00.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardButtonClicked(evt);
            }
        });

        cardbutton01.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cardBack6080.png"))); // NOI18N
        cardbutton01.setActionCommand("card01");
        cardbutton01.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(255, 51, 51)));
        cardbutton01.setBorderPainted(false);
        cardbutton01.setContentAreaFilled(false);
        cardbutton01.setMaximumSize(new java.awt.Dimension(60, 100));
        cardbutton01.setMinimumSize(new java.awt.Dimension(60, 100));
        cardbutton01.setPreferredSize(new java.awt.Dimension(60, 100));
        cardbutton01.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardButtonClicked(evt);
            }
        });

        cardButton02.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cardBack6080.png"))); // NOI18N
        cardButton02.setActionCommand("card02");
        cardButton02.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(255, 51, 51)));
        cardButton02.setBorderPainted(false);
        cardButton02.setContentAreaFilled(false);
        cardButton02.setMaximumSize(new java.awt.Dimension(60, 60));
        cardButton02.setMinimumSize(new java.awt.Dimension(60, 60));
        cardButton02.setPreferredSize(new java.awt.Dimension(60, 80));
        cardButton02.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardButtonClicked(evt);
            }
        });

        cardbutton03.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cardBack6080.png"))); // NOI18N
        cardbutton03.setActionCommand("card03");
        cardbutton03.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(255, 51, 51)));
        cardbutton03.setBorderPainted(false);
        cardbutton03.setContentAreaFilled(false);
        cardbutton03.setMaximumSize(new java.awt.Dimension(60, 100));
        cardbutton03.setMinimumSize(new java.awt.Dimension(60, 100));
        cardbutton03.setPreferredSize(new java.awt.Dimension(60, 100));
        cardbutton03.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardButtonClicked(evt);
            }
        });

        cardButton04.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cardBack6080.png"))); // NOI18N
        cardButton04.setActionCommand("card04");
        cardButton04.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(255, 51, 51)));
        cardButton04.setBorderPainted(false);
        cardButton04.setContentAreaFilled(false);
        cardButton04.setMaximumSize(new java.awt.Dimension(60, 60));
        cardButton04.setMinimumSize(new java.awt.Dimension(60, 60));
        cardButton04.setPreferredSize(new java.awt.Dimension(60, 80));
        cardButton04.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardButtonClicked(evt);
            }
        });

        cardButton05.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cardBack6080.png"))); // NOI18N
        cardButton05.setActionCommand("card05");
        cardButton05.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(255, 51, 51)));
        cardButton05.setBorderPainted(false);
        cardButton05.setContentAreaFilled(false);
        cardButton05.setMaximumSize(new java.awt.Dimension(60, 100));
        cardButton05.setMinimumSize(new java.awt.Dimension(60, 100));
        cardButton05.setPreferredSize(new java.awt.Dimension(60, 100));
        cardButton05.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardButtonClicked(evt);
            }
        });

        handLabel0.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        handLabel0.setText("P1");
        handLabel0.setMaximumSize(new java.awt.Dimension(46, 20));
        handLabel0.setMinimumSize(new java.awt.Dimension(46, 20));
        handLabel0.setName("playerLabel"); // NOI18N
        handLabel0.setPreferredSize(new java.awt.Dimension(46, 20));

        handTurnLabel0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/turnSpot.png"))); // NOI18N
        handTurnLabel0.setIconTextGap(0);
        handTurnLabel0.setMaximumSize(new java.awt.Dimension(60, 24));
        handTurnLabel0.setMinimumSize(new java.awt.Dimension(60, 24));
        handTurnLabel0.setName("turnLabel"); // NOI18N
        handTurnLabel0.setPreferredSize(new java.awt.Dimension(60, 24));

        handScoreLabel0.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        handScoreLabel0.setText("Score:");
        handScoreLabel0.setMaximumSize(new java.awt.Dimension(46, 20));
        handScoreLabel0.setMinimumSize(new java.awt.Dimension(46, 20));
        handScoreLabel0.setName("playerLabel"); // NOI18N
        handScoreLabel0.setPreferredSize(new java.awt.Dimension(46, 20));

        handPointsLabel0.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        handPointsLabel0.setForeground(new java.awt.Color(255, 0, 0));
        handPointsLabel0.setText("0");
        handPointsLabel0.setMaximumSize(new java.awt.Dimension(46, 20));
        handPointsLabel0.setMinimumSize(new java.awt.Dimension(46, 20));
        handPointsLabel0.setName("playerLabel"); // NOI18N
        handPointsLabel0.setPreferredSize(new java.awt.Dimension(46, 20));

        javax.swing.GroupLayout handPanel0Layout = new javax.swing.GroupLayout(handPanel0);
        handPanel0.setLayout(handPanel0Layout);
        handPanel0Layout.setHorizontalGroup(
            handPanel0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(handPanel0Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cardButton00, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cardbutton01, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cardButton02, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cardbutton03, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cardButton04, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cardButton05, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(handPanel0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(handTurnLabel0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(handPanel0Layout.createSequentialGroup()
                        .addComponent(handScoreLabel0, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(handPointsLabel0, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(handLabel0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        handPanel0Layout.setVerticalGroup(
            handPanel0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(handPanel0Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(handPanel0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(handPanel0Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(handLabel0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(handTurnLabel0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(handPanel0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(handPointsLabel0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(handScoreLabel0, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(handPanel0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cardButton00, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cardbutton01, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cardButton02, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cardbutton03, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cardButton04, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cardButton05, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        handPanel3.setMaximumSize(new java.awt.Dimension(465, 90));
        handPanel3.setMinimumSize(new java.awt.Dimension(465, 90));
        handPanel3.setName(""); // NOI18N

        cardButton30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cardBack6080.png"))); // NOI18N
        cardButton30.setActionCommand("card30");
        cardButton30.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(255, 51, 51)));
        cardButton30.setBorderPainted(false);
        cardButton30.setContentAreaFilled(false);
        cardButton30.setMaximumSize(new java.awt.Dimension(60, 60));
        cardButton30.setMinimumSize(new java.awt.Dimension(60, 60));
        cardButton30.setPreferredSize(new java.awt.Dimension(60, 80));
        cardButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardButtonClicked(evt);
            }
        });

        cardButton31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cardBack6080.png"))); // NOI18N
        cardButton31.setActionCommand("card31");
        cardButton31.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(255, 51, 51)));
        cardButton31.setBorderPainted(false);
        cardButton31.setContentAreaFilled(false);
        cardButton31.setMaximumSize(new java.awt.Dimension(60, 100));
        cardButton31.setMinimumSize(new java.awt.Dimension(60, 100));
        cardButton31.setPreferredSize(new java.awt.Dimension(60, 100));
        cardButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardButtonClicked(evt);
            }
        });

        cardButton32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cardBack6080.png"))); // NOI18N
        cardButton32.setActionCommand("card32");
        cardButton32.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(255, 51, 51)));
        cardButton32.setBorderPainted(false);
        cardButton32.setContentAreaFilled(false);
        cardButton32.setMaximumSize(new java.awt.Dimension(60, 60));
        cardButton32.setMinimumSize(new java.awt.Dimension(60, 60));
        cardButton32.setPreferredSize(new java.awt.Dimension(60, 80));
        cardButton32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardButtonClicked(evt);
            }
        });

        cardButton33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cardBack6080.png"))); // NOI18N
        cardButton33.setActionCommand("card33");
        cardButton33.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(255, 51, 51)));
        cardButton33.setBorderPainted(false);
        cardButton33.setContentAreaFilled(false);
        cardButton33.setMaximumSize(new java.awt.Dimension(60, 100));
        cardButton33.setMinimumSize(new java.awt.Dimension(60, 100));
        cardButton33.setPreferredSize(new java.awt.Dimension(60, 100));
        cardButton33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardButtonClicked(evt);
            }
        });

        cardButton34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cardBack6080.png"))); // NOI18N
        cardButton34.setActionCommand("card34");
        cardButton34.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(255, 51, 51)));
        cardButton34.setBorderPainted(false);
        cardButton34.setContentAreaFilled(false);
        cardButton34.setMaximumSize(new java.awt.Dimension(60, 60));
        cardButton34.setMinimumSize(new java.awt.Dimension(60, 60));
        cardButton34.setPreferredSize(new java.awt.Dimension(60, 80));
        cardButton34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardButtonClicked(evt);
            }
        });

        cardButton35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cardBack6080.png"))); // NOI18N
        cardButton35.setActionCommand("card35");
        cardButton35.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(255, 51, 51)));
        cardButton35.setBorderPainted(false);
        cardButton35.setContentAreaFilled(false);
        cardButton35.setMaximumSize(new java.awt.Dimension(60, 100));
        cardButton35.setMinimumSize(new java.awt.Dimension(60, 100));
        cardButton35.setPreferredSize(new java.awt.Dimension(60, 100));
        cardButton35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardButtonClicked(evt);
            }
        });

        handLabel3.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        handLabel3.setText("P4");
        handLabel3.setMaximumSize(new java.awt.Dimension(46, 20));
        handLabel3.setMinimumSize(new java.awt.Dimension(46, 20));
        handLabel3.setName("playerLabel"); // NOI18N
        handLabel3.setPreferredSize(new java.awt.Dimension(46, 20));

        handTurnLabel3.setIconTextGap(0);
        handTurnLabel3.setMaximumSize(new java.awt.Dimension(60, 24));
        handTurnLabel3.setMinimumSize(new java.awt.Dimension(60, 24));
        handTurnLabel3.setName("turnLabel"); // NOI18N
        handTurnLabel3.setPreferredSize(new java.awt.Dimension(60, 24));

        handScoreLabel3.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        handScoreLabel3.setText("Score:");
        handScoreLabel3.setMaximumSize(new java.awt.Dimension(46, 20));
        handScoreLabel3.setMinimumSize(new java.awt.Dimension(46, 20));
        handScoreLabel3.setName("playerLabel"); // NOI18N
        handScoreLabel3.setPreferredSize(new java.awt.Dimension(46, 20));

        handPointsLabel3.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        handPointsLabel3.setForeground(new java.awt.Color(255, 0, 0));
        handPointsLabel3.setText("0");
        handPointsLabel3.setMaximumSize(new java.awt.Dimension(46, 20));
        handPointsLabel3.setMinimumSize(new java.awt.Dimension(46, 20));
        handPointsLabel3.setName("playerLabel"); // NOI18N
        handPointsLabel3.setPreferredSize(new java.awt.Dimension(46, 20));

        javax.swing.GroupLayout handPanel3Layout = new javax.swing.GroupLayout(handPanel3);
        handPanel3.setLayout(handPanel3Layout);
        handPanel3Layout.setHorizontalGroup(
            handPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(handPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cardButton30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cardButton31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cardButton32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cardButton33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cardButton34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cardButton35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(handPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(handLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(handPanel3Layout.createSequentialGroup()
                        .addGroup(handPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(handPanel3Layout.createSequentialGroup()
                                .addComponent(handScoreLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(handPointsLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(handTurnLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        handPanel3Layout.setVerticalGroup(
            handPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(handPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(handPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(handPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cardButton30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cardButton31, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cardButton32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cardButton33, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cardButton34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cardButton35, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(handPanel3Layout.createSequentialGroup()
                        .addComponent(handLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(handTurnLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(handPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(handScoreLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(handPointsLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        controlPanel.setFocusable(false);
        controlPanel.setOpaque(false);

        monsterProgLabel.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 12)); // NOI18N
        monsterProgLabel.setForeground(new java.awt.Color(255, 0, 0));
        monsterProgLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/monstersRemaining.png"))); // NOI18N

        blipLabel0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        blipLabel47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remainingMonstersBlip_RED.png"))); // NOI18N

        javax.swing.GroupLayout controlPanelLayout = new javax.swing.GroupLayout(controlPanel);
        controlPanel.setLayout(controlPanelLayout);
        controlPanelLayout.setHorizontalGroup(
            controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(controlPanelLayout.createSequentialGroup()
                        .addComponent(blipLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(blipLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(blipLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(blipLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(blipLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(blipLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(blipLabel13))
                    .addGroup(controlPanelLayout.createSequentialGroup()
                        .addComponent(blipLabel0)
                        .addGap(18, 18, 18)
                        .addComponent(blipLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(blipLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(blipLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(blipLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(blipLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(blipLabel6))
                    .addGroup(controlPanelLayout.createSequentialGroup()
                        .addComponent(blipLabel14)
                        .addGap(18, 18, 18)
                        .addComponent(blipLabel15)
                        .addGap(18, 18, 18)
                        .addComponent(blipLabel16)
                        .addGap(18, 18, 18)
                        .addComponent(blipLabel17)
                        .addGap(18, 18, 18)
                        .addComponent(blipLabel18)
                        .addGap(18, 18, 18)
                        .addComponent(blipLabel19)
                        .addGap(18, 18, 18)
                        .addComponent(blipLabel20))
                    .addGroup(controlPanelLayout.createSequentialGroup()
                        .addComponent(blipLabel21)
                        .addGap(18, 18, 18)
                        .addComponent(blipLabel22)
                        .addGap(18, 18, 18)
                        .addComponent(blipLabel23)
                        .addGap(18, 18, 18)
                        .addComponent(blipLabel24)
                        .addGap(18, 18, 18)
                        .addComponent(blipLabel25)
                        .addGap(18, 18, 18)
                        .addComponent(blipLabel26)
                        .addGap(18, 18, 18)
                        .addComponent(blipLabel27))
                    .addGroup(controlPanelLayout.createSequentialGroup()
                        .addComponent(blipLabel28)
                        .addGap(18, 18, 18)
                        .addComponent(blipLabel29)
                        .addGap(18, 18, 18)
                        .addComponent(blipLabel30)
                        .addGap(18, 18, 18)
                        .addComponent(blipLabel31)
                        .addGap(18, 18, 18)
                        .addComponent(blipLabel32)
                        .addGap(18, 18, 18)
                        .addComponent(blipLabel33)
                        .addGap(18, 18, 18)
                        .addComponent(blipLabel34))
                    .addGroup(controlPanelLayout.createSequentialGroup()
                        .addComponent(blipLabel35)
                        .addGap(18, 18, 18)
                        .addComponent(blipLabel36)
                        .addGap(18, 18, 18)
                        .addComponent(blipLabel37)
                        .addGap(18, 18, 18)
                        .addComponent(blipLabel38)
                        .addGap(18, 18, 18)
                        .addComponent(blipLabel39)
                        .addGap(18, 18, 18)
                        .addComponent(blipLabel40)
                        .addGap(18, 18, 18)
                        .addComponent(blipLabel41))
                    .addGroup(controlPanelLayout.createSequentialGroup()
                        .addComponent(blipLabel42)
                        .addGap(18, 18, 18)
                        .addComponent(blipLabel43)
                        .addGap(18, 18, 18)
                        .addComponent(blipLabel44)
                        .addGap(18, 18, 18)
                        .addComponent(blipLabel45)
                        .addGap(18, 18, 18)
                        .addComponent(blipLabel46)
                        .addGap(18, 18, 18)
                        .addComponent(blipLabel47)))
                .addContainerGap(56, Short.MAX_VALUE))
            .addGroup(controlPanelLayout.createSequentialGroup()
                .addComponent(monsterProgLabel)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        controlPanelLayout.setVerticalGroup(
            controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlPanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(monsterProgLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(blipLabel0)
                    .addComponent(blipLabel1)
                    .addComponent(blipLabel2)
                    .addComponent(blipLabel3)
                    .addComponent(blipLabel4)
                    .addComponent(blipLabel5)
                    .addComponent(blipLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(blipLabel7)
                    .addComponent(blipLabel8)
                    .addComponent(blipLabel9)
                    .addComponent(blipLabel10)
                    .addComponent(blipLabel11)
                    .addComponent(blipLabel12)
                    .addComponent(blipLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(blipLabel14)
                    .addComponent(blipLabel15)
                    .addComponent(blipLabel16)
                    .addComponent(blipLabel17)
                    .addComponent(blipLabel18)
                    .addComponent(blipLabel19)
                    .addComponent(blipLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(blipLabel21)
                    .addComponent(blipLabel22)
                    .addComponent(blipLabel23)
                    .addComponent(blipLabel24)
                    .addComponent(blipLabel25)
                    .addComponent(blipLabel26)
                    .addComponent(blipLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(blipLabel28)
                    .addComponent(blipLabel29)
                    .addComponent(blipLabel30)
                    .addComponent(blipLabel31)
                    .addComponent(blipLabel32)
                    .addComponent(blipLabel33)
                    .addComponent(blipLabel34))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(blipLabel35)
                    .addComponent(blipLabel36)
                    .addComponent(blipLabel37)
                    .addComponent(blipLabel38)
                    .addComponent(blipLabel39)
                    .addComponent(blipLabel40)
                    .addComponent(blipLabel41))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(blipLabel42)
                    .addComponent(blipLabel43)
                    .addComponent(blipLabel44)
                    .addComponent(blipLabel45)
                    .addComponent(blipLabel46)
                    .addComponent(blipLabel47))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout handPanelLayout = new javax.swing.GroupLayout(handPanel);
        handPanel.setLayout(handPanelLayout);
        handPanelLayout.setHorizontalGroup(
            handPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(handPanelLayout.createSequentialGroup()
                .addGroup(handPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(handPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(handPanel0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(handPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(handPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(handPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(handPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(controlPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        handPanelLayout.setVerticalGroup(
            handPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(handPanelLayout.createSequentialGroup()
                .addGroup(handPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(handPanelLayout.createSequentialGroup()
                        .addComponent(handPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(handPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(handPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(handPanelLayout.createSequentialGroup()
                        .addComponent(handPanel0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(handPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(controlPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(handPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(boardLayeredPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(phasePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(boardLayeredPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(phasePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(3, 3, 3)
                .addComponent(handPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cardButtonClicked
        //need to add functionality for deselecting IOT support new event handlers
        int handClicked = Character.getNumericValue(evt.getActionCommand().charAt(4));
        int cardClicked = Character.getNumericValue(evt.getActionCommand().charAt(5));
        System.out.println("GUI: Card " + String.valueOf(handClicked) + ", " + String.valueOf(cardClicked) + " was clicked.");
        if (handClicked == gameState.getCurrentPlayer()) {
            selectedCard = cardButtons[handClicked][cardClicked];
            gameState.setSelectedCard(cardClicked);
        } else {
            otherCard = cardButtons[handClicked][cardClicked];
            gameState.setOtherCard(handClicked, cardClicked);
        }
        updateCards();

    }//GEN-LAST:event_cardButtonClicked

    private void boardButton04boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton04boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton04boardButtonClicked

    private void boardButton03boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton03boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton03boardButtonClicked

    private void boardButton02boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton02boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton02boardButtonClicked

    private void boardButton01boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton01boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton01boardButtonClicked

    private void boardButton24boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton24boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton24boardButtonClicked

    private void boardButton23boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton23boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton23boardButtonClicked

    private void boardButton32boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton32boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton32boardButtonClicked

    private void boardButton31boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton31boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton31boardButtonClicked

    private void boardButton34boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton34boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton34boardButtonClicked

    private void boardButton33boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton33boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton33boardButtonClicked

    private void boardButton22boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton22boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton22boardButtonClicked

    private void boardButton21boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton21boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton21boardButtonClicked

    private void boardButton14boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton14boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton14boardButtonClicked

    private void boardButton13boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton13boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton13boardButtonClicked

    private void boardButton12boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton12boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton12boardButtonClicked

    private void boardButton11boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton11boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton11boardButtonClicked

    private void boardButton44boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton44boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton44boardButtonClicked

    private void boardButton43boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton43boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton43boardButtonClicked

    private void boardButton42boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton42boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton42boardButtonClicked

    private void boardButton41boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton41boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton41boardButtonClicked

    private void boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButtonClicked

    private void boardButton52boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton52boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton52boardButtonClicked

    private void boardButton53boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton53boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton53boardButtonClicked

    private void boardButton54boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton54boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton54boardButtonClicked

    private void wallButton0wallButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wallButton0wallButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_wallButton0wallButtonClicked

    private void castleButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_castleButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_castleButtonClicked

    private void wallButton1wallButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wallButton1wallButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_wallButton1wallButtonClicked

    private void wallButton2wallButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wallButton2wallButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_wallButton2wallButtonClicked

    private void wallButton3wallButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wallButton3wallButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_wallButton3wallButtonClicked

    private void wallButton5wallButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wallButton5wallButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_wallButton5wallButtonClicked

    private void castleButton5boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_castleButton5boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_castleButton5boardButtonClicked

    private void wallButton4wallButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wallButton4wallButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_wallButton4wallButtonClicked

    private void castleButton4boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_castleButton4boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_castleButton4boardButtonClicked

    private void dealClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dealClicked
        gameState.fillCurrentPlayerHand();
        updateGame();
    }//GEN-LAST:event_dealClicked

    private void tradeDealerClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tradeDealerClicked
        //add gameState phase check to new event handlers
        if (selectedCard != null) {
            if (gameState.discardOption(true) == 0) {
                gameState.discardCurrentPlayersCard();
                gameState.discardOption(false);
                updateGame();
            } else {
                gameState.discardOption(false);
                updateGame();
            }
        }

    }//GEN-LAST:event_tradeDealerClicked

    private void tradePlayerClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tradePlayerClicked
        if (otherCard != null) {
            if (gameState.tradeOption(true) == 0) {
                gameState.tradeCurrentPlayerCards();
                otherCard = null;
                updateGame();
            } else {
                gameState.tradeOption(false);
                updateGame();
            }
        }
    }//GEN-LAST:event_tradePlayerClicked

    private void attackClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_attackClicked

        gameState.playCard();
        selectedCard = null;
        boardShapeLayer.clearSelected();
        updateGame();


    }//GEN-LAST:event_attackClicked

    private void skipButton1Clicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_skipButton1Clicked
        gameState.discardOption(false);
        updateGame();
    }//GEN-LAST:event_skipButton1Clicked

    private void skipButton2Clicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_skipButton2Clicked
        gameState.tradeOption(false);
        updateGame();
    }//GEN-LAST:event_skipButton2Clicked

    private void skipButton3Clicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_skipButton3Clicked
        gameState.playAdvance();
        selectedCard = null;
        otherCard = null;
        updateGame();
    }//GEN-LAST:event_skipButton3Clicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 

        
        
        
        
        
        
         try {
         for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
         if ("Nimbus".equals(info.getName())) {
         javax.swing.UIManager.setLookAndFeel(info.getClassName());
         break;
         }
         }
         } catch (ClassNotFoundException ex) {
         java.util.logging.Logger.getLogger(CastleFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
         } catch (InstantiationException ex) {
         java.util.logging.Logger.getLogger(CastleFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
         } catch (IllegalAccessException ex) {
         java.util.logging.Logger.getLogger(CastleFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
         } catch (javax.swing.UnsupportedLookAndFeelException ex) {
         java.util.logging.Logger.getLogger(CastleFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
         }
         //</editor-fold>

         /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CastleFrame().setVisible(false);

            }
        });

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel blipLabel0;
    private javax.swing.JLabel blipLabel1;
    private javax.swing.JLabel blipLabel10;
    private javax.swing.JLabel blipLabel11;
    private javax.swing.JLabel blipLabel12;
    private javax.swing.JLabel blipLabel13;
    private javax.swing.JLabel blipLabel14;
    private javax.swing.JLabel blipLabel15;
    private javax.swing.JLabel blipLabel16;
    private javax.swing.JLabel blipLabel17;
    private javax.swing.JLabel blipLabel18;
    private javax.swing.JLabel blipLabel19;
    private javax.swing.JLabel blipLabel2;
    private javax.swing.JLabel blipLabel20;
    private javax.swing.JLabel blipLabel21;
    private javax.swing.JLabel blipLabel22;
    private javax.swing.JLabel blipLabel23;
    private javax.swing.JLabel blipLabel24;
    private javax.swing.JLabel blipLabel25;
    private javax.swing.JLabel blipLabel26;
    private javax.swing.JLabel blipLabel27;
    private javax.swing.JLabel blipLabel28;
    private javax.swing.JLabel blipLabel29;
    private javax.swing.JLabel blipLabel3;
    private javax.swing.JLabel blipLabel30;
    private javax.swing.JLabel blipLabel31;
    private javax.swing.JLabel blipLabel32;
    private javax.swing.JLabel blipLabel33;
    private javax.swing.JLabel blipLabel34;
    private javax.swing.JLabel blipLabel35;
    private javax.swing.JLabel blipLabel36;
    private javax.swing.JLabel blipLabel37;
    private javax.swing.JLabel blipLabel38;
    private javax.swing.JLabel blipLabel39;
    private javax.swing.JLabel blipLabel4;
    private javax.swing.JLabel blipLabel40;
    private javax.swing.JLabel blipLabel41;
    private javax.swing.JLabel blipLabel42;
    private javax.swing.JLabel blipLabel43;
    private javax.swing.JLabel blipLabel44;
    private javax.swing.JLabel blipLabel45;
    private javax.swing.JLabel blipLabel46;
    private javax.swing.JLabel blipLabel47;
    private javax.swing.JLabel blipLabel5;
    private javax.swing.JLabel blipLabel6;
    private javax.swing.JLabel blipLabel7;
    private javax.swing.JLabel blipLabel8;
    private javax.swing.JLabel blipLabel9;
    private javax.swing.JButton boardButton01;
    private javax.swing.JButton boardButton02;
    private javax.swing.JButton boardButton03;
    private javax.swing.JButton boardButton04;
    private javax.swing.JButton boardButton11;
    private javax.swing.JButton boardButton12;
    private javax.swing.JButton boardButton13;
    private javax.swing.JButton boardButton14;
    private javax.swing.JButton boardButton21;
    private javax.swing.JButton boardButton22;
    private javax.swing.JButton boardButton23;
    private javax.swing.JButton boardButton24;
    private javax.swing.JButton boardButton31;
    private javax.swing.JButton boardButton32;
    private javax.swing.JButton boardButton33;
    private javax.swing.JButton boardButton34;
    private javax.swing.JButton boardButton41;
    private javax.swing.JButton boardButton42;
    private javax.swing.JButton boardButton43;
    private javax.swing.JButton boardButton44;
    private javax.swing.JButton boardButton51;
    private javax.swing.JButton boardButton52;
    private javax.swing.JButton boardButton53;
    private javax.swing.JButton boardButton54;
    private javax.swing.JLayeredPane boardLayeredPanel;
    private javax.swing.JPanel boardPaletteLayer;
    /**
    private javax.swing.JPanel boardShapeLayer;
    */
    private ShapePanel boardShapeLayer;
    private javax.swing.JButton cardButton00;
    private javax.swing.JButton cardButton02;
    private javax.swing.JButton cardButton04;
    private javax.swing.JButton cardButton05;
    private javax.swing.JButton cardButton10;
    private javax.swing.JButton cardButton11;
    private javax.swing.JButton cardButton12;
    private javax.swing.JButton cardButton13;
    private javax.swing.JButton cardButton14;
    private javax.swing.JButton cardButton15;
    private javax.swing.JButton cardButton20;
    private javax.swing.JButton cardButton21;
    private javax.swing.JButton cardButton22;
    private javax.swing.JButton cardButton23;
    private javax.swing.JButton cardButton24;
    private javax.swing.JButton cardButton25;
    private javax.swing.JButton cardButton30;
    private javax.swing.JButton cardButton31;
    private javax.swing.JButton cardButton32;
    private javax.swing.JButton cardButton33;
    private javax.swing.JButton cardButton34;
    private javax.swing.JButton cardButton35;
    private javax.swing.JButton cardButton40;
    private javax.swing.JButton cardButton41;
    private javax.swing.JButton cardButton42;
    private javax.swing.JButton cardButton43;
    private javax.swing.JButton cardButton44;
    private javax.swing.JButton cardButton45;
    private javax.swing.JButton cardButton50;
    private javax.swing.JButton cardButton51;
    private javax.swing.JButton cardButton52;
    private javax.swing.JButton cardButton53;
    private javax.swing.JButton cardButton54;
    private javax.swing.JButton cardButton55;
    private javax.swing.JButton cardbutton01;
    private javax.swing.JButton cardbutton03;
    private javax.swing.JButton castleButton0;
    private javax.swing.JButton castleButton1;
    private javax.swing.JButton castleButton2;
    private javax.swing.JButton castleButton3;
    private javax.swing.JButton castleButton4;
    private javax.swing.JButton castleButton5;
    private javax.swing.JPanel controlPanel;
    private javax.swing.JLabel handLabel0;
    private javax.swing.JLabel handLabel1;
    private javax.swing.JLabel handLabel2;
    private javax.swing.JLabel handLabel3;
    private javax.swing.JLabel handLabel4;
    private javax.swing.JLabel handLabel5;
    private javax.swing.JPanel handPanel;
    private javax.swing.JPanel handPanel0;
    private javax.swing.JPanel handPanel1;
    private javax.swing.JPanel handPanel2;
    private javax.swing.JPanel handPanel3;
    private javax.swing.JPanel handPanel4;
    private javax.swing.JPanel handPanel5;
    private javax.swing.JLabel handPointsLabel0;
    private javax.swing.JLabel handPointsLabel1;
    private javax.swing.JLabel handPointsLabel2;
    private javax.swing.JLabel handPointsLabel3;
    private javax.swing.JLabel handPointsLabel4;
    private javax.swing.JLabel handPointsLabel5;
    private javax.swing.JLabel handScoreLabel0;
    private javax.swing.JLabel handScoreLabel1;
    private javax.swing.JLabel handScoreLabel2;
    private javax.swing.JLabel handScoreLabel3;
    private javax.swing.JLabel handScoreLabel4;
    private javax.swing.JLabel handScoreLabel5;
    private javax.swing.JLabel handTurnLabel0;
    private javax.swing.JLabel handTurnLabel1;
    private javax.swing.JLabel handTurnLabel2;
    private javax.swing.JLabel handTurnLabel3;
    private javax.swing.JLabel handTurnLabel4;
    private javax.swing.JLabel handTurnLabel5;
    private javax.swing.JLabel monsterProgLabel;
    private javax.swing.JButton phaseButton1;
    private javax.swing.JButton phaseButton2;
    private javax.swing.JButton phaseButton3;
    private javax.swing.JButton phaseButton4;
    private javax.swing.JPanel phasePanel;
    private javax.swing.JLabel phaseTitleLabel;
    private javax.swing.JLabel phaseTitleLabel1;
    private javax.swing.JLabel phaseTitleLabel2;
    private javax.swing.JButton skipButton1;
    private javax.swing.JButton skipButton2;
    private javax.swing.JButton skipButton3;
    private javax.swing.JButton wallButton0;
    private javax.swing.JButton wallButton1;
    private javax.swing.JButton wallButton2;
    private javax.swing.JButton wallButton3;
    private javax.swing.JButton wallButton4;
    private javax.swing.JButton wallButton5;
    // End of variables declaration//GEN-END:variables
    private GameMenu dialog;
    private UserMenu userDialog;
    private MultiMenu multiDialog;
    private ItsCurtains endScreen;
    private String[] players;

    private boolean networkGame;
    private NetworkHandler net;
    private boolean isHost;
    private boolean isActive;

    private javax.swing.JLabel[] handLabels;
    private javax.swing.JButton[][] cardButtons;
    private javax.swing.JPanel[] handPanels;
    private javax.swing.JLabel[] currentPlayerLabels;
    private javax.swing.JButton[] wallButtons;
    private javax.swing.JButton[] castleButtons;
    private javax.swing.JLabel[] scoreLabels;
    private javax.swing.JButton[] phaseButtons;
    private javax.swing.JButton[] skipButtons;
    private javax.swing.JLabel[] blipLabels;

    private GameState gameState;
    private javax.swing.JButton selectedCard;
    private javax.swing.JButton otherCard;

    private javax.swing.ImageIcon blueArcher;
    private javax.swing.ImageIcon redArcher;
    private javax.swing.ImageIcon greenArcher;
    private javax.swing.ImageIcon blueKnight;
    private javax.swing.ImageIcon redKnight;
    private javax.swing.ImageIcon greenKnight;
    private javax.swing.ImageIcon blueSwords;
    private javax.swing.ImageIcon redSwords;
    private javax.swing.ImageIcon greenSwords;
    private javax.swing.ImageIcon blueHero;
    private javax.swing.ImageIcon redHero;
    private javax.swing.ImageIcon greenHero;
    private javax.swing.ImageIcon anyArcher;
    private javax.swing.ImageIcon anyKnight;
    private javax.swing.ImageIcon anySwords;
    private javax.swing.ImageIcon anyHero;
    private javax.swing.ImageIcon blankCard;
    private javax.swing.ImageIcon turnInd;
    private javax.swing.ImageIcon wall;
    private javax.swing.ImageIcon deadWall;
    private javax.swing.ImageIcon rubbleWall;
    private javax.swing.ImageIcon keep;
    private javax.swing.ImageIcon deadKeep;
    private javax.swing.ImageIcon rubbleKeep;
    private javax.swing.ImageIcon missingCard;
    private javax.swing.ImageIcon barbarianCard;
    private javax.swing.ImageIcon timeStopCard;
    private javax.swing.ImageIcon timeSlapCard;
    private javax.swing.ImageIcon rewindCard;
    private javax.swing.ImageIcon turretCard;
    private javax.swing.ImageIcon deadBlip;
    private javax.swing.ImageIcon liveBlip;
    private javax.swing.ImageIcon activeNow;
    private javax.swing.ImageIcon p2pGame;
    private javax.swing.ImageIcon localGame;
    private javax.swing.ImageIcon spectator;

    public class ShapePanel extends javax.swing.JPanel implements java.awt.event.MouseListener {

        private final static int MONSTER_BORDER_OFFSET = 4;
        private final static int MONSTER_SPACER = 1;
        private final static int MONSTER_WIDTH = 30;
        private final static int MONSTER_HEIGHT = 30;
        private final static int MONSTER_ROW_SPACER = MONSTER_HEIGHT / 2;

        private java.awt.image.BufferedImage trollImage = null;
        private java.awt.image.BufferedImage orcImage = null;
        private java.awt.image.BufferedImage goblinImage = null;
        private java.awt.image.BufferedImage selectedImage = null;
        private java.awt.Graphics2D monsterGraphic;
        private ArrayList<MonsterShape> monstersDrawn;

        //private JPanel paletteLayer = null;
        ShapePanel() {

            super();
            this.addMouseListener(this);
            try {
                trollImage = ImageIO.read(this.getClass().getResource("/troll3030.png"));
                goblinImage = ImageIO.read(this.getClass().getResource("/goblin3030.png"));
                orcImage = ImageIO.read(this.getClass().getResource("/orc3030.png"));
                selectedImage = ImageIO.read(this.getClass().getResource("/selected3030.png"));
            } catch (IOException ex) {
                System.err.println("You dolt!  The monster image files are missing!");
            } finally {
                //
            }

            monstersDrawn = new ArrayList();
        }

        @Override
        public java.awt.Dimension getPreferredSize() {
            return new java.awt.Dimension(906, 500);
        }

        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {

            for (MonsterShape shape : monstersDrawn) {
                shape.setSelected(false);
                gameState.setSelectedMonster(-1);
            }
            System.out.println("GUI: mouseClicked deselected shapes.");

            java.awt.Point mousePoint = e.getPoint();
            for (MonsterShape shape : monstersDrawn) {
                if (shape.contains(mousePoint)) {
                    shape.setSelected(true);
                    gameState.setSelectedMonster(shape.getSerial());
                    System.out.println("GUI: mouseClicked shape selected.");
                    this.repaint();
                    return;
                }
            }
            /* Don't read this - just let it work...just let it happen */
            java.awt.Component[] comps = this.getParent().getComponents();
            if (comps[comps.length - 1] instanceof JPanel) {
                JPanel paletteLayer = (JPanel) comps[comps.length - 1];
                java.awt.Component[] squares = paletteLayer.getComponents();
                for (java.awt.Component square : squares) {
                    if (square instanceof JButton) {
                        JButton button = (JButton) square;
                        button.setBorderPainted(false);
                    }
                }
                java.awt.Component comp = SwingUtilities.getDeepestComponentAt(paletteLayer, e.getX(), e.getY());
                if (comp instanceof JButton) {
                    JButton button = (JButton) comp;
                    button.setBorderPainted(true);
                    
                }
            }
        }

        public void clearSelected() {
            for (MonsterShape shape : monstersDrawn) {
                shape.setSelected(false);
            }
        }

        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {

        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {

        }

        @Override
        public void mousePressed(java.awt.event.MouseEvent e) {

        }

        @Override
        public void mouseReleased(java.awt.event.MouseEvent e) {
        }

        public void updateMonsters() {
            if (gameState != null) {
                int[] monsterSerials = gameState.getMonsterSerialsInPlay();

                for (int serial : monsterSerials) {
                    if (serial == -1) {
                        break;
                    }
                    boolean found = false;
                    java.util.Iterator<MonsterShape> shapeIterator = monstersDrawn.iterator();
                    while (shapeIterator.hasNext()) {
                        MonsterShape shape = shapeIterator.next();
                        found = false;
                        if (serial == shape.getSerial()) {
                            shape.setLocation(boardToPoint(gameState.getMonsterX(gameState.getMonsterIndex(serial)),
                                    gameState.getMonsterY(gameState.getMonsterIndex(serial)), 0, shape));
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        MonsterShape newShape = new MonsterShape(serial);
                        newShape.setLocation(boardToPoint((gameState.getMonsterX(gameState.getMonsterIndex(serial))),
                                gameState.getMonsterY(gameState.getMonsterIndex(serial)), 0, newShape));
                        monstersDrawn.add(newShape);
                        System.out.println("GUI ShapePanel: added shape." + String.valueOf(newShape.getSerial()) + " at: " + String.valueOf(newShape.x) + ", " + String.valueOf(newShape.y));
                    }
                }
                this.repaint();
            }
        }

        //remove magic numbers
        public final java.awt.Point boardToPoint(int xBoard, int yBoard, int neighbors, MonsterShape shape) {

            int xCoord, yCoord;
            xCoord = (((xBoard * (this.getWidth() / 6)) + MONSTER_BORDER_OFFSET + MONSTER_SPACER)) - 149;
            xCoord += (neighbors * MONSTER_WIDTH + MONSTER_SPACER);
            // if (shape.getNeighbors() <= 5) {
            yCoord = (-yBoard + 4) * 92 + MONSTER_BORDER_OFFSET;
            // } else { //rewrite
            //   yCoord = ((-yBoard + 4) * (this.getHeight() / 5)) + MONSTER_BORDER_OFFSET + MONSTER_ROW_SPACER;
            // }
            if (yBoard == 0) {
                yCoord += 42; //Account for walls
            }
            for (MonsterShape mShape : monstersDrawn) {
                if (!(mShape.equals(shape))) {
                    if (mShape.contains(xCoord, yCoord)) {
                        neighbors++;
                        return boardToPoint(xBoard, yBoard, neighbors, shape);
                    }
                }
            }
            return new java.awt.Point(xCoord, yCoord);
        }

        @Override
        public void paintComponent(java.awt.Graphics g) {
            updateMonsters();
            super.paintComponent(g);
            monsterGraphic = (java.awt.Graphics2D) g;
            java.util.Iterator<MonsterShape> shapeIterator = monstersDrawn.iterator();
            while (shapeIterator.hasNext()) {
                MonsterShape shape = shapeIterator.next();
                if (gameState.getMonsterIndex(shape.getSerial()) == -1) {
                    shapeIterator.remove();
                } else {
                    shape.paintMonster(monsterGraphic, this);
                }
            }
            monsterGraphic.dispose();
        }

        public class MonsterShape extends java.awt.Rectangle {

            private final int serial;
            private boolean selected;

            MonsterShape(int serial) {
                super(MONSTER_WIDTH, MONSTER_HEIGHT);
                super.setLocation(boardToPoint(gameState.getMonsterX(gameState.getMonsterIndex(serial)),
                        gameState.getMonsterY(gameState.getMonsterY(gameState.getMonsterIndex(serial))), 0, this));
                this.serial = serial;
                this.selected = false;

            }

            public int getSerial() {
                return serial;

            }

            public void setSelected(boolean selected) {
                this.selected = selected;
            }

            public void paintMonster(java.awt.Graphics2D monsterGraphic, java.awt.image.ImageObserver observer) {
                java.util.Iterator<MonsterShape> iterator = monstersDrawn.iterator();
                switch (gameState.getMonsterName(gameState.getMonsterIndex(serial))) {
                    case "troll":
                        monsterGraphic.drawImage(trollImage, x, y, observer);
                        //    System.out.println("GUI: Drew troll at " + x + ", " + y);
                        break;
                    case "goblin":
                        monsterGraphic.drawImage(goblinImage, x, y, observer);
                        //    System.out.println("GUI: Drew goblin at " + x + ", " + y);
                        break;
                    case "orc":
                        monsterGraphic.drawImage(orcImage, x, y, observer);
                        //    System.out.println("GUI: Drew orc at " + x + ", " + y);
                        break;
                    default:
                    //   System.err.println("GUI: AWW!  Paint monster error - monster type not recognized!");
                }

                for (int i = 0; i < gameState.getMonsterHP(gameState.getMonsterIndex(serial)); i++) {
                    monsterGraphic.setColor(Color.WHITE);
                    monsterGraphic.fillOval((x + 6) + (6 * i), (y + 32), 5, 5);
                }

                if (selected) {
                    monsterGraphic.drawImage(selectedImage, x, y, observer);
                }
            }
        }
    }

}
