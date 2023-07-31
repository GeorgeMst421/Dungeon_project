package Game;

import Enemy.AbstractEnemy;
import Enums.Direction;
import Interfaces.Item;
import character.AbstractChars.AbstractPlayer;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private Room N,S,W,E;
    public int row;
    public int col;
    private List<Item> itemsOnRoom;
    private AbstractEnemy enemy;
    private boolean visited = false;
    private boolean finish = false;
    private boolean occupied = false;
    public Room(int row, int col){
        itemsOnRoom = new ArrayList<>();
        this.row=row;
        this.col=col;
        this.enemy=null;
        if( row == 24 && col == 24) finish = true;
    }

    public Room getRoomAt(Direction d){
        switch (d){
            case EAST -> {
                return E;
            }
            case WEST -> {
                return W;
            }
            case NORTH -> {
                return N;
            }
            case SOUTH -> {
                return S;
            }
            default -> {
                return null;
            }
        }
    }

    public void connectRoom(Room newRoom){

        if(row == newRoom.row){ //For safety debugging
            if(newRoom.col > col){
                E = newRoom;
                newRoom.W = this;
            }
            else if(newRoom.col < col){
                W = newRoom;
                newRoom.E = this;
            }
        }
        else if(col == newRoom.col){
            if(newRoom.row > row){
                S = newRoom;
                newRoom.N = this;
            }
            else {
                N = newRoom;
                newRoom.S = this;
            }
        }
        else System.out.println("You can't connect these rooms");
    }

    public AbstractEnemy getEnemy(){return enemy;}
    public void setEnemy(AbstractEnemy enemy){this.enemy = enemy;}
    public boolean isFinish(){return finish;}
    public boolean isVisited(){return visited;}
    public void visit(){
        visited = true;
    }
    public List<Item> getItemsOnRoom(){return itemsOnRoom;}

    public void occupy() {
        occupied = true;
    }
    public void leave(){
        occupied = false;
    }
    public boolean isOccupied() {
        return occupied;
    }
}
