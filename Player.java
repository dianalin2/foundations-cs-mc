import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.*;

public class Player {

    private Item[] inventory = new Item[10];
    private int selectedSlot = 0;

    public void removeItemFromInventory(int index) {
        inventory[index] = null;
    }

    public void removeItemFromInventory() {
        inventory[selectedSlot] = null;
    }

    public boolean pickup(Block block) {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] == null) {
                inventory[i] = block.getItem();
                Main.playAudio("audio/mine.wav", 0);
                return true;
            }
        }

        return false;
    }


    public static final int INVENTORY_SLOT_SIZE = 30;

    private double x, y;
    private Direction direction = Direction.NONE;
    public final static int WIDTH = 50, HEIGHT = 100;

    private Main main;
    private Block selectedBlock;

    private boolean debug = false;
    
    public static enum Direction {
        UP, DOWN, LEFT, RIGHT, NONE
    }

    public Player(double x, double y, Main main) {
        this.x = x;
        this.y = y;
        this.main = main;

        main.addKeyListener(new PlayerKeyListener());
        main.addMouseListener(new PlayerMouseListener());
        main.addMouseWheelListener(new PlayerMouseWheelListener());
        main.addMouseMotionListener(new PlayerMouseMotionListener());
    }

    public void move(Direction direction) {
        switch (direction) {
            case UP:
                y -= 0.1;
                break;
            case DOWN:
                y += 0.1;
                break;
            case LEFT:
                x -= 0.1;
                break;
            case RIGHT:
                x += 0.1;
                break;
            default:
                break;
        }
    }

    public class PlayerMouseWheelListener implements MouseWheelListener {

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            if (e.getWheelRotation() < 0) {
                selectedSlot--;

                if (selectedSlot < 0)
                    selectedSlot = inventory.length - 1;
            } else {
                selectedSlot++;

                if (selectedSlot >= inventory.length)
                    selectedSlot = 0;
            }
        }
    }

    public class PlayerKeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            // Ignore how ugly this switch statement is
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    setDirection(Direction.UP);
                    break;
                case KeyEvent.VK_S:
                    setDirection(Direction.DOWN);
                    break;
                case KeyEvent.VK_A:
                    setDirection(Direction.LEFT);
                    break;
                case KeyEvent.VK_D:
                    setDirection(Direction.RIGHT);
                    break;
                case KeyEvent.VK_Q:
                    debug ^= true;
                    break;
                case KeyEvent.VK_1:
                    selectedSlot = 0;
                    break;
                case KeyEvent.VK_2:
                    selectedSlot = 1;
                    break;
                case KeyEvent.VK_3:
                    selectedSlot = 2;
                    break;
                case KeyEvent.VK_4:
                    selectedSlot = 3;
                    break;
                case KeyEvent.VK_5:
                    selectedSlot = 4;
                    break;
                case KeyEvent.VK_6:
                    selectedSlot = 5;
                    break;
                case KeyEvent.VK_7:
                    selectedSlot = 6;
                    break;
                case KeyEvent.VK_8:
                    selectedSlot = 7;
                    break;
                case KeyEvent.VK_9:
                    selectedSlot = 8;
                    break;
                case KeyEvent.VK_0:
                    selectedSlot = 9;
                    break;
            }
        }

        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    if (getDirection() == Direction.UP)
                        setDirection(Direction.NONE);
                    break;
                case KeyEvent.VK_S:
                    if (getDirection() == Direction.DOWN)
                        setDirection(Direction.NONE);
                    break;
                case KeyEvent.VK_A:
                    if (getDirection() == Direction.LEFT)
                        setDirection(Direction.NONE);
                    break;
                case KeyEvent.VK_D:
                    if (getDirection() == Direction.RIGHT)
                        setDirection(Direction.NONE);
                    break;
            }
        }
    }

    private int lastAbsoluteX, lastAbsoluteY;

    private void setSelectedBlock(int absX, int absY) {
        lastAbsoluteX = absX;
        lastAbsoluteY = absY;
        int xTile = (int) ((absX + main.getRenderOffset().x) / Block.TILE_SIZE);
        int yTile = (int) Math.round((absY + main.getRenderOffset().y) / Block.TILE_SIZE);
        if (xTile >= 0 && xTile < main.getMap()[0].length && yTile >= 0 && yTile < main.getMap().length) {
            selectedBlock = main.getBlockAt(xTile, yTile);
        }
    }

    public class PlayerMouseMotionListener extends MouseMotionAdapter {
        public void mouseMoved(MouseEvent e) {
            setSelectedBlock(e.getX(), e.getY());
        }
    }

    public class PlayerMouseListener extends MouseAdapter {

        public void mousePressed(MouseEvent e) {
            setSelectedBlock(e.getX(), e.getY());
            if (e.getButton() == MouseEvent.BUTTON3) {
                if (inventory[selectedSlot] != null)
                    inventory[selectedSlot].use(selectedBlock.getX(), selectedBlock.getY(), Player.this);
            } else if (e.getButton() == MouseEvent.BUTTON1) {
                selectedBlock.mine(Player.this);
            }
        }
    }

    public void placeBlock(int x, int y, Block block) {
        main.addBlock(x, y, block);
    }

    public void removeBlock(int x, int y) {
        main.removeBlock(x, y);
    }

    public void tick() {
        move(direction);

        if (lastAbsoluteX != -1)
            setSelectedBlock(lastAbsoluteX, lastAbsoluteY);
    }

    public Block getSelectedBlock() {
        return selectedBlock;
    }

    public void draw(Graphics2D g2d, int xOffset, int yOffset) {
        g2d.setColor(Color.BLACK);
        g2d.fillRect(xOffset - WIDTH / 2, yOffset, WIDTH, HEIGHT);

        drawInventory(g2d);

        if (debug)
            drawDebug(g2d);
    }

    private static final Font INVENTORY_FONT = new Font("Monospaced", Font.BOLD, 20);

    public void drawInventory(Graphics2D g2d) {
        int startX = main.getWidth() / 2 - (INVENTORY_SLOT_SIZE + 20) * inventory.length / 2 + 20;
        int startY = main.getHeight() - INVENTORY_SLOT_SIZE - 30;

        g2d.setColor(new Color(0, 0, 0, 0.5f));
        g2d.fillRect(startX - 10, startY - 10, (INVENTORY_SLOT_SIZE + 20) * inventory.length,
                INVENTORY_SLOT_SIZE + 20);

        g2d.fillRect(startX - 10 + selectedSlot * (INVENTORY_SLOT_SIZE + 20), startY - 10,
                INVENTORY_SLOT_SIZE + 20, INVENTORY_SLOT_SIZE + 20);

        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null) {
                inventory[i].draw(g2d, startX + i * (Player.INVENTORY_SLOT_SIZE + 20), startY);
            }
        }

        if (selectedSlot >= 0 && selectedSlot < inventory.length && inventory[selectedSlot] != null) {
            g2d.setColor(Color.BLACK);
            g2d.setFont(INVENTORY_FONT);
            g2d.drawString(inventory[selectedSlot].getName(),
                    startX - 10 + (INVENTORY_SLOT_SIZE + 20) * inventory.length / 2 - 50, startY - 30);
        }
    }

    private static final DecimalFormat DF = new DecimalFormat("#.##");
    private static Font DEBUG_FONT = new Font("Monospaced", Font.PLAIN, 15);

    public void drawDebug(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.setFont(DEBUG_FONT);

        g2d.drawString("X: " + DF.format(x), 10, 20);
        g2d.drawString("Y: " + DF.format(y), 10, 37);
        g2d.drawString("Direction: " + direction, 10, 54);
        g2d.drawString("Selected Block: " + selectedBlock, 10, 71);
        g2d.drawString("Selected Slot: " + selectedSlot, 10, 88);
    }

    // public void drawOtherHUD(Graphics2D g2d, int xOffset, int yOffset) {
    // g2d.setColor(Color.BLACK);
    // g2d.drawString(", x, y);
    // }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }
}
