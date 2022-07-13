import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.awt.event.*;
import javax.sound.sampled.*;
import java.io.File;

public class Main extends JPanel {
   private Player player;
   private Timer timer;
   private BufferedImage img;
   private Graphics2D g2d;
   private Block[][] map;

   private boolean muted = false;
   private Clip bgClip;

   public static final int FRAME_WIDTH = 800;
   public static final int FRAME_HEIGHT = 600;

   public Main() {
      img = new BufferedImage(FRAME_WIDTH, FRAME_HEIGHT, BufferedImage.TYPE_INT_ARGB);
      g2d = (Graphics2D) img.getGraphics();

      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

      setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
      setBackground(Color.BLACK);
      setFocusable(true);
      requestFocus();

      player = new Player(1, 1, this);

      map = MapLoader.readMap("map.png");

      bgClip = playAudio("audio/bg.wav", Clip.LOOP_CONTINUOUSLY);

      // Set volume
      FloatControl gainControl = (FloatControl) bgClip.getControl(FloatControl.Type.MASTER_GAIN);
      gainControl.setValue(-25.0f);

      CraftingRecipe.loadCraftingRecipes();

      timer = new Timer(1000 / 60, new TimerListener());
      timer.start();

      addKeyListener(new VolumeListener());
   }

   public class VolumeListener extends KeyAdapter {
      public void keyPressed(KeyEvent e) {
         if (e.getKeyCode() == KeyEvent.VK_M) {
            muted ^= true;
            if (muted) {
               bgClip.stop();
            } else {
               bgClip.start();
            }
         }
      }
   }

   public static Clip playAudio(String filename, int loop) {
      try {
         File f = new File(filename);
         AudioInputStream ais = AudioSystem.getAudioInputStream(f);

         DataLine.Info lineInfo = new DataLine.Info(Clip.class, ais.getFormat());
         Clip clip = (Clip) AudioSystem.getLine(lineInfo);
         clip.open(ais);

         // Play the Clip
         clip.start();

         clip.loop(loop);

         // Close relevant streams
         ais.close();

         return clip;

      } catch (Exception e) {
         System.out.println("Could not play background music");
      }

      return null;
   }

   public Block[][] getMap() {
      return map;
   }

   public Block getBlockAt(int x, int y) {
      return map[y][x];
   }

   public void removeBlock(int x, int y) {
      map[y][x] = new Blocks.Grass(x, y);
   }

   public void addBlock(int x, int y, Block block) {
      map[y][x] = block;
   }

   private int clamp(int value, int min, int max) {
      return Math.max(min, Math.min(max, value));
   }

   private double clamp(double value, double min, double max) {
      return Math.max(min, Math.min(max, value));
   }

   private static Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
         0, new float[] { 10 }, 0);

   private void drawMap(Graphics2D g2d, DimensionsD renderOffset) {
      g2d.setColor(Color.BLACK);
      g2d.fillRect(0, 0, img.getWidth(), img.getHeight());

      // Calculate seen tiles
      int startX = (int) Math.floor(renderOffset.x / Block.TILE_SIZE) - 1;
      int startY = (int) Math.floor(renderOffset.y / Block.TILE_SIZE) - 1;
      int endX = (int) Math.ceil((renderOffset.x + FRAME_WIDTH) / Block.TILE_SIZE) + 2;
      int endY = (int) Math.ceil((renderOffset.y + FRAME_HEIGHT) / Block.TILE_SIZE) + 3;

      startX = clamp(startX, 0, map[0].length);
      startY = clamp(startY, 0, map.length);
      endX = clamp(endX, 0, map[0].length);
      endY = clamp(endY, 0, map.length);

      for (int i = startY; i < endY; i++) {
         for (int j = startX; j < endX; j++) {
            if (map[i][j] != null)
               map[i][j].draw(g2d, renderOffset.x, renderOffset.y, map);
         }
      }

      if (player.getSelectedBlock() != null) {
         g2d.setStroke(dashed);
         g2d.setColor(Color.WHITE);
         g2d.drawRect(player.getSelectedBlock().getX() * Block.TILE_SIZE - (int) renderOffset.x,
               player.getSelectedBlock().getY() * Block.TILE_SIZE - (int) renderOffset.y, Block.TILE_SIZE,
               Block.TILE_SIZE);
      }
   }

   private class TimerListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         drawMap(g2d, getRenderOffset());

         player.tick();
         player.draw(g2d, getRenderOffset());
         repaint();
      }
   }

   public void paintComponent(Graphics g) {
      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
   }

   public DimensionsD getRenderOffset() {
      return new DimensionsD(
            clamp(player.getX() * Block.TILE_SIZE, FRAME_WIDTH / 2, map[0].length * Block.TILE_SIZE - FRAME_WIDTH),
            clamp(player.getY() * Block.TILE_SIZE, FRAME_HEIGHT / 2, map.length * Block.TILE_SIZE - FRAME_HEIGHT));
   }

   public static class DimensionsD {
      public final double x, y;

      public DimensionsD(double x, double y) {
         this.x = x;
         this.y = y;
      }
   }

   public boolean getMuted() {
      return muted;
   }

   public static void main(String[] args) {
      JFrame frame = new JFrame("2Inventory");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(850, 600);
      frame.setLocationRelativeTo(null);
      frame.setResizable(false);
      frame.add(new Main());
      frame.setVisible(true);
   }
}
