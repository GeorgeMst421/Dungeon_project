package Game.Controllers;

import Enemy.AbstractEnemy;
import Game.GameMap;
import Game.Room;

import java.util.*;

public class EnemiesController {
    private List<AbstractEnemy> enemies;
    private GameMap gameMap;
    private final PlayerController playerController;

    public EnemiesController(GameMap gameMap, PlayerController playerController) {
        this.enemies = new ArrayList<>();
        this.gameMap = gameMap;
        this.playerController = playerController;
    }

    public void addEnemy(AbstractEnemy enemy){
        this.enemies.add(enemy);
    }

    public List<AbstractEnemy> getEnemies(){
        return enemies;
    }
    public void moveEnemiesTowardsPlayer() {
        for(AbstractEnemy enemy : enemies) {
            Room start = enemy.getRoom();
            Room goal = playerController.getCurrentRoom();

            List<Room> cameFrom = breadthFirstSearch(start, goal);

            if (cameFrom == null) {
                System.out.println("No path to player found.");
                return;
            }

            // Get the next room in the path towards the player
            Room nextRoom = getNextRoomInPath(start, goal, cameFrom);

            if (nextRoom != null) {
                // Only move the enemy if there is a next room
                enemy.setRoom(nextRoom);
            }
        }
    }

    private List<Room> breadthFirstSearch(Room start, Room goal) {
            class RoomPath {
                public final RoomPath parent;
                public final Room current;
                public RoomPath(Room current, RoomPath parent) {
                    this.current = current;
                    this.parent = parent;
                }
            }
            LinkedList<RoomPath> scanRooms = new LinkedList<>();
            Set<Room> visited = new HashSet<>();
            RoomPath currentRoom = new RoomPath(start, null);
            scanRooms.addFirst(currentRoom);
            visited.add(start);
            while(currentRoom.current != goal) {
                currentRoom = scanRooms.removeLast();
                for(Room n: gameMap.getNeighbors(currentRoom.current)) {
                    if(! visited.contains(n)) {
                        visited.add(n);
                        scanRooms.addFirst(new RoomPath(n, currentRoom));
                    }
                }
            }
            LinkedList<Room> path = new LinkedList<>();
            while(currentRoom != null) {
                path.addFirst(currentRoom.current);
                currentRoom = currentRoom.parent;
            }
            return path;
    }



    private Room getNextRoomInPath(Room start, Room goal, List<Room> cameFrom) {
        // The path starts with the start room and ends with the goal room.
        // Therefore, the next room in the path towards the goal is the second room in the list.
        if(cameFrom.size() > 1) {
            return cameFrom.get(1);
        } else {
            // If the path only contains the start room, then the start and goal are the same,
            // and there is no next room in the path.
            return null;
        }
    }


}
