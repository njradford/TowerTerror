package castlepanic;

import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author Benjamin
 */
public class ShapePanel extends javax.swing.JPanel implements java.awt.event.MouseListener {

    private final static int MONSTER_BORDER_OFFSET = 4;
    private final static int MONSTER_SPACER = 1;
    private final static int MONSTER_WIDTH = 30;
    private final static int MONSTER_HEIGHT = 30;
    private final static int MONSTER_ROW_SPACER = MONSTER_HEIGHT / 2;
    String[] holder = new String[3];
    GameState gameState = new GameState(holder);

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
            goblinImage = ImageIO.read(this.getClass().getResource("/troll3030.png"));
            orcImage = ImageIO.read(this.getClass().getResource("/troll3030.png"));
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
        for (MonsterShape shape: monstersDrawn) {
            shape.setSelected(false);
        }
        System.out.println("GUI: mouseClicked deselected shapes.");
        int selectedSerial;
        java.awt.Point mousePoint = e.getPoint();
        for (MonsterShape shape: monstersDrawn) {
            if (shape.contains(mousePoint)) {
                shape.setSelected(true);
                gameState.setSelectedMonster(shape.getSerial());
                System.out.println("GUI: mouseClicked shape selected.");
                this.repaint();
                return;
            }
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
        System.out.println(monsterSerials);
        for (int serial : monsterSerials) {
            if (serial == -1) {
                return;
            }
            boolean found = false;
            for (MonsterShape shape : monstersDrawn) {
                found = false;
                if (serial == shape.getSerial()) {
                    if (gameState.getMonsterHP(gameState.getMonsterIndex(serial)) <= 0) {
                        monstersDrawn.remove(shape);
                        found = true;
                    } else {
                        shape.setLocation(boardToPoint(gameState.getMonsterX(gameState.getMonsterIndex(serial)),
                                gameState.getMonsterY(gameState.getMonsterIndex(serial))));
                        found = true;
                    }
                }
            }

            if (found == false) {
                MonsterShape newShape = new MonsterShape(serial);
                newShape.setLocation(boardToPoint((gameState.getMonsterX(gameState.getMonsterIndex(serial))),
                        gameState.getMonsterY(gameState.getMonsterIndex(serial))));
                monstersDrawn.add(newShape);
                System.out.println("GUI ShapePanel: added shape.");
            }

        }
        this.repaint();
    }

//remove magic numbers
    public final java.awt.Point boardToPoint(int xBoard, int yBoard) {
        int population = gameState.getMonstersInSquare(xBoard, yBoard);
        int xCoord, yCoord;

        xCoord = (((xBoard * (this.getWidth() / 6)) + MONSTER_BORDER_OFFSET) + ((MONSTER_WIDTH * population) + MONSTER_SPACER)) - 200;
        if (population <= 5) {
            yCoord = ((-yBoard + 4) * (this.getHeight() / 5)) + MONSTER_BORDER_OFFSET;
        } else { //rewrite
            yCoord = ((-yBoard + 4) * (this.getHeight() / 5)) + MONSTER_BORDER_OFFSET + MONSTER_ROW_SPACER;
        }

        return new java.awt.Point(xCoord, yCoord);
    }

    @Override
    public void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        monsterGraphic = (java.awt.Graphics2D) g;
        for (MonsterShape shape : monstersDrawn) {
            shape.paintMonster(monsterGraphic, this);
        }
        monsterGraphic.dispose();
    }

    public class MonsterShape extends java.awt.Rectangle {

        private final int serial;
        private boolean selected;
        
        MonsterShape(int serial) {
            super(MONSTER_WIDTH, MONSTER_HEIGHT);
            int monsterIndex = gameState.getMonsterIndex(serial);
            super.setLocation(boardToPoint(gameState.getMonsterX(monsterIndex),
                    gameState.getMonsterY(monsterIndex)));
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

            switch (gameState.getMonsterName(gameState.getMonsterIndex(serial))) {
                case "troll":
                    monsterGraphic.drawImage(trollImage, x, y, observer);
                    System.out.println("drew troll");
                    System.out.println("at " + x + " " + y);
                    System.out.println(selected);
                            
                    break;
                case "goblin":
                    monsterGraphic.drawImage(goblinImage, x, y, observer);
                    System.out.println("drew goblin");
                    System.out.println("at " + x + " " + y);
                    System.out.println(selected);
                    break;
                case "orc":
                    monsterGraphic.drawImage(orcImage, x, y, observer);
                    System.out.println("drew orc");
                    System.out.println("at " + x + " " + y);
                    System.out.println(selected);
                    break;
                default:
                    System.err.println("AWW!  Paint monster error - monster type not recognized!");
            }
            monsterGraphic.drawString(String.valueOf(gameState.getMonsterHP(gameState.getMonsterIndex(serial))), x + 12, y + 11); // remove magic numbers
            if (selected) {
                monsterGraphic.drawImage(selectedImage, x, y, observer);
            }
        }
    }
}
