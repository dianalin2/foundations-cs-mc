import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Block {
    public static int TILE_SIZE = 50;

    public abstract void tick();

    public abstract void draw(Graphics2D g2d, double xOffset, double yOffset, Block[][] map);

    private boolean isSolid = true;
    private boolean isMinable = false;
    private int x, y;
    private Class<? extends Item> itemClass;

    public Block(int x, int y, Class<? extends Item> itemClass) {
        this.x = x;
        this.y = y;
        this.itemClass = itemClass;
    }

    public void mine(Player player) {
        if (isMinable()) {
            if (itemClass != null)
                player.pickup(this);

            player.removeBlock(x, y);
        }
    }

    public boolean isSolid() {
        return isSolid;
    }

    protected void setSolid(boolean solid) {
        isSolid = solid;
    }

    public boolean isMinable() {
        return isMinable;
    }

    protected void setMinable(boolean minable) {
        isMinable = minable;
    }

    public Class<? extends Item> getItemClass() {
        return itemClass;
    }

    protected void setItemClass(Class<? extends Item> itemClass) {
        this.itemClass = itemClass;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean collides(Player player) {
        return player.getX() + Player.WIDTH >= getX() && player.getX() <= getX() + TILE_SIZE
                && player.getY() + Player.HEIGHT >= getY() && player.getY() <= getY() + TILE_SIZE;
    }

    protected static void drawTile(Graphics2D g2d, BufferedImage tile, int x, int y, double xOffset,
            double yOffset) {
        g2d.drawImage(tile, (int) (x * TILE_SIZE - xOffset + Main.FRAME_WIDTH / 2),
                (int) (y * TILE_SIZE - yOffset + Main.FRAME_HEIGHT / 2),
                TILE_SIZE, TILE_SIZE, null);
    }

    protected void drawTile(Graphics2D g2d, BufferedImage tile, double xOffset, double yOffset) {
        drawTile(g2d, tile, getX(), getY(), xOffset, yOffset);
    }
}
