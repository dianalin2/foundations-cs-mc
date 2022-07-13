import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class SpriteLoader {
    public static TextureAtlas loadTextureAtlas(String filename, int tileWidth, int tileHeight) {

        BufferedImage image = null;

        try {
            image = ImageIO.read(new File(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }

        TextureAtlas textureAtlas = new TextureAtlas(image, tileWidth, tileHeight);
        return textureAtlas;
    }
}
