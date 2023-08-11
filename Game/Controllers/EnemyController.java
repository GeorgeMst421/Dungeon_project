package Game.Controllers;

import Enemy.AbstractEnemy;
import Enums.DamageType;
import Game.*;
import Interfaces.Equippable;
import Interfaces.Item;
import Items.Consumables.HealthPotion;
import Items.Consumables.ManaPotion;
import Items.ItemGenerator;

import java.util.*;

public class EnemyController {
    private final AbstractEnemy enemy;
    private final GameMap gameMap;
    private Room currentRoom;

    public EnemyController(AbstractEnemy enemy, GameMap map, Room currentRoom) {
        this.enemy = enemy;
        this.gameMap = map;
        this.currentRoom = currentRoom;
    }

    public void attackPlayer(PlayerController playerController){
        playerController.takeDamage(enemy.attack());
    }
    public void takeDamage(Map<DamageType, Integer> damageTypeIntegerMap){
        int dmg = enemy.calculateFinalDamage(damageTypeIntegerMap);
        enemy.takeDamage(dmg);
        Game.log("You hit "+ enemy.getName()+ " for " + dmg + " damage!");
    }
    public boolean isDead(){
        if(enemy.isDead()){
            Item drop = generateDropItem();
            Game.log(enemy.getName() + " died. Got "+ enemy.giveEXP()+ " exp");
            if(drop != null) {
                currentRoom.getItemsOnRoom().add(drop);
                Game.log(enemy.getName() + " dropped " + drop.toString());
            }
            Game.log("");
            currentRoom.setEnemy(null);
            currentRoom.leave();
            currentRoom = null;
            return true;
        }
        return false;
    }
    private Item generateDropItem(){
        Random rng = new Random();
        int n = rng.nextInt(0,101);
        Item drop = null;
        if (n > 70) drop = ItemGenerator.randomEquippable();
        else if (n > 50) drop = new HealthPotion();
        else if (n > 40) drop = new ManaPotion();
        return drop;
    }

    public void moveEnemyTowardsPlayer(PlayerController playerController) {
            Room start = currentRoom;
            Room goal = playerController.getCurrentRoom();

            List<Room> cameFrom = breadthFirstSearch(start, goal);

            // Get the next room in the path towards the player
            Room nextRoom = getNextRoomInPath(start, goal, cameFrom);

            if (nextRoom != null && !nextRoom.isOccupied()) {
                // Only move the enemy if there is a next room and it is not occupied
                currentRoom.leave();
                currentRoom.setEnemy(null);
                currentRoom = nextRoom;
                currentRoom.setEnemy(enemy);
                currentRoom.occupy();
                Game.log(enemy.getName() + " moved to Room["+ currentRoom.row + ", " + currentRoom.col+"]");
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
                for(Room n: GameMap.getConnectedNeighbors(currentRoom.current)) { //TODO IF ERROR CHANGE FROM STATIC
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
    public int giveEXP(){
        return enemy.giveEXP();
    }
    public Room getRoom(){return currentRoom;}

}
