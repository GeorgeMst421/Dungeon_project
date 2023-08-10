package Game.Managers;

import Enums.*;
import Game.Controllers.EnemyController;
import Game.Controllers.PlayerController;
import Game.Game;
import Interfaces.Equippable;
import Interfaces.EventListener;
import Interfaces.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlayerTurnManager{
    private final List<EventListener> listeners = new ArrayList<>();
    private List<Item> itemsToPickUp;
    private int currentItemIndex = 0;
//    private GameState gameState = GameState.NORMAL;
    PlayerController playerController;
    List<EnemyController> enemyControllers;

    public PlayerTurnManager(PlayerController playerController, List<EnemyController> enemyControllers) {
        this.playerController = playerController;
        this.enemyControllers = enemyControllers;
    }

    public boolean playerTurn(Command command) {
        boolean isEnemyTurn=true;
        switch (command) {
            case TURN_LEFT -> {
                playerController.turnLeft();
                isEnemyTurn = false;
            }
            case TURN_RIGHT -> {
                playerController.turnRight();
                isEnemyTurn = false;
            }
            case MOVE -> {
                if (playerController.facingEnemy()) {
                    Game.log("Can't move. Facing Enemy");
                    isEnemyTurn = false;
                } else if (playerController.facingWall()) {
                    Game.log("Can't move inside the wall");
                    isEnemyTurn = false;
                } else {
                    playerController.moveForward();
                }
            }
            case ATTACK -> {
                if (!playerController.facingEnemy()) {
                    Game.log("You are not facing an enemy");
                } else {
//                    EnemyController enemyController = null;
//                    for (EnemyController ec : enemyControllers) {
//                        if (ec.getRoom() == playerController.getFacingRoom())
//                            enemyController = ec;
//                    }
//                    assert enemyController != null;
                    EnemyController enemyController = enemyControllers.stream()
                                    .filter(ec -> ec.getRoom() == playerController.getFacingRoom())
                                    .findFirst()
                                    .get();
                    playerController.attack(enemyController);
                    checkForKill(enemyController);
                }
            }
//                Optional<EnemyController> enemyControllerOptional = enemyControllers.stream()
//                        .filter(eC -> eC.getRoom() == playerController.getFacingRoom())
//                        .findFirst();
//                if (enemyControllerOptional.isEmpty()) {
//                    Game.log("You are not facing an enemy");
//                } else {
//                    EnemyController enemyController = enemyControllerOptional.get();
//                    playerController.attack(enemyController);
//                    checkForKill(enemyController);
//                }
            case SPELL -> {
                if(playerController.facingWall()){
                    Game.log("You are facing a wall");
                    return isEnemyTurn;
                }
                EnemyController enemyController;
                if(playerController.facingEnemy()){
                    enemyController = enemyControllers.stream()
                            .filter(ec -> ec.getRoom() == playerController.getFacingRoom())
                            .findFirst()
                            .get();
                }
                else{
                    Optional<EnemyController> enemyControllerOptional = enemyControllers.stream()
                            .filter(eC -> eC.getRoom() == playerController.getSecondFacingRoom() )
                            .findFirst();
                    if (enemyControllerOptional.isEmpty()) {
                        Game.log("You are not facing an enemy");
                        return isEnemyTurn;
                    } else {
                        enemyController = enemyControllerOptional.get();
                    }
                }
                playerController.spell(enemyController);
                checkForKill(enemyController);
            }
            case REST -> {
                playerController.rest();
                Game.log("Resting");
            }
            case HEALTH_POTION -> {
                playerController.useHealthPot();
            }
            case MANA_POTION -> {
                playerController.useManaPot();
            }
            case PICK_UP -> {
                if(GameManager.gameState != GameState.NORMAL) {
                    Game.log("You can't pick up an item right now.");
                    break;
                }
                itemsToPickUp = playerController.getCurrentRoom().getItemsOnRoom();
                if(itemsToPickUp.isEmpty()) {
                    Game.log("No items to pick up in the room");
                } else {
                    currentItemIndex = 0;
                    GameManager.gameState = GameState.PICKING_UP_ITEM;
                    Game.log("Do you want to pick up " + itemsToPickUp.get(currentItemIndex).toString() + "? (YES/NO)");
                }
                isEnemyTurn = false;
            }
            case YES -> {
                if(GameManager.gameState != GameState.PICKING_UP_ITEM) {
                    Game.log("You can't confirm an action right now.");
                    break;
                }
                Item item = itemsToPickUp.get(currentItemIndex);
                if(item instanceof Equippable) {
//                    playerController.equip((Equippable) item);
//                    playerController.getCurrentRoom().removeItem(item);
                    Game.log("You picked up and equipped " + item.toString());
                } else {
                    Game.log("The item is not equippable.");
                }
                currentItemIndex = (currentItemIndex + 1) % itemsToPickUp.size();
                Game.log("Do you want to pick up " + itemsToPickUp.get(currentItemIndex).toString() + "? (YES/NO)");
                isEnemyTurn = false;
            }
            case NO -> {
                if(GameManager.gameState != GameState.PICKING_UP_ITEM) {
                    Game.log("You can't cancel an action right now.");
                    break;
                }
                currentItemIndex = (currentItemIndex + 1) % itemsToPickUp.size();
                Game.log("Do you want to pick up " + itemsToPickUp.get(currentItemIndex).toString() + "? (YES/NO)");
                isEnemyTurn = false;
            }
            case EXIT -> {
                if(GameManager.gameState != GameState.PICKING_UP_ITEM) {
                    Game.log("You can't exit from current action.");
                    break;
                }
                GameManager.gameState = GameState.NORMAL;
                Game.log("Exiting item pick-up.");
                isEnemyTurn = false;
            }
        }
        for(EventListener listener: listeners){
            listener.onEvent();
        }

        return isEnemyTurn;
    }
    private void checkForKill(EnemyController enemyController){
        if (enemyController.isDead()) {
            int exp = enemyController.giveEXP();
            enemyControllers.remove(enemyController);
            playerController.giveEXP(exp);
        }

    }
    public void addEventListener(EventListener listener) {
        listeners.add(listener);
    }
}
