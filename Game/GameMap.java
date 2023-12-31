package Game;

import Enemy.AbstractEnemy;
import Enums.Direction;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class GameMap {

    private final int width;
    private final int height;
    private Room[][] map;

     public GameMap(int i, int j){
         map = generateMap(i,j);
         width = i;
         height = j;
     }
    public static Set<Room> getConnectedNeighbors(Room r) {
        Set<Room> neighborSet = new HashSet<>();

        if (r.getRoomAt(Direction.NORTH) != null) neighborSet.add(r.getRoomAt(Direction.NORTH));
        if (r.getRoomAt(Direction.SOUTH) != null) neighborSet.add(r.getRoomAt(Direction.SOUTH));
        if (r.getRoomAt(Direction.EAST) != null) neighborSet.add(r.getRoomAt(Direction.EAST));
        if (r.getRoomAt(Direction.WEST) != null) neighborSet.add(r.getRoomAt(Direction.WEST));

        return neighborSet;
    }
    public Room getStartingRoom(){
        return map[0][0];
    }
    public Room getRoomAt(int i, int j){return map[i][j];}
    public int getMapWidth(){return width;}
    public int getMapHeight(){return height;}

    public Map<Room, AbstractEnemy> getEnemyPositions() {
        Map<Room, AbstractEnemy> enemyPositions = new HashMap<>();
        for(int i = 0; i < getMapWidth(); i++) {
            for(int j = 0; j < getMapHeight(); j++) {
                Room room = getRoomAt(i, j);
                AbstractEnemy enemy = room.getEnemy();
                if(enemy != null) {
                    enemyPositions.put(room, enemy);
                }
            }
        }
        return enemyPositions;
    }
    public List<Room> getEmptyRooms(){
        List<Room> emptyRooms = new ArrayList<>();
        for(int i=0; i<getMapWidth(); i++){
            for(int j=0; j<getMapHeight(); j++){
                if( !map[i][j].isOccupied()) emptyRooms.add(map[i][j]);
            }
        }
        return emptyRooms;
    }

    private Set<Room> getNeighbors(Room r){
        int i = r.row;
        int j = r.col;

        Set<Room> neighborSet = new HashSet<>();

        if(i > 0) neighborSet.add(map[i-1][j]);
        if(j > 0) neighborSet.add(map[i][j-1]);

        // Fixed the boundary checks for i and j.
        if(i < map.length - 1) neighborSet.add(map[i+1][j]);
        if(j < map[i].length - 1) neighborSet.add(map[i][j+1]);

        return neighborSet;
    }

    private Room[][] generateMap(int width, int height) {
        map = new Room[height][width];

        for(int col=0; col < width; col++) {
            for(int row = 0; row < height; row++) {
                var r = new Room(row, col);
                map[row][col] = r;
            }
        }
        getStartingRoom().visit();
        getStartingRoom().occupy();
        Random rng = new Random();
        var startRoom = map[rng.nextInt(height)][rng.nextInt(width)];
        Set<Room> visited = new HashSet<>();
        LinkedList<Room> stack = new LinkedList<>();
        visited.add(startRoom);
        stack.push(startRoom);
        while (!stack.isEmpty()) {
            var curRoom = stack.pop();
            Set<Room> unvisitedNeighbors = getNeighbors(curRoom)
                    .stream()
                    .filter((x) -> !visited.contains(x))
                    .collect(Collectors.toSet());
            if (unvisitedNeighbors.size() > 0) {
                stack.push(curRoom);
                var newCurr = randRoom(unvisitedNeighbors);
                curRoom.connectRoom(newCurr);
                visited.add(newCurr);
                stack.push(newCurr);
            }
        }
        return map;
    }

    private Room randRoom(Set<Room> roomSet){
        Random rng = new Random();
        List<Room> roomList = new ArrayList<>(roomSet);
        return roomList.get(rng.nextInt(roomList.size()));
    }

}
