import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;

public class MapLoader {
    public static Block[][] readMap(String filename) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(filename));
        } catch (IOException e) {
            System.err.println("Could not load map file: " + filename);
            return null;
        }

        Block[][] blocks = new Block[img.getHeight()][img.getWidth()];

        for (int r = 0; r < img.getHeight(); r++) {
            for (int c = 0; c < img.getWidth(); c++) {
                int pixel = img.getRGB(c, r);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = pixel & 0xff;

                Block b = null;
                if (red == 0 && green == 255 && blue == 0) { // Grass
                    blocks[r][c] = new Blocks.Grass(c, r);
                } else if (red == 0 && green == 0 && blue == 255) { // Water
                    blocks[r][c] = new Blocks.Water(c, r);
                } else if (red == 255 && green == 255 && blue == 255) { // Iron
                    blocks[r][c] = new Blocks.Iron(c, r);
                } else if (red == 255 && green == 255 && blue == 0) { // Gold
                    blocks[r][c] = new Blocks.Gold(c, r);
                } else if (red == 170 && green == 100 && blue == 0) { // Wood
                    blocks[r][c] = new Blocks.Wood(c, r);
                } else if (red == 0 && green == 0 && blue == 0) {
                    blocks[r][c] = new Blocks.Stone(c, r);
                }
            }
        }

        return blocks;
    }

}
