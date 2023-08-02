package Game.Managers;

import Game.Controllers.*;
import Game.GameMap;
import Game.Room;
import Interfaces.EventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class EnemyTurnManager {
    private final List<EventListener> listeners = new ArrayList<>();
    private final PlayerController playerController;
    private final List<EnemyController> enemyControllers;

    public EnemyTurnManager(PlayerController playerController, List<EnemyController> enemyControllers) {
        this.playerController = playerController;
        this.enemyControllers = enemyControllers;
    }


    public void enemyTurn() {
        Room playerRoom = playerController.getCurrentRoom();

        for(EnemyController enemyController: enemyControllers) {
            Optional<Room> optionalPlayerRoom = GameMap.getConnectedNeighbors(enemyController.getRoom()).stream().
                    filter(r -> r == playerRoom).
                    findFirst();
            if(optionalPlayerRoom.isEmpty()){
                enemyController.moveEnemyTowardsPlayer(playerController);
            }
            else{
                enemyController.attackPlayer(playerController);
            }

        }

        for (EventListener listener : listeners) {
            listener.onEvent();
        }

    }

    public void addEventListener(EventListener listener) {
        listeners.add(listener);
    }
}
