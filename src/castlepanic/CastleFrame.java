package castlepanic;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;

/**
 *
 * @author Benjamin
 */
public class CastleFrame extends javax.swing.JFrame {

    boolean spectating;
    static int startInt;
    boolean hosting;
    boolean client;
    NetworkHandler net;

    /**
     * Creates new form CastleFrame
     */
    public CastleFrame(int startCode) {
        initComponents();

        //SETS APPROPRIATE BOOLEANS BASED ON START CODES
        if(startCode == 1){
            System.out.println("You are a host!");
            hosting= true;
            client = false;
            net = new NetworkHandler();
            net.setLocalActive(true);
            net.openHosting();
        } else if (startCode == 2){
            System.out.println("You are a client!");
            hosting= false;
            client = true;
            net = new NetworkHandler();
            net.setLocalActive(false);
            net.connectToHost();
        }


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
            deadWall = new javax.swing.ImageIcon(getClass().getResource("/deadWall.png"));
            keep = new javax.swing.ImageIcon(getClass().getResource("/keep17070.png"));
            deadKeep = new javax.swing.ImageIcon(getClass().getResource("/deadKeep.png"));
            missingCard = new javax.swing.ImageIcon(getClass().getResource("/missingCard.png")); 
            barbarianCard = new javax.swing.ImageIcon(getClass().getResource("/barbarianCard.png")); 

        } catch (NullPointerException e) {
            System.err.println("GUI: You dolt - The card image files are missing!");
        }

