import java.awt.*;

public abstract class Item {
    private String name;

    public Item(String name) {
        this.name = name;
    }

    public abstract void use(int x, int y, Player player);

    public abstract void draw(Graphics2D g2d, int x, int y);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
