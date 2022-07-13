import java.awt.*;

public class Blocks {
    public static class Grass extends Block {
        public Grass(int x, int y) {
            super(x, y, null);

            setSolid(false);
            setMinable(false);
        }

        public void tick() {
        }

        public void draw(Graphics2D g2d, double xOffset, double yOffset) {
            fillTile(g2d, xOffset, yOffset, Color.GREEN.darker());
        }
    }

    public static class Water extends Block {
        public Water(int x, int y) {
            super(x, y, null);

            setSolid(false);
            setMinable(false);
        }

        public void tick() {
        }

        public void draw(Graphics2D g2d, double xOffset, double yOffset) {
            fillTile(g2d, xOffset, yOffset, Color.BLUE.darker());
        }
    }

    public static class Iron extends Block {
        public Iron(int x, int y) {
            super(x, y, Items.IronBlock.class);

            setSolid(true);
            setMinable(true);
        }

        public void tick() {
        }

        public void draw(Graphics2D g2d, double xOffset, double yOffset) {
            fillTile(g2d, xOffset, yOffset, Color.GRAY.brighter());
        }
    }

    public static class Gold extends Block {
        public Gold(int x, int y) {
            super(x, y, Items.GoldBlock.class);

            setSolid(true);
            setMinable(true);
        }

        public void tick() {
        }

        public void draw(Graphics2D g2d, double xOffset, double yOffset) {
            fillTile(g2d, xOffset, yOffset, Color.YELLOW.brighter());
        }
    }

    public static class Wood extends Block {
        public Wood(int x, int y) {
            super(x, y, Items.Wood.class);

            setSolid(true);
            setMinable(true);
        }

        public void tick() {
        }

        public void draw(Graphics2D g2d, double xOffset, double yOffset) {
            // This is not elegant, but it works
            fillTile(g2d, getX(), getY() - 2, xOffset, yOffset, Color.GREEN.darker().darker());
            fillTile(g2d, getX() - 1, getY() - 1, xOffset, yOffset, Color.GREEN.darker().darker());
            fillTile(g2d, getX() + 1, getY() - 1, xOffset, yOffset, Color.GREEN.darker().darker());

            fillTile(g2d, getX(), getY(), xOffset, yOffset, Color.ORANGE.darker().darker());
            fillTile(g2d, getX(), getY() - 1, xOffset, yOffset, Color.ORANGE.darker().darker());
        }
    }

    public static class Stone extends Block {
        public Stone(int x, int y) {
            super(x, y, Items.Stone.class);

            setSolid(true);
            setMinable(true);
        }

        public void tick() {
        }

        public void draw(Graphics2D g2d, double xOffset, double yOffset) {
            fillTile(g2d, xOffset, yOffset, Color.GRAY.darker());
        }
    }
}
