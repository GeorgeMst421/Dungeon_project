package UI;

import Enemy.AbstractEnemy;
import Enums.Direction;
import Game.GameMap;
import Game.Room;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class MiniMapPanel extends JPanel {


    private GameMap map;
    private Room curRoom;
    private Direction d;

    public MiniMapPanel(GameMap map, Room curRoom) {
        this.map = map;
        this.curRoom = map.getStartingRoom();
        this.d = Direction.SOUTH;

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintMiniMap((Graphics2D) g, map, curRoom, d);
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(256, 256);
    }
    public void updateMiniMap(Room newRoom, Direction newDirection) {
        this.curRoom = newRoom;
        this.d = newDirection;
        this.repaint(); // This line causes the JPanel to be redrawn
    }
    public void paintMiniMap(Graphics2D g,GameMap map, Room curRoom, Direction d) {
        final int mapPixelWidth = this.getWidth(); // Pixel width of the minimap
        final int mapPixelHeight = this.getHeight(); // Pixel height of the minimap
        final int roomCountWidth = map.getMapWidth(); // Room count in width
        final int roomCountHeight = map.getMapHeight(); // Room count in height
        final int tileWidth = mapPixelWidth / roomCountWidth;
        final int tileHeight = mapPixelHeight / roomCountHeight;
        Map<Room, AbstractEnemy> enemyPositions = map.getEnemyPositions();

        // Filling the whole component with black color
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, mapPixelWidth, mapPixelHeight);
        int offs = 3;

        Color vTile = new Color(130, 130, 130);
        Color vWall = new Color(200, 120, 120);
        Color dColor = new Color(255, 255, 255);
        for(int col = 0; col < roomCountWidth; col++) {
            for(int row = 0; row < roomCountHeight; row++) {
                Room room = (Room)map.getRoomAt(row, col);
                if(room.isVisited()) {
                    // Paint the visited room gray
                    g.setColor(vTile);
                    g.fillRect(offs + col*tileWidth, offs+row*tileHeight,
                            tileWidth, tileHeight);

                    // Paint the walls red if there is no room at the direction
                    g.setColor(vWall);
                    if(room.getRoomAt(Direction.NORTH) == null) {
                        g.fillRect(offs + col*tileWidth, offs+row*tileHeight,
                                tileWidth, 2);
                    }
                    if(room.getRoomAt(Direction.SOUTH) == null) {
                        g.fillRect(offs + col*tileWidth,
                                offs+row*tileHeight + tileHeight - 2,
                                tileWidth, 2);
                    }
                    if(room.getRoomAt(Direction.EAST) == null) {
                        g.fillRect(offs + col*tileWidth+tileWidth-2,
                                offs+row*tileHeight, 2, tileHeight);
                    }
                    if(room.getRoomAt(Direction.WEST) == null) {
                        g.fillRect(offs + col*tileWidth, offs+row*tileHeight,
                                2, tileHeight);
                    }
                }
                // If there's an enemy in this room, paint a red dot over it
                if (enemyPositions.containsKey(room)) {
                    g.setColor(Color.BLUE);
                    int enemyCenterX = offs + col*tileWidth + tileWidth/2;
                    int enemyCenterY = offs + row*tileHeight + tileHeight/2;
                    g.fillOval(enemyCenterX, enemyCenterY, tileWidth / 4, tileHeight / 4);
                }
                if(room == curRoom) {
                    g.setColor(dColor);
                    int ec = 4;
                    int xcent = 0, ycent = 0;
                    if(d == Direction.NORTH) {
                        xcent = offs + col*tileWidth + tileWidth/2 - ec/2;
                        ycent = offs + row*tileHeight;
                    } else if(d == Direction.SOUTH) {
                        xcent = offs + col*tileWidth + tileWidth/2 - ec/2;
                        ycent = offs + row*tileHeight + tileHeight - ec;
                    } else if(d == Direction.EAST) {
                        xcent = offs + col*tileWidth + tileWidth - ec;
                        ycent = offs + row*tileHeight + tileHeight/2 - ec/2;
                    } else if(d == Direction.WEST) {
                        xcent = offs + col*tileWidth;
                        ycent = offs + row*tileHeight + tileHeight/2 - ec/2;
                    }
                    g.fillArc(xcent, ycent, ec, ec, 0, 180);
                }
            }
        }
    }


    public void setGameMap(GameMap gameMap) {
        this.map = gameMap;
    }
}
