package Game;

import Enums.Direction;
import Game.Room;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class GameMap {
    private final int width;
    private final int height;
    public Room[][] map;

     public GameMap(int i, int j){
         map = generateMap(i,j);
         width = 25;
         height = 25;
     }

     public int getMapWidth(){return width;}
     public int getMapHeight(){return height;}
     public Room getRoomAt(int i, int j){return map[i][j];}

    public Set<Room> getNeighbors(Room r){
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
        map[0][0].visit();
        map[0][0].occupy();
        Random rng = new Random();
        var startRoom = map[rng.nextInt(height)][rng.nextInt(width)];
        Set<Room> visited = new HashSet<>();
        LinkedList<Room> stack = new LinkedList<>();
        visited.add(startRoom);
        stack.push(startRoom);
        while(!stack.isEmpty()) {
            var curRoom = stack.pop();
            Set<Room> unvisitedNeighbors = getNeighbors(curRoom)
                    .stream()
                    .filter((x)-> ! visited.contains(x))
                    .collect(Collectors.toSet());
            if(unvisitedNeighbors.size() > 0) {
                stack.push(curRoom);
                var newCurr = randRoom(unvisitedNeighbors);
                curRoom.connectRoom(newCurr);
                visited.add(newCurr);
                stack.push(newCurr);
            }
        }
        System.out.println("TOTAL VISITED ROOMS: " + visited.size());
        return map;
    }

    private Room randRoom(Set<Room> roomSet){
        Random rng = new Random();
        List<Room> roomList = new ArrayList<>(roomSet);
        return roomList.get(rng.nextInt(roomList.size()));
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

    public void printMap() {
        for(int row = 0; row < height; row++) {
            // Print the room row
            for(int col = 0; col < width; col++) {
                System.out.print("X");
                Room room = map[row][col];
                if (col < width - 1 && room.getRoomAt(Direction.EAST) != null) {
                    System.out.print("-");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
            // Print the connection row
            if (row < height - 1) {
                for (int col = 0; col < width; col++) {
                    Room room = map[row][col];
                    if (room.getRoomAt(Direction.SOUTH) != null) {
                        System.out.print("| ");
                    } else {
                        System.out.print("  ");
                    }
                }
                System.out.println();
            }
        }
    }


}
