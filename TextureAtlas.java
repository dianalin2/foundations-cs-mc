import java.awt.image.BufferedImage;

public class TextureAtlas {
    public static final TextureAtlas overworldTextures = SpriteLoader.loadTextureAtlas(
            "images/Overworld.png",
            16, 16);

    public static final TextureAtlas objectTextures = SpriteLoader.loadTextureAtlas(
            "images/objects.png",
            16, 16);

    public static final TextureAtlas caveTextures = SpriteLoader.loadTextureAtlas(
            "images/cave.png",
            16, 16);

    public static final TextureAtlas characterTextures = SpriteLoader.loadTextureAtlas(
            "images/character.png",
            16, 16);

    BufferedImage image;

    BufferedImage[][] textures;

    private int tileWidth;
    private int tileHeight;

    public TextureAtlas(BufferedImage image, int tileWidth, int tileHeight) {
        this.image = image;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;

        textures = new BufferedImage[image.getWidth() / tileWidth][image.getHeight() / tileHeight];
    }

    public BufferedImage getTile(int tileX, int tileY) {
        if (textures[tileX][tileY] == null)
            textures[tileX][tileY] = image.getSubimage(tileX * tileWidth, tileY * tileHeight, tileWidth, tileHeight);
        return textures[tileX][tileY];
    }

    public BufferedImage getTile(int tileX, int tileY, int tileWidth, int tileHeight) {
        return image.getSubimage(tileX * tileWidth, tileY * tileHeight, tileWidth, tileHeight);
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }
}
