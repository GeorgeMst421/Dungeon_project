package UI;

import Enemy.AbstractEnemy;
import Enums.Direction;
import Game.Controllers.PlayerController;
import Game.GameMap;
import Game.Room;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MapPanel extends JPanel {
    final int width = 256;
    final int height = 256;
    Map<String, BufferedImage> wallImages;
    private Room currentRoom;
    private final PlayerController playerController;
    private GameMap gameMap;

    public MapPanel(PlayerController playerController, GameMap gameMap) {
        setPreferredSize(new Dimension(width, height));
        wallImages = readImages();
        this.playerController = playerController;
        this.gameMap = gameMap;
        currentRoom = playerController.getCurrentRoom();
        setCurrentRoom(currentRoom);
    }
    public void setGameMap(GameMap gameMap){
        this.gameMap = gameMap;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            Direction currentDirection = playerController.getCurrentDirection();
            Map<Room, AbstractEnemy> enemyLocations = new HashMap<>();
            for(int i = 0; i < gameMap.getMapWidth(); i++) {
                for(int j = 0; j < gameMap.getMapHeight(); j++) {
                    Room room = gameMap.getRoomAt(i, j);
                    AbstractEnemy enemy = room.getEnemy();
                    if(enemy != null) {
                        enemyLocations.put(room, enemy);
                    }
                }
            }
            paintMap(currentRoom, currentDirection, enemyLocations, g);
        } catch(IOException ioex) {
            System.out.println("Error drawing first person view");
            ioex.printStackTrace();
        }
    }
    public void updateRoomAndRepaint(Room newRoom) {
        this.currentRoom = newRoom;
        this.repaint();
    }

    void paintMap(Room r, Direction d, Map<Room, AbstractEnemy> enemyLocations, Graphics g) throws IOException {
        BufferedImage b = getImage(r, d);
        Room rn = r.getRoomAt(d);

        if (rn != null) {
            Room rnn = rn.getRoomAt(d);
            if(rnn != null && enemyLocations.get(rnn) != null) {
                paintEnemyFar(b, enemyLocations.get(rnn));
            }
            if(enemyLocations.get(rn) != null) {
                paintEnemyClose(b, enemyLocations.get(rn));
            }
        }


        g.drawImage(b, 0, 0, null);
    }


    // Images for drawing
    Map<String, BufferedImage> readImages() {
        try {
            Map<String, BufferedImage> ret = new HashMap<>();
            ret.put("wall_near",
                    ImageIO.read(new File("images/front_near256x256.png")));
            ret.put("wall_far",
                    ImageIO.read(new File("images/front_near256x256.png")));
            ret.put("left_wall_near",
                    ImageIO.read(new File("images/left_wall_near64x256.png")));
            ret.put("right_wall_near",
                    ImageIO.read(new File("images/right_wall_near64x256.png")));
            ret.put("left_wall_far",
                    ImageIO.read(new File("images/left_wall_far32x128.png")));
            ret.put("right_wall_far",
                    ImageIO.read(new File("images/right_wall_far32x128.png")));
            ret.put("bottom_near",
                    ImageIO.read(new File("images/bottom_near256x64.png")));
            ret.put("bottom_far",
                    ImageIO.read(new File("images/bottom_far128x32.png")));
            ret.put("left_door_near",
                    ImageIO.read(new File("images/left_door_near64x256.png")));
            ret.put("right_door_near",
                    ImageIO.read(new File("images/right_door_near64x256.png")));
            ret.put("left_door_far",
                    ImageIO.read(new File("images/left_door_far32x128.png")));
            ret.put("right_door_far",
                    ImageIO.read(new File("images/right_door_far32x128.png")));
            return ret;
        } catch(IOException ioex) {
            System.out.println("could not read file");
            System.out.println(ioex);
            return null;
        }
    }



    public BufferedImage paintEnemyClose(BufferedImage i, AbstractEnemy e) throws IOException {
        Graphics2D g = (Graphics2D)i.getGraphics();
        g.drawImage(e.getSprite(), 128-64, 256-128, 128, 128, null);
        return i;
    }
    public BufferedImage paintEnemyFar(BufferedImage i, AbstractEnemy e) throws IOException {
        Graphics2D g = (Graphics2D)i.getGraphics();
        g.drawImage(e.getSprite(), 128-32, 256-128, 64, 64, null);
        return i;
    }
    public BufferedImage getImage(Room r, Direction d) {
        BufferedImage b = new BufferedImage(width, height,
                BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = (Graphics2D) b.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        if(r.getRoomAt(d) == null) {
            g.drawImage(wallImages.get("wall_near"), 0, 0, width,height, null);
            return b;
        }
// draw floor of next room as well as left and right
        g.drawImage(wallImages.get("bottom_near"), 0, 191, null);
        Room rN = r.getRoomAt(d);
        if(rN.getRoomAt(d.left()) == null) {
            g.drawImage(wallImages.get("left_wall_near"), 0, 0, null);
        } else {
// draw a door
            g.drawImage(wallImages.get("left_door_near"), 0, 0, null);
        }

        if(rN.getRoomAt(d.right()) == null) {
            g.drawImage(wallImages.get("right_wall_near"), 191, 0, null);
        } else {
// draw a door
            g.drawImage(wallImages.get("right_door_near"), 191, 0, null);
        }
// far room
        Room rNN = rN.getRoomAt(d);
        if(rNN == null) {
            g.drawImage(wallImages.get("wall_far"), 64, 64, null);
            return b;
        }
// draw floor of next next room
        g.drawImage(wallImages.get("bottom_far"), 63, 128+32-1, null);
        if(rNN.getRoomAt(d.left()) == null) {
            g.drawImage(wallImages.get("left_wall_far"), 63, 63, null);
        } else {
// draw a door
            g.drawImage(wallImages.get("left_door_far"), 63, 63, null);
        }
        if(rNN.getRoomAt(d.right()) == null) {
            g.drawImage(wallImages.get("right_wall_far"), 128+32-1, 63, null);
        } else {
// draw a door
            g.drawImage(wallImages.get("right_door_far"), 128+32-1, 63, null);
        }
        if(rNN.getRoomAt(d) == null) {
            g.drawImage(wallImages.get("wall_far"), 95, 95, 64, 64, null);
        }
        return b;
    }

    public void setCurrentRoom(Room room){
        currentRoom = room;
    }

}
