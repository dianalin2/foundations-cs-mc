import java.awt.*;

public abstract class Block {
    public static int TILE_SIZE = 50;

    public abstract void tick();

    public abstract void draw(Graphics2D g2d, double xOffset, double yOffset);

    private boolean isSolid = true;
    private boolean isMinable = false;
    private int x, y;
    private Item item;

    public Block(int x, int y, Item item) {
        this.x = x;
        this.y = y;
        this.item = item;
    }

    public void mine(Player player) {
        if (isMinable()) {
            if (item != null)
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

    public Item getItem() {
        return item;
    }

    protected void setItem(Item item) {
        this.item = item;
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

    protected void fillTile(Graphics2D g2d, double xOffset, double yOffset, Color c) {
        fillTile(g2d, getX(), getY(), xOffset, yOffset, c);
    }

    protected void fillTile(Graphics2D g2d, int x, int y, double xOffset, double yOffset, Color c) {
        g2d.setColor(c);
        g2d.fillRect((x * TILE_SIZE - (int) xOffset), (y * TILE_SIZE - (int) yOffset), TILE_SIZE, TILE_SIZE);
    }
}
