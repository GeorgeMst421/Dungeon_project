package Game.Managers;

import Enums.Command;
import Game.Controllers.EnemyController;
import Game.Controllers.PlayerController;
import Game.Game;
import Interfaces.EventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlayerTurnManager{
    private final List<EventListener> listeners = new ArrayList<>();

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
                Game.log("Turned Left");
                isEnemyTurn = false;
            }
            case TURN_RIGHT -> {
                playerController.turnRight();
                Game.log("Turned right");
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
                    Game.log("You moved to " + playerController.getCurrentRoom().row + " " + playerController.getCurrentRoom().col);
                }
            }
            case ATTACK -> {
                Optional<EnemyController> enemyControllerOptional = enemyControllers.stream()
                        .filter(eC -> eC.getRoom() == playerController.getFacingRoom())
                        .findFirst();
                if (enemyControllerOptional.isEmpty()) {
                    Game.log("You are not facing an enemy");
                } else {
                    EnemyController enemyController = enemyControllerOptional.get();
                    playerController.attack(enemyController);
                    if (enemyController.isDead()) {
                        int exp = enemyController.giveEXP();
                        enemyControllers.remove(enemyController);
                        playerController.getEXP(exp);
                        Game.log("Enemy died");
                        Game.log("Got " + exp + " exp");
                    }
                }
            }
            case SPELL -> {
                playerController.spell();
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
        }
        for(EventListener listener: listeners){
            listener.onEvent();
        }

        return isEnemyTurn;
    }
    public void addEventListener(EventListener listener) {
        listeners.add(listener);
    }
}
