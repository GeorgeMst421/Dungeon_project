package Game.Managers;

import Enemy.AbstractEnemy;
import Game.Controllers.PlayerController;
import Game.Controllers.EnemyController;
import Game.GameMap;
import Interfaces.EventListener;

import java.util.ArrayList;
import java.util.List;

public class EnemyTurnManager {
    private final List<EventListener> listeners = new ArrayList<>();
    private final PlayerController playerController;
    private final List<EnemyController> enemyControllers;

    public EnemyTurnManager(PlayerController playerController, List<EnemyController> enemyControllers) {
        this.playerController = playerController;
        this.enemyControllers = enemyControllers;
    }


    public void enemyTurn() {
        if(enemyControllers.isEmpty()) System.out.println("Empty");
        for(EnemyController enemyController: enemyControllers) {
            enemyController.moveEnemyTowardsPlayer(playerController);
        }

        for (EventListener listener : listeners) {
            listener.onEvent();
        }

    }

    public void addEventListener(EventListener listener) {
        listeners.add(listener);
    }
}
