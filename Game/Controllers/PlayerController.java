package Game.Controllers;

import Enemy.AbstractEnemy;
import Enums.*;
import Game.Room;
import Items.Consumables.HealthPotion;
import Items.Consumables.ManaPotion;
import character.AbstractChars.*;

import java.util.*;
import java.util.stream.Collectors;

public class PlayerController {
    private final AbstractPlayer player;
    private Room currentRoom;
    private Direction currentDirection;
    public PlayerController(AbstractPlayer player, Room startingRoom, Direction startingDirection) {
        this.currentRoom = startingRoom;
        this.currentDirection = startingDirection;
        this.player = player;
    }
    public void turnLeft() {
        currentDirection = currentDirection.left();
        System.out.println("I turned left");
    }

    public void turnRight() {
        currentDirection = currentDirection.right();
        System.out.println("I turned right");;
    }

    public void moveForward() {
        Room nextRoom = currentRoom.getRoomAt(currentDirection);
        if (nextRoom == null || nextRoom.isOccupied()) {
            System.out.println("Cannot move forward");
        } else {
            currentRoom.leave();
            currentRoom = nextRoom;
            currentRoom.visit();
            currentRoom.occupy();
        }
    }
    public void attack() {
        if(facingWall()) return;

        if(!facingEnemy()) return;

        AbstractEnemy enemy = currentRoom.getRoomAt(currentDirection).getEnemy();

        enemy.takeDamage(player.weaponAttack());

        if(!enemy.isAlive()) {
            player.addXP(enemy.giveEXP());
            Room room = enemy.getRoom();
            enemy.setRoom(null);
            room.setEnemy(null);
        }

    }
    public void spell(){
        if(facingWall()) return;

        AbstractEnemy enemy = currentRoom.getRoomAt(currentDirection).getEnemy(); // check the first room for enemy
        if(enemy == null) {
            enemy = currentRoom.getRoomAt(currentDirection).getRoomAt(currentDirection).getEnemy(); // check 2 rooms ahead for enemy
            if( enemy == null ) return;
        };

        if(player instanceof AbstractMage){
            Map<DamageType, Integer> damage = ((AbstractMage) player).castSpell();
            enemy.takeDamage(damage);
        }
        else if(player instanceof AbstractBattleMage){
            Map<DamageType, Integer> damage = ((AbstractBattleMage) player).castSpell();
            enemy.takeDamage(damage);
        }
        else if(player instanceof AbstractWarrior){
            System.out.println("You can't cast spells");
        }
    }
    public void rest(){
        player.rest();
    }
    public void useHealthPot() {
        List<HealthPotion> healthPotions = player.getConsumableItems(HealthPotion.class);
        if (healthPotions.isEmpty()) {
            System.out.println("No health potions available");
        } else {
            player.useItem(healthPotions.get(0));
        }
    }

    public void useManaPot() {
        List<ManaPotion> manaPotions = player.getConsumableItems(ManaPotion.class);
        if (manaPotions.isEmpty()) {
            System.out.println("No mana potions available");
        } else {
            player.useItem(manaPotions.get(0));
        }
    }

    private boolean facingEnemy(){
        if(facingWall()) return false;
        AbstractEnemy enemy = currentRoom.getRoomAt(currentDirection).getEnemy();
        if(enemy == null){
            System.out.println("There is no enemy");
            return false;
        }
        return true;
    }
    public boolean facingWall(){
        if(currentRoom.getRoomAt(currentDirection) == null){
            System.out.println("You are facing a wall");
            return true;
        }
        return false;
    }


    public Room getCurrentRoom() {
        return currentRoom;
    }
    public Direction getCurrentDirection() {
        return currentDirection;
    }
    public void setCurrentRoom(Room room){currentRoom = room;}

}

