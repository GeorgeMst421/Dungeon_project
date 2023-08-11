package Game.Controllers;

import Enemy.*;
import Enums.*;
import Game.*;
import Interfaces.Consumable;
import Interfaces.Equippable;
import Interfaces.Item;
import Items.Consumables.*;
import character.AbstractChars.*;

import java.util.*;

public class PlayerController {

    private final AbstractPlayer player;
    private Room currentRoom;
    private Direction currentDirection;
    public PlayerController(AbstractPlayer player, Room startingRoom) {
        this.currentRoom = startingRoom;
        this.currentDirection = Direction.SOUTH;
        this.player = player;
    }
    public void attack(EnemyController enemyController) {
        enemyController.takeDamage(player.weaponAttack());
    }
    public void takeDamage(Map<DamageType, Integer> damageTypeIntegerMap){
        int dmg = player.calculateFinalDamage(damageTypeIntegerMap);
        player.takeDamage(dmg);
        Game.log("You have taken "+ dmg + " damage!");
    }
    public void spell(EnemyController enemyController){
        if(player instanceof AbstractWarrior){
            Game.log("You can't cast spells");
            return;
        }
        if(player.getCurrentMP() < 5){
            Game.log("You don't have enough mana!");
            return;
        }
        Game.log("Casting Fireball!");
        Map<DamageType, Integer> damage;
        if(player instanceof AbstractMage){
            damage = ((AbstractMage) player).castSpell();
        }
        else{
            damage = ((AbstractBattleMage) player).castSpell();
        }
        player.reduceMP(5);
        enemyController.takeDamage(damage);
    }
    public Equippable equip(Equippable e){
        Equippable unequiped = null;
        SlotType s = e.getSlotType();
        unequiped = player.equip(s, e);
        return unequiped;
    }
    public void addToInventory(Consumable c){
        player.inventory.add(c);
    }
    public void moveForward() {
        currentRoom.leave();
        currentRoom = currentRoom.getRoomAt(currentDirection);;
        currentRoom.visit();
        currentRoom.occupy();

        showItemsOnRoom();
    }
    public void turnLeft() {
        currentDirection = currentDirection.left();
    }
    public void turnRight() {
        currentDirection = currentDirection.right();
    }

    private void showItemsOnRoom(){
        List<Item> roomItems = currentRoom.getItemsOnRoom();
        if (!roomItems.isEmpty()){
            if (roomItems.size() == 1) Game.log("You stepped on an item");
            else Game.log("You stepped on many Items");

            for (int i = 0; i < roomItems.size(); i++){
                Game.log(i+1 + ") " + roomItems.get(i).toString());
            }
        }
        Game.log("");
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
    public void giveEXP(int exp){
        player.addXP(exp);
    }
    public void rest(){
        player.rest();
    }
    public boolean facingEnemy(){
        if(facingWall()) return false;
        AbstractEnemy enemy = currentRoom.getRoomAt(currentDirection).getEnemy();
        return enemy != null;
    }
    public boolean facingWall(){
        return currentRoom.getRoomAt(currentDirection) == null;
    }
    public Room getSecondFacingRoom(){
        return currentRoom.getRoomAt(currentDirection).getRoomAt(currentDirection);
    }
    public Room getFacingRoom(){
        return currentRoom.getRoomAt(currentDirection);
    }
    public Room getCurrentRoom() {
        return currentRoom;
    }
    public Direction getCurrentDirection() {
        return currentDirection;
    }
    public void setCurrentRoom(Room room){currentRoom = room;}
    public void setCurrentDirection(Direction direction){currentDirection = direction;}


}

