import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public class Player {

    private Item[][] craftingGrid = new Item[CraftingRecipe.RECIPE_SIZE][CraftingRecipe.RECIPE_SIZE];
    private boolean isCrafting = false;

    public static final int INVENTORY_SIZE = 10;

    private Inventory inventory = new Inventory(INVENTORY_SIZE);
    private int selectedSlot = 0;

    public void removeItemFromInventory(int index) {
        inventory.removeItemFromInventory(index);
    }

    public void removeItemFromInventory() {
        inventory.removeItemFromInventory(selectedSlot);
    }

    public boolean pickup(Block block) {
        int emptySlot = inventory.getEmptySlot();

        if (emptySlot == -1)
            return false;

        try {
            inventory.setSlot(emptySlot, block.getItemClass().newInstance());
            Main.playAudio("audio/mine.wav", 0);
            return true;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void swapInventoryCraftingGrid(int r, int c, int slot) {
        Item temp = inventory.getSlot(slot);
        inventory.setSlot(slot, craftingGrid[r][c]);
        craftingGrid[r][c] = temp;
    }

    public void swapCraftingGrid(int r1, int c1, int r2, int c2) {
        Item temp = craftingGrid[r1][c1];
        craftingGrid[r1][c1] = craftingGrid[r2][c2];
        craftingGrid[r2][c2] = temp;
    }

    public void closeCraftingGrid() {
        for (int i = 0; i < craftingGrid.length; i++) {
            for (int j = 0; j < craftingGrid[i].length; j++) {
                if (craftingGrid[i][j] != null) {
                    int emptySlot = inventory.getEmptySlot();
                    if (emptySlot != -1) {
                        inventory.setSlot(emptySlot, craftingGrid[i][j]);
                    }
                    craftingGrid[i][j] = null;
                    break;
                }
            }
        }
    }

    public boolean craft(CraftingRecipe recipe) {
        if (recipe.matches(craftingGrid)) {
            int emptySlot = inventory.getEmptySlot();
            if (emptySlot == -1)
                return false;

            inventory.setSlot(emptySlot, recipe.createResult());

            for (int i = 0; i < craftingGrid.length; i++) {
                for (int j = 0; j < craftingGrid[i].length; j++) {
                    craftingGrid[i][j] = null;
                }
            }
            // Main.playAudio("audio/craft.wav", 0);
            return true;
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
                    selectedSlot = inventory.size - 1;
            } else {
                selectedSlot++;

                if (selectedSlot >= inventory.size)
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
                case KeyEvent.VK_C:
                    if (isCrafting) {
                        closeCraftingGrid();
                        isCrafting = false;
                    } else {
                        isCrafting = true;
                    }
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

    private int inventorySlotMouseSelected = -1;
    private Dimension craftingSlotMouseSelected = null;

    public class PlayerMouseListener extends MouseAdapter {

        public void mousePressed(MouseEvent e) {
            if (!isCrafting)
                setSelectedBlock(e.getX(), e.getY());

            if (e.getButton() == MouseEvent.BUTTON3) {
                if (!isCrafting && inventory.getSlot(selectedSlot) != null) {
                    inventory.getSlot(selectedSlot).use(selectedBlock.getX(), selectedBlock.getY(), Player.this);
                }
            } else if (e.getButton() == MouseEvent.BUTTON1) {
                if (isCrafting) {
                    int slot = getSlotFromMouse(e.getX(), e.getY());
                    System.out.println("Slot: " + slot);
                    if (slot != -1) {
                        inventorySlotMouseSelected = slot;
                    } else {
                        Dimension cSlot = getCraftingSlotFromMouse(e.getX(), e.getY());
                        System.out.println("Crafting slot: " + cSlot);
                        if (cSlot != null) {
                            craftingSlotMouseSelected = cSlot;
                        }
                    }
                } else {
                    selectedBlock.mine(Player.this);
                }
            }
        }

        public void mouseReleased(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                if (isCrafting) {
                    int slot = getSlotFromMouse(e.getX(), e.getY());
                    System.out.println("slot: " + slot);
                    if (craftingSlotMouseSelected != null && slot != -1) {
                        swapInventoryCraftingGrid(craftingSlotMouseSelected.height, craftingSlotMouseSelected.width,
                                slot);
                        clearCraftingMoveSelection();
                    } else if (inventorySlotMouseSelected != -1 && slot != -1) {
                        inventory.swapSlots(inventorySlotMouseSelected, slot);
                        clearCraftingMoveSelection();
                    } else {
                        Dimension cSlot = getCraftingSlotFromMouse(e.getX(), e.getY());
                        System.out.println("Crafting slot: " + cSlot);
                        System.out.println("Inventory slot: " + inventorySlotMouseSelected);
                        if (inventorySlotMouseSelected != -1 && cSlot != null) {
                            swapInventoryCraftingGrid(cSlot.height, cSlot.width,
                                    inventorySlotMouseSelected);
                            clearCraftingMoveSelection();
                        } else if (craftingSlotMouseSelected != null && cSlot != null) {
                            swapCraftingGrid(cSlot.height, cSlot.width, craftingSlotMouseSelected.height,
                                    craftingSlotMouseSelected.width);
                            clearCraftingMoveSelection();
                        }
                    }

                    boolean hover = getMouseIsHoveringCraftingResult(e.getX(), e.getY());
                    System.out.println("hover: " + hover);
                    if (hover) {
                        CraftingRecipe recipe = CraftingRecipe.getMatch(craftingGrid);
                        if (recipe != null) {
                            craft(recipe);
                        }
                    }
                }
            }
        }

        public void clearCraftingMoveSelection() {
            inventorySlotMouseSelected = -1;
            craftingSlotMouseSelected = null;
        }
    }

    public Dimension getCraftingSlotFromMouse(int x, int y) {
        int startX = main.getWidth() / 2 - ((INVENTORY_SLOT_SIZE + 20) * 3) / 2 - 10;
        int startY = main.getHeight() / 2 - ((INVENTORY_SLOT_SIZE + 20) * 3) / 2 - 10;

        int slotX = (x - startX) / (INVENTORY_SLOT_SIZE + 20);
        int slotY = (y - startY) / (INVENTORY_SLOT_SIZE + 20);

        if (slotX >= 0 && slotX < craftingGrid[0].length && slotY >= 0 && slotY < craftingGrid.length) {
            return new Dimension(slotX, slotY);
        } else {
            return null;
        }
    }

    public int getSlotFromMouse(int x, int y) {
        int startX = main.getWidth() / 2 - (INVENTORY_SLOT_SIZE + 20) * inventory.size / 2 + 20;
        int startY = main.getHeight() - INVENTORY_SLOT_SIZE - 30;

        int slotX = (x - startX) / (INVENTORY_SLOT_SIZE + 20);
        int slotY = (y - startY) / (INVENTORY_SLOT_SIZE + 20);

        if (slotX >= 0 && slotX < inventory.size && slotY == 0) {
            return slotX;
        } else {
            return -1;
        }
    }

    public void placeBlock(int x, int y, Block block) {
        main.addBlock(x, y, block);
    }

    public void removeBlock(int x, int y) {
        main.removeBlock(x, y);
    }

    public void tick() {
        tick++;

        move(direction);

        if (lastAbsoluteX != -1)
            setSelectedBlock(lastAbsoluteX, lastAbsoluteY);
    }

    public Block getSelectedBlock() {
        return selectedBlock;
    }

    public void draw(Graphics2D g2d, int xOffset, int yOffset) {
        drawPlayer(g2d, xOffset, yOffset);

        drawInventory(g2d);

        if (isCrafting) {
            drawCraftingGrid(g2d);
        }

        if (debug)
            drawDebug(g2d);
    }

    private int tick = 0;

    public void drawPlayer(Graphics2D g2d, int xOffset, int yOffset) {
        int x = xOffset - WIDTH / 2;
        int y = yOffset;

        int tileX = direction == Direction.NONE ? 0 : tick / 8 % 4;
        int tileY;

        switch (direction) {
            case UP:
                tileY = 4;
                break;
            case DOWN:
                tileY = 0;
                break;
            case LEFT:
                tileY = 6;
                break;
            case RIGHT:
                tileY = 2;
                break;
            default:
                tileY = 0;
                break;
        }

        g2d.drawImage(TextureAtlas.characterTextures.getTile(tileX, tileY + 1), x, y, WIDTH, HEIGHT / 2, null);
        g2d.drawImage(TextureAtlas.characterTextures.getTile(tileX, tileY), x, y - HEIGHT / 2, WIDTH, HEIGHT / 2, null);
    }

    private static final Font INVENTORY_FONT = new Font("Monospaced", Font.BOLD, 20);

    private boolean getMouseIsHoveringCraftingResult(int mouseX, int mouseY) {
        int startX = main.getWidth() / 2 - ((INVENTORY_SLOT_SIZE + 20) * 3) / 2;
        int startY = main.getHeight() / 2 - ((INVENTORY_SLOT_SIZE + 20) * 3) / 2;

        return (mouseX >= startX + INVENTORY_SLOT_SIZE + 10 && mouseY >= startY + (INVENTORY_SLOT_SIZE + 20) * 3 + 10 &&
                mouseX <= startX + 2 * INVENTORY_SLOT_SIZE + 30
                && mouseY <= startY + (INVENTORY_SLOT_SIZE + 20) * 4 + 10);
    }

    public void drawCraftingGrid(Graphics2D g2d) {
        int startX = main.getWidth() / 2 - ((INVENTORY_SLOT_SIZE + 20) * 3) / 2;
        int startY = main.getHeight() / 2 - ((INVENTORY_SLOT_SIZE + 20) * 3) / 2;

        g2d.setColor(new Color(0, 0, 0, 0.5f));
        g2d.fillRect(startX - 10, startY - 10, (INVENTORY_SLOT_SIZE + 20) * 3, (INVENTORY_SLOT_SIZE + 20) * 3);

        g2d.fillRect(startX + INVENTORY_SLOT_SIZE + 10, startY + (INVENTORY_SLOT_SIZE + 20) * 3 + 10,
                INVENTORY_SLOT_SIZE + 20, INVENTORY_SLOT_SIZE + 20);

        CraftingRecipe match = CraftingRecipe.getMatch(craftingGrid);
        if (match != null)
            match.drawResult(g2d, startX + INVENTORY_SLOT_SIZE + 20, startY + (INVENTORY_SLOT_SIZE + 20) * 3 + 20);

        for (int r = 0; r < craftingGrid.length; r++) {
            for (int c = 0; c < craftingGrid[0].length; c++) {
                if (craftingGrid[r][c] != null) {
                    craftingGrid[r][c].draw(g2d, startX + c * (INVENTORY_SLOT_SIZE + 20),
                            startY + r * (INVENTORY_SLOT_SIZE + 20));
                }
            }
        }

        if (craftingSlotMouseSelected != null) {
            g2d.setColor(Color.WHITE);
            g2d.drawRect(startX + craftingSlotMouseSelected.width * (INVENTORY_SLOT_SIZE + 20),
                    startY + craftingSlotMouseSelected.height * (INVENTORY_SLOT_SIZE + 20),
                    INVENTORY_SLOT_SIZE, INVENTORY_SLOT_SIZE);
        }
    }

    public void drawInventory(Graphics2D g2d) {
        int startX = main.getWidth() / 2 - (INVENTORY_SLOT_SIZE + 20) * inventory.size / 2 + 20;
        int startY = main.getHeight() - INVENTORY_SLOT_SIZE - 30;

        g2d.setColor(new Color(0, 0, 0, 0.5f));
        g2d.fillRect(startX - 10, startY - 10, (INVENTORY_SLOT_SIZE + 20) * inventory.size,
                INVENTORY_SLOT_SIZE + 20);

        g2d.fillRect(startX - 10 + selectedSlot * (INVENTORY_SLOT_SIZE + 20), startY - 10,
                INVENTORY_SLOT_SIZE + 20, INVENTORY_SLOT_SIZE + 20);

        for (int i = 0; i < inventory.size; i++) {
            if (!inventory.isEmpty(i)) {
                inventory.getSlot(i).draw(g2d, startX + i * (Player.INVENTORY_SLOT_SIZE + 20), startY);
            }
        }

        if (selectedSlot >= 0 && selectedSlot < inventory.size && !inventory.isEmpty(selectedSlot)) {
            g2d.setColor(Color.BLACK);
            g2d.setFont(INVENTORY_FONT);
            g2d.drawString(inventory.getSlot(selectedSlot).getName(),
                    startX - 10 + (INVENTORY_SLOT_SIZE + 20) * inventory.size / 2 - 50, startY - 30);
        }

        if (inventorySlotMouseSelected != -1) {
            g2d.setColor(Color.WHITE);
            g2d.drawRect(startX + inventorySlotMouseSelected * (INVENTORY_SLOT_SIZE + 20), startY,
                    INVENTORY_SLOT_SIZE, INVENTORY_SLOT_SIZE);
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
