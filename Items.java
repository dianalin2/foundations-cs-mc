import java.awt.*;

public class Items {
    public static class GoldBlock extends Item {

        public GoldBlock() {
            super("Gold Block");
        }

        @Override
        public void use(int x, int y, Player player) {
            player.removeItemFromInventory();
            player.placeBlock(x, y, new Blocks.Gold(x, y));
        }

        @Override
        public void draw(Graphics2D g2d, int x, int y) {
            g2d.setColor(Color.YELLOW.brighter());
            g2d.fillRect(x, y, Player.INVENTORY_SLOT_SIZE, Player.INVENTORY_SLOT_SIZE);
        }
    }

    public static class Stone extends Item {

        public Stone() {
            super("Stone Block");
        }

        @Override
        public void use(int x, int y, Player player) {
            player.removeItemFromInventory();
            player.placeBlock(x, y, new Blocks.Stone(x, y));
        }

        @Override
        public void draw(Graphics2D g2d, int x, int y) {
            g2d.setColor(Color.GRAY);
            g2d.fillRect(x, y, Player.INVENTORY_SLOT_SIZE, Player.INVENTORY_SLOT_SIZE);
        }
    }

    public static class Wood extends Item {

        public Wood() {
            super("Tree");
        }

        @Override
        public void use(int x, int y, Player player) {
            player.removeItemFromInventory();
            player.placeBlock(x, y, new Blocks.Wood(x, y));
        }

        @Override
        public void draw(Graphics2D g2d, int x, int y) {
            g2d.setColor(Color.ORANGE.darker().darker());
            g2d.fillRect(x + Player.INVENTORY_SLOT_SIZE / 3, y + Player.INVENTORY_SLOT_SIZE / 3 * 2,
                    Player.INVENTORY_SLOT_SIZE / 3, Player.INVENTORY_SLOT_SIZE / 3);

            g2d.setColor(Color.GREEN.darker().darker());
            g2d.fillPolygon(new int[] { x + Player.INVENTORY_SLOT_SIZE / 2, x, x + Player.INVENTORY_SLOT_SIZE },
                    new int[] { y, y + Player.INVENTORY_SLOT_SIZE / 3 * 2, y + Player.INVENTORY_SLOT_SIZE / 3 * 2 }, 3);
        }
    }

    public static class IronBlock extends Item {

        public IronBlock() {
            super("Iron Block");
        }

        @Override
        public void use(int x, int y, Player player) {
            player.removeItemFromInventory();
            player.placeBlock(x, y, new Blocks.Iron(x, y));
        }

        @Override
        public void draw(Graphics2D g2d, int x, int y) {
            g2d.setColor(Color.GRAY.brighter());
            g2d.fillRect(x, y, Player.INVENTORY_SLOT_SIZE, Player.INVENTORY_SLOT_SIZE);
        }
    }

    public static class WoodenPlank extends Item {

        public WoodenPlank() {
            super("Wooden Plank");
        }

        @Override
        public void use(int x, int y, Player player) {
            player.removeItemFromInventory();
            // player.placeBlock(x, y, new Blocks.WoodenPlank(x, y));
        }

        @Override
        public void draw(Graphics2D g2d, int x, int y) {
            g2d.setColor(Color.ORANGE.darker().darker());
            g2d.fillRect(x, y, Player.INVENTORY_SLOT_SIZE, Player.INVENTORY_SLOT_SIZE);
        }
    }

    public static class IronSword extends Item {

        public IronSword() {
            super("Iron Sword");
        }

        @Override
        public void use(int x, int y, Player player) {
            System.out.println("You used an Iron Sword");
        }

        @Override
        public void draw(Graphics2D g2d, int x, int y) {
            g2d.setColor(Color.GRAY.brighter());
            g2d.fillRect(x, y, Player.INVENTORY_SLOT_SIZE, Player.INVENTORY_SLOT_SIZE);
        }
    }
}
