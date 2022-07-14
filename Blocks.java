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

        public void draw(Graphics2D g2d, double xOffset, double yOffset, Block[][] map) {
            drawTile(g2d, TextureAtlas.overworldTextures.getTile(0, 0), xOffset, yOffset);
        }
    }

    public static class Water extends Block {
        private boolean animated = Math.random() < 0.1;
        private int tick = 0;
        private int frame = 5;

        public Water(int x, int y) {
            super(x, y, null);

            setSolid(false);
            setMinable(false);
        }

        public void tick() {
        }

        public void draw(Graphics2D g2d, double xOffset, double yOffset, Block[][] map) {
            if (animated) {
                if (tick % 10 == 0) {
                    frame++;
                    if (frame > 5)
                        frame = 0;
                }
                tick++;
            }

            if (animated)
                drawTile(g2d, TextureAtlas.overworldTextures.getTile(3 + frame % 3, 3 + frame / 3), xOffset, yOffset);
            else
                drawTile(g2d, TextureAtlas.overworldTextures.getTile(3, 7), xOffset, yOffset);
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

        public void draw(Graphics2D g2d, double xOffset, double yOffset, Block[][] map) {
            drawTile(g2d, TextureAtlas.overworldTextures.getTile(0, 0), xOffset, yOffset);
            drawTile(g2d, TextureAtlas.objectTextures.getTile(16, 1), xOffset, yOffset);
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

        public void draw(Graphics2D g2d, double xOffset, double yOffset, Block[][] map) {
            drawTile(g2d, TextureAtlas.overworldTextures.getTile(0, 0), xOffset, yOffset);
            drawTile(g2d, TextureAtlas.objectTextures.getTile(0, 5), xOffset, yOffset);
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

        public void draw(Graphics2D g2d, double xOffset, double yOffset, Block[][] map) {
            drawTile(g2d, TextureAtlas.overworldTextures.getTile(0, 0), xOffset, yOffset);
            drawTile(g2d, TextureAtlas.overworldTextures.getTile(2, 14), xOffset, yOffset);
        }
    }

    public static class Stone extends Block {
        private int texture = Math.random() < 0.5 ? 0 : 1;

        public Stone(int x, int y) {
            super(x, y, Items.Stone.class);

            setSolid(true);
            setMinable(true);
        }

        public void tick() {
        }

        public void draw(Graphics2D g2d, double xOffset, double yOffset, Block[][] map) {
            drawTile(g2d, TextureAtlas.overworldTextures.getTile(0, 0), xOffset, yOffset);
            drawTile(g2d, TextureAtlas.caveTextures.getTile(5 + texture, 4), xOffset, yOffset);
        }
    }

    public static class WoodenPlank extends Block {
        public WoodenPlank(int x, int y) {
            super(x, y, Items.WoodenPlank.class);

            setSolid(true);
            setMinable(true);
        }

        public void tick() {
        }

        public void draw(Graphics2D g2d, double xOffset, double yOffset, Block[][] map) {
            drawTile(g2d, TextureAtlas.innerTextures.getTile(0, 1), xOffset, yOffset);
        }
    }
}
