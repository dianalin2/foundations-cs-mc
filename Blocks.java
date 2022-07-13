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

            // if (getX() - 1 >= 0 && map[getY()][getX() - 1] instanceof Water) {
            // g2d.drawImage(TextureAtlas.overworldTextures.getTile(4, 7), getX() *
            // TILE_SIZE - (int) xOffset,
            // getY() * TILE_SIZE - (int) yOffset, TILE_SIZE, TILE_SIZE, null);
            // } else {
            // if (getY() + 1 < map.length && map[getY() + 1][getX()] instanceof Grass) {
            // g2d.drawImage(TextureAtlas.overworldTextures.getTile(4, 6), getX() *
            // TILE_SIZE - (int) xOffset,
            // getY() * TILE_SIZE - (int) yOffset, TILE_SIZE, TILE_SIZE, null);
            // } else {
            g2d.drawImage(TextureAtlas.overworldTextures.getTile(0, 0), getX() *
                    TILE_SIZE - (int) xOffset,
                    getY() * TILE_SIZE - (int) yOffset, TILE_SIZE, TILE_SIZE, null);
            // }
            // }
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
                g2d.drawImage(TextureAtlas.overworldTextures.getTile(3 + frame % 3, 3 + frame / 3), getX() *
                        TILE_SIZE - (int) xOffset,
                        getY() * TILE_SIZE - (int) yOffset, TILE_SIZE, TILE_SIZE, null);
            else
                g2d.drawImage(TextureAtlas.overworldTextures.getTile(3, 7), getX() *
                        TILE_SIZE - (int) xOffset,
                        getY() * TILE_SIZE - (int) yOffset, TILE_SIZE, TILE_SIZE, null);
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
            g2d.drawImage(TextureAtlas.overworldTextures.getTile(0, 0), getX() * TILE_SIZE - (int) xOffset,
                    getY() * TILE_SIZE - (int) yOffset, TILE_SIZE, TILE_SIZE, null);
            g2d.drawImage(TextureAtlas.objectTextures.getTile(16, 1), getX() * TILE_SIZE - (int) xOffset,
                    getY() * TILE_SIZE - (int) yOffset, TILE_SIZE, TILE_SIZE, null);
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
            g2d.drawImage(TextureAtlas.overworldTextures.getTile(0, 0), getX() * TILE_SIZE - (int) xOffset,
                    getY() * TILE_SIZE - (int) yOffset, TILE_SIZE, TILE_SIZE, null);
            g2d.drawImage(TextureAtlas.objectTextures.getTile(0, 5), getX() * TILE_SIZE - (int) xOffset,
                    getY() * TILE_SIZE - (int) yOffset, TILE_SIZE, TILE_SIZE, null);
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
            g2d.drawImage(TextureAtlas.overworldTextures.getTile(0, 0), getX() * TILE_SIZE - (int) xOffset,
                    getY() * TILE_SIZE - (int) yOffset, TILE_SIZE, TILE_SIZE, null);
            g2d.drawImage(TextureAtlas.overworldTextures.getTile(2, 14), getX() * TILE_SIZE - (int) xOffset,
                    getY() * TILE_SIZE - (int) yOffset, TILE_SIZE, TILE_SIZE, null);
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
            g2d.drawImage(TextureAtlas.overworldTextures.getTile(0, 0), getX() * TILE_SIZE - (int) xOffset,
                    getY() * TILE_SIZE - (int) yOffset, TILE_SIZE, TILE_SIZE, null);
            g2d.drawImage(TextureAtlas.caveTextures.getTile(5 + texture, 4), getX() * TILE_SIZE - (int) xOffset,
                    getY() * TILE_SIZE - (int) yOffset, TILE_SIZE, TILE_SIZE, null);
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
            g2d.drawImage(TextureAtlas.innerTextures.getTile(0, 1), getX() * TILE_SIZE - (int) xOffset,
                    getY() * TILE_SIZE - (int) yOffset, TILE_SIZE, TILE_SIZE, null);
        }
    }
}