        dialog = new GameMenu(this, true);
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            }

            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {

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

                if(hosting){
                    String[] clientNames = net.listenForNames();
                    //LISTENING FOR CLIENT NAMES SO THAT THEY CAN BE COMBINED
                    for(String current : clientNames){
                        System.out.println("Name Recieved");
                        System.out.println(current);
                    }
                    //PUTTING THE NAMES TOGETHER
                    String localName = players[0];
                    players  = new String[2];
                    players[0] = localName;
                    players[1] = clientNames[0];
                    //SEND THE NEW NAME LISTBACK
                    net.transmitNames(players);

                    players[0] = players[0]+" (HOST)";

                } else if(client){
                    //SEND LOCAL NAME
                    net.transmitNames(players);
                    //RECIEVE COMBINED NAME ARRAY
                    players = net.listenForNames();
                    //LISTEN FOR FINAL GAMESTATE
                    players[1] = players[1]+" (CLIENT)";

                }

                gameState = new GameState(players);

                if(hosting){
                    net.transmitGameState(gameState);
                }else if(client){
                    gameState = net.listenForState();
                }



                handLabels = new javax.swing.JLabel[]{handLabel0, handLabel1, handLabel2,
                    handLabel3, handLabel4, handLabel5};
                handPanels = new javax.swing.JPanel[]{handPanel0, handPanel1, handPanel2,
                    handPanel3, handPanel4, handPanel5};
                cardButtons = new javax.swing.JButton[gameState.getPlayers()][gameState.getMaxHandSize()];
                wallButtons = new javax.swing.JButton[]{wallButton0, wallButton1, wallButton2, wallButton3,
                    wallButton4, wallButton5};
                castleButtons = new javax.swing.JButton[]{castleButton0, castleButton1, castleButton2,
                    castleButton3, castleButton4, castleButton5};
                phaseLabels = new javax.swing.JLabel[]{phaseCurrentLabel1, phaseCurrentLabel2, phaseCurrentLabel3, phaseCurrentLabel4};
                currentPlayerLabels = new javax.swing.JLabel[]{handTurnLabel0, handTurnLabel1, handTurnLabel2,
                    handTurnLabel3, handTurnLabel4, handTurnLabel5};
                scoreLabels = new javax.swing.JLabel[]{handPointsLabel0, handPointsLabel1, handPointsLabel2, handPointsLabel3,
                    handPointsLabel4, handPointsLabel5};


                initializeGame();
                if(net.isSessionActive()){
                    net.updateLocalActive(gameState);
                }
                updateGame();

            }
        });
        dialog.setVisible(true);
        switch (dialog.getDecision()) {
            case ("Text"):
                
                TextUI textUI = new TextUI();
        }
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
        monsterProgBar.setMaximum(gameState.getUnplayedMonsters());
        this.updateGame();

    }



    public void updateGame() {
        if (gameState.getDeadYet()) {
        endScreen = new ItsCurtains (this, true);
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
            }
            if (gameState.getTowerStatus(i + 1)) { //FIX
                castleButtons[i].setIcon(keep);
            } else {
                castleButtons[i].setIcon(deadKeep);
            }
        }
        boardShapeLayer.updateMonsters();
        this.repaint();

    }

    public void updateMisc() {
        monsterProgBar.setValue(gameState.getUnplayedMonsters());
        monsterCountLabel.setText(String.valueOf(gameState.getUnplayedMonsters()) + "/"
                + String.valueOf(monsterProgBar.getMaximum()));
        for (javax.swing.JLabel label : currentPlayerLabels) {
            label.setIcon(null);
        }
        currentPlayerLabels[gameState.getCurrentPlayer()].setIcon(turnInd);

        for (javax.swing.JLabel label : phaseLabels) {
            label.setIcon(null);
        }
        phaseLabels[gameState.getCurrentPhase() - 1].setIcon(turnInd);

        for (int i = 0; i < gameState.getPlayers(); i++) {
            scoreLabels[i].setText(String.valueOf(gameState.getScore(i)));
        }
        switch (gameState.getCurrentPhase()) {
            case 1:
                commitButton.setText("Deal!");
                break;
            case 2:
                commitButton.setText("Discard!");
                break;
            case 3:
                commitButton.setText("Trade!");
                break;
            case 4:
                commitButton.setText("Attack!");
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
        phaseLabel1 = new javax.swing.JLabel();
        phaseLabel2 = new javax.swing.JLabel();
        phaseLabel3 = new javax.swing.JLabel();
        phaseLabel4 = new javax.swing.JLabel();
        phaseTitleLabel = new javax.swing.JLabel();
        titleLabel = new javax.swing.JLabel();
        phaseCurrentLabel2 = new javax.swing.JLabel();
        phaseCurrentLabel1 = new javax.swing.JLabel();
        phaseCurrentLabel3 = new javax.swing.JLabel();
        phaseCurrentLabel4 = new javax.swing.JLabel();
        controlPanel = new javax.swing.JPanel();
        monsterLabel = new javax.swing.JLabel();
        deckLabel = new javax.swing.JLabel();
        discardLabel = new javax.swing.JLabel();
        commitButton = new javax.swing.JButton();
        menuButton = new javax.swing.JButton();
        endPhaseButton = new javax.swing.JButton();
        rulesButton = new javax.swing.JButton();
        monsterProgLabel = new javax.swing.JLabel();
        deckNameLabel = new javax.swing.JLabel();
        discardNameLabel = new javax.swing.JLabel();
        monsterCountLabel = new javax.swing.JLabel();
        monsterProgBar = new javax.swing.JProgressBar();
        vertLabelPanel = new javax.swing.JPanel();
        vertForestLabel = new javax.swing.JLabel();
        vertArcherLabel = new javax.swing.JLabel();
        vertKnightLabel = new javax.swing.JLabel();
        vertSwordLabel = new javax.swing.JLabel();
        vertWallLabel = new javax.swing.JLabel();
        vertCastleLabel = new javax.swing.JLabel();
        horzLabelPanel = new javax.swing.JPanel();
        horzLabel0 = new javax.swing.JLabel();
        horzLabel1 = new javax.swing.JLabel();
        horzLabel2 = new javax.swing.JLabel();
        horzLabel3 = new javax.swing.JLabel();
        horzLabel4 = new javax.swing.JLabel();
        horzLabel5 = new javax.swing.JLabel();
        boardLayeredPanel = new javax.swing.JLayeredPane();
        boardBottomLayer = new javax.swing.JPanel();
        boardPaletteLayer = new javax.swing.JPanel();
        boardButton04 = new javax.swing.JButton();
        boardButton5 = new javax.swing.JButton();
        boardButton6 = new javax.swing.JButton();
        boardButton7 = new javax.swing.JButton();
        boardButton24 = new javax.swing.JButton();
        boardButton25 = new javax.swing.JButton();
        boardButton26 = new javax.swing.JButton();
        boardButton27 = new javax.swing.JButton();
        boardButton28 = new javax.swing.JButton();
        boardButton29 = new javax.swing.JButton();
        boardButton30 = new javax.swing.JButton();
        boardButton31 = new javax.swing.JButton();
        boardButton8 = new javax.swing.JButton();
        boardButton9 = new javax.swing.JButton();
        boardButton10 = new javax.swing.JButton();
        boardButton11 = new javax.swing.JButton();
        boardButton32 = new javax.swing.JButton();
        boardButton33 = new javax.swing.JButton();
        boardButton34 = new javax.swing.JButton();
        boardButton35 = new javax.swing.JButton();
        boardButton36 = new javax.swing.JButton();
        boardButton37 = new javax.swing.JButton();
        boardButton38 = new javax.swing.JButton();
        boardButton39 = new javax.swing.JButton();
        wallButton0 = new javax.swing.JButton();
        castleButton0 = new javax.swing.JButton();
        wallButton1 = new javax.swing.JButton();
        castleButton1 = new javax.swing.JButton();
        wallButton2 = new javax.swing.JButton();
        castleButton2 = new javax.swing.JButton();
        wallButton3 = new javax.swing.JButton();
        castleButton3 = new javax.swing.JButton();
        wallButton4 = new javax.swing.JButton();
        castleButton5 = new javax.swing.JButton();
        wallButton5 = new javax.swing.JButton();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 255, 255));
        setMinimumSize(new java.awt.Dimension(1200, 680));
        setPreferredSize(new java.awt.Dimension(1280, 710));

        phasePanel.setFocusable(false);
        phasePanel.setOpaque(false);

        phaseLabel1.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 12)); // NOI18N
        phaseLabel1.setText("Phase 1 - Draw");
        phaseLabel1.setToolTipText("");
        phaseLabel1.setMaximumSize(new java.awt.Dimension(80, 14));
        phaseLabel1.setMinimumSize(new java.awt.Dimension(80, 14));
        phaseLabel1.setPreferredSize(new java.awt.Dimension(80, 14));

        phaseLabel2.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 12)); // NOI18N
        phaseLabel2.setText("Phase 2 - Discard");
        phaseLabel2.setMaximumSize(new java.awt.Dimension(80, 14));
        phaseLabel2.setMinimumSize(new java.awt.Dimension(80, 14));
        phaseLabel2.setPreferredSize(new java.awt.Dimension(80, 14));

        phaseLabel3.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 12)); // NOI18N
        phaseLabel3.setText("Phase 3 - Trade");
        phaseLabel3.setMaximumSize(new java.awt.Dimension(80, 14));
        phaseLabel3.setMinimumSize(new java.awt.Dimension(80, 14));
        phaseLabel3.setPreferredSize(new java.awt.Dimension(80, 14));

        phaseLabel4.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 12)); // NOI18N
        phaseLabel4.setText("Phase 4 - Attack");
        phaseLabel4.setMaximumSize(new java.awt.Dimension(80, 14));
        phaseLabel4.setMinimumSize(new java.awt.Dimension(80, 14));
        phaseLabel4.setPreferredSize(new java.awt.Dimension(80, 14));

        phaseTitleLabel.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 12)); // NOI18N
        phaseTitleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        phaseTitleLabel.setText("PHASE GUIDE");
        phaseTitleLabel.setMaximumSize(new java.awt.Dimension(80, 14));
        phaseTitleLabel.setMinimumSize(new java.awt.Dimension(80, 14));
        phaseTitleLabel.setPreferredSize(new java.awt.Dimension(80, 14));

        titleLabel.setFont(new java.awt.Font("Tw Cen MT", 0, 12)); // NOI18N
        titleLabel.setText("BG Implementation v.0");

        phaseCurrentLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/turnSpot.png"))); // NOI18N

        javax.swing.GroupLayout phasePanelLayout = new javax.swing.GroupLayout(phasePanel);
        phasePanel.setLayout(phasePanelLayout);
        phasePanelLayout.setHorizontalGroup(
            phasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(phasePanelLayout.createSequentialGroup()
                .addGroup(phasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(phaseTitleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(titleLabel))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(phasePanelLayout.createSequentialGroup()
                .addGroup(phasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(phasePanelLayout.createSequentialGroup()
                        .addComponent(phaseLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(phaseCurrentLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(phasePanelLayout.createSequentialGroup()
                        .addGroup(phasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(phaseLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                            .addComponent(phaseLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(phaseLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(5, 5, 5)
                        .addGroup(phasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(phaseCurrentLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(phaseCurrentLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(phaseCurrentLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        phasePanelLayout.setVerticalGroup(
            phasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(phasePanelLayout.createSequentialGroup()
                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(phaseTitleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(phasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(phasePanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(phaseLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(phasePanelLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(phaseCurrentLabel1)))
                .addGap(10, 10, 10)
                .addGroup(phasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(phaseLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(phasePanelLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(phaseCurrentLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(phasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(phasePanelLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(phaseLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(phasePanelLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(phaseCurrentLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(phasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(phasePanelLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(phaseLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(phasePanelLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(phaseCurrentLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        controlPanel.setFocusable(false);
        controlPanel.setOpaque(false);

        monsterLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/troll3030.png"))); // NOI18N

        deckLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cardBack6080.png"))); // NOI18N

        discardLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cardBack6080.png"))); // NOI18N

        commitButton.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 12)); // NOI18N
        commitButton.setForeground(new java.awt.Color(255, 0, 0));
        commitButton.setText("DEAL!");
        commitButton.setMaximumSize(new java.awt.Dimension(115, 40));
        commitButton.setMinimumSize(new java.awt.Dimension(115, 40));
        commitButton.setPreferredSize(new java.awt.Dimension(115, 40));
        commitButton.setRequestFocusEnabled(false);
        commitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commitClicked(evt);
            }
        });

        menuButton.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 12)); // NOI18N
        menuButton.setText("Menu");
        menuButton.setMaximumSize(new java.awt.Dimension(115, 40));
        menuButton.setMinimumSize(new java.awt.Dimension(115, 40));
        menuButton.setPreferredSize(new java.awt.Dimension(115, 40));
        menuButton.setRequestFocusEnabled(false);
        menuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuClicked(evt);
            }
        });

        endPhaseButton.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 12)); // NOI18N
        endPhaseButton.setText("End Phase");
        endPhaseButton.setMaximumSize(new java.awt.Dimension(115, 40));
        endPhaseButton.setMinimumSize(new java.awt.Dimension(115, 40));
        endPhaseButton.setPreferredSize(new java.awt.Dimension(115, 40));
        endPhaseButton.setRequestFocusEnabled(false);
        endPhaseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endPhaseClicked(evt);
            }
        });

        rulesButton.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 12)); // NOI18N
        rulesButton.setText("Rules");
        rulesButton.setMaximumSize(new java.awt.Dimension(115, 40));
        rulesButton.setMinimumSize(new java.awt.Dimension(115, 40));
        rulesButton.setPreferredSize(new java.awt.Dimension(115, 40));
        rulesButton.setRequestFocusEnabled(false);
        rulesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rulesClicked(evt);
            }
        });

        monsterProgLabel.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 10)); // NOI18N
        monsterProgLabel.setText("Monsters Remaining:");

        deckNameLabel.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 10)); // NOI18N
        deckNameLabel.setText("Deck");

        discardNameLabel.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 10)); // NOI18N
        discardNameLabel.setText("Discard");

        monsterCountLabel.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 10)); // NOI18N
        monsterCountLabel.setForeground(new java.awt.Color(255, 0, 0));
        monsterCountLabel.setText("42/42");

        monsterProgBar.setForeground(new java.awt.Color(255, 0, 0));
        monsterProgBar.setMaximum(0);
        monsterProgBar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        monsterProgBar.setPreferredSize(new java.awt.Dimension(200, 20));

        javax.swing.GroupLayout controlPanelLayout = new javax.swing.GroupLayout(controlPanel);
        controlPanel.setLayout(controlPanelLayout);
        controlPanelLayout.setHorizontalGroup(
            controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(controlPanelLayout.createSequentialGroup()
                        .addComponent(commitButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(endPhaseButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(controlPanelLayout.createSequentialGroup()
                        .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(deckLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(deckNameLabel))
                        .addGap(46, 46, 46)
                        .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(discardLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(discardNameLabel))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(controlPanelLayout.createSequentialGroup()
                        .addComponent(menuButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rulesButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(controlPanelLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(monsterLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(controlPanelLayout.createSequentialGroup()
                                .addComponent(monsterProgLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(monsterCountLabel)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(monsterProgBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        controlPanelLayout.setVerticalGroup(
            controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(monsterLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(controlPanelLayout.createSequentialGroup()
                        .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(monsterProgLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(monsterCountLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(monsterProgBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(discardNameLabel)
                    .addComponent(deckNameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(discardLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deckLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(endPhaseButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(commitButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(menuButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rulesButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        vertForestLabel.setText("Forest");

        vertArcherLabel.setText("Archer");

        vertKnightLabel.setText("Knight");

        vertSwordLabel.setText("Swords.");

        vertWallLabel.setText("Wall");

        vertCastleLabel.setText("Castle");

        javax.swing.GroupLayout vertLabelPanelLayout = new javax.swing.GroupLayout(vertLabelPanel);
        vertLabelPanel.setLayout(vertLabelPanelLayout);
        vertLabelPanelLayout.setHorizontalGroup(
            vertLabelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vertLabelPanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(vertLabelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(vertForestLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(vertArcherLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(vertKnightLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(vertSwordLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(vertWallLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(vertCastleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        vertLabelPanelLayout.setVerticalGroup(
            vertLabelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vertLabelPanelLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(vertForestLabel)
                .addGap(56, 56, 56)
                .addComponent(vertArcherLabel)
                .addGap(56, 56, 56)
                .addComponent(vertKnightLabel)
                .addGap(56, 56, 56)
                .addComponent(vertSwordLabel)
                .addGap(41, 41, 41)
                .addComponent(vertWallLabel)
                .addGap(26, 26, 26)
                .addComponent(vertCastleLabel)
                .addContainerGap())
        );

        horzLabel0.setText("0");

        horzLabel1.setText("1");

        horzLabel2.setText("2");

        horzLabel3.setText("3");

        horzLabel4.setText("4");

        horzLabel5.setText("5");

        javax.swing.GroupLayout horzLabelPanelLayout = new javax.swing.GroupLayout(horzLabelPanel);
        horzLabelPanel.setLayout(horzLabelPanelLayout);
        horzLabelPanelLayout.setHorizontalGroup(
            horzLabelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(horzLabelPanelLayout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(horzLabel0)
                .addGap(171, 171, 171)
                .addComponent(horzLabel1)
                .addGap(171, 171, 171)
                .addComponent(horzLabel2)
                .addGap(171, 171, 171)
                .addComponent(horzLabel3)
                .addGap(171, 171, 171)
                .addComponent(horzLabel4)
                .addGap(171, 171, 171)
                .addComponent(horzLabel5)
                .addGap(78, 78, 78))
        );
        horzLabelPanelLayout.setVerticalGroup(
            horzLabelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(horzLabelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(horzLabel0)
                .addComponent(horzLabel1)
                .addComponent(horzLabel2)
                .addComponent(horzLabel3)
                .addComponent(horzLabel4)
                .addComponent(horzLabel5))
        );

        boardLayeredPanel.setPreferredSize(new java.awt.Dimension(1020, 380));

        boardBottomLayer.setPreferredSize(new java.awt.Dimension(1020, 380));

        javax.swing.GroupLayout boardBottomLayerLayout = new javax.swing.GroupLayout(boardBottomLayer);
        boardBottomLayer.setLayout(boardBottomLayerLayout);
        boardBottomLayerLayout.setHorizontalGroup(
            boardBottomLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1020, Short.MAX_VALUE)
        );
        boardBottomLayerLayout.setVerticalGroup(
            boardBottomLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        boardPaletteLayer.setOpaque(false);
        boardPaletteLayer.setPreferredSize(new java.awt.Dimension(1020, 380));

        boardButton04.setIcon(new javax.swing.ImageIcon(getClass().getResource("/redButtonTrans55.png"))); // NOI18N
        boardButton04.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.red, null));
        boardButton04.setContentAreaFilled(false);
        boardButton04.setFocusPainted(false);
        boardButton04.setMaximumSize(new java.awt.Dimension(170, 70));
        boardButton04.setMinimumSize(new java.awt.Dimension(170, 70));
        boardButton04.setPreferredSize(new java.awt.Dimension(170, 70));
        boardButton04.setRequestFocusEnabled(false);
        boardButton04.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton04boardButtonClicked(evt);
            }
        });

        boardButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/redButtonTrans55.png"))); // NOI18N
        boardButton5.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.red, null));
        boardButton5.setContentAreaFilled(false);
        boardButton5.setFocusPainted(false);
        boardButton5.setMaximumSize(new java.awt.Dimension(170, 70));
        boardButton5.setMinimumSize(new java.awt.Dimension(170, 70));
        boardButton5.setPreferredSize(new java.awt.Dimension(170, 70));
        boardButton5.setRequestFocusEnabled(false);
        boardButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton5boardButtonClicked(evt);
            }
        });

        boardButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/redButtonTrans55.png"))); // NOI18N
        boardButton6.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.red, null));
        boardButton6.setContentAreaFilled(false);
        boardButton6.setFocusPainted(false);
        boardButton6.setMaximumSize(new java.awt.Dimension(170, 70));
        boardButton6.setMinimumSize(new java.awt.Dimension(170, 70));
        boardButton6.setPreferredSize(new java.awt.Dimension(170, 70));
        boardButton6.setRequestFocusEnabled(false);
        boardButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton6boardButtonClicked(evt);
            }
        });

        boardButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/redButtonTrans55.png"))); // NOI18N
        boardButton7.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.red, null));
        boardButton7.setContentAreaFilled(false);
        boardButton7.setFocusPainted(false);
        boardButton7.setMaximumSize(new java.awt.Dimension(170, 70));
        boardButton7.setMinimumSize(new java.awt.Dimension(170, 70));
        boardButton7.setPreferredSize(new java.awt.Dimension(170, 70));
        boardButton7.setRequestFocusEnabled(false);
        boardButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton7boardButtonClicked(evt);
            }
        });

        boardButton24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/greenButtonTrans55.png"))); // NOI18N
        boardButton24.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.green, null));
        boardButton24.setContentAreaFilled(false);
        boardButton24.setMaximumSize(new java.awt.Dimension(170, 70));
        boardButton24.setMinimumSize(new java.awt.Dimension(170, 70));
        boardButton24.setPreferredSize(new java.awt.Dimension(170, 70));
        boardButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton24boardButtonClicked(evt);
            }
        });

        boardButton25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/greenButtonTrans55.png"))); // NOI18N
        boardButton25.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.green, null));
        boardButton25.setContentAreaFilled(false);
        boardButton25.setMaximumSize(new java.awt.Dimension(170, 70));
        boardButton25.setMinimumSize(new java.awt.Dimension(170, 70));
        boardButton25.setPreferredSize(new java.awt.Dimension(170, 70));
        boardButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton25boardButtonClicked(evt);
            }
        });

        boardButton26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/greenButtonTrans55.png"))); // NOI18N
        boardButton26.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.green, null));
        boardButton26.setContentAreaFilled(false);
        boardButton26.setMaximumSize(new java.awt.Dimension(170, 70));
        boardButton26.setMinimumSize(new java.awt.Dimension(170, 70));
        boardButton26.setPreferredSize(new java.awt.Dimension(170, 70));
        boardButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton26boardButtonClicked(evt);
            }
        });

        boardButton27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/greenButtonTrans55.png"))); // NOI18N
        boardButton27.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.green, null));
        boardButton27.setContentAreaFilled(false);
        boardButton27.setMaximumSize(new java.awt.Dimension(170, 70));
        boardButton27.setMinimumSize(new java.awt.Dimension(170, 70));
        boardButton27.setPreferredSize(new java.awt.Dimension(170, 70));
        boardButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton27boardButtonClicked(evt);
            }
        });

        boardButton28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/greenButtonTrans55.png"))); // NOI18N
        boardButton28.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.green, null));
        boardButton28.setContentAreaFilled(false);
        boardButton28.setMaximumSize(new java.awt.Dimension(170, 70));
        boardButton28.setMinimumSize(new java.awt.Dimension(170, 70));
        boardButton28.setPreferredSize(new java.awt.Dimension(170, 70));
        boardButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton28boardButtonClicked(evt);
            }
        });

        boardButton29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/greenButtonTrans55.png"))); // NOI18N
        boardButton29.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.green, null));
        boardButton29.setContentAreaFilled(false);
        boardButton29.setMaximumSize(new java.awt.Dimension(170, 70));
        boardButton29.setMinimumSize(new java.awt.Dimension(170, 70));
        boardButton29.setPreferredSize(new java.awt.Dimension(170, 70));
        boardButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton29boardButtonClicked(evt);
            }
        });

        boardButton30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/greenButtonTrans55.png"))); // NOI18N
        boardButton30.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.green, null));
        boardButton30.setContentAreaFilled(false);
        boardButton30.setMaximumSize(new java.awt.Dimension(170, 70));
        boardButton30.setMinimumSize(new java.awt.Dimension(170, 70));
        boardButton30.setPreferredSize(new java.awt.Dimension(170, 70));
        boardButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton30boardButtonClicked(evt);
            }
        });

        boardButton31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/greenButtonTrans55.png"))); // NOI18N
        boardButton31.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.green, null));
        boardButton31.setContentAreaFilled(false);
        boardButton31.setMaximumSize(new java.awt.Dimension(170, 70));
        boardButton31.setMinimumSize(new java.awt.Dimension(170, 70));
        boardButton31.setPreferredSize(new java.awt.Dimension(170, 70));
        boardButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton31boardButtonClicked(evt);
            }
        });

        boardButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/redButtonTrans55.png"))); // NOI18N
        boardButton8.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.red, null));
        boardButton8.setContentAreaFilled(false);
        boardButton8.setFocusPainted(false);
        boardButton8.setMaximumSize(new java.awt.Dimension(170, 70));
        boardButton8.setMinimumSize(new java.awt.Dimension(170, 70));
        boardButton8.setPreferredSize(new java.awt.Dimension(170, 70));
        boardButton8.setRequestFocusEnabled(false);
        boardButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton8boardButtonClicked(evt);
            }
        });

        boardButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/redButtonTrans55.png"))); // NOI18N
        boardButton9.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.red, null));
        boardButton9.setContentAreaFilled(false);
        boardButton9.setFocusPainted(false);
        boardButton9.setMaximumSize(new java.awt.Dimension(170, 70));
        boardButton9.setMinimumSize(new java.awt.Dimension(170, 70));
        boardButton9.setPreferredSize(new java.awt.Dimension(170, 70));
        boardButton9.setRequestFocusEnabled(false);
        boardButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton9boardButtonClicked(evt);
            }
        });

        boardButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/redButtonTrans55.png"))); // NOI18N
        boardButton10.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.red, null));
        boardButton10.setContentAreaFilled(false);
        boardButton10.setFocusPainted(false);
        boardButton10.setMaximumSize(new java.awt.Dimension(170, 70));
        boardButton10.setMinimumSize(new java.awt.Dimension(170, 70));
        boardButton10.setPreferredSize(new java.awt.Dimension(170, 70));
        boardButton10.setRequestFocusEnabled(false);
        boardButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton10boardButtonClicked(evt);
            }
        });

        boardButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/redButtonTrans55.png"))); // NOI18N
        boardButton11.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.red, null));
        boardButton11.setContentAreaFilled(false);
        boardButton11.setFocusPainted(false);
        boardButton11.setMaximumSize(new java.awt.Dimension(170, 70));
        boardButton11.setMinimumSize(new java.awt.Dimension(170, 70));
        boardButton11.setPreferredSize(new java.awt.Dimension(170, 70));
        boardButton11.setRequestFocusEnabled(false);
        boardButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton11boardButtonClicked(evt);
            }
        });

        boardButton32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/blueButtonTrans55.png"))); // NOI18N
        boardButton32.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.blue, null));
        boardButton32.setContentAreaFilled(false);
        boardButton32.setFocusPainted(false);
        boardButton32.setFocusable(false);
        boardButton32.setMaximumSize(new java.awt.Dimension(170, 70));
        boardButton32.setMinimumSize(new java.awt.Dimension(170, 70));
        boardButton32.setPreferredSize(new java.awt.Dimension(170, 70));
        boardButton32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton32boardButtonClicked(evt);
            }
        });

        boardButton33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/blueButtonTrans55.png"))); // NOI18N
        boardButton33.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.blue, null));
        boardButton33.setContentAreaFilled(false);
        boardButton33.setFocusPainted(false);
        boardButton33.setFocusable(false);
        boardButton33.setMaximumSize(new java.awt.Dimension(170, 70));
        boardButton33.setMinimumSize(new java.awt.Dimension(170, 70));
        boardButton33.setPreferredSize(new java.awt.Dimension(170, 70));
        boardButton33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton33boardButtonClicked(evt);
            }
        });

        boardButton34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/blueButtonTrans55.png"))); // NOI18N
        boardButton34.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.blue, null));
        boardButton34.setContentAreaFilled(false);
        boardButton34.setFocusPainted(false);
        boardButton34.setFocusable(false);
        boardButton34.setMaximumSize(new java.awt.Dimension(170, 70));
        boardButton34.setMinimumSize(new java.awt.Dimension(170, 70));
        boardButton34.setPreferredSize(new java.awt.Dimension(170, 70));
        boardButton34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton34boardButtonClicked(evt);
            }
        });

        boardButton35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/blueButtonTrans55.png"))); // NOI18N
        boardButton35.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.blue, null));
        boardButton35.setContentAreaFilled(false);
        boardButton35.setFocusPainted(false);
        boardButton35.setFocusable(false);
        boardButton35.setMaximumSize(new java.awt.Dimension(170, 70));
        boardButton35.setMinimumSize(new java.awt.Dimension(170, 70));
        boardButton35.setPreferredSize(new java.awt.Dimension(170, 70));
        boardButton35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton35boardButtonClicked(evt);
            }
        });

        boardButton36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/blueButtonTrans55.png"))); // NOI18N
        boardButton36.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.blue, null));
        boardButton36.setContentAreaFilled(false);
        boardButton36.setFocusPainted(false);
        boardButton36.setFocusable(false);
        boardButton36.setMaximumSize(new java.awt.Dimension(170, 70));
        boardButton36.setMinimumSize(new java.awt.Dimension(170, 70));
        boardButton36.setPreferredSize(new java.awt.Dimension(170, 70));
        boardButton36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButtonClicked(evt);
            }
        });

        boardButton37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/blueButtonTrans55.png"))); // NOI18N
        boardButton37.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.blue, null));
        boardButton37.setContentAreaFilled(false);
        boardButton37.setFocusPainted(false);
        boardButton37.setFocusable(false);
        boardButton37.setMaximumSize(new java.awt.Dimension(170, 70));
        boardButton37.setMinimumSize(new java.awt.Dimension(170, 70));
        boardButton37.setPreferredSize(new java.awt.Dimension(170, 70));
        boardButton37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton37boardButtonClicked(evt);
            }
        });

        boardButton38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/blueButtonTrans55.png"))); // NOI18N
        boardButton38.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.blue, null));
        boardButton38.setContentAreaFilled(false);
        boardButton38.setFocusPainted(false);
        boardButton38.setFocusable(false);
        boardButton38.setMaximumSize(new java.awt.Dimension(170, 70));
        boardButton38.setMinimumSize(new java.awt.Dimension(170, 70));
        boardButton38.setPreferredSize(new java.awt.Dimension(170, 70));
        boardButton38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton38boardButtonClicked(evt);
            }
        });

        boardButton39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/blueButtonTrans55.png"))); // NOI18N
        boardButton39.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.blue, null));
        boardButton39.setContentAreaFilled(false);
        boardButton39.setFocusPainted(false);
        boardButton39.setFocusable(false);
        boardButton39.setMaximumSize(new java.awt.Dimension(170, 70));
        boardButton39.setMinimumSize(new java.awt.Dimension(170, 70));
        boardButton39.setPreferredSize(new java.awt.Dimension(170, 70));
        boardButton39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardButton39boardButtonClicked(evt);
            }
        });

        wallButton0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wall17040.png"))); // NOI18N
        wallButton0.setActionCommand("wall1");
        wallButton0.setContentAreaFilled(false);
        wallButton0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wallButton0wallButtonClicked(evt);
            }
        });

        castleButton0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/keep17070.png"))); // NOI18N
        castleButton0.setActionCommand("board00");
        castleButton0.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.black, null));
        castleButton0.setContentAreaFilled(false);
        castleButton0.setMaximumSize(new java.awt.Dimension(170, 70));
        castleButton0.setMinimumSize(new java.awt.Dimension(170, 70));
        castleButton0.setPreferredSize(new java.awt.Dimension(130, 70));
        castleButton0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                castleButtonClicked(evt);
            }
        });

        wallButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wall17040.png"))); // NOI18N
        wallButton1.setActionCommand("wall1");
        wallButton1.setContentAreaFilled(false);
        wallButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wallButton1wallButtonClicked(evt);
            }
        });

        castleButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/keep17070.png"))); // NOI18N
        castleButton1.setActionCommand("board00");
        castleButton1.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.black, null));
        castleButton1.setContentAreaFilled(false);
        castleButton1.setMaximumSize(new java.awt.Dimension(170, 70));
        castleButton1.setMinimumSize(new java.awt.Dimension(170, 70));
        castleButton1.setPreferredSize(new java.awt.Dimension(130, 70));
        castleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                castleButtonClicked(evt);
            }
        });

        wallButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wall17040.png"))); // NOI18N
        wallButton2.setActionCommand("wall1");
        wallButton2.setContentAreaFilled(false);
        wallButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wallButton2wallButtonClicked(evt);
            }
        });

        castleButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/keep17070.png"))); // NOI18N
        castleButton2.setActionCommand("board00");
        castleButton2.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.black, null));
        castleButton2.setContentAreaFilled(false);
        castleButton2.setMaximumSize(new java.awt.Dimension(170, 70));
        castleButton2.setMinimumSize(new java.awt.Dimension(170, 70));
        castleButton2.setPreferredSize(new java.awt.Dimension(130, 70));
        castleButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                castleButtonClicked(evt);
            }
        });

        wallButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wall17040.png"))); // NOI18N
        wallButton3.setActionCommand("wall1");
        wallButton3.setContentAreaFilled(false);
        wallButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wallButton3wallButtonClicked(evt);
            }
        });

        castleButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/keep17070.png"))); // NOI18N
        castleButton3.setActionCommand("board00");
        castleButton3.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.black, null));
        castleButton3.setContentAreaFilled(false);
        castleButton3.setMaximumSize(new java.awt.Dimension(170, 70));
        castleButton3.setMinimumSize(new java.awt.Dimension(170, 70));
        castleButton3.setPreferredSize(new java.awt.Dimension(130, 70));
        castleButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                castleButtonClicked(evt);
            }
        });

        wallButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wall17040.png"))); // NOI18N
        wallButton4.setActionCommand("wall1");
        wallButton4.setContentAreaFilled(false);
        wallButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wallButton4wallButtonClicked(evt);
            }
        });

        castleButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/keep17070.png"))); // NOI18N
        castleButton5.setActionCommand("board00");
        castleButton5.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.black, null));
        castleButton5.setContentAreaFilled(false);
        castleButton5.setMaximumSize(new java.awt.Dimension(170, 70));
        castleButton5.setMinimumSize(new java.awt.Dimension(170, 70));
        castleButton5.setPreferredSize(new java.awt.Dimension(130, 70));
        castleButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                castleButton5boardButtonClicked(evt);
            }
        });

        wallButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wall17040.png"))); // NOI18N
        wallButton5.setActionCommand("wall1");
        wallButton5.setContentAreaFilled(false);
        wallButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wallButton5wallButtonClicked(evt);
            }
        });

        castleButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/keep17070.png"))); // NOI18N
        castleButton4.setActionCommand("board00");
        castleButton4.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.black, null));
        castleButton4.setContentAreaFilled(false);
        castleButton4.setMaximumSize(new java.awt.Dimension(170, 70));
        castleButton4.setMinimumSize(new java.awt.Dimension(170, 70));
        castleButton4.setPreferredSize(new java.awt.Dimension(130, 70));
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
                .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(castleButton0, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(wallButton0, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(boardButton04, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(boardButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(boardButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(boardButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(boardButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(boardButton9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(boardButton10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(boardButton11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(castleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(wallButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0)
                .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(boardPaletteLayerLayout.createSequentialGroup()
                        .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(boardPaletteLayerLayout.createSequentialGroup()
                                .addComponent(wallButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(wallButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(boardPaletteLayerLayout.createSequentialGroup()
                                .addComponent(castleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(castleButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, 0)
                        .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(wallButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(castleButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(castleButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(wallButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(boardPaletteLayerLayout.createSequentialGroup()
                        .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(boardButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(boardButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(boardButton30, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(boardButton31, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(boardButton26, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(boardButton27, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(boardButton29, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(boardButton28, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(boardButton32, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(boardButton33, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(boardButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(boardButton35, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(boardButton36, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(boardButton37, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(boardButton38, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(boardButton39, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        boardPaletteLayerLayout.setVerticalGroup(
            boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(boardPaletteLayerLayout.createSequentialGroup()
                .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(boardPaletteLayerLayout.createSequentialGroup()
                        .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(boardPaletteLayerLayout.createSequentialGroup()
                                .addComponent(boardButton28, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(boardButton29, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, boardPaletteLayerLayout.createSequentialGroup()
                                .addComponent(boardButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(boardButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(boardPaletteLayerLayout.createSequentialGroup()
                                .addComponent(boardButton30, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(boardButton31, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(boardPaletteLayerLayout.createSequentialGroup()
                                .addComponent(boardButton26, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(boardButton27, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(boardPaletteLayerLayout.createSequentialGroup()
                        .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(boardButton04, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(boardButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(boardPaletteLayerLayout.createSequentialGroup()
                                .addComponent(boardButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(boardButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(boardButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(boardPaletteLayerLayout.createSequentialGroup()
                                .addComponent(boardButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(boardButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(boardButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(boardPaletteLayerLayout.createSequentialGroup()
                        .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(boardPaletteLayerLayout.createSequentialGroup()
                                .addComponent(boardButton39, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(boardButton38, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, boardPaletteLayerLayout.createSequentialGroup()
                                .addComponent(boardButton32, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(boardButton33, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(boardPaletteLayerLayout.createSequentialGroup()
                                .addComponent(boardButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(boardButton35, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(boardPaletteLayerLayout.createSequentialGroup()
                                .addComponent(boardButton37, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(boardButton36, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(boardPaletteLayerLayout.createSequentialGroup()
                        .addComponent(wallButton0, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(castleButton0, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(boardPaletteLayerLayout.createSequentialGroup()
                        .addComponent(wallButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(castleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(boardPaletteLayerLayout.createSequentialGroup()
                        .addComponent(wallButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(castleButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(boardPaletteLayerLayout.createSequentialGroup()
                        .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(wallButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(wallButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(wallButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(boardPaletteLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(castleButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(castleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(castleButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );

        boardShapeLayer.setOpaque(false);
        boardShapeLayer.setPreferredSize(new java.awt.Dimension(1020, 380));

        javax.swing.GroupLayout boardShapeLayerLayout = new javax.swing.GroupLayout(boardShapeLayer);
        boardShapeLayer.setLayout(boardShapeLayerLayout);
        boardShapeLayerLayout.setHorizontalGroup(
            boardShapeLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1020, Short.MAX_VALUE)
        );
        boardShapeLayerLayout.setVerticalGroup(
            boardShapeLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 390, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout boardLayeredPanelLayout = new javax.swing.GroupLayout(boardLayeredPanel);
        boardLayeredPanel.setLayout(boardLayeredPanelLayout);
        boardLayeredPanelLayout.setHorizontalGroup(
            boardLayeredPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(boardBottomLayer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(boardLayeredPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(boardLayeredPanelLayout.createSequentialGroup()
                    .addComponent(boardPaletteLayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(boardLayeredPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(boardShapeLayer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        boardLayeredPanelLayout.setVerticalGroup(
            boardLayeredPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(boardBottomLayer, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)
            .addGroup(boardLayeredPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(boardLayeredPanelLayout.createSequentialGroup()
                    .addComponent(boardPaletteLayer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(boardLayeredPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(boardLayeredPanelLayout.createSequentialGroup()
                    .addComponent(boardShapeLayer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        boardLayeredPanel.setLayer(boardBottomLayer, 1);
        boardLayeredPanel.setLayer(boardPaletteLayer, 2);
        boardLayeredPanel.setLayer(boardShapeLayer, 3);

        handPanel5.setMaximumSize(new java.awt.Dimension(460, 100));
        handPanel5.setMinimumSize(new java.awt.Dimension(460, 100));
        handPanel5.setPreferredSize(new java.awt.Dimension(460, 90));

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

        handTurnLabel5.setName("turnLabel"); // NOI18N
        handTurnLabel5.setPreferredSize(new java.awt.Dimension(15, 20));

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
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
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
                        .addContainerGap()
                        .addComponent(handLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(handTurnLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(handPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(handScoreLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(handPointsLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        handPanel1.setMaximumSize(new java.awt.Dimension(460, 100));
        handPanel1.setMinimumSize(new java.awt.Dimension(460, 100));
        handPanel1.setPreferredSize(new java.awt.Dimension(460, 90));

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

        handTurnLabel1.setName("turnLabel"); // NOI18N
        handTurnLabel1.setPreferredSize(new java.awt.Dimension(15, 20));

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
                    .addGroup(handPanel1Layout.createSequentialGroup()
                        .addGroup(handPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(handTurnLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(handPanel1Layout.createSequentialGroup()
                                .addComponent(handScoreLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(handPointsLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        handPanel1Layout.setVerticalGroup(
            handPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(handPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(handPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cardButton12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(handPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cardButton10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cardButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cardButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cardButton14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cardButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(handPanel1Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(handLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(handTurnLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(handPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(handScoreLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(handPointsLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        handPanel4.setMaximumSize(new java.awt.Dimension(460, 100));
        handPanel4.setMinimumSize(new java.awt.Dimension(460, 100));
        handPanel4.setPreferredSize(new java.awt.Dimension(460, 90));

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

        handTurnLabel4.setName("turnLabel"); // NOI18N
        handTurnLabel4.setPreferredSize(new java.awt.Dimension(15, 20));

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
                .addContainerGap())
        );
        handPanel4Layout.setVerticalGroup(
            handPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(handPanel4Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(handPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cardButton40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cardButton41, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cardButton42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cardButton43, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cardButton44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cardButton45, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, handPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(handLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(handTurnLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(handPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(handScoreLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(handPointsLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );

        handPanel2.setMaximumSize(new java.awt.Dimension(460, 90));
        handPanel2.setMinimumSize(new java.awt.Dimension(460, 90));
        handPanel2.setPreferredSize(new java.awt.Dimension(460, 90));

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

        handTurnLabel2.setName("turnLabel"); // NOI18N
        handTurnLabel2.setPreferredSize(new java.awt.Dimension(15, 20));

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
                .addContainerGap())
        );
        handPanel2Layout.setVerticalGroup(
            handPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(handPanel2Layout.createSequentialGroup()
                .addGroup(handPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(handPanel2Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(handPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cardButton20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cardButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cardButton22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cardButton23, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cardButton24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cardButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(handPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(handLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(handTurnLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(handPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(handScoreLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(handPointsLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        handPanel0.setMaximumSize(new java.awt.Dimension(460, 100));
        handPanel0.setMinimumSize(new java.awt.Dimension(460, 100));
        handPanel0.setName(""); // NOI18N
        handPanel0.setPreferredSize(new java.awt.Dimension(460, 90));

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
        handTurnLabel0.setName("turnLabel"); // NOI18N

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
                    .addComponent(handLabel0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(handPanel0Layout.createSequentialGroup()
                        .addGroup(handPanel0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(handTurnLabel0)
                            .addGroup(handPanel0Layout.createSequentialGroup()
                                .addComponent(handScoreLabel0, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(handPointsLabel0, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        handPanel0Layout.setVerticalGroup(
            handPanel0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(handPanel0Layout.createSequentialGroup()
                .addGroup(handPanel0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(handPanel0Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(handLabel0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(handTurnLabel0)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(handPanel0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(handScoreLabel0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(handPointsLabel0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(handPanel0Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(handPanel0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cardButton00, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cardbutton01, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cardButton02, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cardbutton03, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cardButton04, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cardButton05, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        handPanel3.setMaximumSize(new java.awt.Dimension(460, 100));
        handPanel3.setMinimumSize(new java.awt.Dimension(460, 100));
        handPanel3.setPreferredSize(new java.awt.Dimension(460, 90));

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

        handTurnLabel3.setName("turnLabel"); // NOI18N
        handTurnLabel3.setPreferredSize(new java.awt.Dimension(15, 20));

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
                            .addComponent(handTurnLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(handPanel3Layout.createSequentialGroup()
                                .addComponent(handScoreLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(handPointsLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        handPanel3Layout.setVerticalGroup(
            handPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(handPanel3Layout.createSequentialGroup()
                .addGroup(handPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(handPanel3Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(handPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cardButton30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cardButton31, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cardButton32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cardButton33, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cardButton34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cardButton35, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(handPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(handLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(handTurnLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(handPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(handScoreLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(handPointsLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout handPanelLayout = new javax.swing.GroupLayout(handPanel);
        handPanel.setLayout(handPanelLayout);
        handPanelLayout.setHorizontalGroup(
            handPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(handPanelLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(handPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, handPanelLayout.createSequentialGroup()
                        .addComponent(handPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(handPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(handPanelLayout.createSequentialGroup()
                        .addGroup(handPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(handPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(handPanel0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(handPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(handPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(handPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(5, 5, 5))
        );
        handPanelLayout.setVerticalGroup(
            handPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(handPanelLayout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(handPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(handPanelLayout.createSequentialGroup()
                        .addComponent(handPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(handPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(handPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(handPanelLayout.createSequentialGroup()
                        .addComponent(handPanel0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(handPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(handPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(3, 3, 3))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(vertLabelPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(horzLabelPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(boardLayeredPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(phasePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(handPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(controlPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 31, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(horzLabelPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(vertLabelPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(boardLayeredPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(phasePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(controlPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(handPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cardButtonClicked

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

    private void endPhaseClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_endPhaseClicked

        switch (gameState.getCurrentPhase()) {
            case 1:
                gameState.fillCurrentPlayerHand();
                updateGame();
                break;
            case 2:
                gameState.discardOption(false);
                updateGame();
                break;
            case 3:
                gameState.tradeOption(false);
                gameState.setOtherCard(-1, -1);
                otherCard = null;
                updateGame();
                break;
            case 4:
                gameState.playAdvance();
                gameState.setSelectedCard(-1);
                selectedCard = null;
                updateGame();
                this.repaint();
                break;

        }

    }//GEN-LAST:event_endPhaseClicked

    private void menuClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_menuClicked

    private void commitClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_commitClicked
        switch (gameState.getCurrentPhase()) {
            case 1:
                gameState.fillCurrentPlayerHand();
                updateGame();
                break;
            case 2:
                if (gameState.discardOption(true) == 0) {
                    gameState.discardCurrentPlayersCard();
                }
                updateGame();
                break;
            case 3:
                if (gameState.tradeOption(true) == 0) {
                    gameState.tradeCurrentPlayerCards();
                }
                updateGame();
                break;
            case 4:
                gameState.playCard();
                selectedCard = null;
                boardShapeLayer.clearSelected();
                updateGame();
                break;
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_commitClicked

    private void rulesClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rulesClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_rulesClicked

    private void boardButton04boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton04boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton04boardButtonClicked

    private void boardButton5boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton5boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton5boardButtonClicked

    private void boardButton6boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton6boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton6boardButtonClicked

    private void boardButton7boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton7boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton7boardButtonClicked

    private void boardButton24boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton24boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton24boardButtonClicked

    private void boardButton25boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton25boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton25boardButtonClicked

    private void boardButton26boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton26boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton26boardButtonClicked

    private void boardButton27boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton27boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton27boardButtonClicked

    private void boardButton28boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton28boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton28boardButtonClicked

    private void boardButton29boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton29boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton29boardButtonClicked

    private void boardButton30boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton30boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton30boardButtonClicked

    private void boardButton31boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton31boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton31boardButtonClicked

    private void boardButton8boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton8boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton8boardButtonClicked

    private void boardButton9boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton9boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton9boardButtonClicked

    private void boardButton10boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton10boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton10boardButtonClicked

    private void boardButton11boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton11boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton11boardButtonClicked

    private void boardButton32boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton32boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton32boardButtonClicked

    private void boardButton33boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton33boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton33boardButtonClicked

    private void boardButton34boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton34boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton34boardButtonClicked

    private void boardButton35boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton35boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton35boardButtonClicked

    private void boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButtonClicked

    private void boardButton37boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton37boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton37boardButtonClicked

    private void boardButton38boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton38boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton38boardButtonClicked

    private void boardButton39boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardButton39boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_boardButton39boardButtonClicked

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

    private void wallButton4wallButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wallButton4wallButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_wallButton4wallButtonClicked

    private void castleButton5boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_castleButton5boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_castleButton5boardButtonClicked

    private void wallButton5wallButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wallButton5wallButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_wallButton5wallButtonClicked

    private void castleButton4boardButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_castleButton4boardButtonClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_castleButton4boardButtonClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {


        startInt=0;

        if(args.length>0){
            if(args.length>1){
                System.out.println("I can't even have this argument right now.");
            }else{
                if(args[0].equals("host")){
                    startInt=1;
                } else if(args[0].equals("client")){
                    startInt=2;
                }
            }

        }
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
            public void run() {
                new CastleFrame(startInt).setVisible(true);

            }
        });

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel boardBottomLayer;
    private javax.swing.JButton boardButton04;
    private javax.swing.JButton boardButton10;
    private javax.swing.JButton boardButton11;
    private javax.swing.JButton boardButton24;
    private javax.swing.JButton boardButton25;
    private javax.swing.JButton boardButton26;
    private javax.swing.JButton boardButton27;
    private javax.swing.JButton boardButton28;
    private javax.swing.JButton boardButton29;
    private javax.swing.JButton boardButton30;
    private javax.swing.JButton boardButton31;
    private javax.swing.JButton boardButton32;
    private javax.swing.JButton boardButton33;
    private javax.swing.JButton boardButton34;
    private javax.swing.JButton boardButton35;
    private javax.swing.JButton boardButton36;
    private javax.swing.JButton boardButton37;
    private javax.swing.JButton boardButton38;
    private javax.swing.JButton boardButton39;
    private javax.swing.JButton boardButton5;
    private javax.swing.JButton boardButton6;
    private javax.swing.JButton boardButton7;
    private javax.swing.JButton boardButton8;
    private javax.swing.JButton boardButton9;
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
    private javax.swing.JButton commitButton;
    private javax.swing.JPanel controlPanel;
    private javax.swing.JLabel deckLabel;
    private javax.swing.JLabel deckNameLabel;
    private javax.swing.JLabel discardLabel;
    private javax.swing.JLabel discardNameLabel;
    private javax.swing.JButton endPhaseButton;
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
    private javax.swing.JLabel horzLabel0;
    private javax.swing.JLabel horzLabel1;
    private javax.swing.JLabel horzLabel2;
    private javax.swing.JLabel horzLabel3;
    private javax.swing.JLabel horzLabel4;
    private javax.swing.JLabel horzLabel5;
    private javax.swing.JPanel horzLabelPanel;
    private javax.swing.JButton menuButton;
    private javax.swing.JLabel monsterCountLabel;
    private javax.swing.JLabel monsterLabel;
    private javax.swing.JProgressBar monsterProgBar;
    private javax.swing.JLabel monsterProgLabel;
    private javax.swing.JLabel phaseCurrentLabel1;
    private javax.swing.JLabel phaseCurrentLabel2;
    private javax.swing.JLabel phaseCurrentLabel3;
    private javax.swing.JLabel phaseCurrentLabel4;
    private javax.swing.JLabel phaseLabel1;
    private javax.swing.JLabel phaseLabel2;
    private javax.swing.JLabel phaseLabel3;
    private javax.swing.JLabel phaseLabel4;
    private javax.swing.JPanel phasePanel;
    private javax.swing.JLabel phaseTitleLabel;
    private javax.swing.JButton rulesButton;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JLabel vertArcherLabel;
    private javax.swing.JLabel vertCastleLabel;
    private javax.swing.JLabel vertForestLabel;
    private javax.swing.JLabel vertKnightLabel;
    private javax.swing.JPanel vertLabelPanel;
    private javax.swing.JLabel vertSwordLabel;
    private javax.swing.JLabel vertWallLabel;
    private javax.swing.JButton wallButton0;
    private javax.swing.JButton wallButton1;
    private javax.swing.JButton wallButton2;
    private javax.swing.JButton wallButton3;
    private javax.swing.JButton wallButton4;
    private javax.swing.JButton wallButton5;
    // End of variables declaration//GEN-END:variables
    private GameMenu dialog;
    private ItsCurtains endScreen;
    private String[] players;
    private javax.swing.JLabel[] handLabels;
    private javax.swing.JButton[][] cardButtons;
    private javax.swing.JPanel[] handPanels;
    private javax.swing.JLabel[] phaseLabels;
    private javax.swing.JLabel[] currentPlayerLabels;
    private javax.swing.JButton[] wallButtons;
    private javax.swing.JButton[] castleButtons;
    private javax.swing.JLabel[] scoreLabels;

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
    private javax.swing.ImageIcon keep;
    private javax.swing.ImageIcon deadKeep;
    private javax.swing.ImageIcon missingCard; 
    private javax.swing.ImageIcon barbarianCard; 

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
            return new java.awt.Dimension(1020, 390);
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

            int[] monsterSerials = gameState.getMonsterSerialsInPlay();

            for (int serial : monsterSerials) {
                if (serial == -1) {
                    System.out.print("GUI: Caught -1 in monsterSerials");
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

//remove magic numbers
        public final java.awt.Point boardToPoint(int xBoard, int yBoard, int neighbors, MonsterShape shape) {

            int xCoord, yCoord;
            xCoord = (((xBoard * (this.getWidth() / 6)) + MONSTER_BORDER_OFFSET + MONSTER_SPACER)) - 170;
            xCoord += (neighbors * MONSTER_WIDTH + MONSTER_SPACER);
            // if (shape.getNeighbors() <= 5) {
            yCoord = ((-yBoard + 4) * (this.getHeight() / 5)) + MONSTER_BORDER_OFFSET;
            // } else { //rewrite
            //   yCoord = ((-yBoard + 4) * (this.getHeight() / 5)) + MONSTER_BORDER_OFFSET + MONSTER_ROW_SPACER;
            // }
            if (yBoard == 0) {
                yCoord += 20; //Account for walls
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

                    monsterGraphic.fillOval((x + 6) + (6 * i), (y + 32), 5, 5);
                }

                if (selected) {
                    monsterGraphic.drawImage(selectedImage, x, y, observer);
                }
            }
        }
    }

}
